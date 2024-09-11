document.addEventListener('DOMContentLoaded', function () {
    $("#delete-placeholder").load("/deleteJS");
    let selectedID = null;
    let selectedButton = null;

    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'), {});
    const alertModal = new bootstrap.Modal(document.getElementById('alertModal'), {});
    const alertModalBody = document.getElementById('alertModalBody');

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
            fetch(`/subject/delete/${selectedID}`, {
                method: 'DELETE', headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                confirmationModal.hide();
                if (response.ok) {
                    selectedButton.closest('tr').remove();
                    alertModalBody.textContent = 'Successfully deleted';
                    alertModal.show();
                } else {
                    return response.text().then(text => {
                        alertModalBody.textContent = `Failed to delete the subject. Server responded with: ${text}`;
                        alertModal.show();
                    });
                }
            }).catch(error => {
                confirmationModal.hide();
                console.error('Error:', error);
                alertModalBody.textContent = 'An error occurred. Please try again.';
                alertModal.show();
            });
        }
    });
});
