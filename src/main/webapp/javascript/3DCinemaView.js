/**
 * Created by marco on 16/07/15.
 */
"use strict";



var container, stats;

var camera, controls, scene, renderer, chair, raycaster, mouse;
var seats = [];

var leftClick;
var touchStatus = 0;
var chairModel = new THREE.Object3D();
var distanceX = 35.0;
var distanceY = 45.0;
var sizeX;
var sizeY;
var offsetZ = -20;
var busyStatus = 0;
var freeStatus = 1;
var brokenStatus = 2;
var freeSeatMesh;
var busySeatMesh;
var brokenSeatMesh;

var freeChairMaterial = new THREE.MeshLambertMaterial( { color:0xdd1122, shading: THREE.flatShading } );
var selectedChairMaterial = new THREE.MeshLambertMaterial( { color:0x00ff00, shading: THREE.flatShading} );
var hoveringChairMaterial = new THREE.MeshLambertMaterial( { color:0xffff00, shading: THREE.flatShading} );

var seatMap = {};

var hoveredSeat;

var addFunction = function(){};
var removeFunction = function(){};







function animate() {
    requestAnimationFrame(animate);
    controls.update();
}

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

function computeSeat(standMaterial,chairMaterial, broken)
{
    var seat = new THREE.Mesh();

    var rotation = 0.5;

    var footSize = new THREE.Vector3(2,22,2);
    var legSize = new THREE.Vector3(2,2,22);
    var footDistance = 18;


    var singleStand = new THREE.Object3D();
    var stand = new THREE.Object3D();
    var foot = new THREE.Mesh( new THREE.CubeGeometry(footSize.x,footSize.y, footSize.z ), standMaterial );
    foot.position.z+=footSize.x/2.0;
    foot.name="metal";

    var leg = new THREE.Mesh( new THREE.CubeGeometry(legSize.x,legSize.y, legSize.z ), standMaterial );

    leg.position.z+=12;
    leg.name="metal";
    if(broken)
    {
        foot.rotateX(Math.random()*rotation);
        foot.rotateY(Math.random()*rotation);
        foot.rotateZ(Math.random()*rotation);

        leg.rotateX(Math.random()*rotation);
        leg.rotateY(Math.random()*rotation);
        leg.rotateZ(Math.random()*rotation);
    }


    singleStand.add(foot);
    singleStand.add(leg);

    var secondStand = singleStand.clone();

    secondStand.position.x-=footDistance;

    stand.add(singleStand);
    stand.add(secondStand);

    var chair = new THREE.Object3D();
    var pillowSize = new THREE.Vector3(22,33,10);
    var backSize = new THREE.Vector3(22,10,42);

    var pillow = new THREE.Mesh( new THREE.CubeGeometry(pillowSize.x,pillowSize.y, pillowSize.z ), chairMaterial  );
    var back = new THREE.Mesh( new THREE.CubeGeometry(backSize.x,backSize.y,backSize.z ),  chairMaterial  );
    pillow.position.z+=20;
    pillow.name="cloth";
    back.position.z+=20+backSize.z/2.0;
    back.name="cloth";
    var armSize = new THREE.Vector3(6,25,7);
    var arm = new THREE.Mesh( new THREE.CubeGeometry(armSize.x,armSize.y,armSize.z ),  chairMaterial );
    arm.position.z+=35;
    arm.position.x-=pillowSize.x/2.0;
    arm.name = "cloth";

    var otherArm = new THREE.Mesh( new THREE.CubeGeometry(armSize.x,armSize.y,armSize.z ),  chairMaterial );
    otherArm.position.x+=11;
    otherArm.position.z+=35;

    if(broken)
    {
        back.rotateX(Math.random()*rotation);
        back.rotateY(Math.random()*rotation);
        back.rotateZ(Math.random()*rotation);

        pillow.rotateX(Math.random()*rotation);
        pillow.rotateY(Math.random()*rotation);
        pillow.rotateZ(Math.random()*rotation);

        arm.rotateX(Math.random()*rotation);
        arm.rotateY(Math.random()*rotation);
        arm.rotateZ(Math.random()*rotation);

        otherArm.rotateX(Math.random()*rotation);
        otherArm.rotateY(Math.random()*rotation);
        otherArm.rotateZ(Math.random()*rotation);
    }

    back.position.y+=(pillowSize.y-backSize.y)/2.0;
    chair.add(pillow);

    chair.add(back);
    chair.add(arm);
    chair.add(otherArm);

    chair.name = "chair";

    chair.position.x-=pillowSize.x/2.0-2;

    seat.add(chair);
    seat.add(stand);

    return seat;
}


function computeFreeSeatMesh()
{
    var footColor = 0x777777;

    var standMaterial =  new THREE.MeshLambertMaterial({color:footColor});
    var chairMaterial =  freeChairMaterial

    freeSeatMesh = computeSeat(standMaterial,chairMaterial, false);
    freeSeatMesh.name = "seat";
}

function computeBusySeatMesh()
{
    var footColor = 0x666666;
    var chairColor = 0xcc1515;

    var standMaterial =  new THREE.MeshLambertMaterial({color:footColor, transparent:true, opacity:0.4});
    var chairMaterial =  new THREE.MeshLambertMaterial({color:chairColor, transparent:true, opacity:0.4});

    busySeatMesh = computeSeat(standMaterial,chairMaterial,false);
}

function computeBrokenSeatMesh()
{
    var footColor = 0x666666;
    var chairColor = 0xcc9900;

    var standMaterial =  new THREE.MeshLambertMaterial({color:footColor, transparent:true, opacity:0.9});
    var chairMaterial =  new THREE.MeshLambertMaterial({color:chairColor, transparent:true, opacity:0.9});

    brokenSeatMesh = computeSeat(standMaterial,chairMaterial,true);
}

function init(cont,add,remove, seatsList,sx,sy) {
    addFunction=add;
    removeFunction=remove;
    container = cont;
    sizeX=sx;
    sizeY=sy;
    $(container).on('touchmove', onTouchMove);
    container.addEventListener( 'mousedown', onMouseDown, false );
    container.addEventListener( 'mouseup', onMouseUp, false );
    //document.addEventListener('mousemove',onMouseMove,true);
    container.addEventListener('touchstart', onTouchStart, false);
    $(container).bind('touchend',onTouchEnd);

    renderer = new THREE.WebGLRenderer( { antialias: false } );
    renderer.setClearColor(0xffffff,1);
    renderer.setPixelRatio( 3/2.0 );
    renderer.setSize( $('#test').width(),$('#test').height() );

    camera = new THREE.PerspectiveCamera( 60, window.innerWidth / window.innerHeight, 1, 3000 );
    camera.position.z = 200;
    camera.position.y = 300;
    camera.position.x = 300;
    controls = new THREE.OrbitControls( camera, renderer.domElement );
    controls.damping = 0.2;
    controls.maxDistance=1000;
    controls.minDistance=100;
    controls.maxPolarAngle = Math.PI/2.0;
    /*controls.autoRotate=true;
     controls.autoRotateSpeed=1;*/


    scene = new THREE.Scene();


    // world


    var material =  new THREE.MeshLambertMaterial( { color:0xffffff, shading: THREE.flatShading } );



    raycaster = new THREE.Raycaster();
    mouse = new THREE.Vector2();

    computeBusySeatMesh();
    computeFreeSeatMesh();
    computeBrokenSeatMesh();


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

    controls.center = new THREE.Vector3(0,0,sizeY*offsetZ);

    seatsList.forEach(function(seatPos){
        placeSeat(seatPos);
    });

    /*for(var j = 0; j < sizeY; j++) {
     for (var i = 0; i < sizeX; i++) {

     placeSeat(i,j,Math.random()>0.3 ? 1: Math.random()>0.4 ? 0 : 2);
     }
     }*/



    var step = new THREE.Mesh( new THREE.CubeGeometry( sizeX*(distanceX+5),distanceY, distanceY ), new THREE.MeshLambertMaterial({color:0x555555}) );
    var stepOffsetZ = -22;
    var stepOffsetY = 5;
    for(var j = 0; j < sizeY; j++) {
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

function placeSeat(seatPos)
{
    var singleChair;
    var position = new THREE.Vector3(-10+(sizeX * distanceX) / 2.0 - distanceX * seatPos.x,-offsetZ * seatPos.y+offsetZ*sizeY,(sizeY * distanceY) / 2.0 - distanceY * seatPos.y);

    if(seatPos.status == freeStatus)
    {
        singleChair = freeSeatMesh.clone();
        seatMap[singleChair.uuid]={status:0,x:seatPos.x,y:seatPos.y};
        singleChair.position.set(position.x, position.y, position.z);
        singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
        singleChair.children.forEach(function (i){
            if(i.name=="chair")
            {
                i.children.forEach(function (j){
                    seats.push(j);
                });
            }
        });
    }
    else if(seatPos.status == busyStatus)
    {
        singleChair = busySeatMesh.clone();
        singleChair.position.set(position.x, position.y, position.z);
        singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
    }
    else if(seatPos.status == brokenStatus)
    {
        singleChair = brokenSeatMesh.clone();
        singleChair.position.set(position.x, position.y, position.z);
        singleChair.rotation.set(-Math.PI / 2.0, 0, 0);
    }
    else
    {
        return;
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
    //alert("ciao");
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
        //console.log(intersects[0].object);

        if (intersects.length > 0) {
            selectChair(intersects[0].object.parent);
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

function onMouseMove(event)
{
    var pos = new THREE.Vector2();
    pos.x = ( ( event.pageX - container.offsetLeft ) / container.clientWidth ) * 2 - 1;
    pos.y = -( ( event.pageY - container.offsetTop ) / container.clientHeight ) * 2 + 1;
    var minDistance = container.clientHeight/10000.0;
    var distance = Math.sqrt(Math.pow(mouse.x - pos.x, 2) + Math.pow(mouse.y - pos.y, 2));
    if (distance > minDistance && leftClick)
    {
        leftClick = false;
    }
    else if(!leftClick)
    {
        var intersects = raycaster.intersectObjects(seats);
        console.log(intersects);
        if (intersects.length > 0) {
            hoverChair(intersects[0].object.parent);
        }
    }
}

function onMouseUp(event)
{
    if(leftClick) {
        var pos = new THREE.Vector2();
        pos.x = ( ( event.pageX - container.offsetLeft ) / container.clientWidth ) * 2 - 1;
        pos.y = -( ( event.pageY - container.offsetTop ) / container.clientHeight ) * 2 + 1;
        var minDistance = container.clientHeight/3000.0;
        var distance = Math.sqrt(Math.pow(mouse.x - pos.x, 2) + Math.pow(mouse.y - pos.y, 2));
        if (distance < minDistance) {

            raycaster.setFromCamera(pos, camera);

            var intersects = raycaster.intersectObjects(seats);

            if (intersects.length > 0) {
                selectChair(intersects[0].object.parent);
            }

        }
    }

    leftClick=false;
}

function selectChair(clicked)
{

    if (clicked.name == "chair" ) {
        console.log(seatMap[clicked.parent.uuid]);
        if(seatMap[clicked.parent.uuid].status == 0) {
            seatMap[clicked.parent.uuid].status = 1;
            addFunction(seatMap[clicked.parent.uuid].x,seatMap[clicked.parent.uuid].y);
            clicked.children.forEach(function (obj) {
                obj.material = selectedChairMaterial;
            });
        }
        else{
            seatMap[clicked.parent.uuid].status = 0;
            removeFunction(seatMap[clicked.parent.uuid].x,seatMap[clicked.parent.uuid].y);
            clicked.children.forEach(function (obj) {
                obj.material = freeChairMaterial;
            });
        }
    }
}

function hoverChair(hover)
{
    if(hoveredSeat == null)
    {
        hoveredSeat=hover;
        if (hover.name == "chair") {
            if (seatMap[clicked.parent.uuid].status == 0) {
                seatMap[clicked.parent.uuid].status = 1;
                clicked.children.forEach(function (obj) {
                    obj.material = selectedChairMaterial;
                });
            }
            else {
                seatMap[clicked.parent.uuid].status = 0;
                clicked.children.forEach(function (obj) {
                    obj.material = freeChairMaterial;
                });
            }
        }
    }
    else if(hoveredSeat.uuid != hover.uuid)
    {
        hoveredSeat=hover;
        if (hover.name == "chair") {
            if (seatMap[clicked.parent.uuid].status == 0) {
                seatMap[clicked.parent.uuid].status = 1;
                clicked.children.forEach(function (obj) {
                    obj.material = selectedChairMaterial;
                });
            }
            else {
                seatMap[clicked.parent.uuid].status = 0;
                clicked.children.forEach(function (obj) {
                    obj.material = freeChairMaterial;
                });
            }
        }
    }
}

function render() {
    requestAnimationFrame(render);
    renderer.render(scene, camera);
}
