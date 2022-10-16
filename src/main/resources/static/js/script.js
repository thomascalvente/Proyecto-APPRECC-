function redirect(){

    alert("entramos a la funcion");
    setTimeout("location.href='/'", 5000);
}

function validarContrasenia(){
    alert(document.getElementById("password").innerHTML);
    document.getElementById("boton").disabled = document.getElementById("password") === document.getElementById("password2");
}