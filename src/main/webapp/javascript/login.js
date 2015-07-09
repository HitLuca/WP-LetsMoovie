/**
 * Created by mion00 on 08/07/15.
 */

function successNotifier(data) {
    saveNotification("success", "Benvenuto, " + data.username + "!");
    window.location.assign("/");

}
function errorNotifier(data) {
    var error = data.responseJSON.errorCode;
    switch (error) {
        case 7:
        {
            saveNotification("warning", "Sei già autenticato!");
            window.location.assign("/");
        }
            break;
        default:
        {
            alertify.error("Username o password errata!");
        }
    }

    var passwordField = $("input[type='password']");
    passwordField.val('');
}

$(document).ready(function () {
    PostForm("loginForm", successNotifier, errorNotifier)
});