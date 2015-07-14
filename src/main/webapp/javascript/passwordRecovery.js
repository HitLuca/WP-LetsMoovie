
function successPassword(data) {
    saveNotification("success", "La password è stata correttamente modificata!");
    window.location.assign("/");

}
function errorPassword(data) {
    //TODO:FINIRE LAVORO
    var error = data.responseJSON.errorCode;
    switch (error) {
        case 0:case 2:
        {
            alertify.error("Errore, riprova!");
        }
            break;
        case 11:
        {
            saveNotification("error", "Il token non è più valido!");
            window.location.assign("/login");
        }
            break;
    }

    var passwordField = $("input[type='password']");
    passwordField.val('');
}

$(document).ready(function () {
    PostForm("setNewPassoword", successPassword, errorPassword);
});