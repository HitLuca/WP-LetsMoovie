var BestCustomer = {
    table: $("#customers"),
    DataTable: null,
    url: "/api/adminFunction/getRankedUsers/",
    getCustomerList: function () {

    },
    successRequest: function (data) {
        if (!BestCustomer.DataTable) {
            BestCustomer.DataTable = BestCustomer.table.DataTable({
                columns: [
                    {data: "name"},
                    {data: "surname"},
                    {data: "username"},
                    {data: "totalPayments"}
                ]
            });
        }
        //PULISCE TABELLA
        BestCustomer.DataTable.clear();
        BestCustomer.DataTable.rows.add(data).draw();
    },
    errorRequest: function (data) {
        //TODO:GESTIRE ERRORE
    }

};

$(function () {
    Forms.PostForm("percentageSelect", BestCustomer.successRequest, BestCustomer.errorRequest, false);
});
