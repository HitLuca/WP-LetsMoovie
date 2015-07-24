var BestCustomer = {
    table: $("#customers"),
    url: "/api/adminFunction/getRankedUsers/",
    getCustomerList: function () {

    },
    successRequest: function (data) {
    },
    errorRequest: function (data) {

    }

};

$(function () {
    Forms.PostForm("percentageSelect", BestCustomer.successRequest, BestCustomer.errorRequest, false);
});
