<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Login"/>
</c:url>
<c:import url="${url}"/>
<body>
<div class="wrapper">
    <c:import url="/jsp/header.jsp"/>
    <div class="row content">
        <div class="small-11 small-centered large-6 medium-8 columns">
            <h3>Accedi al tuo account:</h3>

            <form action="doLogin" id="loginForm" data-abide="ajax">
                <div>
                    <label>
                        Username
                        <input type="text" name="username" required
                               placeholder="Inserisci il tuo username"/>
                    </label>
                    <small class="error">Username richiesto!</small>

                </div>
                <div>
                    <label>
                        Password
                        <input type="password" name="password" required
                               placeholder="Inserisci la tua password"/>
                        <%--TODO:CHECK PASSWORD RICHIESTA--%>
                    </label>
                    <small class="error">Password richiesta!</small>
                </div>

                <div class="row">
                    <div class="medium-6 columns">
                        <button class="button radius expand">Login</button>
                    </div>
                    <div class="medium-6 columns">
                        <a href="#" class="button radius expand" data-reveal-id="firstModal">Password dimenticata?</a>
                    </div>
                </div>
            </form>
            <div id="firstModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true"
                 role="dialog">
                <form id="passwordRecovery" data-abide="ajax">
                    <h2 id="modalTitle">Hai dimenticato la tua password?</h2>

                    <p></p>

                    <p>Per reimpostare la nuova password, inserisci il tuo indirizzo email nel campo sottostante.<br>
                        Let's Moovie ti invierà le istruzioni per reimpostare la nuova password all'inidirizzo email
                        collegato a questo account.</p>

                    <div class="row text-center">
                        <div class="medium-4 columns">
                            <h3>Email:</h3>
                        </div>
                        <div class="medium-8 columns">
                            <label>
                                <input type="email" id="emailadd" name="email" placeholder="mario.rossi@gmail.com"
                                       required/>
                            </label>
                            <small class="error">Indirizzo email obbligatorio!</small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="medium-12 centered text-center columns">
                            <button class="button radius">Invia</button>
                        </div>
                    </div>
                    <a class="close-reveal-modal" aria-label="Close">&#215;</a>
                </form>
                <form id="sendEmail">
                    <div id="secondModal" class="reveal-modal" data-reveal aria-labelledby="secondModalTitle"
                         aria-hidden="true" role="dialog" data-options="close_on_background_click:false">
                        <h2 id="secondModalTitle"> Controlla la tua casella di posta elettronica!</h2>

                        <p></p>

                        <p>Abbiamo provveduto ad inviarti una e-mail all'indirizzo inserito.<br>
                            Per completare correttamente la procedura di recupero password segui le istruzioni che trovi
                            nell'e-mail.
                        </p>

                        <p></p>

                        <h3>L'e-mail non ti è ancora arrivata?</h3>

                        <p>Ti consigliamo di attendere qualche minuto o di controllare nella cartella Spam. <br>
                            Nel caso tu non l'abbia effettivamente ricevuta, fai click sul bottone "Invia nuovamente".
                        </p>

                        <form id="resendEmail">
                            <div class="row">
                                <div class="medium-12 columns">
                                    <button class="button radius">Invia nuovamente</button>
                                </div>
                            </div>
                        </form>
                        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="push"></div>
</div>
<c:import url="/jsp/footer.jsp"/>

<script src="<c:url value="/javascript/login.js"/>"></script>

</body>
</html>
