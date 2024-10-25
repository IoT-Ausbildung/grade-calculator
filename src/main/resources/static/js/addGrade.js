$(document).ready(function () {
    const addGradeButton = $('.add-grade-btn');
    addGradeButton.prop('disabled', true);

    function validateGradeValue(gradeValue) {
        return gradeValue >= 1 && gradeValue <= 6;
    }

    $('input[name="grade"]').on('input', function () {
        const gradeValue = $(this).val();

        if (validateGradeValue(gradeValue)) {
            addGradeButton.prop('disabled', false);
        } else {
            addGradeButton.prop('disabled', true);
        }
    });

    addGradeButton.click(function () {
        const gradeInput = $(this).closest('tr').find('input[name="grade"]');
        const gradeTypeSelect = $(this).closest('tr').find('select[name="gradeType"]');
        const userSubjectId = $(this).closest('tr').find('.remove-btn').data('id');
        const gradeValue = gradeInput.val();
        const gradeTypeId = gradeTypeSelect.val();
        const gradeTypeName = gradeTypeSelect.find("option:selected").text();

        if (!validateGradeValue(gradeValue)) {
            showAlertModal('Please select a valid grade between 1 and 6.');
            return;
        }

        const gradeData = {
            userSubjectId: userSubjectId,
            gradeTypeId: gradeTypeId,
            gradeTypeName: gradeTypeName,
            gradeValue: gradeValue
        };

        addGradeToDatabase(gradeData);
    });

    function showAlertModal(message) {
        document.getElementById('alertModalBody').textContent = message;
        $('#alertModal').modal('show');
    }

    function addGradeToDatabase(gradeData) {
        $.ajax({
            url: '/grades/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(gradeData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(
                    $('meta[name="_csrf_header"]').attr('content'),
                    $('meta[name="_csrf"]').attr('content')
                );
            },
            success: function () {
                window.location.reload();
            },
            error: function () {
                alert('Error saving grade!');
            }
        });
    }
});
