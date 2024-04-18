const passwordId = "password";
const passwordCfId = "passwordConfirm";

function validatePassword(){
    var passwordIn = document.getElementById(passwordId);
    var passwordCf = document.getElementById(passwordCfId);
    if(passwordIn.value != passwordCf.value) {
        passwordCf.setCustomValidity("Passwords Don't Match");
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
    if (inputElement.type === "password") {
        inputElement.type = "text";
    } else {
        inputElement.type = "password";
    }
}