/**
 * Created by mion00 on 08/07/15.
 */

//LOGOUT
$("#logout").click(function (event) {
    event.preventDefault();

    var url = "/api/logout";

    var posting = $.post(url);

    posting.done(function () {
        saveNotification("success", "Hai effettuato il logout!");

        window.location.assign("/");
    });

    posting.fail(function () {
        alertify.error("Errore");
    });
});