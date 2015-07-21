/**
 * Created by mion00 on 08/07/15.
 */

var Login = {
    success: function (data) {
        Notifications.saveNotification("success", "Benvenuto, " + data.username + "!");
        Session.setUsername(data.username);
        var url;
        if ((url = Session.storage.get("backUrl")) != null) {
            Session.storage.remove("backUrl");
            Session.redirectToUrl(url);
        } else
            Session.redirectToUser();
    },
    error: function (data) {
        var error = data.responseJSON.errorCode;
        switch (error) {
            case 0:
            {
                alertify.error("Errore, riprova!");
            }
                break;
            case 2:
            {
                alertify.error("Username o password errata!");
            }
                break;
            case 7:
            {
                Notifications.saveNotification("warning", "Sei già autenticato!");
                Session.redirectToUser(JSON.username);
            }
                break;
        }

        var passwordField = this.find("input[type='password']");
        passwordField.val('');
    }

};
var ResetPassword = {
    success: function (data) {
        alertify.success("È stata inviata una mail a " + data.email);
        var modal = $("#secondModal");
        //SETTA L'INPUT NASCOSTO AL VALORE DELLA MAIL INVIATA
        modal.find("input").attr("value", data.email);
        try {
            modal.foundation('reveal', 'open');
        } catch (e) {

        }
    },
    error: function (data) {
        var JSON = data.responseJSON;

        var code = JSON.errorCode;

        switch (code) {
            case 0:
            case 2:
            case 9:
            {
                alertify.error("Email non valida!");
                this.find("input[type='email']").val('');
            }
                break;
            case 7:
            {
                Notifications.saveNotification("error", "Sei autenticato!");
                window.location.assign("/");
            }
        }
    }

};

$(document).ready(function () {
    Forms.PostForm("loginForm", Login.success, Login.error, true);
    Forms.PostForm("passwordRecovery", ResetPassword.success, ResetPassword.error, true);
    Forms.PostForm("resendEmail", ResetPassword.success, ResetPassword.error, false);
});