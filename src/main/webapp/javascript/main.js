"use strict";

var Forms = {
    loadingButton: function (button) {
        button.attr("disabled", '');
        if (button.hasClass("ladda-button")) {
            var l = Ladda.create(button[0]);
            button.data('ladda', l);
            l.start();
        }
    },
    enableButton: function (button) {
        button.removeAttr("disabled");
        if (button.data('ladda') != null)
            button.data('ladda').stop();
    },
    ajax: function (jsonData, url, form) {
        return $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(jsonData),
            dataType: "json",
            context: form,
            processData: false,
            contentType: "application/json; charset=UTF-8"
        });
    },
    PostForm: function (formID, doneCallback, failCallback, Abide) {
        var form = $("#" + formID);

        var send = function (event) {
            event.preventDefault();

            var button = form.find("button");

            Forms.loadingButton(button);

            //Serializza la form in JSON
            var Json = form.serializeJSON({
                parseBooleans: true,
                useIntKeysAsArrayIndex: true
            });
            // Stop form from submitting normally

            // Get some values from elements on the page:
            var url = form.attr("action");

            // Send the data using post
            var posting = Forms.ajax(Json, url, form);

            posting.done(doneCallback);
            posting.fail(failCallback);
            posting.always(function () {
                Forms.enableButton(button);
            });
        };
        if (Abide) {
            form.on('valid.fndtn.abide', send);
        } else {
            form.on('submit', send);
        }
    }
};

var Notifications = {
    setNotifications: function () {
        alertify.set('notifier', 'position', 'top-right');
    },
    showNotifications: function () {
        this.setNotifications();
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
    },
    saveNotification: function (type, message, position) {
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
};

var Session = {
    storage: $.localStorage,
    getUsername: function () {
        return Session.storage.get("username");
    },
    setUsername: function (username) {
        Session.storage.set("username", username);
    },
    logout: function () {
        Session.storage.remove("username");
    },
    redirectToUser: function () {
        //Session.storage.set("backUrl", window.location.pathname);
        window.location.assign("/user/" + Session.getUsername());
    },
    redirectToLogin: function () {
        Session.storage.set("backUrl", window.location.pathname);
        Notifications.saveNotification("error", "Non hai effettuato l'accesso!");
        window.location.assign("/login");
    },
    redirectToRegister: function () {
        window.location.assign("/registration");
    },
    redirectToHome: function () {
        window.location.assign("/");
    },
    redirectToUrl: function (url) {
        window.location.assign(url);
    }
};

moment.locale('it');
numeral.language('it');

Notifications.showNotifications();