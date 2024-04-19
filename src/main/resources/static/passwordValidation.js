const passwordId = "password";
const passwordCfId = "passwordConfirm";

function validatePassword(){
    var passwordIn = document.getElementById(passwordId);
    var passwordCf = document.getElementById(passwordCfId);
    if(passwordIn.value != passwordCf.value) {
        passwordCf.setCustomValidity("Passwords don't match");
    } else {
        passwordCf.setCustomValidity('');
    }
}
document.getElementById(passwordId).onchange = validatePassword;
document.getElementById(passwordCfId).onkeyup = validatePassword;

function togglePasswordVisibility() {
    var passwordField = document.getElementById(passwordId);
    var passwordConfirmField = document.getElementById(passwordCfId);

    toggleInputVisibility(passwordField);
    toggleInputVisibility(passwordConfirmField);
}

function toggleInputVisibility(inputElement) {
    if (inputElement.type === passwordId) {
        inputElement.type = "text";
    } else {
        inputElement.type = passwordId;
    }
}