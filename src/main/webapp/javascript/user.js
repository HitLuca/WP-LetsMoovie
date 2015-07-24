numeral.language('it');

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

var Cards = {
    url: "/api/debitCards/",
    displayUserCards: function () {
        var request = $.ajax({
            url: Cards.url + User.username
        });
        request.done(Cards.showCardsInfo);
        request.fail(Cards.showCardError);
    },
    showCardsInfo: function (data) {
        var item = $("#contenitoreCarte");
        Transparency.render(item[0], data.cards);
    },
    showCardError: function () {
        alertify.error("Errore nel recuperare la lista delle carte associate all'account");
    }
};

var Credit = {
    url: "/api/credit/",
    displayCredit: function () {
        var request = $.ajax({
            url: Credit.url + User.username
        });
        request.done(Credit.showCreditInfo);
        request.fail(Credit.showCreditError);
    },
    showCreditInfo: function (data) {
        var credit = $("#credit");
        credit.html(numeral(data.credit).format('0,0.00 $'));
    },
    showCreditError: function () {
        alertify.error("Errore nel recuperare il credito associato all'account");
    }
}


$(function () {
    var name = Session.getUsername();
    if (name == null) {
        Session.redirectToLogin();
    } else {
        User.displayUserData();
    }
    Cards.displayUserCards();
    Credit.displayCredit();
});