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
var Payments = {
    format: function (d) {
        var string = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;width:100%">';
        string += '<tr>' +
            '<th>' + 'Tipologia' + '</th>' +
            '<th>' + 'Prezzo' + '</th>' +
            '<th>' + 'Fila' + '</th>' +
            '<th>' + 'Posto' + '</th>' +
            '</tr>';
        // `d` is the original data object for the row
        d.seatsPaid.forEach(function (paid) {
            string += '<tr>' +
                '<td>' + paid.ticket_type + '</td>' +
                '<td>' + numeral(paid.price).format('0,0.00 $') + '</td>' +
                '<td>' + paid.s_row + '</td>' +
                '<td>' + paid.s_column + '</td>' +
                '</tr>';
        });

        string += '</table>';
        return string;
    },
    url: "/api/historyPayments/",
    table: $("#storico").DataTable({
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {data: "code"},
            {data: "film_title"},
            {
                data: "payment_date", render: function (data, type) {
                if (type === 'display') {
                    return moment(data, 'AAAA-MM-DD').format("ll");
                }
                return data;
            }
            },
            {
                data: "payment_time", render: function (data, type) {
                if (type === 'display') {
                    return moment(data, 'HH:mm:ss').format("HH:mm");
                }
                return data;
            }
            },
            {
                data: "total", render: function (data, type) {
                if (type === 'display') {
                    return numeral(data).format('0,0.00 $');
                }
                return data;
            }
            }
        ],
        order: [[1, 'asc']]
    }),
    getPayaments: function () {
        var request = $.ajax({
            url: Payments.url + User.username
        });
        request.done(Payments.addData);

    },
    addData: function (data) {
        Payments.table.rows.add(data.pastPaymentList).draw();
        $('#storico tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = Payments.table.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child(Payments.format(row.data())).show();
                tr.addClass('shown');
            }
        });
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
};


$(function () {
    var name = Session.getUsername();
    if (name == null) {
        Session.redirectToLogin();
    } else {
        User.displayUserData();
    }
    Cards.displayUserCards();
    Credit.displayCredit();
    Payments.getPayaments();
});