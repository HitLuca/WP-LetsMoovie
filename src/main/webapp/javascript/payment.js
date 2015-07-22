"use strict";

numeral.language('it');

var Reservation = {
    url: "/api/reservation/",
    getReservationInfo: function (id) {
        var request = $.ajax({
            url: Reservation.url + id
        });
        request.done(Reservation.showTicketsInfo);
        request.fail(Reservation.showTicketsError);
    },
    showTicketsInfo: function (data) {
        var template = $("#listaPostiScelti");
        var directives = {
            seats: {
                price: {
                    text: function () {
                        return numeral(this.price).format('0,0.00 $');
                    }
                }
            },
            total: {
                text: function () {
                    return numeral(this.total).format('0,0.00 $');
                }
            }
        };
        Transparency.render(template[0], data, directives);
    },
    showTicketsError: function (data) {

    }

};

var Payment = {
    getIdPayment: function () {
        var link = window.location.pathname;
        var re = /\/payment\/(.+)/;
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

var CreditCard = {
    url: "/api/",
    getCreditCards: function () {

    }
};

$(function () {
    Payment.init();
});