var Map = {
    id: "#roomMap",
    renderMap: function (data) {
        var container = $(Map.id)[0];
        Cinema3DView.init(
            container,
            data.showSeatList,
            data.column,
            data.row
        );
        Map.listeners();
    },
    listeners: function () {
        //TODO: USATO PER DEBUG
        $(Map.id).on("onSeatAdd", Tickets.addSeat);
        $(Map.id).on("onRemoveAdd", Tickets.removeSeat);
    }
};

numeral.language("it");

var Tickets = {
    total: 0,
    form: $("#biglietti"),
    posti: $("#riepilogoBiglietti"),
    addSeat: function (event, column, row) {
        event.preventDefault();

        Tickets.total++;
        if (Tickets.total == 1) {
            Tickets.form.removeClass("hide");
            Tickets.form.addClass("wow fadeIn");
        }
        var posto = Tickets.posti.find("#posto").clone();
        posto.removeAttr("id");
        posto.removeClass("hide");
        posto.addClass("wow flipInX");
        posto.find("#columnNumber").html(column + 1);
        posto.find("#rowNumber").html(row + 1);
        posto.attr("data-position", column + "" + row);
        posto.find("select").removeAttr("disabled");
        posto.find("[data-seat='column']").attr("value", column).removeAttr("disabled");
        posto.find("[data-seat='row']").attr("value", row).removeAttr("disabled");
        TicketsType.showPrice(posto);
        Tickets.posti.append(posto);
    },
    removeSeat: function (event, column, row) {
        event.preventDefault();
        Tickets.total--;
        if (Tickets.total == 0) {
            Tickets.form.addClass("hide");
        }
        var posto = Tickets.posti.find("[data-position=" + column + "" + row + "]");
        posto.remove();
    }
};

var Seats = {
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
            url: Seats.url + id
        });
        request.done(Map.renderMap);
        request.fail(Seats.error);
    },
    init: function () {
        var id = Seats.getIdShow();
        if (id == null) {
            Notifications.saveNotification("error", "Errore nel recuperare informazioni sulla sala!");
            Session.redirectToHome();
            return;
        }
        Seats.getShowInfo(id);
        $("#show").attr("value", id);

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

var Reservation = {
    redirectUrl: "/payment/",
    successPost: function (data) {
        //    TODO: REDIRECT A PAGINA DI PAGAMENTO
        Notifications.saveNotification("warning", "Completa il pagamento per prenotare i posti!");
        window.location.assign(Reservation.redirectUrl + data.reservationCode);
    },
    errorPost: function (data) {
        //    TODO: GESTIRE ERRORE
    }
};

var TicketsType = {
    url: "/api/tickets",
    init: function () {
        var request = $.ajax({
            url: TicketsType.url
        });
        request.done(TicketsType.successGet);
    },
    successGet: function (data) {
        var lista = $("#ticketsList");
        var directives = {
            tickets: {
                ticket_type: {
                    html: function () {
                        return this.ticket_type;
                    },
                    value: function () {
                        return this.ticket_type;
                    },
                    price: function () {
                        return numeral(this.price).format('0,0.00 $');
                    }
                }
            }
        };
        Transparency.render(lista[0], data, directives);
    },
    showPrice: function (posto) {
        var select = $(posto).find(".ticketChoice");
        var price = $(posto).find("#price");
        price.html(select.find("option:selected").attr("price"));
        select.change(function (event) {
            var selected = $(this).find("option:selected");
            var value = selected.attr("price");
            price.html(value);
        })
    }
};

$(function () {
    TicketsType.init();
    Seats.init();
    Forms.PostForm("biglietti", Reservation.successPost, Reservation.errorPost, false);
    $("#resettaVisuale").on("click", function (event) {
        event.preventDefault();
        Cinema3DView.resetCamera();
    });
    $("#bloccaVisuale").on("click", function (event) {
        //event.preventDefault();
        //Cinema3DView.resetCamera();
        Cinema3DView.lockView();
    });
});