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
 * @author Nicolas PC
 */
public class MessageUtil {
    
    public static void addMessage(String clientId, String message, String detail, boolean prop, FacesMessage.Severity severity) {
        FacesMessage msj = new FacesMessage(severity,message,detail);
        FacesContext.getCurrentInstance().addMessage(clientId, msj);
    }

    public static void addInfoMessage(String clientId, String message, String detail, boolean prop) {
        addMessage(clientId,message,detail, prop, FacesMessage.SEVERITY_INFO);
    }
    
    public static void addErrorMessage(String clientId, String message, String detail, boolean prop) {
        addMessage(clientId,message,detail, prop, FacesMessage.SEVERITY_ERROR);
    }
    
    public static void addWarnMessage(String clientId, String message, String detail, boolean prop) {
        addMessage(clientId,message,detail, prop, FacesMessage.SEVERITY_WARN);
    }
}
