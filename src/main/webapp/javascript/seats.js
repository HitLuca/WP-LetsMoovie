var map = {
    renderMap: function (data) {
        var container = $("#roomMap")[0];
        Cinema3DView.init(
            container,
            data.showSeatList,
            data.column,
            data.row
        );
    }
};

var seats = {
    url: "/api/viewShowRoom/",
    getIdShow: function () {
        var link = window.location.pathname;
        var re = /\/seats\/(\d+)/;
        var m;

        if ((m = re.exec(link)) !== null) {
            if (m.index === re.lastIndex) {
                re.lastIndex++;
            }
            // View your result using the m-variable.
            // eg m[0] etc.
            return m[1];
        } else return null;
    },
    getShowInfo: function (id) {
        var request = $.ajax({
            url: seats.url + id
        });
        request.done(map.renderMap);
        request.fail(seats.error);
    },
    init: function () {
        var id = seats.getIdShow();
        if (id == null) {
            Notifications.saveNotification("error", "Errore nel recuperare informazioni sulla sala!");
            Session.redirectToHome();
            return;
        }
        seats.getShowInfo(id);

    },
    error: function (data) {
        var JSON = data.responseJSON;
        switch (JSON.errorCode) {
            case 10:
            {
                Notifications.saveNotification("warning", "Devi aver effettuato l'accesso per visualizzare la sala!");
                Session.redirectToLogin();
            }
                break;
            default:
            {
                Notifications.saveNotification("error", "Errore nel recuperare informazioni sulla sala!");
                Session.redirectToHome();
            }
        }
    }
};


$(function () {
    seats.init();
});