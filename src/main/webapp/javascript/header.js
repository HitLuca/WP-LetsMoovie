/**
 * Created by mion00 on 08/07/15.
 */

var checkFeatures = {
    check: function () {
        if (!Modernizr.localstorage) {
            var dialog = $("#oldBrowser");
            var title = dialog.find("p.title");
            var message = dialog.find("p.content");

            var alert = alertify.alert(title[0], message[0]);

            alert.set('label', 'Ok, lo farò!');
        }
    }
};

//LOGOUT
$("#logout").click(function (event) {
    event.preventDefault();

    var url = "/api/logout";

    var posting = $.post(url);

    posting.done(function () {
        Notifications.saveNotification("success", "Hai effettuato il logout!");
        Session.logout();
        window.location.assign("/");
    });

    posting.fail(function () {
        alertify.error("Errore");
    });
});

$(function () {
    checkFeatures.check();
});