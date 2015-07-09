/**
 * Created by mion00 on 19/06/15.
 */

"use strict";

alertify.set('notifier', 'position', 'top-right');

function PostForm(formID, doneCallback, failCallback) {
    $("#" + formID).on('valid.fndtn.abide', function (event) {
        event.preventDefault();
        var form = $(this);

        var button = form.find('input[type="submit"]').first();
        //console.log(button);
        button.attr("disabled", true);
        //Serializza la form in JSON
        var JSON = form.serializeJSON();

        // Stop form from submitting normally

        // Get some values from elements on the page:
        var url = form.attr("action");

        // Send the data using post
        var posting = $.post(url, JSON);

        posting.done(doneCallback);
        posting.fail(failCallback);
        posting.always(function () {
            button.attr("disabled", false);
        });
    });

}

function saveNotification(type, message, position) {
    var storage = $.localStorage;

    var notificationCount;

    notificationCount = storage.get("notificationCount");

    if (notificationCount == null) {
        notificationCount = 0;
    }

    var notification = {
        type: type,
        message: message
    };

    if (position != null) {
        notification.position = position;
    }

    notificationCount++;

    storage.set("notification" + notificationCount, notification);
    storage.set("notificationCount", notificationCount);
}

function showNotification() {
    var storage = $.localStorage;

    var notificationCount;

    notificationCount = storage.get("notificationCount");

    if (notificationCount != null) {
        for (notificationCount; notificationCount > 0; notificationCount--) {
            var nome = "notification" + notificationCount;
            var notification = storage.get(nome);
            storage.remove(nome);
            switch (notification.type) {
                case "success":
                    alertify.success(notification.message);
                    break;
                case "error":
                    alertify.error(notification.message);
                    break;
                case "warning":
                    alertify.warning(notification.message);
            }
        }
    } else {
        notificationCount = 0;
    }
    //console.log(notificationCount);
    storage.set("notificationCount", notificationCount);
}

function redirectToUser(userName) {
    window.location.assign("/user/" + userName);
}

showNotification();