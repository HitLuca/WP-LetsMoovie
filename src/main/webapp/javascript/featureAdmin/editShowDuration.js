var EditShowDuration = {
    editButton: $("#edit"),
    resetButton: $("#reset"),
    editRequest: function () {
        var request = $.ajax({
            url: "/api/test",
            data: $("#minuti").serialize()
        });
        var l = Ladda.create(EditShowDuration.editButton[0]);
        l.start();
        request.done(function () {
            Notifications.saveNotification("success", "Gli spettacoli sono stati modificati correttamente!");
            Session.redirectToHome();
        });
    },
    resetRequest: function () {
        var request = $.ajax({
            url: "/api/test",
            method: "POST"
        });
        var l = Ladda.create(EditShowDuration.resetButton[0]);
        l.start();
        request.done(function () {
            Notifications.saveNotification("success", "Gli spettacoli sono stati reimpostati correttamente!");
            Session.redirectToHome();
        });
    },
    bind: function () {
        EditShowDuration.editButton.on("click", EditShowDuration.editRequest);
        EditShowDuration.resetButton.on("click", EditShowDuration.resetRequest);
    }
};


$(function () {
    EditShowDuration.bind();
});