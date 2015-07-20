/**
 * Created by marco on 16/07/15.
 */
"use strict";

var Materials = {
    freeChair: new THREE.MeshLambertMaterial({
        color: 0xdd1122,
        shading: THREE.flatShading
    }),
    selectedChair: new THREE.MeshLambertMaterial({
        color: 0x00ff00,
        shading: THREE.flatShading
    }),
    hoveringChair: new THREE.MeshLambertMaterial({
        color: 0xffff00,
        shading: THREE.flatShading
    })
};

var Cinema3DView = {
    seats: [],
    distanceX: 35.0,
    distanceY: 45.0,
    offsetZ: -20,
    busyStatus: 0,
    freeStatus: 1,
    brokenStatus: 2,
    leftClick: false,
    seatMap: {},

    animate: function () {
        requestAnimationFrame(Cinema3DView.animate);
        Cinema3DView.controls.update();
    },
    render: function () {
        requestAnimationFrame(Cinema3DView.render);
        Cinema3DView.renderer.render(Cinema3DView.scene, Cinema3DView.camera);
    },
    init: function (cont, seatsList, sx, sy) {
        Cinema3DView.container = cont;
        Cinema3DView.sizeX = sx;
        Cinema3DView.sizeY = sy;
        $(Cinema3DView.container).on('touchmove', Cinema3DView.onTouchMove);
        Cinema3DView.container.addEventListener('mousedown', Cinema3DView.onMouseDown, false);
        Cinema3DView.container.addEventListener('mouseup', Cinema3DView.onMouseUp, false);
        //document.addEventListener('mousemove',Cinema3DView.onMouseMove,true);
        Cinema3DView.container.addEventListener('touchstart', Cinema3DView.onTouchStart, false);
        $(Cinema3DView.container).bind('touchend', Cinema3DView.onTouchEnd);

        Cinema3DView.renderer = new THREE.WebGLRenderer({antialias: false});
        Cinema3DView.renderer.setClearColor(0x000000, 1);
        Cinema3DView.renderer.setPixelRatio($(Cinema3DView.container).width() / $(Cinema3DView.container).height());
        Cinema3DView.renderer.setSize($(cont).width(), $(cont).height());

        Cinema3DView.camera = new THREE.PerspectiveCamera(60,$(Cinema3DView.container).width() / $(Cinema3DView.container).height(), 1, 3000);
        Cinema3DView.camera.position.z = 200;
        Cinema3DView.camera.position.y = 300;
        Cinema3DView.camera.position.x = 300;
        Cinema3DView.controls = new THREE.OrbitControls(Cinema3DView.camera, Cinema3DView.renderer.domElement);
        Cinema3DView.controls.damping = 0.2;
        Cinema3DView.controls.maxDistance = 1000;
        Cinema3DView.controls.minDistance = 100;
        Cinema3DView.controls.maxPolarAngle = Math.PI / 2.0;
        /*Cinema3DView.controls.autoRotate=true;
         Cinema3DView.controls.autoRotateSpeed=1;*/


        Cinema3DView.scene = new THREE.Scene();


        // world


        var material = new THREE.MeshLambertMaterial({color: 0xffffff, shading: THREE.flatShading});


        Cinema3DView.raycaster = new THREE.Raycaster();
        Cinema3DView.mouse = new THREE.Vector2();

        Cinema3DView.computeBusySeatMesh();
        Cinema3DView.computeFreeSeatMesh();
        Cinema3DView.computeBrokenSeatMesh();


        // lights

        var light = new THREE.DirectionalLight(0x666666);
        light.position.set(-50, 100, 70);
        Cinema3DView.scene.add(light);

        var light = new THREE.DirectionalLight(0x777777);
        light.position.set(10, 10, 20);
        Cinema3DView.scene.add(light);

        var light = new THREE.DirectionalLight(0x888877);
        light.position.set(50, -150, -170);
        Cinema3DView.scene.add(light);

        light = new THREE.AmbientLight(0x555555);
        Cinema3DView.scene.add(light);

        Cinema3DView.lastLight = new THREE.PointLight(0xffff66);
        Cinema3DView.lastLight.intensity = 0;
        //Cinema3DView.scene.add(Cinema3DView.lastLight)

        // light = new THREE.AmbientLight( 0x222222 );
        // Cinema3DView.scene.add( light );

        Cinema3DView.controls.center = new THREE.Vector3(0, 0, Cinema3DView.sizeY * Cinema3DView.offsetZ);

        seatsList.forEach(function (seatPos) {
            Cinema3DView.placeSeat(seatPos);
        });

        /*for(var j = 0; j < Cinema3DView.sizeY; j++) {
         for (var i = 0; i < Cinema3DView.sizeX; i++) {

         Cinema3DView.placeSeat(i,j,Math.random()>0.3 ? 1: Math.random()>0.4 ? 0 : 2);
         }
         }*/


        var step = new THREE.Mesh(new THREE.CubeGeometry(Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.distanceY, Cinema3DView.distanceY), new THREE.MeshLambertMaterial({color: 0x555555}));
        var stepOffsetZ = -22;
        var stepOffsetY = 5;
        for (var j = 0; j < Cinema3DView.sizeY; j++) {
            var singleStep = step.clone();
            singleStep.position.z = (-Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0 + Cinema3DView.distanceY * j + stepOffsetY;
            singleStep.position.y = stepOffsetZ + j * Cinema3DView.offsetZ;
            Cinema3DView.scene.add(singleStep);
        }

        var floorSize = new THREE.Vector3(Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.distanceY);
        var floor = new THREE.Mesh(new THREE.CubeGeometry(Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.distanceY, Cinema3DView.sizeX * (Cinema3DView.distanceX + 5)), new THREE.MeshLambertMaterial({color: 0x555555}));
        floor.position.y = (-Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0;
        floor.position.z += 680;
        Cinema3DView.scene.add(floor);

        var screenSize = new THREE.Vector2(Cinema3DView.sizeX * Cinema3DView.distanceX * 0.9, Cinema3DView.sizeX * Cinema3DView.distanceX * 0.5);
        var screen = new THREE.Mesh(new THREE.CubeGeometry(screenSize.x, screenSize.y, 0.001), new THREE.MeshLambertMaterial({color: 0xeeeeee}));
        screen.position.y = 0;
        screen.position.z = (Cinema3DView.sizeY + 1) * Cinema3DView.distanceY;
        Cinema3DView.scene.add(screen);


        // Cinema3DView.renderer


        Cinema3DView.container.appendChild(Cinema3DView.renderer.domElement);


        window.addEventListener('resize', Cinema3DView.onWindowResize, false);

        Cinema3DView.animate();
        Cinema3DView.render();

    },
    computeSeat: function (standMaterial, chairMaterial, broken) {
        var seat = new THREE.Mesh();

        var rotation = 0.5;

        var footSize = new THREE.Vector3(2, 22, 2);
        var legSize = new THREE.Vector3(2, 2, 22);
        var footDistance = 18;


        var singleStand = new THREE.Object3D();
        var stand = new THREE.Object3D();
        var foot = new THREE.Mesh(new THREE.CubeGeometry(footSize.x, footSize.y, footSize.z), standMaterial);
        foot.position.z += footSize.x / 2.0;
        foot.name = "metal";

        var leg = new THREE.Mesh(new THREE.CubeGeometry(legSize.x, legSize.y, legSize.z), standMaterial);

        leg.position.z += 12;
        leg.name = "metal";
        if (broken) {
            foot.rotateX(Math.random() * rotation);
            foot.rotateY(Math.random() * rotation);
            foot.rotateZ(Math.random() * rotation);

            leg.rotateX(Math.random() * rotation);
            leg.rotateY(Math.random() * rotation);
            leg.rotateZ(Math.random() * rotation);
        }


        singleStand.add(foot);
        singleStand.add(leg);

        var secondStand = singleStand.clone();

        secondStand.position.x -= footDistance;

        stand.add(singleStand);
        stand.add(secondStand);

        var chair = new THREE.Object3D();
        var pillowSize = new THREE.Vector3(22, 33, 10);
        var backSize = new THREE.Vector3(22, 10, 42);

        var pillow = new THREE.Mesh(new THREE.CubeGeometry(pillowSize.x, pillowSize.y, pillowSize.z), chairMaterial);
        var back = new THREE.Mesh(new THREE.CubeGeometry(backSize.x, backSize.y, backSize.z), chairMaterial);
        pillow.position.z += 20;
        pillow.name = "cloth";
        back.position.z += 20 + backSize.z / 2.0;
        back.name = "cloth";
        var armSize = new THREE.Vector3(7, 20, 10);
        var arm = new THREE.Mesh(new THREE.CubeGeometry(armSize.x, armSize.y, armSize.z), chairMaterial);
        arm.position.z += 35;
        arm.position.x -= pillowSize.x / 2.0;
        arm.name = "cloth";

        var otherArm = new THREE.Mesh(new THREE.CubeGeometry(armSize.x, armSize.y, armSize.z), chairMaterial);
        otherArm.position.x += 11;
        otherArm.position.z += 35;

        if (broken) {
            back.rotateX(Math.random() * rotation);
            back.rotateY(Math.random() * rotation);
            back.rotateZ(Math.random() * rotation);

            pillow.rotateX(Math.random() * rotation);
            pillow.rotateY(Math.random() * rotation);
            pillow.rotateZ(Math.random() * rotation);

            arm.rotateX(Math.random() * rotation);
            arm.rotateY(Math.random() * rotation);
            arm.rotateZ(Math.random() * rotation);

            otherArm.rotateX(Math.random() * rotation);
            otherArm.rotateY(Math.random() * rotation);
            otherArm.rotateZ(Math.random() * rotation);
        }

        back.position.y += (pillowSize.y - backSize.y) / 2.0;
        chair.add(pillow);

        chair.add(back);
        chair.add(arm);
        chair.add(otherArm);

        chair.name = "chair";

        chair.position.x -= pillowSize.x / 2.0 - 2;

        seat.add(chair);
        seat.add(stand);

        return seat;
    },
    computeFreeSeatMesh: function () {
        var footColor = 0x777777;

        var standMaterial = new THREE.MeshLambertMaterial({color: footColor});
        var chairMaterial = Materials.freeChair;

        Cinema3DView.freeSeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, false);
        Cinema3DView.freeSeatMesh.name = "seat";
    },
    computeBusySeatMesh: function () {
        var footColor = 0x666666;
        var chairColor = 0xcc1515;

        var standMaterial = new THREE.MeshLambertMaterial({color: footColor, transparent: true, opacity: 0.4});
        var chairMaterial = new THREE.MeshLambertMaterial({color: chairColor, transparent: true, opacity: 0.4});

        Cinema3DView.busySeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, false);
    },
    computeBrokenSeatMesh: function () {
        var footColor = 0x666666;
        var chairColor = 0xcc9900;

        var standMaterial = new THREE.MeshLambertMaterial({color: footColor, transparent: true, opacity: 0.9});
        var chairMaterial = new THREE.MeshLambertMaterial({color: chairColor, transparent: true, opacity: 0.9});

        Cinema3DView.brokenSeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, true);
    },
    resetCamera: function () {
        var min = Cinema3DView.sizeX / $(Cinema3DView.container).width() > Cinema3DView.sizeY / $(Cinema3DView.container).clientHeight ? Cinema3DView.sizeX : Cinema3DView.sizeY;
        Cinema3DView.controls.reset();

        Cinema3DView.camera.position.x = 0;
        Cinema3DView.camera.position.y = 82 * min / 2.0;
        Cinema3DView.camera.position.z = -9 * min / 2.0;

    },
    placeSeat: function (seatPos) {
        var singleChair;
        var position = new THREE.Vector3(-10 + (Cinema3DView.sizeX * Cinema3DView.distanceX) / 2.0 - Cinema3DView.distanceX * seatPos.column, -Cinema3DView.offsetZ * seatPos.row + Cinema3DView.offsetZ * Cinema3DView.sizeY, (Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0 - Cinema3DView.distanceY * seatPos.row);

        if (seatPos.status == Cinema3DView.freeStatus) {
            singleChair = Cinema3DView.freeSeatMesh.clone();
            Cinema3DView.seatMap[singleChair.uuid] = {status: 0, x: seatPos.column, y: seatPos.row};
            singleChair.position.set(position.x, position.y, position.z);
            singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
            singleChair.children.forEach(function (i) {
                if (i.name == "chair") {
                    i.children.forEach(function (j) {
                        Cinema3DView.seats.push(j);
                    });
                }
            });
        }
        else if (seatPos.status == Cinema3DView.busyStatus) {
            singleChair = Cinema3DView.busySeatMesh.clone();
            singleChair.position.set(position.x, position.y, position.z);
            singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
        }
        else if (seatPos.status == Cinema3DView.brokenStatus) {
            singleChair = Cinema3DView.brokenSeatMesh.clone();
            singleChair.position.set(position.x, position.y, position.z);
            singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
        }
        else {
            return;
        }

        Cinema3DView.scene.add(singleChair);
    },
    onWindowResize: function () {
        Cinema3DView.camera.aspect = $(Cinema3DView.container).width() / $(Cinema3DView.container).height();
        Cinema3DView.renderer.setSize($(Cinema3DView.container).width(), $(Cinema3DView.container).height());

        Cinema3DView.camera.updateProjectionMatrix();

        //Cinema3DView.render();
    },
    onTouchStart: function (event) {
        event.preventDefault();

        if (event.targetTouches.length > 1) {
            Cinema3DView.touchStatus = -event.targetTouches.length;
        }
        else if (event.targetTouches.length == 1 && Cinema3DView.touchStatus == 0) {
            Cinema3DView.mouse.x = +( (event.targetTouches[0].pageX - Cinema3DView.container.offsetLeft ) / window.innerWidth) * 2 - 1;
            Cinema3DView.mouse.y = -( (event.targetTouches[0].pageY - Cinema3DView.container.offsetTop ) / window.innerHeight) * 2 + 1;
            Cinema3DView.touchStatus = 1;
        }
    },
    onTouchMove: function (event) {
        //alert("ciao");
        if (Cinema3DView.touchStatus == 1) {
            Cinema3DView.touchStatus = 2;
        }

    },
    onTouchEnd: function (event) {
        event.preventDefault();
        console.log(event);

        if (Cinema3DView.touchStatus == 1) {

            Cinema3DView.raycaster.setFromCamera(Cinema3DView.mouse, Cinema3DView.camera);
            var intersects = Cinema3DView.raycaster.intersectObjects(Cinema3DView.seats);
            //console.log(Cinema3DView.seats);
            //console.log(intersects[0].object);

            if (intersects.length > 0) {
                Cinema3DView.selectChair(intersects[0].object.parent);
            }
            Cinema3DView.touchStatus = 0;
        } else if (Cinema3DView.touchStatus == 2) {
            Cinema3DView.touchStatus = 0;
        } else if (Cinema3DView.touchStatus < 0) {
            Cinema3DView.touchStatus++;
        }

    },
    onMouseDown: function (event) {
        console.log(Cinema3DView.camera.position);
        console.log(Cinema3DView.camera.rotation);
        event.preventDefault();
        if (Cinema3DView.leftClick == false) {
            if (event.button == 0) {
                Cinema3DView.clickStart(event);
            }
        }
    },
    clickStart: function (event) {
        Cinema3DView.leftClick = true;
        //console.log(Cinema3DView.seats);
        Cinema3DView.mouse.x = ( ( event.pageX - Cinema3DView.container.offsetLeft ) / Cinema3DView.container.clientWidth ) * 2 - 1;
        Cinema3DView.mouse.y = -( ( event.pageY - Cinema3DView.container.offsetTop ) / Cinema3DView.container.clientHeight ) * 2 + 1;
    },
    onMouseMove: function (event) {
        var pos = new THREE.Vector2();
        pos.x = ( ( event.pageX - Cinema3DView.container.offsetLeft ) / Cinema3DView.container.clientWidth ) * 2 - 1;
        pos.y = -( ( event.pageY - Cinema3DView.container.offsetTop ) / Cinema3DView.container.clientHeight ) * 2 + 1;
        var minDistance = Cinema3DView.container.clientHeight / 10000.0;
        var distance = Math.sqrt(Math.pow(Cinema3DView.mouse.x - pos.x, 2) + Math.pow(Cinema3DView.mouse.y - pos.y, 2));
        if (distance > minDistance && Cinema3DView.leftClick) {
            Cinema3DView.leftClick = false;
        }
        else if (!Cinema3DView.leftClick) {
            var intersects = Cinema3DView.raycaster.intersectObjects(Cinema3DView.seats);
            console.log(intersects);
            if (intersects.length > 0) {
                Cinema3DView.hoverChair(intersects[0].object.parent);
            }
        }
    },
    onMouseUp: function (event) {
        if (Cinema3DView.leftClick) {
            var pos = new THREE.Vector2();
            pos.x = ( ( event.pageX - Cinema3DView.container.offsetLeft ) / Cinema3DView.container.clientWidth ) * 2 - 1;
            pos.y = -( ( event.pageY - Cinema3DView.container.offsetTop ) / Cinema3DView.container.clientHeight ) * 2 + 1;
            var minDistance = Cinema3DView.container.clientHeight / 3000.0;
            var distance = Math.sqrt(Math.pow(Cinema3DView.mouse.x - pos.x, 2) + Math.pow(Cinema3DView.mouse.y - pos.y, 2));
            if (distance < minDistance) {

                Cinema3DView.raycaster.setFromCamera(pos, Cinema3DView.camera);

                var intersects = Cinema3DView.raycaster.intersectObjects(Cinema3DView.seats);

                if (intersects.length > 0) {
                    Cinema3DView.selectChair(intersects[0].object.parent);
                }

            }
        }

        Cinema3DView.leftClick = false;
    },
    selectChair: function (clicked) {

        if (clicked.name == "chair") {
            console.log(Cinema3DView.seatMap[clicked.parent.uuid]);
            if (Cinema3DView.seatMap[clicked.parent.uuid].status == 0) {
                Cinema3DView.lastLight.position.x = clicked.parent.position.x;
                Cinema3DView.lastLight.position.y = clicked.parent.position.y;
                Cinema3DView.lastLight.position.z = clicked.parent.position.z;
                Cinema3DView.lastLight.intensity = 0.1;
                Cinema3DView.seatMap[clicked.parent.uuid].status = 1;
                $(Cinema3DView.container).trigger("onSeatAdd",[Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                clicked.children.forEach(function (obj) {
                    obj.material = Materials.selectedChair;
                });
            }
            else {
                Cinema3DView.seatMap[clicked.parent.uuid].status = 0;
                Cinema3DView.lastLight.intensity = 0;
                $(Cinema3DView.container).trigger("onRemoveAdd",[Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                clicked.children.forEach(function (obj) {
                    obj.material = Materials.freeChair;
                });
            }
        }

    },
    hoverChair: function (hover) {
        if (Cinema3DView.hoveredSeat == null) {
            Cinema3DView.hoveredSeat = hover;
            if (hover.name == "chair") {
                if (Cinema3DView.seatMap[clicked.parent.uuid].status == 0) {
                    Cinema3DView.seatMap[clicked.parent.uuid].status = 1;
                    clicked.children.forEach(function (obj) {
                        obj.material = Materials.selectedChair;
                    });
                }
                else {
                    Cinema3DView.seatMap[clicked.parent.uuid].status = 0;
                    clicked.children.forEach(function (obj) {
                        obj.material = Materials.freeChair;
                    });
                }
            }
        }
        else if (Cinema3DView.hoveredSeat.uuid != hover.uuid) {
            Cinema3DView.hoveredSeat = hover;
            if (hover.name == "chair") {
                if (Cinema3DView.seatMap[clicked.parent.uuid].status == 0) {
                    Cinema3DView.seatMap[clicked.parent.uuid].status = 1;
                    clicked.children.forEach(function (obj) {
                        obj.material = Materials.selectedChair;
                    });
                }
                else {
                    Cinema3DView.lastLight.intensity = 0;
                    Cinema3DView.seatMap[clicked.parent.uuid].status = 0;
                    clicked.children.forEach(function (obj) {
                        obj.material = Materials.freeChair;
                    });
                }
            }
        }
    }

};


/*function computeStandGeometry()
 {
 var standGeometry = new THREE.Geometry();

 var footDistance = 18;

 var footGeometry = new THREE.BoxGeometry(2,22,2);
 var legGeometry = new THREE.BoxGeometry(2,2,22);
 var singleStandGeometry = new THREE.Geometry();
 singleStandGeometry.merge(footGeometry);
 singleStandGeometry.merge(legGeometry);
 var otherStandGeometry = singleStandGeometry.clone();

 otherStandGeometry.x+= footDistance;
 standGeometry.merge(singleStandGeometry);
 standGeometry.merge(otherStandGeometry);

 return standGeometry;
 }

 function computeChairGeometry()
 {
 var chairGeometry = new THREE.Geometry();

 var pillowSize = new THREE.Vector3(22,33,10);
 var backSize = new THREE.Vector3(22,10,42);
 var pillowGeometry = new THREE.BoxGeometry(pillowSize);
 var backGeometry = new THREE.BoxGeometry(backSize);

 pillowGeometry.z+=20;
 backGeometry.z+=20+backSize.z/2.0;

 var armGeometry = new THREE.BoxGeometry(6,25,7);
 armGeometry.z+=35;
 armGeometry.x-=pillowSize.x/2.0;

 var otherArmGeometry = armGeometry.clone();
 otherArmGeometry.x+=11;
 otherArmGeometry.z+=35;

 backGeometry.y+=(pillowSize.y-backSize.y)/2.0;

 chairGeometry.merge(backGeometry);
 chairGeometry.merge(pillowGeometry);
 chairGeometry.merge(armGeometry);
 chairGeometry.merge(otherArmGeometry);

 return chairGeometry;
 }*/