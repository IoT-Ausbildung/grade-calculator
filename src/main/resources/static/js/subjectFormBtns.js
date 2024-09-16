$(document).ready(function () {
    $('#addBtn').click(function () {
        var selectedSubject = $('#subjects option:selected');
        var selectedSubjectText = selectedSubject.text();
        var selectedSubjectValue = selectedSubject.val();

        var selectedYear = $('#schoolYear option:selected');
        var selectedYearText = selectedYear.text();
        var selectedYearValue = selectedYear.val();

        var combinedValue = `${selectedSubjectValue}-${selectedYearValue}`;

        $(`.selected-subject[data-combined-value="${combinedValue}"]`).remove();

        if (selectedSubjectText && selectedYearText) {
            var subjectHtml = `
            <div class="selected-subject" data-combined-value="${combinedValue}">
                <span>${selectedSubjectText} - ${selectedYearText}</span>
                <button type="button" class="btn btn-link remove-btn"><i class="fas fa-trash"></i></button>
                <input type="hidden" name="selectedValues" value="${combinedValue}">
            </div>`;
            $('#selectedSubjectsContainer').append(subjectHtml);
        }
    });

    $('#selectedSubjectsContainer').on('click', '.remove-btn', function () {
        $(this).closest('.selected-subject').remove();
    });

        $('#submitFormBtn').click(function () {
            var formData = $("#subjectForm").serialize();

            $.ajax({
                type: "POST",
                url: "/userSubject/save",
                data: formData
            })
                .done(function () {
                    window.location.href = "/userSubject/selected";
                })
                .fail(function (xhr) {
                    console.error('AJAX error response:', xhr.responseText);
                    var result = xhr.responseJSON;
                    var errorMessages = result?.errors?.join("<br>") || 'An unexpected error occurred';

                    $('#errorMessage').html(errorMessages);
                    $('#errorModal').modal('show');
                });
        });

        $('#errorModalCloseBtn').click(function () {
            window.location.href = "/userSubject/selected";
        });
});
