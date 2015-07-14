<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mion00
  Date: 09/07/15
  Time: 10.53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:import url="/jsp/head.jsp"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>

    <div class="content">
        <form id="tester" action="/api/getUser" data-abide="ajax" method="post">
            <div class="row">
                <div class="column medium-3 small-centered">
                    <input type="text" id="url" placeholder="url"/>
                </div>
            </div>
            <div class="row" id="template">
                <div class="column medium-6">
                    <input type="text" id="parametroValue" name="parametro" placeholder="nome parametro"/>
                </div>
                <div class="column medium-6">
                    <input type="text" id="parametro" name="username" placeholder="valore"/>
                </div>
            </div>
            <button id="add" class="radius">aggiungi</button>
            <button id="remove" class="radius">rimuovi</button>

            <div class="row">
                <div class="columns small-3 small-centered text-centered">
                    <button class="button small radius expand ladda-button" data-type="zoom-out">
                        Submit
                    </button>
                </div>
            </div>
        </form>

        <div class="row">
            <div class="column small-6">
                <pre id="request" class="panel">
                </pre>

            </div>
            <div class="column small-6">
                <pre id="response" class="panel"></pre>
            </div>

        </div>
    </div>
    <div class="push"></div>
</div>

<c:import url="/jsp/footer.jsp"/>
<script>
    var id = 0;

    //    BIND URL
    $("input#url").change(function (event) {
        var valore = $(this).val();
        $("form").attr("action", valore);
    });

    function binda(divID) {
        var div = $(divID);
        div.find("input#parametroValue").change(function (event) {
            var valore = $(this).val();
            div.find("input#parametro").attr("name", valore);
        })
    }

    $(function () {
        binda("#template");

        $("#add").click(function () {
            var clone = $("#template").clone();
            $("#template").after(clone).attr("id", "template" + id);
            binda("#template" + id);
            id++;
            binda("#template");
        });

        $("#remove").click(function () {
            id--;
            $("#template" + id).detach();
        })
    });

    var response = $("#response");
    var request = $("#request");

    function Succes(data) {
        //response.before(response.clone().JSONView(data));
        //request.before(request.clone().JSONView($("form").serializeJSON()));
        response.html(JSON.stringify(data, null, 2));
        request.html(JSON.stringify($.parseJSON($("form").serializeJSON()), null, 2));
    }

    function error(data) {
        //response.before(response.clone().JSONView(data.responseJSON));
        //request.before(request.clone().JSONView($("form").serializeJSON()));
        response.html(JSON.stringify(data.responseJSON, null, 2));
        request.html(JSON.stringify($.parseJSON($("form").serializeJSON()), null, 2));
    }
    PostForm("tester", Succes, error);
</script>
</body>
</html>
