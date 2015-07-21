"use strict";

var Reservation = {
    url: "/api/reservation/",
    getReservationInfo: function (id) {
        var request = $.ajax({
            url: Reservation.url + id
        });
        request.done(Map.renderMap);
        request.fail(Seats.error);
    }

};

var Payment = {
    getIdPayment: function () {
        var link = window.location.pathname;
        var re = /\/payment\/(\d+)/;
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
    init: function () {
        var id = Payment.getIdPayment();
        if (id == null) {
            Notifications.saveNotification("error", "Errore nel recuperare informazioni sulla prenotazione!");
            Session.redirectToHome();
            return;
        }
        Reservation.getReservationInfo(id);
    }
};


$(function () {
    Payment.init();
});