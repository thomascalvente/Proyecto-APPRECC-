function redirect(){

    alert("entramos a la funcion");
    setTimeout("location.href='/'", 5000);
}

function validarContrasenia(){
    alert(document.getElementById("password").innerHTML);
    document.getElementById("boton").disabled = document.getElementById("password") === document.getElementById("password2");
}


const passwordField = document.querySelector("#password");
const eyeIcon = document.querySelector("#eye");

const passwordField2 = document.querySelector("#password2");
const eyeIcon2 = document.querySelector("#eye2");

eye.addEventListener("click", function(){
  this.classList.toggle("fa-eye-slash");
  const type = passwordField.getAttribute("type") === "password" ? "text" : "password";
  passwordField.setAttribute("type", type);
})

eye2.addEventListener("click", function(){
  this.classList.toggle("fa-eye-slash");
  const type2 = passwordField2.getAttribute("type") === "password" ? "text" : "password";
  passwordField2.setAttribute("type", type2);
})


function validarImagen() {
    var fileSize = $('#imagen')[0].files[0].size;
    var siezekiloByte = parseInt(fileSize / 1024);
    if (siezekiloByte >  64000) {
        alert("Imagen muy grande");
        return false;
    }
}