/**
 * Created by marco on 16/07/15.
 */
"use strict";


var Shaders = {
    vignetteShader: new THREE.ShaderMaterial({

        uniforms: {

            "tDiffuse": {type: "t", value: null},
            "v": {type: "f", value: 1.0 / 512.0}

        },

        vertexShader: [

            "varying vec2 vUv;",

            "void main() {",

            "vUv = uv;",
            "gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );",

            "}"

        ].join("\n"),

        fragmentShader: [

            "uniform sampler2D tDiffuse;",
            "uniform float v;",

            "varying vec2 vUv;",

            "void main() {",

            "vec4 sum = vec4( 0.0 );",

            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y - 4.0 * v ) ) * 0.051;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y - 3.0 * v ) ) * 0.0918;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y - 2.0 * v ) ) * 0.12245;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y - 1.0 * v ) ) * 0.1531;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y ) ) * 0.1633;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y + 1.0 * v ) ) * 0.1531;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y + 2.0 * v ) ) * 0.12245;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y + 3.0 * v ) ) * 0.0918;",
            "sum += texture2D( tDiffuse, vec2( vUv.x, vUv.y + 4.0 * v ) ) * 0.051;",

            "gl_FragColor = sum;",

            "}"

        ].join("\n")
    })

};

var Materials = {
    freeChair: new THREE.MeshLambertMaterial({
        color: 0xffffcc,
        shading: THREE.flatShading
    }),
    selectedChair: new THREE.MeshLambertMaterial({
        color: 0x009c06,
        shading: THREE.flatShading
    }),
    hoveringChair: new THREE.MeshLambertMaterial({
        color: 0xaaff88,
        shading: THREE.flatShading
    }),
    hoveringChairSelected: new THREE.MeshLambertMaterial({
        color: 0xffaa88,
        shading: THREE.flatShading
    }),
    busySeatMaterial: new THREE.MeshLambertMaterial({
        color: 0xffffcc,
        transparent: true,
        opacity: 0.4,
        shading: THREE.flatShading
    })
};

var Cinema3DView = {
    seats: [],
    distanceX: 35.0,
    distanceY: 45.0,
    offsetZ: -20,
    reservedStatus: "RESERVED",
    freeStatus: "FREE",
    brokenStatus: "BROKEN",
    okStatus: "ok",
    brokenSeatStatus: "broken",
    leftClick: false,
    seatMap: {},
    previousState: {},
    locked: false,
    cameraSpeed: {},
    touchStatus: 0,
    maxCount: 0,
    alreadyBinded: false,

    empty: function (elem) {
        while (elem.lastChild) elem.removeChild(elem.lastChild);
    },

    animate: function () {
        requestAnimationFrame(Cinema3DView.animate);
        Cinema3DView.controls.update();
    },
    render: function () {
        requestAnimationFrame(Cinema3DView.render);
        Cinema3DView.renderer.render(Cinema3DView.scene, Cinema3DView.camera);
    },
    init: function (cont, seatsList, sx, sy, readOnly) {

        Cinema3DView.container = cont;
        if (Cinema3DView.scene != null) {
            Cinema3DView.container.innerHTML = "";
            cancelAnimationFrame(this.id);// Stop the animation
            Cinema3DView.empty(Cinema3DView.scene);
            Cinema3DView.scene = null;
            Cinema3DView.camera = null;
            Cinema3DView.controls = null;
        }


        Cinema3DView.sizeX = sx;
        Cinema3DView.sizeY = sy;
        if (!readOnly && !Cinema3DView.alreadyBinded) {
            Cinema3DView.alreadyBinded = true;
            $(Cinema3DView.container).on('touchmove', Cinema3DView.onTouchMove);
            $(Cinema3DView.container).on('mousedown', Cinema3DView.onMouseDown);
            $(Cinema3DView.container).on('mouseup', Cinema3DView.onMouseUp);
            $(Cinema3DView.container).on('mousemove', Cinema3DView.onMouseMove);
            $(Cinema3DView.container).on('touchstart', Cinema3DView.onTouchStart);
            $(Cinema3DView.container).on('touchend', Cinema3DView.onTouchEnd);
        }

        Cinema3DView.seats = [];

        Cinema3DView.renderer = new THREE.WebGLRenderer({antialias: false});
        Cinema3DView.renderer.setClearColor(0x000000, 1);
        Cinema3DView.renderer.setPixelRatio($(Cinema3DView.container).width() * 1.0 / $(Cinema3DView.container).height() * 1.0);
        Cinema3DView.renderer.setSize($(cont).width(), $(cont).height());

        Cinema3DView.camera = new THREE.PerspectiveCamera(60, $(Cinema3DView.container).width() / $(Cinema3DView.container).height(), 1, 3000);
        Cinema3DView.camera.position.z = 200;
        Cinema3DView.camera.position.y = 300;
        Cinema3DView.camera.position.x = 300;
        Cinema3DView.controls = new THREE.OrbitControls(Cinema3DView.camera, Cinema3DView.renderer.domElement);
        Cinema3DView.controls.damping = 0.2;
        Cinema3DView.controls.maxDistance = 1000;
        Cinema3DView.controls.minDistance = 100;
        Cinema3DView.controls.maxPolarAngle = Math.PI / 2.0;
        Cinema3DView.controls.zoomSpeed = 3;
        Cinema3DView.controls.keyPanSpeed = 4;
        Cinema3DView.cameraSpeed = Cinema3DView.controls.rotateSpeed;
        /*Cinema3DView.controls.autoRotate=true;
         Cinema3DView.controls.autoRotateSpeed=1;*/


        Cinema3DView.scene = new THREE.Scene();


        Materials.freeChair = new THREE.MeshLambertMaterial({
            color: 0xffffcc,
            shading: THREE.flatShading
        });
        Materials.selectedChair = new THREE.MeshLambertMaterial({
            color: 0x009c06,
            shading: THREE.flatShading
        });
        Materials.hoveringChair = new THREE.MeshLambertMaterial({
            color: 0xaaff88,
            shading: THREE.flatShading
        });
        Materials.hoveringChairSelected = new THREE.MeshLambertMaterial({
            color: 0xffaa88,
            shading: THREE.flatShading
        });
        Materials.busySeatMaterial = new THREE.MeshLambertMaterial({
            color: 0xffffcc,
            transparent: true,
            opacity: 0.4,
            shading: THREE.flatShading
        });


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

        //Cinema3DView.lastLight = new THREE.PointLight(0xffff66,1,1000);
        //Cinema3DView.lastLight.position.set(0,0,0);
        //Cinema3DView.lastLight.intensity = 0;
        //Cinema3DView.scene.add(Cinema3DView.lastLight);

        // light = new THREE.AmbientLight( 0x222222 );
        // Cinema3DView.scene.add( light );

        Cinema3DView.controls.center = new THREE.Vector3(0, 0, Cinema3DView.sizeY * Cinema3DView.offsetZ);


        this.maxCount = 0;
        seatsList.forEach(function (seatPos) {
            if (seatPos.count != null) {
                if (Cinema3DView.maxCount < seatPos.count) {
                    Cinema3DView.maxCount = seatPos.count;
                }
            }
            else {

            }
        });


        seatsList.forEach(function (seatPos) {
            Cinema3DView.placeSeat(seatPos);
        });

        /*for(var j = 0; j < Cinema3DView.sizeY; j++) {
         for (var i = 0; i < Cinema3DView.sizeX; i++) {

         Cinema3DView.placeSeat(i,j,Math.random()>0.3 ? 1: Math.random()>0.4 ? 0 : 2);
         }
         }*/


        var step = new THREE.Mesh(new THREE.BoxGeometry(Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.distanceY, Cinema3DView.distanceY), new THREE.MeshLambertMaterial({color: 0x555555}));
        var stepOffsetZ = -22;
        var stepOffsetY = 5;
        for (var j = 0; j <= Cinema3DView.sizeY; j++) {
            var singleStep = step.clone();
            singleStep.position.z = (-Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0 + Cinema3DView.distanceY * j + stepOffsetY;
            singleStep.position.y = stepOffsetZ + j * Cinema3DView.offsetZ;
            Cinema3DView.scene.add(singleStep);
        }


        var floor = new THREE.Mesh(new THREE.BoxGeometry(Cinema3DView.sizeX * (Cinema3DView.distanceX + 5), Cinema3DView.distanceY, Cinema3DView.sizeX * (Cinema3DView.distanceX + 5)), new THREE.MeshLambertMaterial({color: 0x555555}));
        floor.position.y = (2 - Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0;
        floor.position.z = (Cinema3DView.sizeY * Cinema3DView.distanceY) - Cinema3DView.distanceY / 2;
        Cinema3DView.scene.add(floor);

        var screenSize = new THREE.Vector2(Cinema3DView.sizeX * Cinema3DView.distanceX * 0.9, Cinema3DView.sizeX * Cinema3DView.distanceX * 0.5);
        var screen = new THREE.Mesh(new THREE.BoxGeometry(screenSize.x, screenSize.y, 0.001), new THREE.MeshLambertMaterial({color: 0xeeeeee}));
        screen.position.y = 0;
        screen.position.z = (Cinema3DView.sizeY + 1) * Cinema3DView.distanceY;
        Cinema3DView.scene.add(screen);


        // Cinema3DView.renderer


        Cinema3DView.container.appendChild(Cinema3DView.renderer.domElement);


        window.addEventListener('resize', Cinema3DView.onWindowResize, false);

        //Cinema3DView.showEdges(Cinema3DView.scene.children);

        Cinema3DView.animate();
        Cinema3DView.render();

    },
    init2: function (cont, seatsList, sx, sy, readOnly, modify) {
        Cinema3DView.modify = modify;
        Cinema3DView.init(cont, seatsList, sx, sy, readOnly);
    },
    computeSeat: function (standMaterial, chairMaterial, broken) {


        var seat = new THREE.Mesh();

        var rotation = 0.5;

        var footSize = new THREE.Vector3(2, 22, 2);
        var legSize = new THREE.Vector3(2, 2, 22);
        var footDistance = 18;


        var singleStand = new THREE.Object3D();
        var stand = new THREE.Object3D();
        var foot = new THREE.Mesh(new THREE.BoxGeometry(footSize.x, footSize.y, footSize.z), standMaterial);
        foot.position.z += footSize.x / 2.0;
        foot.name = "metal";

        var leg = new THREE.Mesh(new THREE.BoxGeometry(legSize.x, legSize.y, legSize.z), standMaterial);

        leg.position.z += 12;
        leg.name = "metal";


        singleStand.add(foot);
        singleStand.add(leg);

        var secondStand = singleStand.clone();

        secondStand.position.x -= footDistance;

        stand.add(singleStand);
        stand.add(secondStand);

        var chair = new THREE.Object3D();
        var pillowSize = new THREE.Vector3(22, 33, 10);
        var backSize = new THREE.Vector3(26, 10, 42);

        var pillow = new THREE.Mesh(new THREE.BoxGeometry(pillowSize.x, pillowSize.y, pillowSize.z), chairMaterial);
        var back = new THREE.Mesh(new THREE.BoxGeometry(backSize.x, backSize.y, backSize.z), chairMaterial);

        pillow.position.z += 20;
        pillow.name = "cloth";
        back.position.z += 20 + backSize.z / 2.0;
        back.name = "cloth";

        var armSize = new THREE.Vector3(7, 22, 22);
        var arm = new THREE.Mesh(new THREE.BoxGeometry(armSize.x, armSize.y, armSize.z), chairMaterial);
        arm.position.z += 25;
        arm.position.x -= pillowSize.x / 2.0 + 1;
        arm.position.y += 5;

        arm.name = "cloth";

        var otherArm = new THREE.Mesh(new THREE.BoxGeometry(armSize.x, armSize.y, armSize.z), chairMaterial);
        otherArm.position.x += 11 + 1;
        otherArm.position.z += 25;
        otherArm.position.y += 5;

        if (broken) {

            back.rotateX(0.3);
            back.position.y -= 2;


            pillow.rotateX(2.1);

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
        var chairMaterial = Materials.freeChair.clone();

        Cinema3DView.freeSeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, false);
        Cinema3DView.freeSeatMesh.name = "seat";
    },
    computeBusySeatMesh: function () {
        var footColor = 0x666666;
        var chairColor = 0xcc1515;

        var standMaterial = new THREE.MeshLambertMaterial({
            color: footColor,
            transparent: true,
            opacity: 0.8,
            shading: THREE.flatShading
        });
        var chairMaterial = Materials.busySeatMaterial;

        Cinema3DView.busySeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, false);
    },
    computeBrokenSeatMesh: function () {
        var footColor = 0x666666;
        var chairColor = 0xcc9955;

        var standMaterial = new THREE.MeshLambertMaterial({
            color: footColor,
            transparent: true,
            opacity: 0.9,
            shading: THREE.flatShading
        });
        var chairMaterial = new THREE.MeshLambertMaterial({
            color: chairColor,
            transparent: true,
            opacity: 0.9,
            shading: THREE.flatShading
        });

        Cinema3DView.brokenSeatMesh = Cinema3DView.computeSeat(standMaterial, chairMaterial, true);
    },
    resetCamera: function () {
        var min = Cinema3DView.sizeX / $(Cinema3DView.container).width() > Cinema3DView.sizeY / $(Cinema3DView.container).clientHeight ? Cinema3DView.sizeX : Cinema3DView.sizeY;
        Cinema3DView.controls.reset();

        Cinema3DView.camera.position.x = 0;
        Cinema3DView.camera.position.y = 83 * min / 2.0;
        Cinema3DView.camera.position.z = -0.1 * min / 2.0;

    },
    placeSeat: function (seatPos) {
        var singleChair;
        var position;
        if (seatPos.column != null) {
            position = new THREE.Vector3(-10 + (Cinema3DView.sizeX * Cinema3DView.distanceX) / 2.0 - Cinema3DView.distanceX * seatPos.column, -Cinema3DView.offsetZ * seatPos.row + Cinema3DView.offsetZ * Cinema3DView.sizeY, (Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0 - Cinema3DView.distanceY * seatPos.row);
        }
        else if (seatPos.seat_column != null) {
            position = new THREE.Vector3(-10 + (Cinema3DView.sizeX * Cinema3DView.distanceX) / 2.0 - Cinema3DView.distanceX * seatPos.seat_column, -Cinema3DView.offsetZ * seatPos.seat_row + Cinema3DView.offsetZ * Cinema3DView.sizeY, (Cinema3DView.sizeY * Cinema3DView.distanceY) / 2.0 - Cinema3DView.distanceY * seatPos.seat_row);
        }
        else {
            console.log(seatPos);
        }
        if (seatPos.status != null) {
            if (seatPos.status == Cinema3DView.freeStatus) {
                if (Cinema3DView.modify) {
                    singleChair = Cinema3DView.freeSeatMesh.clone();
                    Cinema3DView.seatMap[singleChair.uuid] = {status: 3, x: seatPos.column, y: seatPos.row};
                    singleChair.position.set(position.x, position.y, position.z);
                    singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
                    singleChair.children.forEach(function (i) {
                        if (i.name == "chair") {
                            i.children.forEach(function (j) {
                                Cinema3DView.seats.push(j);
                            });
                        }
                    });
                } else {
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
            }
            else if (seatPos.status == Cinema3DView.reservedStatus) {
                singleChair = Cinema3DView.busySeatMesh.clone();
                singleChair.position.set(position.x, position.y, position.z);
                singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
            }
            else if (seatPos.status == Cinema3DView.brokenStatus) {
                if (Cinema3DView.modify) {
                    singleChair = Cinema3DView.brokenSeatMesh.clone();
                    Cinema3DView.seatMap[singleChair.uuid] = {
                        status: 4,
                        x: seatPos.column,
                        y: seatPos.row,
                        changed: false
                    };
                    singleChair.position.set(position.x, position.y, position.z);
                    singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
                    singleChair.children.forEach(function (i) {
                        if (i.name == "chair") {
                            i.children.forEach(function (j) {
                                Cinema3DView.seats.push(j);
                            });
                        }
                    });
                } else {
                    singleChair = Cinema3DView.brokenSeatMesh.clone();
                    singleChair.position.set(position.x, position.y, position.z);
                    singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
                }
            }
            else if (seatPos.status == Cinema3DView.okStatus) {
                singleChair = Cinema3DView.freeSeatMesh.clone();
                Cinema3DView.seatMap[singleChair.uuid] = {status: 3, x: seatPos.column, y: seatPos.row};
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
            else if (seatPos.status == Cinema3DView.brokenSeatStatus) {
                singleChair = Cinema3DView.brokenSeatMesh.clone();
                Cinema3DView.seatMap[singleChair.uuid] = {status: 4, x: seatPos.column, y: seatPos.row, changed: false};
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
        }
        else if (seatPos.count != null) {

            singleChair = Cinema3DView.freeSeatMesh.clone();
            singleChair.position.set(position.x, position.y, position.z);
            singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
            singleChair.children.forEach(function (i) {
                if (i.name == "chair") {
                    i.children.forEach(function (j) {
                        var material = Materials.freeChair.clone();
                        material.color.r = 0;
                        material.color.g = (seatPos.count / Cinema3DView.maxCount) * 2 - 1;
                        material.color.b = 0x00;
                        j.material = material;
                    });
                }
            });
        }

        Cinema3DView.scene.add(singleChair);
    },
    onWindowResize: function () {
        Cinema3DView.camera.aspect = $(Cinema3DView.container).width() / $(Cinema3DView.container).height();
        Cinema3DView.renderer.setSize($(Cinema3DView.container).width(), $(Cinema3DView.container).height());

        Cinema3DView.camera.updateProjectionMatrix();

        //Cinema3DView.render();
    },
    onTouchStart: function (e) {
        var event = e.originalEvent;
        event.preventDefault();

        if (event.targetTouches.length > 1) {
            Cinema3DView.touchStatus = -event.targetTouches.length;
        }
        else if (event.targetTouches.length == 1 && Cinema3DView.touchStatus == 0) {

            Cinema3DView.mouse.x = +((event.changedTouches[0].clientX) / window.innerWidth) * 2 - 1;
            Cinema3DView.mouse.y = -((event.changedTouches[0].clientY) / window.innerHeight) * 2 + 1;

            Cinema3DView.touchStatus = 1;
        }
    },
    onTouchMove: function (e) {
        var event = e.originalEvent;
        alert("ciao");
        if (Cinema3DView.touchStatus == 1) {
            var pos = new THREE.Vector2();
            pos.x = +((event.changedTouches[0].clientX) / window.innerWidth) * 2 - 1;
            pos.y = -((event.changedTouches[0].clientY) / window.innerHeight) * 2 + 1;
            var distance = Math.sqrt(Math.pow(Cinema3DView.mouse.x - pos.x, 2) + Math.pow(Cinema3DView.mouse.y - pos.y, 2));
            var maxDistance = 0.01;
            if (distance > maxDistance) {
                Cinema3DView.touchStatus = 2;
            }
        }

    },
    onTouchEnd: function (e) {
        var event = e.originalEvent;
        event.preventDefault();
        console.log(event);


        if (Cinema3DView.touchStatus == 1) {

            var pos = new THREE.Vector2();

            pos.x = +((event.changedTouches[0].clientX) / window.innerWidth) * 2 - 1;
            pos.y = -((event.changedTouches[0].clientY) / window.innerHeight) * 2 + 1;

            var distance = Math.sqrt(Math.pow(Cinema3DView.mouse.x - pos.x, 2) + Math.pow(Cinema3DView.mouse.y - pos.y, 2));
            var maxDistance = 0.2;
            if (distance < maxDistance) {

                Cinema3DView.raycaster.setFromCamera(pos, Cinema3DView.camera);
                var intersects = Cinema3DView.raycaster.intersectObjects(Cinema3DView.seats);
                //console.log(Cinema3DView.seats);
                //console.log(intersects[0].object);

                if (intersects.length > 0) {
                    Cinema3DView.selectChair(intersects[0].object.parent);
                }
                Cinema3DView.touchStatus = 0;
            }
        } else if (Cinema3DView.touchStatus == 2) {
            Cinema3DView.touchStatus = 0;
        } else if (Cinema3DView.touchStatus < 0) {
            Cinema3DView.touchStatus++;
        }

    },
    onMouseDown: function (event) {
        //console.log(Cinema3DView.camera.position);
        //console.log(Cinema3DView.camera.rotation);
        Cinema3DView.removeHover();

        event.preventDefault();
        if (Cinema3DView.leftClick == false) {
            if (event.button == 0) {
                Cinema3DView.clickStart(event);
            }
        }
    },
    clickStart: function (e) {
        var event = e.originalEvent;
        Cinema3DView.leftClick = true;
        //console.log(Cinema3DView.seats);
        Cinema3DView.mouse.x = (  event.layerX / Cinema3DView.container.clientWidth ) * 2 - 1;
        Cinema3DView.mouse.y = -(  event.layerY / Cinema3DView.container.clientHeight ) * 2 + 1;
    },
    onMouseMove: function (e) {
        var event = e.originalEvent;
        //console.log(event);
        var pos = new THREE.Vector2();
        pos.x = ( event.layerX / Cinema3DView.container.clientWidth ) * 2 - 1;
        pos.y = -( event.layerY / Cinema3DView.container.clientHeight ) * 2 + 1;

        if (!Cinema3DView.leftClick) {
            Cinema3DView.raycaster.setFromCamera(pos, Cinema3DView.camera);
            var intersects = Cinema3DView.raycaster.intersectObjects(Cinema3DView.seats);
            //console.log(intersects);
            if (intersects.length > 0) {
                Cinema3DView.hoverChair(intersects[0].object.parent);
            } else {
                Cinema3DView.removeHover();
            }
        }
    },
    onMouseUp: function (e) {

        var event = e.originalEvent;
        if (Cinema3DView.leftClick) {
            //console.log(event);
            var pos = new THREE.Vector2();
            pos.x = (  event.layerX / Cinema3DView.container.clientWidth ) * 2 - 1;
            pos.y = -(  event.layerY / Cinema3DView.container.clientHeight ) * 2 + 1;
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
            //console.log(Cinema3DView.seatMap[clicked.parent.uuid]);
            if (Cinema3DView.seatMap[clicked.parent.uuid].status == 0) {
                Cinema3DView.hoveredSeat = null;
                console.log(clicked.parent.position);
                //Cinema3DView.lastLight.position.set(clicked.parent.position);
                //Cinema3DView.lastLight.position.y-=10;
                //Cinema3DView.lastLight.intensity = 1;
                Cinema3DView.seatMap[clicked.parent.uuid].status = 1;
                $(Cinema3DView.container).trigger("onSeatAdd", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                clicked.children.forEach(function (obj) {
                    obj.material = Materials.selectedChair;
                });
            }
            else if (Cinema3DView.seatMap[clicked.parent.uuid].status == 1) {
                Cinema3DView.seatMap[clicked.parent.uuid].status = 0;
                //Cinema3DView.lastLight.intensity = 0;
                $(Cinema3DView.container).trigger("onRemoveAdd", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                clicked.children.forEach(function (obj) {
                    obj.material = Materials.freeChair;
                });

            }
            else if (Cinema3DView.seatMap[clicked.parent.uuid].status == 3) {
                Cinema3DView.seatMap[clicked.parent.uuid].status = 4;
                if (Cinema3DView.seatMap[clicked.parent.uuid].changed == false || Cinema3DView.seatMap[clicked.parent.uuid].changed == null) {
                    Cinema3DView.seatMap[clicked.parent.uuid].changed = true;
                    $(Cinema3DView.container).trigger("onSeatChange", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                }
                else {
                    Cinema3DView.seatMap[clicked.parent.uuid].changed = false;
                    $(Cinema3DView.container).trigger("onResetChange", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                }
                //Cinema3DView.lastLight.intensity = 0;

                Cinema3DView.scene.remove(clicked.parent);
                var uuid = clicked.parent.uuid;
                var position = clicked.parent.position;
                var rotation = clicked.parent.rotation.x;
                clicked.parent = Cinema3DView.brokenSeatMesh.clone();
                clicked.parent.position.x = position.x;
                clicked.parent.position.y = position.y;
                clicked.parent.position.z = position.z;
                clicked.parent.rotation.x = rotation;
                clicked.parent.uuid = uuid;
                Cinema3DView.scene.add(clicked.parent);
            }
            else if (Cinema3DView.seatMap[clicked.parent.uuid].status == 4) {
                Cinema3DView.seatMap[clicked.parent.uuid].status = 3;
                //Cinema3DView.lastLight.intensity = 0;
                if (Cinema3DView.seatMap[clicked.parent.uuid].changed == false || Cinema3DView.seatMap[clicked.parent.uuid].changed == null) {
                    Cinema3DView.seatMap[clicked.parent.uuid].changed = true;
                    $(Cinema3DView.container).trigger("onSeatChange", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                }
                else {
                    Cinema3DView.seatMap[clicked.parent.uuid].changed = false;
                    $(Cinema3DView.container).trigger("onResetChange", [Cinema3DView.seatMap[clicked.parent.uuid].x, Cinema3DView.seatMap[clicked.parent.uuid].y]);
                }
                Cinema3DView.scene.remove(clicked.parent);
                var uuid = clicked.parent.uuid;
                var position = clicked.parent.position;
                var rotation = clicked.parent.rotation.x;
                clicked.parent = Cinema3DView.freeSeatMesh.clone();
                clicked.parent.position.x = position.x;
                clicked.parent.position.y = position.y;
                clicked.parent.position.z = position.z;
                clicked.parent.rotation.x = rotation;
                clicked.parent.uuid = uuid;
                Cinema3DView.scene.add(clicked.parent);

            }
        }

    },
    hoverChair: function (hover) {
        if (Cinema3DView.seatMap[hover.parent.uuid].status == 0 || Cinema3DView.seatMap[hover.parent.uuid].status == 1) {
            if (Cinema3DView.seatMap[hover.parent.uuid].status == 0) {
                if (Cinema3DView.hoveredSeat == null) {
                    Cinema3DView.hoveredSeat = hover;
                    if (hover.name == "chair") {
                        hover.children.forEach(function (obj) {
                            Cinema3DView.previousState = obj.material;
                            obj.material = Materials.hoveringChair;
                        });

                    }
                }
                else if (Cinema3DView.hoveredSeat.uuid != hover.uuid) {
                    Cinema3DView.removeHover();
                    Cinema3DView.hoveredSeat = hover;
                    if (hover.name == "chair") {
                        hover.children.forEach(function (obj) {
                            obj.material = Materials.hoveringChair;
                        });

                    }
                }
            } else {
                if (Cinema3DView.hoveredSeat == null) {
                    Cinema3DView.hoveredSeat = hover;
                    if (hover.name == "chair") {
                        hover.children.forEach(function (obj) {
                            obj.material = Materials.hoveringChairSelected;
                        });

                    }
                }
                else if (Cinema3DView.hoveredSeat.uuid != hover.uuid) {
                    Cinema3DView.removeHover();
                    Cinema3DView.hoveredSeat = hover;
                    if (hover.name == "chair") {
                        hover.children.forEach(function (obj) {
                            obj.material = Materials.hoveringChairSelected;
                        });

                    }
                }
            }
        }
    },
    removeHover: function () {
        if (Cinema3DView.hoveredSeat != null) {
            //console.log(Cinema3DView.hoveredSeat);
            if (Cinema3DView.seatMap[Cinema3DView.hoveredSeat.parent.uuid] != null) {
                if (Cinema3DView.seatMap[Cinema3DView.hoveredSeat.parent.uuid].status == 0) {
                    Cinema3DView.hoveredSeat.children.forEach(function (obj) {
                        obj.material = Materials.freeChair;
                    });
                } else {
                    Cinema3DView.hoveredSeat.children.forEach(function (obj) {
                        obj.material = Materials.selectedChair;
                    });
                }
            } else {
                Cinema3DView.hoveredSeat.children.forEach(function (obj) {
                    obj.material = Materials.freeChair;
                });
            }
            Cinema3DView.hoveredSeat = null;
        }
    },

    lockView: function () {
        if (Cinema3DView.locked) {
            Cinema3DView.locked = false;
            Cinema3DView.controls.rotateSpeed = Cinema3DView.cameraSpeed;
        }
        else {
            Cinema3DView.controls.rotateSpeed = 0;
            Cinema3DView.locked = true;
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