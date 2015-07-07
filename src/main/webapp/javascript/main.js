/**
 * Created by mion00 on 19/06/15.
 */

"use srict";
function PostForm(formID, doneCallback, failCallback) {
    $("#" + formID).on('valid.fndtn.abide', function (event) {
        event.preventDefault();

        var button = $(this).find('input[type="submit"]').first();
        //console.log(button);
        button.attr("disabled", true);
        //Serializza la form in JSON
        var JSON = $(this).serializeJSON();

        // Stop form from submitting normally

        // Get some values from elements on the page:
        var url = $(this).attr("action");

        // Send the data using post
        var posting = $.post(url, JSON);

        posting.done(doneCallback);
        posting.fail(failCallback);
        posting.always(function () {
            button.attr("disabled", false);
        });
    });

}

alertify.set('notifier', 'position', 'top-right');