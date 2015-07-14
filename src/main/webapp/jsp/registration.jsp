<%--
  Created by IntelliJ IDEA.
  User: alessandro
  Date: 25/06/15
  Time: 19.26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Registrazione"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>
    <div class="row content">
        <h3>Completa i seguenti campi per effettuare la registrazione:</h3>

        <form action="<c:url value="/api/register"/>" method="post" data-abide="ajax" id="registerForm">
            <fieldset>
                <legend>Generalità utente</legend>
                <div class="row">
                    <div class="medium-6 columns">
                        <label>Nome:
                            <input type="text" name="name" placeholder="Mario" required/>
                        </label>
                        <small class="error">Il nome deve iniziare con una maiuscola</small>
                    </div>
                    <div class="medium-6 columns">
                        <label>Cognome:
                            <input type="text" name="surname" placeholder="Rossi" required/>
                        </label>
                        <small class="error">Il cognome deve iniziare con una maiuscola</small>
                    </div>
                </div>
                <div class="row">
                    <div class="medium-6 columns">
                        <label>Data di nascita:
                            <input type="date" name="birthday" placeholder="AAAA-MM-GG" required/>
                        </label>
                        <small class="error">Inserire la data nel giusto formato! (AAAA-MM-GG)</small>
                    </div>
                    <div class="medium-6 columns">
                        <label>Telefono:
                            <input type="tel" name="phone" placeholder="3481330331" required/>
                        </label>
                        <small class="error">Inserire il numero di telefono nel giusto formato!</small>
                    </div>
                </div>
                <div class="row">
                    <div class="medium-6 columns">
                        <label>Indirizzo email:
                            <input type="email" id="emailadd" name="email" placeholder="mario.rossi@gmail.com"
                                   required/>
                        </label>
                        <small class="error">Indirizzo email non valido!</small>
                    </div>
                    <div class="medium-6 columns">
                        <label>Conferma indirizzo email:
                            <input type="email" name="email" placeholder="mario.rossi@gmail.com" autocomplete="off"
                                   required
                                   data-equalto="emailadd"/>
                        </label>
                        <small class="error">Gli indirizzi email devono corrispondere!</small>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <legend>Credenziali d'accesso</legend>
                <div class="row">
                    <div class="medium-6 columns">
                        <label>Username:
                            <input type="text" name="username" placeholder="MRossi" required pattern=".{4,}"/>
                        </label>
                        <small class="error">Lo username deve contenere almeno 4 caratteri</small>
                    </div>
                </div>
                <div class="row">
                </div>
                <div class="row">
                    <div class="medium-6 columns">
                        <label>Password:
                            <input type="password" id="password" name="password" placeholder="Password" required
                                   aria-describedby="textpsw"/>
                        </label>
                        <small class="error">La password deve includere almeno 4 caratteri di cui almeno uno speciale.
                        </small>
                    </div>
                    <div class="medium-6 columns">
                        <label>Conferma password:
                            <input type="password" name="passwordconfirm" placeholder="Password" required
                                   data-equalto="password"/>
                        </label>
                        <small class="error">Le password devono corrispondere!</small>
                    </div>
                </div>
            </fieldset>
            <p></p>

            <div class="medium-12 centered text-center columns">
                <button class="button radius ladda-button" data-style="zoom-out">
                    <span class="ladda-label">
                    Conferma
                    </span>
                </button>
            </div>
        </form>

        <%--TODO: AGGIUNGERE MODALE CHE INDICHI L'INVIO DELLA MAIL DI CONFERMA--%>
    </div>
    <div class="push"></div>
</div>

<c:import url="/jsp/footer.jsp"/>

<script src="<c:url value="/javascript/registration.js"/>"></script>

</body>
</html>
