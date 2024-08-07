function showSelections() {
    var subjectText = $("#subjects option:selected").text();
    var subjectValue = $("#subjects").val();
    if(subjectValue !== "") {
        $("#selectedSubjectsContainer").append("<div class='subject-item'>"+ subjectText +"</div>");
    }
}