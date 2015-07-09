/**
 * Created by mion00 on 08/07/15.
 */

"use strict";

function successNotifier(data) {
    saveNotification("success", "Registrazione effettuata!");
    window.location.assign("user/" + data.username);
}
function errorNotifier(data) {
    var JSON = data.responseJSON;
    var error = JSON.errorCode;
    switch (error) {
        case 2:
        { //EMPTY WRONG FIELD
            var fields = JSON.invalidParameters;
            for (var i = 0; i < fields.length; i++) {
                switch (fields[i]) {
                    case "password":
                    {
                        alertify.error("Password non valida!");
                    }
                        break;
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
                }
                $("input[name=" + fields[i].toString() + "]").val('');
            }
        }
            break;
        case 3:
        {
            alertify.error("Username non disponibile!");
            //    TODO:finire
        }
            break;
    }

}

$(document).ready(function () {
    PostForm("registerForm", successNotifier, errorNotifier)
});
