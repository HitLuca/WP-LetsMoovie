var confirmRegistration = {
    success: function (data) {
        Notifications.saveNotification("success", "Account confermato!");
        Session.setUsername(data.username);
        Session.redirectToUser();
    },
    error: function (data) {
        var JSON = data.responseJSON;
        var error = JSON.errorCode;
        switch (error) {
            case 0:
            {
                Notifications.saveNotification("error", "Errore, riprova!");
                Session.redirectToRegister();
            }
                break;
            case 7:
            {
                Notifications.saveNotification("error", "Sei autenticato!");
                Session.redirectToUser();
            }
                break;
            case 11:
            {
                Notifications.saveNotification("error", "Codice di verifica non valido!");
                Session.redirectToRegister();
            }
        }
    },
    getVerificationCode: function (name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "))
    },
    postConfirm: function () {
        var verificationObject = {verificationCode: confirmRegistration.getVerificationCode("verificationCode")};
        console.log(verificationObject);
        var ajax = $.ajax({
            type: "POST",
            url: "/api/confirmRegistration",
            data: JSON.stringify(verificationObject),
            dataType: "json"
        });

        ajax.done(confirmRegistration.success);
        ajax.fail(confirmRegistration.error);
    }
};

confirmRegistration.postConfirm();