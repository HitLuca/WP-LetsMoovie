/**
 * Created by mion00 on 24/07/15.
 */

var Films = {
    //TODO: necessario url per ricevere films
    url: "/api/",
    requestFilm: function () {
        var request = $.ajax({
            url: Films.url
        });
    }
};

$(function () {
    Films.requestFilm();
});