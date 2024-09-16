$(document).ready(function () {
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

