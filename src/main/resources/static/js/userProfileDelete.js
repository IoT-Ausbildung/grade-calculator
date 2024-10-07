document.addEventListener('DOMContentLoaded', function () {

    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'), {});
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/myProfile', {
        method: 'DELETE',
        headers: {
            [csrfHeader]: csrfToken,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ your: "data" })
    })

    document.getElementById('deleteProfileBtn').addEventListener('click', function () {
        console.log('Button clicked!');
        confirmationModal.show();
    });

    document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
        fetch('/myProfile', {
            method: 'DELETE'
        }).then(response => {
            confirmationModal.hide();
            if (response.ok) {
                window.location.herf = "/login";
            }
        }).catch(error => {
            console.error('Error:', error);
            confirmationModal.hide();
        });
    });
});