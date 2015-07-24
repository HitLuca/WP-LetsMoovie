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
        UserCredit.getUserCredit();
    },
    showTicketsError: function (data) {

    }

};

var UserCredit = {
    url: "/api/credit/",
    template: $("#userCredit"),
    getUserCredit: function () {
        var request = $.ajax({
            url: UserCredit.url + Session.getUsername()
        });
        request.done(UserCredit.showUserCredit);
        request.fail(UserCredit.errorUserCredit);
    },
    errorUserCredit: function (data) {
        Notifications.saveNotification("error", "Errore nel recupeare il credito utente");
        Session.redirectToHome();
    },
    showUserCredit: function (data) {
        var render = {
            credit: {
                html: function () {
                    return numeral(this.credit).format('0,0.00 $');
                }
            }
        };
        Transparency.render(UserCredit.template[0], data, render);
    }
};

var Payment = {
    url: "/api/payment/",
    id: null,
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
            Payment.id = m[1];
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
        $("#confermaPagamento").on("click", Payment.sendPayment);
    },
    sendPayment: function () {
        var data = {
            code: Payment.id,
            credit_card_number: CreditCard.selected ? CreditCard.selected : null
        };
        var request = $.ajax({
            url: Payment.url,
            method: "POST",
            data: JSON.stringify(data),
            type: "json"
        });
        request.done(Payment.successPayment);
        request.fail(Payment.errorPayment);
    },
    successPayment: function (data) {
        alertify.success("Il pagamento è andato a buon fine!");
    },
    errorPayment: function(data) {
        alertify.error("Errore nel processare il pagamento!");
    }
};

var CreditCard = {
    url: "/api/debitCards/",
    cardsList: $("#cardsList"),
    getCreditCards: function () {
        var request = $.ajax({
            url: CreditCard.url + Session.getUsername()
        });
        request.done(CreditCard.showCreditCards);
        request.fail(CreditCard.errorCreditCards)
    },
    bindCreditCards: function () {
        CreditCard.cardsList.find(".creditCard").on("click", function (event) {
            event.preventDefault();
            CreditCard.modal.foundation("reveal", "close");
            var button = $(event.target);
            var number = button.html();
            CreditCard.selected = number;
            var selectedCard = $("#selectedCard");
            selectedCard.removeClass("hide");
            selectedCard.addClass("animated zoomIn");

            var card = selectedCard.find("#paymentCard");
            card.html(number);
        });
    },
    showCreditCards: function (data) {
        var template = CreditCard.modal[0];

        Transparency.render(template, data);
        CreditCard.bindCreditCards();
    },
    errorCreditCards: function (data) {
        Notifications.saveNotification("error", "Errore nel recuperare informazioni sulla carta di credito");
        CreditCard.modal.foundation('reveal', 'close');
        Session.redirectToLogin();
    },
    addCreditCardSuccess: function (data) {
        alertify.success("Carta di credito aggiunta con successo!");
        CreditCard.getCreditCards();
    },
    addCreditCardError: function (data) {
        alertify.error("Errore nell'aggiungere la carta indicata");
    },
    init: function () {
        //TODO QUANDO SI CLICCA SU UNA CARTA
        var modal = $("#cardsList");
        modal.on('opened.fndtn.reveal', function () {
            CreditCard.modal = $(this);
            if (!CreditCard.bind) {
                CreditCard.getCreditCards();
                CreditCard.bind = true;
            }
        });
        $("#addCard").on('click', function (event) {
            event.preventDefault();
            $("#addCard").hide();
            var addForm = $("#addForm");
            addForm.removeClass("hide");
            addForm.addClass("animated fadeIn");
            Forms.PostForm("addForm", CreditCard.addCreditCardSuccess, CreditCard.addCreditCardError, false);
        });
        $("#rimuoviCarta").on("click", function(event) {
            event.preventDefault();
            var selectedCard = $("#selectedCard");
            selectedCard.addClass("hide");
            CreditCard.selected = null;
        });
    }
};

$(function () {
    Payment.init();
    CreditCard.init();
});