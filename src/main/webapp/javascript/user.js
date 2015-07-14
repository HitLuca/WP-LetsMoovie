var User = {
    username: Session.getUsername(),
    displayUserData: function () {
        $.getJSON("/api/user/" + User.username)
            .done(function (data) {
                var directives = {
                    birthday: {
                        text: function (params) {
                            moment.locale("it");
                            return new moment(this.birthday).format("LL");
                        }
                    }
                };
                $("#panel1a").render(data, directives);
            })
            .fail(function (data) {
                var error = data.responseJSON.errorCode;
                switch (error) {
                    case 0:
                    case 2:
                    {
                        alertify.error("Errore!");
                    }
                        break;
                    case 8:
                    {
                        alertify.error("Non sei autorizzato a accedere a questa risorsa!");
                    }
                        break;
                    case 10:
                    {
                        Session.redirectToLogin();
                    }
                        break;
                }
            });
    }
};


$(function () {
    var name = Session.getUsername();
    if (name == null) {
        Session.redirectToLogin();
    } else {
        User.displayUserData();
    }


});