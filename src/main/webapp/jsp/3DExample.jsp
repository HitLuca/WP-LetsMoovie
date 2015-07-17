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
        <div id="test" style="height: 80%; width: 100%;"></div>
    </div>
    <div class="push"></div>

</div>
<input type="button" onclick="resetCamera()" value="top View">
<c:import url="footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r71/three.min.js"></script>
<script src="http://threejs.org/examples/js/controls/OrbitControls.js"></script>
<script src="http://threejs.org/examples/js/loaders/OBJLoader.js"></script>
<script src="/javascript/3DCinemaView.js"></script>
<script>


    function select(x, y) {
        console.log("added x:" + x, "y:" + y)
    }

    function deSelect(x, y) {
        console.log("removed x:" + x, "y:" + y)
    }

    var sl = [];
    for (var h = 0; h < 16; h++) {
        for (var g = 0; g < 16; g++) {
            if (Math.random() < 0.9) {
                sl.push({
                    column: h,
                    row: g,
                    status: Math.random() > 0.5 ? 1 : Math.random() > 0.9 ? 2 : 0
                });
            }
        }
    }

    Cinema3DView.init(document.getElementById('test'), select, deSelect, sl, 17, 17);

</script>
</body>

</html>