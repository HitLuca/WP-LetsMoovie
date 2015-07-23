var RoomsList = {
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
    },
    showRoomsError: function (data) {
        alertify.error("Errore nel recuperare la lista delle sale");
    }
};

var SeatsList = {
    correctData: function (data) {

        Cinema3DView.init(document.getElementById("roomMap"), data.showSeatList, data.column, data.row, true);
    },
    wrongData: function (data) {
        alertify.error("Dati non validi");
    }
};


$(function () {
    RoomsList.getRoomsInfo();
    Forms.PostForm("selezionePercentuale", SeatsList.correctData, SeatsList.wrongData, false);
});