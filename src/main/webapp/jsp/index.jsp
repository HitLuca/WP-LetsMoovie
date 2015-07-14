<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Let's Moovie"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>

    <div class="content">
        <div class="row">
            <div class="small-12 columns">
                <ul class="tabs show-for-medium-up" data-tab>
                    <li class="tab-title active"><a href="#panel1">Questa settimana</a></li>
                    <li class="tab-title"><a href="#panel2">Lista completa</a></li>
                </ul>

                <dl id="home" class="accordion" data-accordion>

                    <dd class="accordion-navigation">
                        <a href="#panel1" class="show-for-small-only">Questa settimana</a>

                        <div id="panel1" class="content active">
                            <div class="content-box section-box">
                                <ul class="tabs" data-tab>
                                    <li class="tab-title active"><a href="#panelLunedì">Lunedì</a></li>
                                    <li class="tab-title"><a href="#panelMartedì">Martedì</a></li>
                                    <li class="tab-title"><a href="#panelMercoledì">Mercoledì</a></li>
                                    <li class="tab-title"><a href="#panelGiovedì">Giovedì</a></li>
                                    <li class="tab-title"><a href="#panelVenerdì">Venerdì</a></li>
                                    <li class="tab-title"><a href="#panelSabato">Sabato</a></li>
                                    <li class="tab-title"><a href="#panelDomenica">Domenica</a></li>
                                </ul>
                                <div class="tabs-content">
                                    <div class="content" id="panelLunedì">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelMartedì">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelMercoledì">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelGiovedì">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelVenerdì">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelSabato">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                    <div class="content" id="panelDomenica">
                                        <div id="listItem" class="panel callout radius">
                                            <c:import url="filmItemList.jsp"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a href="#panel2" class="show-for-small-only">Lista completa</a>

                        <div id="panel2" class="content">
                            <div class="content-box section-box">
                                <div id="listItem" class="panel callout radius">
                                    <c:import url="filmItemList.jsp"/>
                                </div>
                            </div>
                        </div>
                    </dd>
                </dl>

            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="footer.jsp"/>

</body>

</html>
