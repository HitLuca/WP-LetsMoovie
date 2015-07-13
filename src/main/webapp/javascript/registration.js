/**
 * Created by mion00 on 08/07/15.
 */

"use strict";

var Registration = {
    error: function (data) {
        var JSON = data.responseJSON;
        var error = JSON.errorCode;
        var fields = JSON.parameters;
        var i = 0;
        switch (error) {
            case 2:
            { //EMPTY WRONG FIELD
                for (i = 0; i < fields.length; i++) {
                    switch (fields[i]) {
                        case "username":
                        {
                            alertify.error("Username non valido!");
                        }
                            break;
                        case "name":
                        {
                            alertify.error("Nome non valido!");
                        }
                            break;
                        case "surname":
                        {
                            alertify.error("Cognome non valido!");
                        }
                            break;
                        case "email":
                        {
                            alertify.error("Email non valida!");
                        }
                            break;
                        case "birthday":
                        {
                            alertify.error("Data di nascita non valida!");
                        }
                            break;
                        case "phone":
                        {
                            alertify.error("Telefono non valido!");
                        }
                            break;
                        case "password":
                        {
                            alertify.error("Password non valida!");
                            this.find("input[type=password]").val('');
                        }
                            break;
                    }
                    this.find("input[name=" + fields[i].toString() + "]").val('');
                }
            }
                break;
            case 3: //USERNAME O MAIL GIÀ ASSOCIATA AD ACCOUNT
            {
                for (i = 0; i < fields.length; i++) {
                    switch (fields[i]) {
                        case "email":
                            alertify.error("L'email è già associata ad un account esistente");
                            break;
                        case "username":
                            alertify.error("Lo username scelto è già associato ad un account esistente");
                            break;
                    }
                    this.find("input[name=" + fields[i].toString() + "]").val('');
                }
            }
                break;
            case 7: //GIÀ LOGGATO
            {
                saveNotification("error", "Non puoi registrarti se sei autenticato!");
                redirectToUser(JSON.username);
            }
                break;
            case 0: //ERRORE GENERICO
            {
                alertify.error("Errore, riprova!");
            }
        }
    },
    success: function (data) {
        saveNotification("success", "Registrazione effettuata!");
        Session.setUsername(data.username);
    }

};


$(document).ready(function () {
    Forms.PostForm("registerForm", Registration.success, Registration.error, true);
});