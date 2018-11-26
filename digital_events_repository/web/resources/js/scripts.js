var administrador = "admin@gmail.com";
var contrasenaAdministrador = "admin12345";

var coordinador= "coordinador@gmail.com";
var contrasenaCoordinador = "coordinador12345";

var recreador= "recreador@gmail.com";
var contrasenaRecreador = "recreador12345";

var cliente = "cliente@gmail.com";
var contrasenaCliente = "cliente12345";


function validateUser(){
  var usuario =document.getElementById ("usuario").value;
  var contrasena =document.getElementById("contrasena").value;

  if (administrador == usuario && contrasenaAdministrador == contrasena ) {

    window.location.href='SI/Administrador/Administrador.html';

  }else if (coordinador == usuario && contrasenaCoordinador == contrasena ){

  	window.location.href='SI/Coordinador/Coordinador.html'

  }
  else if (recreador == usuario && contrasenaRecreador == contrasena ){

  	window.location.href='SI/Recreador/Recreador.html'

  }else{
    swal("OH! NO","usuario o contraseña incorrecto","error");

  }
}

(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();

/* menu acordion del dashboard*/
var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var panel = this.nextElementSibling;
    if (panel.style.maxHeight){
      panel.style.maxHeight = null;
    } else {
      panel.style.maxHeight = panel.scrollHeight + "px";
    } 
  });
}

/*funcion datatable*/

$(document).ready(function() {
    $('#example').DataTable();
} );


//----------FUNCIONES DE ADMINISTADOR---------
function deleteRowByIndex (rowIndex){
  document.getElementById("tabla").deleteRow(rowIndex);
}

function insert (){

  var tabla = document.getElementById("tabla");
  var row  =  tabla.insertRow (tabla.rows.length);      

  var cellNombres2 = row.insertCell(0);
  var cellApellidos2 = row.insertCell(1);
  var cellTipoDocumento2 = row.insertCell(2);
  var cellNumeroDocumento2 = row.insertCell(3);
  var cellNumeroCelular2 = row.insertCell(4);
  var cellDependencia2 = row.insertCell(5);
  var cellCorreoElectronico2 = row.insertCell(6);
  var cellboton2 = row.insertCell(7);

  cellNombres2.innerHTML = document.getElementById("nombres2").value;
  cellApellidos2.innerHTML = document.getElementById("apellidos2").value;

  var selectTipoDocumento2 = document.getElementById("tDocumento");
  cellTipoDocumento2.innerHTML = selectTipoDocumento2.options[selectTipoDocumento2.selectedIndex].value;

  cellNumeroDocumento2.innerHTML = document.getElementById("numeroID").value;

  cellNumeroCelular2.innerHTML = document.getElementById("numeroCelular").value;

  var selectDependencia2 = document.getElementById("dependencia"); 
  cellDependencia2.innerHTML = selectDependencia2.options[selectDependencia2.selectedIndex].value;

  cellCorreoElectronico2.innerHTML = document.getElementById("correoElectronico").value;



  cellboton2.innerHTML = "<button class='btn btn-danger' onclick='deleteRowByIndex(this.parentNode.parentNode.rowIndex)''><i class='fa fa-times'></i></button>";
}