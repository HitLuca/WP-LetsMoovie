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

var GetRoomMap = {
    url: "/api/viewRoom/",
    getRoomMap: function (id) {
        var request = $.ajax({
            url: GetRoomMap.url + id
        });
        request.done(GetRoomMap.roomsSuccess);
        request.fail(GetRoomMap.roomError);
    },
    roomsSuccess: function (data) {
        Cinema3DView.init(document.getElementById("roomMap"), data.showSeatList, data.column, data.row, true)
    },
    roomError: function () {
        alertify.error("Errore nel visualizzare la mappa");
    }


};


$(function () {
    RoomsList.getRoomsInfo();
    $("#listaSale").on("change", function () {
        var id = $(this).find(":selected").attr("value");
        GetRoomMap.getRoomMap(id);
    })
});