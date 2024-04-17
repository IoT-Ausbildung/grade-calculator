function validatePassword(){
    var passwordIn = document.getElementById("password");
    var passwordCf = document.getElementById("passwordConfirm");
    if(passwordIn.value != passwordCf.value) {
        passwordCf.setCustomValidity("Passwords Don't Match");
    } else {
        passwordCf.setCustomValidity('');
    }
}
document.getElementById("password").onchange = validatePassword;
document.getElementById("passwordConfirm").onkeyup = validatePassword;

function togglePasswordVisibility() {
    var passwordField = document.getElementById("password");
    var passwordConfirmField = document.getElementById("passwordConfirm");

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
