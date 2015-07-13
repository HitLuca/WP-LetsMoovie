<html>
<head>
    <title>My first Three.js app</title>
    <style>
        body { margin: 0; }
        canvas { width: 100%; height: 100% }
    </style>
</head>
<div id="test" style="height: 600px; width: 800px;"></div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r71/three.min.js"></script>
<script src="http://threejs.org/examples/js/controls/OrbitControls.js"></script>
<script src="http://threejs.org/examples/js/loaders/OBJLoader.js"></script>
<script>




    var container, stats;

    var camera, controls, scene, renderer;

    init();
    render();

    function animate() {
        requestAnimationFrame(animate);
        controls.update();
    }

    function init() {

        camera = new THREE.PerspectiveCamera( 60, window.innerWidth / window.innerHeight, 0.1, 10000 );
        camera.position.z = 300;

        controls = new THREE.OrbitControls( camera );
        controls.damping = 0.2;
        controls.addEventListener( 'change', render );

        scene = new THREE.Scene();


        // world

        var geometry = new THREE.CylinderGeometry( 0, 10, 30, 4, 1 );
        var material =  new THREE.MeshLambertMaterial( { color:0xffffff, shading: THREE.flatShading } );



       /* var mesh = new THREE.Mesh( geometry, material );
        mesh.position.x = 0;
        mesh.position.y = 0;
        mesh.position.z = 0;
        mesh.updateMatrix();
        mesh.matrixAutoUpdate = false;
        scene.add( mesh );*/



        // lights

        light = new THREE.DirectionalLight( 0x777766 );
        light.position.set( 1, 2, 1 );
        scene.add( light );

        light = new THREE.DirectionalLight( 0x666666 );
        light.position.set( -1, -1, -1 );
        scene.add( light );

        light = new THREE.AmbientLight( 0x222222 );
        scene.add( light );

        var manager = new THREE.LoadingManager();
        var loader = new THREE.OBJLoader( manager );
        loader.load( '/obj/Armchair.obj', function ( object ) {
            object.position.y = - 80;
            scene.add( object );

        });

        // renderer


        renderer = new THREE.WebGLRenderer( { antialias: true } );
        renderer.setClearColor(0xffffff,1);
        renderer.setPixelRatio( 3/2.0 );
        renderer.setSize( window.innerWidth/2.0, window.innerHeight/1.5 );

        container = document.getElementById( 'test' );
        container.appendChild( renderer.domElement );

        //

        window.addEventListener( 'resize', onWindowResize, false );

        animate();

    }

    function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

        renderer.setSize( window.innerWidth, window.innerHeight );

        render();

    }

    function render() {

        renderer.render( scene, camera );

    }

   // render();
</script>
</body>
</html>