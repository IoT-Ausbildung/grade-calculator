function onButtonClick(){

    var yearText = $("#schoolYear option:selected").text();
    var subjectText = $("#subjects option:selected").text();
    var yearValue = $("#schoolYear").val();
    var subjectValue = $("#subjects").val();

    if(yearValue !== "" && subjectValue !== "") {
        var newItem = $('<div class="item-row">' + yearText + ' - ' + subjectText + '</div>');
        $("#selectedSubjectsContainer").append(newItem);
    }
}

$(document).ready(function () {
    $("#addBtn").click(onButtonClick);
});
