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

var Tickets = {
    posti: $("#riepilogoBiglietti"),
    successPost: function (data) {
        Notifications.saveNotification("warning", "Completa il pagamento per prenotare i posti!");
        //    TODO: REDIRECT A PAGINA DI PAGAMENTO
    },
    errorPost: function (data) {
        //    TODO: GESTIRE ERRORE
    },
    addSeat: function (event, column, row) {
        event.preventDefault();


        var posto = Tickets.posti.find("#posto").clone();
        posto.removeAttr("id");
        posto.removeClass("hide");
        posto.addClass("wow flipInX");
        posto.find("#columnNumber").html(column);
        posto.find("#rowNumber").html(row);
        posto.attr("data-position", column + "" + row);
        posto.find("[data-seat='column']").attr("value", column);
        posto.find("[data-seat='row']").attr("value", row);

        Tickets.posti.append(posto);
    },
    removeSeat: function (event, column, row) {
        event.preventDefault();
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
    Seats.init();
    Forms.PostForm("biglietti", Tickets.successPost, Tickets.errorPost, false);
    $("#resettaVisuale").on("click", function (event) {
        event.preventDefault();
        Cinema3DView.resetCamera();
    });
});