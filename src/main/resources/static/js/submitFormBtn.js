$(document).ready(function () {
    $('#submitFormBtn').click(function () {
        var formData = $("#subjectForm").serialize();

        $.ajax({
            type: "POST",
            url: "/userSubject/save",
            data: formData,
            success: function (response) {
                if (response.success) {
                    window.location.href = "/userSubject/selected";
                } else {
                    var errorMessages = (response.errors || []).join("<br>");
                    $('#errorMessage').html(errorMessages);
                    $('#errorModal').modal('show');
                }
            },
            error: function (xhr) {
                console.error('AJAX error response:', xhr.responseText);
                var result = xhr.responseJSON;
                var errorMessages = result.errors?.join("<br>") || 'An unexpected error occurred';

                $('#errorMessage').html(errorMessages);
                $('#errorModal').modal('show');
            }
        });
    });

    $('#errorModalCloseBtn').click(function () {
        window.location.href = "/userSubject/selected";
    });
});

