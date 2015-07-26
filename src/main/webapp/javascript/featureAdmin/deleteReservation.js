numeral.language('it');

var getCode = {
    code: null,
    correctCode: function (data) {
        console.log(data);
        $(".checkbox-biglietto").removeClass("hide");
        var directives = {
            price: {
                text: function () {
                    return numeral(this.price).format('0,0.00 $');
                }
            },
            s_row: {
                text: function () {
                    return this.s_row + 1;
                }
            },
            s_column: {
                text: function () {
                    return this.s_column + 1;
                }
            },
            s_row_input: {
                value: function () {
                    return this.s_row;
                }
            },
            s_column_input: {
                value: function () {
                    return this.s_column;
                }
            }
        };
        Transparency.render($("#postiPrenotazione")[0], data, directives);
        getCode.code = $("#sentCode").val();
        $("#reservationCode").val(getCode.code);
    },
    wrongCode: function (data) {
        alertify.error("Codice inserito non valido");
    }
};

var cancellaPrenotazione = {
    successCancella: function () {
        alertify.success("Prenotazione cancellata con successo!");
    },
    failCancella: function () {
        alertify.error("Errore nel cancellare la prenotazione");
    }
};

$(function () {
    Forms.PostForm("codicePrenotazione", getCode.correctCode, getCode.wrongCode, false);
    Forms.PostForm("cancellaPrenotazione", cancellaPrenotazione.successCancella, cancellaPrenotazione.failCancella, false);
});