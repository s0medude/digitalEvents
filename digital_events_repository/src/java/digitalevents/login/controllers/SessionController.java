/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.login.controllers;


import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.Rol;
import edu.digitalEvents.modelo.entities.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
/**
 *
 * @author Nicolas PC
 */
@Named(value = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

    @EJB
    private IUsuarioDAO uDAO;

    private String email;
    private String password;

    private Usuario user;
    private Rol rol;

    @PostConstruct
    public void init() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String startSession() {
        String url = "";
        if (email != null && password != null && password.length() > 0) {
            user = uDAO.findByCorreoAndContra(email, password);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
            if (user != null) {
                if (user.getRolList() != null && !user.getRolList().isEmpty()) {
                    rol = user.getRolList().get(0);
                    url = "/SI/index.xhtml";
                } else {
                    user = null;
                    MessageUtil.addErrorMessage(null, "Usuario sin rol", "Debe esperar a que se le asigne un rol dentro del sistema.", true);
                }
            } else {
                MessageUtil.addErrorMessage(null, "Datos incorrectos", "El documento y la clave no son validos, verifique y vuelva a intentar.", true);
            }
        } else {
            MessageUtil.addErrorMessage(null, "Campos requeridos", "El documento y la clave son obligatorias.", true);
        }
        return url;
    }

    public String closeSession() throws IOException {
        String url;
        url = "index.xhtml";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath());
        return url;
    }

    public boolean isStartSession() {
        return user != null;
    }

    public void validateSession() throws IOException {
        if (!isStartSession()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath());
        }
    }

    public Usuario usuarioSession() {
        user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
        return user;
    }

}
