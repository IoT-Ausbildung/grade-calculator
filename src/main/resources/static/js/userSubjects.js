$(document).ready(function () {
    $('.add-grade-btn').click(function () {
        const gradeInput = $(this).closest('tr').find('input[name="grade"]');
        const gradeTypeSelect = $(this).closest('tr').find('select[name="gradeType"]');
        const userSubjectId = $(this).closest('tr').find('.remove-btn').data('id');
        const gradeValue = gradeInput.val();
        const gradeTypeId = gradeTypeSelect.val();
        const gradeTypeName = gradeTypeSelect.find("option:selected").text();

        const gradeData = {
            userSubjectId: userSubjectId,
            gradeTypeId: gradeTypeId,
            gradeTypeName: gradeTypeName,
            gradeValue: gradeValue
        };

        console.log("Grade data:", gradeData);
        addGradeToDatabase(gradeData);
    });

    function addGradeToDatabase(gradeData) {
        $.ajax({
            url: '/grades/save-grade',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(gradeData),
            beforeSend: function(xhr) {
                xhr.setRequestHeader($('meta[name="_csrf_header"]').attr('content'), $('meta[name="_csrf"]').attr('content'));
            },
            success: function(response) {
                console.log("Grade added successfully.");
                // Reload the page after 5 seconds
                setTimeout(function(){
                    console.log("Reloading the page.");
                    window.location.reload(1);
                }, 1000);
            },
            error: function(response) {
                console.log("Error adding grade.");
                alert('Error saving grade!');
            }
        });
    }

});