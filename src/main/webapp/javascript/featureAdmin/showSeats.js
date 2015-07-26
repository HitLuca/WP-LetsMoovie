var picker = new Pikaday({
    field: document.getElementById('datepicker'),
    onSelect: function (data) {
        $("#sceltaData").attr("value", moment(data).format("YYYY-MM-DD"));
        $("#sceltaData").trigger("submit");
    }
});

var getShowList = {
    correctData: function (data) {
        var selectItem = $("#Film");
        var directives = {
            film_Name: {
                html: function () {
                    return this.film_Name + " - " + moment(this.show_time, "HH:mm:ss").format("HH:mm");
                },
                value: function () {
                    return this.id_show;
                }
            }
        };
        Transparency.render(selectItem[0], data.showDataList, directives);
        $("#Film").select2({
            placeholder: "Scegli un film dalla lista"
        });
    },
    wrongData: function (data) {
        alertify.error("Data non valida");
    },
    correctShow: function (data) {
        Cinema3DView.init(document.getElementById("roomMap"), data.showSeatList, data.column, data.row, true);
    },
    wrongShow: function (data) {
        alertify.error("Errore nel recupero delle proiezioni");
    }
};

$(function () {
    Forms.PostForm("sceltaData", getShowList.correctData, getShowList.wrongData, false);
    Forms.PostForm("sceltaSpettacolo", getShowList.correctShow, getShowList.wrongShow, false);

    $("#Film").on("select2:select", function (data) {
        $("#sceltaSpettacolo").trigger("submit");
    });
});

