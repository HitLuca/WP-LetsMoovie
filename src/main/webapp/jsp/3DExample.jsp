<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Let's Moovie"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="header.jsp"/>

    <div class="content">
        <div id="test" style="height: 100%; width: 100%;"></div>
    </div>
    <div class="push"></div>
</div>
<c:import url="footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r71/three.min.js"></script>
<script src="http://threejs.org/examples/js/controls/OrbitControls.js"></script>
<script src="http://threejs.org/examples/js/loaders/OBJLoader.js"></script>
<script>

    container = document.getElementById( 'test' );
    $(container).on('touchmove', onTouchMove);
    container.addEventListener( 'mousedown', onMouseDown, false );
    container.addEventListener( 'mouseup', onMouseUp, false );
    container.addEventListener('touchstart', onTouchStart, false);
    $(container).bind('touchend',onTouchEnd);

 "use strict";

    var container, stats;

    var camera, controls, scene, renderer, chair, raycaster, mouse;
    var seats = [];
    var chairMaterial2 = new THREE.MeshLambertMaterial( { color:0xcc1515, shading: THREE.flatShading} );
    chairMaterial2.transparent = true;
    chairMaterial2.opacity = 0.3;
    var leftClick;
    var touchStatus = 0;
    var chairModel = new THREE.Object3D();
    var distanceX = 35.0;
    var distanceY = 45.0;
    var sizeX = 10;
    var sizeY = 10;
    var offsetZ = -20;
    var busyStatus = 0;
    var freeStatus = 1;
    var brokenStatus = 2;
    var brokenMaterial = new THREE.MeshLambertMaterial( { color:0x223344, shading: THREE.flatShading } );
    var busyClothMaterial = new THREE.MeshLambertMaterial( { color:0xcc1515, shading: THREE.flatShading, transparent:true, opacity:0.4 } );
    var busyMetalMaterial = new THREE.MeshLambertMaterial( { color:0xaaaaaa, shading: THREE.flatShading, transparent:true, opacity:0.3 } );

    init();
    render();

    function animate() {
        requestAnimationFrame(animate);
        controls.update();
    }

    function init() {
        renderer = new THREE.WebGLRenderer( { antialias: false } );
        renderer.setClearColor(0x000000);
        renderer.setPixelRatio( 3/2.0 );
        renderer.setSize( $('#test').width(),$('#test').height() );

        camera = new THREE.PerspectiveCamera( 60, window.innerWidth / window.innerHeight, 1, 1000 );
        camera.position.z = -150;
        camera.position.y = 400;
        camera.position.x = 300;
        controls = new THREE.OrbitControls( camera, renderer.domElement );
        controls.damping = 0.2;
        controls.maxDistance=600;
        controls.minDistance=100;
        controls.maxPolarAngle = Math.PI/2.0;
        /*controls.autoRotate=true;
        controls.autoRotateSpeed=1;*/


        scene = new THREE.Scene();


        // world


        var material =  new THREE.MeshLambertMaterial( { color:0xffffff, shading: THREE.flatShading } );



        raycaster = new THREE.Raycaster();
        mouse = new THREE.Vector2();



        // lights

        var light = new THREE.DirectionalLight(0x666666);
        light.position.set( -50, 100, 70 );
        scene.add( light );

        var light = new THREE.DirectionalLight(0x777777);
        light.position.set( 10, 10, 20);
        scene.add( light );

        var light = new THREE.DirectionalLight(0x888877);
        light.position.set( 50, -150, -170 );
        scene.add( light );

        light = new THREE.AmbientLight( 0x555555 );
        scene.add( light );

        // light = new THREE.AmbientLight( 0x222222 );
        // scene.add( light );

        var manager = new THREE.LoadingManager();
        var chairLoader = new THREE.OBJLoader( manager );
        var armLoader = new THREE.OBJLoader( manager );





        var footSize = new THREE.Vector3(2,22,2);
        var legSize = new THREE.Vector3(2,2,22);
        var footColor = 0x777777;
        var footDistance = 18;


        var singleStand = new THREE.Object3D();
        var stand = new THREE.Object3D();
        var foot = new THREE.Mesh( new THREE.CubeGeometry(footSize.x,footSize.y, footSize.z ), new THREE.MeshLambertMaterial(footColor) );
        foot.position.z+=footSize.x/2.0;
        foot.name="metal";

        var leg = new THREE.Mesh( new THREE.CubeGeometry(legSize.x,legSize.y, legSize.z ), new THREE.MeshLambertMaterial(footColor) );
        leg.position.z+=12;
        leg.name="metal";


        singleStand.add(foot);
        singleStand.add(leg);

        var secondStand = singleStand.clone();

        secondStand.position.x-=footDistance;

        stand.add(singleStand);
        stand.add(secondStand);

        var chair = new THREE.Object3D();
        var pillowSize = new THREE.Vector3(22,33,10);
        var chairColor = 0xdd2222;
        var backSize = new THREE.Vector3(22,10,42);

        var pillow = new THREE.Mesh( new THREE.CubeGeometry(pillowSize.x,pillowSize.y, pillowSize.z ),  new THREE.MeshLambertMaterial({color:chairColor}) );
        var back = new THREE.Mesh( new THREE.CubeGeometry(backSize.x,backSize.y,backSize.z ),  new THREE.MeshLambertMaterial({color:chairColor}) );
        pillow.position.z+=20;
        pillow.name="cloth";
        back.position.z+=20+backSize.z/2.0;
        back.name="cloth";
        var armSize = new THREE.Vector3(6,25,7);
        var arm = new THREE.Mesh( new THREE.CubeGeometry(armSize.x,armSize.y,armSize.z ),  new THREE.MeshLambertMaterial({color:chairColor}) );
        arm.position.z+=35;
        arm.position.x-=pillowSize.x/2.0;
        arm.name = "cloth";

        var otherArm = new THREE.Mesh( new THREE.CubeGeometry(armSize.x,armSize.y,armSize.z ),  new THREE.MeshLambertMaterial({color:chairColor}) );
        otherArm.position.x+=11;
        otherArm.position.z+=35;

        back.position.y+=(pillowSize.y-backSize.y)/2.0;
        chair.add(pillow);

        chair.add(back);
        chair.add(arm);
        chair.add(otherArm);

        chair.name = "chair";

        chair.position.x-=pillowSize.x/2.0-2;
        chairModel.add(chair);

        chairModel.add(stand);
        chairModel.name = "chair";

        /*var boundingBox = new THREE.Mesh( new THREE.CubeGeometry(distanceX,distanceY,70 ),  new THREE.MeshLambertMaterial({color:0x000000}) );
        boundingBox.position.x-=sizeX/2.0+2;
        boundingBox.position.z+=35;
        boundingBox.name="box";
        boundingBox.visible = false;
        chairModel.add(boundingBox);*/

        controls.center = new THREE.Vector3(0,0,sizeY*offsetZ);

        for(var j = 0; j < sizeY; j++) {
            for (var i = 0; i < sizeX; i++) {

                placeSeat(i,j,i%2);
            }
        }



        var step = new THREE.Mesh( new THREE.CubeGeometry( sizeX*(distanceX+5),distanceY, distanceY ), new THREE.MeshLambertMaterial({color:0x555555}) );
        var stepOffsetZ = -22;
        var stepOffsetY = 5;
        for(j = 0; j < sizeY; j++) {
            var singleStep = step.clone();
            singleStep.position.z = (-sizeY * distanceY) / 2.0 + distanceY * j+ stepOffsetY;
            singleStep.position.y = stepOffsetZ+j*offsetZ;
            scene.add(singleStep);
        }

        var floorSize = THREE.Vector3(sizeX*(distanceX+5),sizeX*(distanceX+5),distanceY);
        var floor = new THREE.Mesh( new THREE.CubeGeometry( sizeX*(distanceX+5),distanceY, sizeX*(distanceX+5) ), new THREE.MeshLambertMaterial({color:0x555555}) );
        floor.position.y= (-sizeY * distanceY) / 2.0;
        floor.position.z+= 250;
        scene.add(floor);



        // renderer






        container.appendChild( renderer.domElement );



        window.addEventListener( 'resize', onWindowResize, false );

        animate();

    }

    function placeSeat(x,y,status)
    {
        var singleChair = chairModel.clone();
        singleChair.position.set(22+(-sizeX * distanceX) / 2.0 + distanceX * x, offsetZ * y, (-sizeY * distanceY) / 2.0 + distanceY * y);
        singleChair.rotation.set(-Math.PI / 2.0, 0, 0);

        if(status==busyStatus)
        {
            singleChair.children.forEach(function (val){
                    val.children.forEach(function (obj){
                        console.log(obj);
                        if(obj.material != null) {
                            if(obj.name=="cloth")
                                obj.material = busyClothMaterial;
                            else
                            {
                                obj.children.forEach(function (leg){
                                    leg.material = busyMetalMaterial;
                                });

                            }
                        }
                    });
            });
        } else if( status = brokenStatus )
        {
            singleChair.children.forEach(function (val){
                val.children.forEach(function (obj){
                    //obj.material = brokenMaterial;
                });
            });
        }
        else
        {
            singleChair.children.forEach(function (val){
                if(val.name=="chair")
                {
                    val.children.forEach(function (obj){
                        seats.push(obj);
                    });
                }
            });
        }


        scene.add(singleChair);
    }

    function onWindowResize() {
        camera.aspect = $('#test').width()/$('#test').height() ;
        camera.updateProjectionMatrix();


        renderer.setSize( $('#test').width(),$('#test').height() );

        render();
    }

    function onTouchStart( event ) {
        event.preventDefault();

        if(event.targetTouches.length>1) {
            touchStatus=-event.targetTouches.length;
        }
        else if(event.targetTouches.length==1 && touchStatus==0)
        {
            mouse.x = +( (event.targetTouches[0].pageX - container.offsetLeft )/ window.innerWidth) * 2 -1;
            mouse.y = -( (event.targetTouches[0].pageY - container.offsetTop )/ window.innerHeight) * 2 + 1;
            touchStatus=1;
        }
    }

    function onTouchMove( event  )
    {
        alert("ciao");
        if(touchStatus==1) {
            touchStatus = 2;
        }

    }

    function onTouchEnd( event ) {
        event.preventDefault();
        console.log(event);

        if(touchStatus==1)
        {

            raycaster.setFromCamera(mouse, camera);
            var intersects = raycaster.intersectObjects(seats);
            //console.log(seats);
            //console.log(intersects[0].object.parent.name);

            if (intersects.length > 0) {
                if (intersects[0].object.parent.name == "chair") {
                    intersects[0].object.parent.children.forEach(function (obj) {
                        obj.material = chairMaterial2;
                    });
                }

            }
            touchStatus=0;
        } else if(touchStatus==2) {
            touchStatus=0;
        } else if(touchStatus<0){
            touchStatus++;
        }

    }

    function onMouseDown( event ) {
        event.preventDefault();
        if(leftClick==false) {
            if(event.button==0) {
                clickStart(event);
            }
        }
    }

    function clickStart(event)
    {
        leftClick = true;
        //console.log(seats);
        mouse.x = ( ( event.pageX - container.offsetLeft ) / container.clientWidth ) * 2 - 1;
        mouse.y = -( ( event.pageY - container.offsetTop ) / container.clientHeight ) * 2 + 1;
    }


    function onMouseUp(event)
    {
        if(leftClick) {
            var currentMouse = new THREE.Vector2();
            currentMouse.x = ( ( event.pageX - container.offsetLeft ) / container.clientWidth ) * 2 - 1;
            currentMouse.y = -( ( event.pageY - container.offsetTop ) / container.clientHeight ) * 2 + 1;

            var minDistance = container.clientHeight/10000.0;
            var distance = Math.sqrt(Math.pow(mouse.x - currentMouse.x, 2) + Math.pow(mouse.y - currentMouse.y, 2));

            if (distance <= minDistance) {
                raycaster.setFromCamera(currentMouse, camera);

                var intersects = raycaster.intersectObjects(seats);
                //console.log(seats);
                //console.log(intersects[0].object.parent.name);

                if (intersects.length > 0) {
                    if (intersects[0].object.parent.name == "chair") {
                        intersects[0].object.parent.children.forEach(function (obj) {
                            obj.material = chairMaterial2;
                        });
                    }

                }
            }
        }
        leftClick=false;

    }

    function render() {
        requestAnimationFrame(render);
        renderer.render(scene, camera);
    }

</script>
</body>

</html>