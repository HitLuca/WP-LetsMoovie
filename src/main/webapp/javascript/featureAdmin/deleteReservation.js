numeral.language('it');

var getCode = {
    correctCode: function (data) {
        console.log(data);
        $(".checkbox-biglietto").removeClass("hide");
        var directives = {
            price: {
                text: function () {
                    return numeral(this.price).format('0,0.00 $');
                }
            }
        };
        Transparency.render($("#postiPrenotazione")[0], data.seatList, directives);
    },
    wrongCode: function (data) {
        alertify.error("Codice inserito non valido");
    }
};

var cancellaPrenotazione = {
    successCancella: function () {

    },
    failCancella: function () {

    }
};

$(function () {
    Forms.PostForm("codicePrenotazione", getCode.correctCode, getCode.wrongCode, false);
    Forms.PostForm("cancellaPrenotazione", cancellaPrenotazione.successCancella, cancellaPrenotazione.failCancella, false);
});