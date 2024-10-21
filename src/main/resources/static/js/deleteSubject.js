document.addEventListener('DOMContentLoaded', function () {
    $("#delete-placeholder").load("/deleteJS");
    let selectedID = null;
    let selectedButton = null;

    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'), {});
    const alertModal = new bootstrap.Modal(document.getElementById('alertModal'), {});
    const alertModalBody = document.getElementById('alertModalBody');

    function getMetaContent(name) {
        const metaTag = document.querySelector(`meta[name="${name}"]`);
        return metaTag ? metaTag.getAttribute('content') : null;
    }

    const csrfToken = getMetaContent('_csrf');
    const csrfHeader = getMetaContent('_csrf_header');

    document.querySelectorAll('.remove-btn').forEach(button => {
        button.addEventListener('click', function () {
            selectedID = this.getAttribute('data-id');
            selectedButton = this;

            if (!selectedID) {
                alertModalBody.textContent = 'Subject ID is missing.';
                alertModal.show();
                return;
            }

            confirmationModal.show();
        });
    });

    document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
        if (selectedID) {
            const csrfToken = $('meta[name="_csrf"]').attr('content');
            const csrfHeader = $('meta[name="_csrf_header"]').attr('content');

            fetch(`/userSubject/delete/${selectedID}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                }
            }).then(response => {
                confirmationModal.hide();
                if (response.ok) {
                    selectedButton.closest('tr').remove();
                    console.log("Subject successfully deleted.");
                    window.location.reload();

                } else {
                    return response.text().then(text => {
                        alertModalBody.textContent = 'Subjects that have grade cannot be deleted';
                        alertModal.show();
                    });
                }
            }).catch(error => {
                confirmationModal.hide();
                console.error('Network error:', error);
                alertModalBody.textContent = 'An error occurred. Please try again.';
                alertModal.show();
            });
        }
    });
});