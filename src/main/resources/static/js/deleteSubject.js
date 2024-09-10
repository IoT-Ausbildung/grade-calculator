document.addEventListener('DOMContentLoaded', function () {
    $("#delete-placeholder").load("/deleteJS");
    document.querySelectorAll('.remove-btn').forEach(button => {
        button.addEventListener('click', function () {
            const ID = this.getAttribute('data-id');

            if (!ID) {
                alert('Subject ID is missing.');
                return;
            }

            if (confirm('Are you sure you want to delete this subject?')) {
                console.log(`Deleting subject with name: ${ID}`);

                fetch(`/subject/delete/${ID}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                    if (response.ok) {
                        this.closest('tr').remove();
                        alert('Successfully deleted');
                    } else {
                        return response.text().then(text => {
                            alert(`Failed to delete the subject. Server responded with: ${text}`);

                        });
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred. Please try again.');
                });
            }
        });
    });
});
