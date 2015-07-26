var RoomsList = {
    bind: false,
    url: "/api/adminData/getRooms",
    getRoomsInfo: function () {
        var request = $.ajax({
            url: RoomsList.url
        });
        request.done(RoomsList.showRoomsInfo);
        request.fail(RoomsList.showRoomsError);
    },
    showRoomsInfo: function (data) {
        var selectItem = $("#listaSale");
        var directives = {
            room_number: {
                html: function () {
                    return this.room_number;
                },
                value: function () {
                    return this.room_number;
                }
            }
        };
        Transparency.render(selectItem[0], data.roomList, directives);
        selectItem.prepend("<option disabled selected hidden>Scegli una sala dalla lista</option>");
        if (!RoomsList.bind) {
            RoomsList.bind = true;
            selectItem.on("change", function (event) {
                event.preventDefault();
                $("#salaCinema").trigger("submit");
                RoomMap.id = $(this).find(":selected").attr("value");
            });
        }
    },
    showRoomsError: function (data) {
        alertify.error("Errore nel recuperare la lista delle sale");
    }
};

var RoomMap = {
    id: null,
    map: document.getElementById("roomMap"),
    url: "/api/viewRoom/",
    roomsSuccess: function (data) {
        Cinema3DView.init2(RoomMap.map, data.showSeatList, data.column, data.row, false, true);
        RoomMap.bindEvents();
        //    SALVA NUMERO SALA NELLA FORM USATA PER MODIFICARE I POSTI NELLA SALA
        $("#roomId").val(RoomMap.id);
    },
    roomError: function () {
        alertify.error("Errore nel visualizzare la mappa");
    },
    bindEvents: function () {
        $(RoomMap.map).on("onSeatChange", ChangedSeats.addSeat);
        $(RoomMap.map).on("onResetChange", ChangedSeats.removeSeat);
    }

};

var ChangedSeats = {
    form: $("#conferma"),
    posto: $("#posto"),
    posti: $("#posti"),
    addSeat: function (event, y, x) {
        event.preventDefault();

        var posto = ChangedSeats.posto.clone();
        posto.attr("id", "");
        posto.attr("data-coordinate", "" + x + "" + y);
        posto.find("#s_row").removeAttr("disabled").val(x);
        posto.find("#s_column").removeAttr("disabled").val(y);
        alertify.success("Selezionato posto alla fila " + (1 + x) + " colonna " + (1 + y));
        ChangedSeats.posto.after(posto);

    },
    removeSeat: function (event, y, x) {
        event.preventDefault();

        var posto = ChangedSeats.posti.find("[data-coordinate='" + x + "" + y + "']");
        posto.remove();

        alertify.success("Posto deselezionato");
    },
    successPost: function (data) {
        alertify.succes("I posti selezionati sono stati correttamente modificati");
    },
    errorPost: function (data) {
        alertify.error("Errore nel modificare i posti selezionati");
    }
};

$(function () {
    RoomsList.getRoomsInfo();
    Forms.PostForm("salaCinema", RoomMap.roomsSuccess, RoomMap.roomError);
    Forms.PostForm("conferma", ChangedSeats.successPost, ChangedSeats.errorPost, false);
});