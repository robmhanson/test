var isEscalatedState = null;

var originalLaw = '';
var originalEvidence = '';
var originalReason = '';
var originalReturnReason = '';

$(document).ready(function () {

    $("#tabs").tabs();

    var verb = 'Action';

    var law = $('#law').val();
    var evidence = $('#evidence').val();
    var reason = $('#reason').val();
    var returnReason = $('#returnReason').val();

    originalLaw = law;
    originalEvidence = evidence;
    originalReason = reason;
    originalReturnReason = returnReason;

    $('#law').bind('change', function (e) {
        updateActionButton();
    });
    $('#evidence').bind('change', function (e) {
        updateActionButton();
    });
    $('#reason').bind('change', function (e) {
        updateActionButton();
    });
    $('#law').bind('keyup', function (e) {
        updateActionButton();
    });
    $('#evidence').bind('keyup', function (e) {
        updateActionButton();
    });
    $('#reason').bind('keyup', function (e) {
        updateActionButton();
    });
    $('#returnReason').bind('keyup', function (e) {
        updateActionButton();
    });

    $('#lawList').bind('change', function (e) {

        // Clearing everything

        clearDropDown('#evidenceList');
        clearDropDown('#reasonList');

        $('#law').val('');

        $('#evidence').val("");
        $("#evidence").attr('disabled', 'disabled');
        $("#evidenceList").attr('disabled', 'disabled');

        $('#reason').val("");
        $("#reason").attr('disabled', 'disabled');
        $("#reasonList").attr('disabled', 'disabled');

        var lawValue = $('#lawList').val();

        if (lawMap[lawValue] != undefined) {
            $('#law').val(lawMap[lawValue].label);

            var evidenceList = evidenceByLaw[lawValue];

            evidenceList.sort(function(a, b) {
                var o1 = a.label.trim().toLowerCase();
                var o2 = b.label.trim().toLowerCase();
                return ((o1 < o2) ? -1 : ((o1 > o2) ? 1 : 0));
            });

            var $el = $("#evidenceList");
            evidenceList.forEach(function (entry) {
                $el.append($("<option></option>")
                               .attr("value", entry.value).text(entry.label));
            });

            $("#evidenceList").removeAttr('disabled');
        }

        updateActionButton();
    });

    $('#evidenceList').bind('change', function (e) {

        $('#evidence').val("");
        $("#evidence").attr('disabled', 'disabled');

        $("#reason").attr('disabled', 'disabled');
        $('#reason').val("");
        $("#reasonList").attr('disabled', 'disabled');

        clearDropDown('#reasonList');

        var evidenceValue = $('#evidenceList').val();

        var isTextSet = false;
        for (var key in evidenceByLaw) {
            if (evidenceByLaw.hasOwnProperty(key)) {

                evidenceByLaw[key].forEach(function (entry) {
                    if (entry.value === evidenceValue) {
                        $('#evidence').val(entry.label);
                        isTextSet = true;
                    }
                });
            }
        }

        if (isTextSet) {

            $("#evidence").removeAttr('disabled');

            var reasonList = reasonByEvidence[evidenceValue];

            if (reasonList != undefined && reasonList.length > 0) {

                reasonList.sort(function(a, b) {
                    var o1 = a.label.trim().toLowerCase();
                    var o2 = b.label.trim().toLowerCase();
                    return ((o1 < o2) ? -1 : ((o1 > o2) ? 1 : 0));
                });

                var $el = $("#reasonList");
                reasonList.forEach(function (entry) {
                    $el.append($("<option></option>")
                                   .attr("value", entry.value).text(entry.label));
                });

                $("#reasonList").removeAttr('disabled');
            }

            $("#reason").removeAttr('disabled');
        }

        updateActionButton();
    });

    $('#reasonList').bind('change', function (e) {

        var reasonValue = $('#reasonList').val();

        var isTextSet = false;
        for (var key in reasonByEvidence) {
            if (reasonByEvidence.hasOwnProperty(key)) {

                reasonByEvidence[key].forEach(function (entry) {
                    if (entry.value === reasonValue) {
                        $('#reason').val(entry.label);
                        isTextSet = true;
                    }
                });
            }
        }
        if (!isTextSet) {
            $('#reason').val('');
        }

        $("#reason").removeAttr('disabled');

        updateActionButton();
    });
});

function clearDropDown(selector) {
    var $el = $(selector);
    $el.empty(); // remove old options
    $el.append($("<option></option>").attr("value", '-').text('-'));
}

function showModal(e, link, isEscalated) {

    if (isEscalatedState == undefined || isEscalatedState == null) {
        isEscalatedState = isEscalated;
    }
    e.preventDefault();

    var isApprove = ('' + link).endsWith("approve");

    if (isApprove) {
        $("#rejectReturnDetails").hide();
        $("#returnDetails").hide();
        verb = 'Approve';
    }
    else {
        if (('' + link).endsWith("reject")) {
            verb = 'Reject';
            $("#rejectReturnDetails").show();
            $("#returnDetails").hide();
        }
        else if (('' + link).endsWith("return")) {
            verb = 'Return';
            $("#rejectReturnDetails").hide();
            $("#returnDetails").show();
        }
    }

    var confirmationQuestion = 'Are you sure you want to ' + verb + ' the case?';

    $('#confirmationQuestion').text(confirmationQuestion);
    var dialog = $("#dialog-confirm").dialog({
                                                 resizable: false,
                                                 height: "auto",
                                                 width: "1280",
                                                 modal: true,
                                                 buttons: {
                                                     "Continue": {
                                                         id:"continueAction",
                                                         text: verb,
                                                         click: function () {
                                                             $(this).dialog("close");

                                                             var url = link.href; // the script where you handle the form input.

                                                             var law = $('#law').val();
                                                             var evidence = $('#evidence').val();

                                                             var reason;
                                                             if (verb == 'Reject') {
                                                                 reason = $('#reason').val();
                                                             }
                                                             else if (verb == 'Return') {
                                                                 reason = $('#returnReason').val();
                                                             }

                                                             url =
                                                                 url + '?law=' + law + '&evidence=' + evidence + '&reason='
                                                                 + reason;

                                                             $.ajax({
                                                                        type: "GET",
                                                                        url: url
                                                                    }).done(function (data) {
                                                                 location.reload();
                                                             }).fail(function (data) {
                                                                 alert('Action [' + verb + '] could not be performed.');
                                                             });

                                                         }
                                                     },
                                                     "Cancel": {
                                                         text: "Cancel", click: function() {cancelDialog(dialog);}
                                                     },
                                                 },
                                                 // Need to keep this show property here even though it's empty.
                                                 // Without this being here, the open hook is not working.
                                                 show: {
                                                     effect:"slide"
                                                 },
                                                 open: function () {
                                                     $(this).parent().promise().done(function () {
                                                         if (!isApprove) {
                                                             $('#continueAction').attr("disabled", "disabled");

                                                             if (isEscalatedState) {
                                                                 $("#evidence").removeAttr('disabled');
                                                                 $("#reason").removeAttr('disabled');
                                                             }
                                                         }
                                                     });
                                                 }
                                             });

    dialog.on('keydown', function(evt) {
        if (evt.keyCode === $.ui.keyCode.ESCAPE) {
            console.log('Escape has been pressed');
            cancelDialog(dialog);
        }
        evt.stopPropagation();
    });
    $('#dialog-confirm').dialog('option', 'title', verb);

}

function cancelDialog(dialog) {
    $('#law').val(originalLaw);
    $('#evidence').val(originalEvidence);
    $('#reason').val(originalReason);
    $('#returnReason').val(originalReturnReason);

    dialog.dialog("close");

    // This is a temporary fix to revert back the changes in the pop up dialog
    //location.reload();
}

function updateActionButton() {

    var allProvided;

    if (verb == 'Reject') {

        var law = $('#law').val();
        var evidence = $('#evidence').val();
        var reason = $('#reason').val();

        if (law.trim() != '' && evidence.trim() != '' && reason.trim() != '') {
            allProvided = true;
        }
        else {
            allProvided = false;
        }
    }
    else if (verb == 'Return') {

        var reason = $('#returnReason').val();

        if (originalReason == '') {
            originalReason = reason;
        }

        if (reason.trim() != '') {
            allProvided = true;
        }
        else {
            allProvided = false;
        }
    }
    else {
        allProvided = true;
    }

    if (allProvided) {
        $("#continueAction").removeAttr('disabled');
    }
    else {
        $('#continueAction').attr("disabled", "disabled");
    }
}