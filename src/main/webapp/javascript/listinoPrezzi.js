/**
 * Created by fuffaknight on 23/07/15.
 */
numeral.language('it');

var Ticket = {
    getTicket: function () {
        $.ajax({
            dataType: "json",
            url: "/api/tickets"
        }).done(Ticket.displayTicket);
    },
    displayTicket: function (data) {
        console.log(data);
        var directives = {

            price: {
                text: function () {
                    return numeral(this.price).format('0,0.00 $');
                }
            }
        };
        Transparency.render($("#allTickets")[0], data.tickets, directives);
    }
};


$(function () {
    Ticket.getTicket();
});