/**
 * Created by mion00 on 24/07/15.
 */
"use strict";

numeral.language('it');

var Films = {
    url: "/api/adminData/getFilmList",
    requestFilm: function () {
        var request = $.ajax({
            url: Films.url
        });
        request.done(Films.showFilm);
        request.fail(Films.errorFilm);
    },
    showFilm: function (data) {
        var set = $("#listaFilm");
        var directives = {
            film_title: {
                html: function () {
                    return this.film_title;
                },
                value: function () {
                    return this.id_film;
                }
            }
        };
        Transparency.render(set[0], data.filmList, directives);
        set.select2({
            placeholder: "Scegli un film dalla lista"
        });
        set.on("select2:select", function (event) {
            event.preventDefault();
            $("#sceltaFilm").trigger("submit");
        });
    },
    errorFilm: function (data) {
        alertify.error("Errore nel recupero dei film")
    }
};

var getIncome = {
    incomeSuccess: function (data) {
        var income = numeral(data.income).format('0,0.00 $');
        $("#incasso").html(income);
    },
    incomeError: function () {
        alertify.error("Errore nel recuperare l'incasso relativo al film");
    }
};

$(function () {
    Films.requestFilm();
    Forms.PostForm("sceltaFilm", getIncome.incomeSuccess, getIncome.incomeError, false);
});