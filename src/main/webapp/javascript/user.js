var User = {
    displayUserData: function () {

    }
};


$(function () {
    var name = Session.getUsername();
    if (name == null) {
        Session.redirectToLogin();
    } else {
        $.getJSON("/api/user/"+name)
            .done(function (data) {
                $("#panel1a").render(data);
            })
            .fail(function (data) {

            });
    }


});