/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Juan
 */
public class MensajesUtilCSV {
     public static void mensaje(String codigo, String mensaje, String detalle, FacesMessage.Severity severity) {
        FacesMessage mensajeF = new FacesMessage(severity, mensaje, detalle);
        FacesContext.getCurrentInstance().addMessage(codigo, mensajeF);
    }

    public static void warningMensajes(String codigo, String mensaje, String detalle) {
                mensaje(codigo, mensaje, detalle, FacesMessage.SEVERITY_WARN);

    }
    public static void errorMensajes(String codigo, String mensaje, String detalle) {
                mensaje(codigo, mensaje, detalle, FacesMessage.SEVERITY_ERROR);

    }
    public static void infoMensaje(String codigo, String mensaje, String detalle) {
        mensaje(codigo, mensaje, detalle, FacesMessage.SEVERITY_INFO);
        
    }
}
