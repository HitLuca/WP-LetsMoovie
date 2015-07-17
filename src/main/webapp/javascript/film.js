"use strict";

var Film = {
    getId: function () {
        var link = window.location.pathname;
        var re = /\/film\/(\d+)/;
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
    renderFilm: function (data) {
        var film = data.filmData;
        var template = $("#content");
        var directives = {
            //TODO: mancano le proiezioni
            poster: {
                src: function (params) {
                    return "/img/poster/" + this.id_film + ".jpg";
                }
            },
            trailer: {
                src: function () {
                    return this.trailer;
                }
            }
        };
        $("#copertina").on('load', function (event) {
            event.preventDefault();
            var c = $("#copertina");
            c.removeClass("hide");
            c.addClass("animated fadeInLeft");
        });
        Transparency.render(template[0], film, directives);
        $(document).foundation();
    },
    getFilm: function () {
        var id = Film.getId();
        if (id == null) {
            Notifications.saveNotification("error", "Errore nel caricare il film richiesto!");
            Session.redirectToHome();
        } else {
            $.ajax({
                dataType: "json",
                url: "/api/film/" + id
            }).done(Film.renderFilm);
        }
    }


};

$(function () {
    Film.getFilm();
});