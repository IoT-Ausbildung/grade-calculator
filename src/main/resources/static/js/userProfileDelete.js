document.addEventListener('DOMContentLoaded', function () {

    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'), {});
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    document.getElementById('deleteProfileBtn').addEventListener('click', function () {
        confirmationModal.show();
    });

    document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
        fetch('/deleteProfile', {
            method: 'DELETE',
            headers: {
                [csrfHeader]: csrfToken,
                'Content-Type': 'application/json',
            },
        }).then(response => {
            confirmationModal.hide();
            if (response.ok) {
                window.location = "/signup";
            }
        }).catch(error => {
            console.error('Error:', error);
            confirmationModal.hide();
        });
    });
});