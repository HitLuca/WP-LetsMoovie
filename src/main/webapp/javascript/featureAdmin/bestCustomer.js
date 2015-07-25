var BestCustomer = {
        table: $("#customers"),
        DataTable: null,
        url: "/api/adminFunction/getRankedUsers/",
        getCustomerList: function () {

        },
        successRequest: function (data) {
            var contenitoreTabella = $("#customerList");
            contenitoreTabella.removeClass("hide");
            contenitoreTabella.addClass("animated fadeIn");
            if (!BestCustomer.DataTable) {
                BestCustomer.DataTable = BestCustomer.table.DataTable({
                    columns: [
                        {data: "rank"},
                        {data: "name"},
                        {data: "surname"},
                        {data: "username"},
                        {
                            data: "totalPayments", render: function (data, type) {
                            if (type === 'display') {
                                return numeral(data).format('0,0.00 $');
                            }
                            return data;
                        }
                        }
                    ]
                })
                ;
            }
//PULISCE TABELLA
            BestCustomer.DataTable.clear();
            BestCustomer.DataTable.rows.add(data).draw();
        },
        errorRequest: function (data) {
            //TODO:GESTIRE ERRORE
        }

    }
    ;

$(function () {
    Forms.PostForm("percentageSelect", BestCustomer.successRequest, BestCustomer.errorRequest, false);
});
