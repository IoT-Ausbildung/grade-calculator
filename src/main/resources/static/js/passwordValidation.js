const passwordId = "password";
const passwordCfId = "passwordConfirm";
const oldPasswordId = "oldPassword"

document.getElementById(passwordId).onchange = validatePassword;
document.getElementById(passwordCfId).onkeyup = validatePassword;
function validatePassword(){
    var passwordIn = document.getElementById(passwordId);
    var passwordCf = document.getElementById(passwordCfId);
    if(passwordIn.value != passwordCf.value) {
        passwordCf.setCustomValidity("Passwords don't match");
    } else {
        passwordCf.setCustomValidity('');
    }
}


function togglePasswordVisibility() {
    var passwordField = document.getElementById(passwordId);
    var passwordConfirmField = document.getElementById(passwordCfId);
    var oldPasswordField = document.getElementById(oldPasswordId)

    toggleInputVisibility(passwordField);
    toggleInputVisibility(passwordConfirmField);
    toggleInputVisibility(oldPasswordField);
}

function toggleInputVisibility(inputElement) {
    if (inputElement.type === passwordId) {
        inputElement.type = "text";
    } else {
        inputElement.type = passwordId;
    }
}