/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('form[id="form_registerE"]').validate({
    rules: {
        pNombre: {
            required: true,
            maxlength: 15,
            soloLetras: true
        },
        pApellido: {
            required: true,
            maxlength: 15,
            soloLetras: true
        },
        sNombre: {
            maxlength: 15

        },
        sApellido: {
            maxlength: 15

        },
        tDocumento: {
            required: true
        },
        nI: {
            required: true,
            maxlength: 12
        },

        correo: {
            required: true
        },

        contrasenia: {
            required: true,
            contraseniaValidate: true
        },

        nombreE: {
            required: true,
            maxlength: 20
        },
        direccion: 'required',

        //Modal Solicitar Mantenimiento
        selectEmpresa: {
            required: true
        }

    },
    messages: {
        pNombre: {
            required: 'Este campo es obligatorio',
            maxlength: 'Deben ser {0} o menos caracteres'
        },
        pApellido: {
            required: 'Este campo es obligatorio',
            maxlength: 'Deben ser {0} o menos caracteres'
        },
        sNombre: {
            maxlength: 'Deben ser {0} o menos caracteres'
        },
        sApellido: {
            maxlength: 'Deben ser {0} o menos caracteres'
        },
        tDocumento: {
            required: 'Este campo es obligatorio'
        },
        nI: {
            required: 'Este campo es obligatorio',
            maxlength: 'Deben ser {0} o menos caracteres'
        },
        correo: {
            required: 'Este campo es obligatorio',
            email: 'Ingresa un correo valido'
        },
        contrasenia: {
            required: 'Este campo es obligatorio'
        },
        nombreE: {
            required: 'Este campo es obligatorio',
            maxlength: jQuery.validator.format('Deben tener {0} o menos caracteres')
        },
        direccion: 'Este campo es obligatorio',
        selectEmpresa: {
            required: 'Este campo es obligatorio'
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});