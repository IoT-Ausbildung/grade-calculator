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
        $(function () {
            var token = $("input[name='_csrf']").val();
            var header = "X-CSRF-TOKEN";
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
        if (selectedID) {
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
                    const successAlert = document.getElementById('success-alert');
                    successAlert.style.display = 'block';
                    setTimeout(function () {
                        successAlert.style.display = 'none';
                    }, 3000); 
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
