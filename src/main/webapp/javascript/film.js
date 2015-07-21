"use strict";

var Film = {
    seatUrl: "/seats/",
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
        var film = data.filmAndShowsData;
        var template = $("#content");
        var directives = {
            rating: {
                text: function () {
                    if (this.rating == -1)
                        return "Non conosciuto";
                    else return this.rating;
                }
            },
            metascore: {
                text: function () {
                    if (this.metascore == -1)
                        return "Non conosciuto";
                    else return this.metascore;
                }
            },
            duration: {
                text: function (params) {
                    return this.duration + params.value;
                }
            },
            vm: {
                text: function () {
                    switch (this.vm) {
                        case 0:
                        {
                            return "No";
                        }
                            break;
                        case 1:
                        {
                            return "Si";
                        }
                    }
                }
            },
            poster: {
                src: function (params) {
                    return "/img/poster/" + this.id_film + ".jpg";
                }
            },
            trailer: {
                src: function () {
                    return this.trailer;
                }
            },
            shows: {
                dayLabel: {
                    html: function (params) {
                        var date = moment(this.date);
                        return date.format("ddd D MMM");
                    },
                    href: function (params) {
                        var date = moment(this.date);
                        return "#panel" + date.format("YYYYMMDD");
                    }
                },
                dayPanel: {
                    id: function (params) {
                        var date = moment(this.date);
                        return "panel" + date.format("YYYYMMDD");
                    }
                },
                orari: {
                    show_time: {
                        text: function (params) {
                            var time = moment(this.show_time, "HH:mm:ss");
                            return time.format("LT") + " - Sala " + this.room_number;
                        },
                        href: function (params) {
                            return Film.seatUrl + this.id_show;
                        }
                    }

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