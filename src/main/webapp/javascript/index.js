"use strict";

var Films = {
        url: "/film/",
        renderDay: function (data) {
            var template = $(this);
            var directives = {
                filmLink: {
                    href: function (params) {
                        return Films.url + this.id_film;
                    }
                },
                poster: {
                    src: function (params) {
                        return this.poster;
                    }
                },
                openModal: {
                    "data-reveal-id": function (params) {
                        return "modal" + params.index + template.attr("data-day");
                    }
                },
                'reveal-modal': {
                    id: function (params) {
                        return "modal" + params.index + template.attr("data-day");
                    }
                },
                shows: {
                    orari: {
                        show_time: {
                            text: function (params) {
                                var time = moment(this.show_time, "HH:mm:ss");
                                return time.format("LT");
                            }
                        }

                    }
                }

            };
            Transparency.render(template[0], data.filmList, directives);
            $(document).foundation();
        },
        renderWeek: function (data) {
            var template = $(this);
            var directives = {
                poster: {
                    src: function (params) {
                        return this.poster;
                    }
                }
            };
            Transparency.render(template[0], data.filmList, directives);
            $(document).foundation();
        },
        getFilmsDay: function (day, tab) {
            return $.ajax({
                dataType: "json",
                async: true,
                url: "/api/filmDay/" + day,
                context: tab
            });
        },
        getFilmsWeek: function (tab) {
            return $.ajax({
                dataType: "json",
                async: true,
                url: "/api/filmWeek",
                context: tab
            });
        },
        bind: function () {
            $('#daysTab').find('.content').on('toggled', function (event, tab) {
                event.preventDefault();
                var day = $(tab).attr('data-day');
                var request = Films.getFilmsDay(day, $(tab));
                request.done(Films.renderDay);
            });
            $('#panel2').on('toggled', function (event, tab) {
                event.preventDefault();
                var request = Films.getFilmsWeek($(tab));
                request.done(Films.renderWeek);
            });
        },
        first: function () {
            $('#daysLabel').find('a').first().click();
        }
    }
    ;

var Days = {
    tabSetDays: function () {
        var tabs = $('#daysTab').find('.content');
        tabs.each(function (index) {
            var day = moment();
            var addDay = day.add(index, 'd');
            $(this).attr("data-day", addDay.format('YYYY-MM-DD'));
        })
    },
    labelSetDays: function () {
        var labels = $('#daysLabel').find('a');
        labels.each(function (index) {
            var day = moment();
            var addDay = day.add(index, 'd');
            $(this).html(addDay.format('dddd D'));
        })
    }
};

$(function () {
    Days.tabSetDays();
    Days.labelSetDays();
    Films.bind();
    Films.first();
});