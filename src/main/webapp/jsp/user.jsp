<%--
  Created by IntelliJ IDEA.
  User: alessandro
  Date: 08/07/15
  Time: 10.37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Registration"/>
</c:url>
<c:import url="${url}"/>
<body>
<c:import url="/jsp/header.jsp"/>
<div class="row content">
    <h3>Informazioni utente</h3>
    <ul class="accordion" data-accordion>
        <li class="accordion-navigation">
            <a href="#panel1a">Generalità</a>

            <div id="panel1a" class="content active">
                <div class="panel radius">
                    <h3>Generalità utente</h3>

                    <p></p>

                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Nome:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="name"></span>
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Cognome:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="surname"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Data di nascita:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="birthday"></span>
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Indirizzo email:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="email"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Telefono:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="phone_number"></span>
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-5 columns">
                                    Username:
                                </div>
                                <div class="medium-7 text-center columns">
                                    <span id="username"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </li>
        <li class="accordion-navigation">
            <a href="#panel2a">Acquisti</a>

            <div id="panel2a" class="content">
                <div class="panel radius">
                    <h3>Storico acquisti</h3>

                    <p></p>

                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Film 1
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Film 2
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Film 3
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Film 4
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </li>
        <li class="accordion-navigation">
            <a href="#panel3a">Portafoglio</a>

            <div id="panel3a" class="content">
                <div class="panel radius">
                    <h3>Carte registrate e credito disponibile</h3>

                    <p></p>

                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Carta 1
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Carta 2
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Carta 3
                                </div>
                            </div>
                        </div>
                        <div class="medium-6 columns">
                            <div class="row">
                                <div class="medium-12 columns">
                                    Carta 4
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</div>
<c:import url="/jsp/footer.jsp"/>
</body>
</html>
