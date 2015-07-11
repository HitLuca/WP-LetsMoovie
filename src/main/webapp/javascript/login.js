/**
 * Created by mion00 on 08/07/15.
 */

function successLogin(data) {
    saveNotification("success", "Benvenuto, " + data.username + "!");
    window.location.assign("/");

}
function errorLogin(data) {
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
            saveNotification("warning", "Sei già autenticato!");
            redirectToUser(JSON.username);
        }
            break;
    }

    var passwordField = $("input[type='password']");
    passwordField.val('');
}

function successSendMail(data) {
    alertify.success("È stata inviata una mail a " + data.email);
    $("#secondModal").foundation('reveal', 'open');
}

function errorSendMail(data) {
    var JSON = data.responseJSON;

    var code = JSON.errorCode;

    switch (code) {
        case 0:case 2:case 9: {
        alertify.error("Email non valida!");
        $("input[type='email']").val('');
    } break;
        case 7: {
            saveNotification("error", "Sei autenticato!");
            window.location.assign("/");
        }
    }
    $("#secondModal").foundation('reveal', 'open');
}

$(document).ready(function () {
    PostForm("loginForm", successLogin, errorLogin);
    PostForm("passwordRecovery", successSendMail, errorSendMail);
    PostForm("resendEmail", successSendMail, errorSendMail);
});