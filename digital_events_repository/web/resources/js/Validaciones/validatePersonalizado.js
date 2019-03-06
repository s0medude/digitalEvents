/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery.validator.addMethod("soloLetras", function (value, element) {
    return /^[A-Za-z]+$/.test(value);
}, "Este campo solo admite letras");

jQuery.validator.addMethod("contraseniaValidate", function (value, element) {
    return /^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,100}$/.test(value);
}, "La contraseña debe contener más de 8 caracteres,al menos una Mayuscula y números");



jQuery.validator.addMethod("fechaESP", function (value, element)
{
    var validator = this;
    var datePat = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
    var fechaCompleta = value.match(datePat);
    var fechaActual = new Date();

    if (fechaCompleta == null) {
        $.validator.messages.fechaESP = "Debes ingresar una fecha valida";
        return false;
    }
    anio = fechaCompleta[1];
    mes = fechaCompleta[3];
    dia = fechaCompleta[5];




    if (anio > fechaActual.getFullYear()) {
        $.validator.messages.fechaESP = "El año " + anio + " es mayor al año actual =" + fechaActual.getFullYear() + "";
        return false;
    }
    if (mes < 1 || mes > 12) {
        $.validator.messages.fechaESP = "El valor del mes debe estar comprendido entre 1 y 12.";
        return false;
    }
    if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) {
        $.validator.messages.fechaESP = "El mes " + mes + " no tiene 31 días!";
        return false;
    }
    if (mes == 2) { // bisiesto
        var bisiesto = (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0));
        if (dia > 29 || (dia == 29 && !bisiesto)) {
            $.validator.messages.fechaESP = "Febrero del " + anio + " no contiene " + dia + " dias!";
            return false;
        }
    }
    if (dia < 1 || dia > 31) {
        $.validator.messages.fechaESP = "El valor del día debe estar comprendido entre 1 y 31.";
        return false;
    }

    return true;
}, "Bien");

jQuery.validator.addMethod("soloLetrasSi", function(value, element) {
  return this.optional(element) || /^[a-z\s]+$/i.test(value);
}, "Este campo solo admite letras");

