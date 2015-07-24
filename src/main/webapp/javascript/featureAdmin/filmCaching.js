/**
 * Created by mion00 on 24/07/15.
 */

var Films = {
    //TODO: necessario url per ricevere films
    url: "/api/adminData/getFilmList",
    requestFilm: function () {
        var request = $.ajax({
            url: Films.url
        });
        request.done(Films.showFilm);
        request.fail();
    },
    showFilm: function (data) {
        var set = $("#listaFilm");
        var directives = {
            film_title: {
                html: function () {
                    return this.film_title;
                },
                film_id: function () {
                    return this.id_film;
                }
            }
        };
        Transparency.render(set[0], data.filmList, directives);
    },
    errorFilm: function (data) {
        alertify.error("Errore nel recupero dei film")
    }
};

var getIncome = {
    url: "/api/adminFunction/getFilmIncome",
    getIncome: function (id) {
        var request = $.ajax({
            url: getIncome.url + id
        });
        request.done(getIncome.incomeSuccess);
        request.fail(getIncome.incomeError);
    },
    incomeSuccess: function (data) {
    },
    incomeError: function () {
        alertify.error("Errore nel recuperare l'incasso relativo al film");
    }
}

$(function () {
    Films.requestFilm();
    $("#listaFilm").on("change", function () {
        var id = $(this).find(":selected").attr("film_id");
        getIncome.getIncome(titolo);
    })
});