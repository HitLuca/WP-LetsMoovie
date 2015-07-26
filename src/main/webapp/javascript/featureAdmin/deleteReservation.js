numeral.language('it');

var getCode = {
    code: null,
    correctCode: function (data) {
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
                value: function (params) {
                    return this.s_row;
                },
                name: function (params) {
                    return "seatList[" + params.index + "][s_row]";
                }
            },
            s_column_input: {
                value: function () {
                    return this.s_column;
                },
                name: function (params) {
                    return "seatList[" + params.index + "][s_column]";
                }
            },
            s_checked: {
                name: function (params) {
                    return "seatList[" + params.index + "][checked]:string";
                }
            }
        };
        Transparency.render($("#postiPrenotazione")[0], data, directives);
        getCode.code = $("#sentCode").val();
        $("#reservationCode").val(getCode.code);
        $("input:checkbox").removeAttr("checked");
    },
    wrongCode: function (data) {
        var errorCode = data.responseJSON.errorCode;
        if (errorCode != null) {
            switch (errorCode) {
                case 15:
                {
                    alertify.error("Lo spettacolo è già stato proiettato!");
                }
                    break;
                default:
                    alertify.error("Codice inserito non valido!");
            }
        } else {
            alertify.error("Codice inserito non valido!");
        }
    }
};

var cancellaPrenotazione = {
    successCancella: function () {
        alertify.success("Prenotazione cancellata con successo!");
        $("#codicePrenotazione").trigger('submit');
    },
    failCancella: function (data) {
        var JSON = data.responseJSON;
        var errorCode = JSON.errorCode;
        if (errorCode != null) {
            switch (errorCode) {
                case 2:
                    alertify.warning("Nessun biglietto selezionato!");
                    break;
                default:
                    alertify.error("Errore nel cancellare la prenotazione");
            }
        } else {
            alertify.error("Errore nel cancellare la prenotazione");
        }
    }
};

$(function () {
    Forms.PostForm("codicePrenotazione", getCode.correctCode, getCode.wrongCode, false);
    Forms.PostForm("cancellaPrenotazione", cancellaPrenotazione.successCancella, cancellaPrenotazione.failCancella, false);
});