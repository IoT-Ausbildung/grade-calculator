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
                appendGradeToTable(gradeData);
            },
            error: function(response) {
                alert('Error saving grade!');
            }
        });
    }

    function appendGradeToTable(gradeData) {
        const gradeList = $(`#grades-${gradeData.userSubjectId}`);
        const gradeRow = `
            <tr>
                <td>${gradeData.gradeTypeName}</td>
                <td>${gradeData.gradeValue}</td>
            </tr>
        `;
        gradeList.append(gradeRow);
    }

    fetchGrades();

    function fetchGrades() {
        $.ajax({
            url: '/grades/userGrades',
            type: 'GET',
            success: function(grades) {
                grades.forEach(grade => {
                    appendGradeToTable({
                        userSubjectId: grade.userSubjectId,
                        gradeTypeId: grade.gradeTypeId,
                        gradeTypeName: grade.gradeTypeName,
                        gradeValue: grade.gradeValue
                    });
                });
            },
            error: function() {
                alert('Error fetching grades!');
            }
        });
    }
});