<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="shows" class="text-center">
    <a href=# data-dropdown="drop1" data-bind="show" aria-controls="drop1" aria-expanded="false" class="button">
        Orari
        proiezioni
    </a>
    <ul id="drop1" data-bind="orari" data-dropdown-content class="content f-dropdown" aria-hidden="true">
        <li><a href="#" data-bind="show_time"></a></li>
    </ul>

</div>
