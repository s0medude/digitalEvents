/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.usuarios.controllers;

import digitalevents.login.controllers.SessionController;
import digitalevents.utils.Mail;
import edu.digitalEvents.modelo.dao.IEstadoUsuarioDAO;
import edu.digitalEvents.modelo.dao.ITipoDocumentoDAO;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.EstadoUsuario;
//import edu.digitalEvents.modelo.entities.Rol;
import edu.digitalEvents.modelo.entities.Usuario;
import digitalevents.utils.MessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    @EJB
    private IUsuarioDAO uDAO;
    @EJB
    private ITipoDocumentoDAO tDAO;
    @EJB
    private IEstadoUsuarioDAO euDAO;

    private String confirmarClave;

    private Usuario nuevoUsuario;
    private Usuario usuarioSeleccionado;
    private List<Usuario> usuariosList;
    private List<Usuario> usuarioSessionList;
    private List<EstadoUsuario> estadosUsuarioList;
    private Usuario user;
    private SessionController session;
    private Mail mail;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
    }

    @PostConstruct
    public void init() {

    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public Usuario getNuevoUsuario() {
        if (nuevoUsuario == null ) {
            nuevoUsuario = new Usuario();
        }
        return nuevoUsuario;
    }   

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public List<Usuario> getUsuariosList() {
        if (usuariosList == null || usuariosList.isEmpty()) {
            usuariosList = uDAO.findAll();
        }
        return usuariosList;
    }
    
    public Usuario sessionUser() {
        session = new SessionController();
        user = session.usuarioSession();
        return user;
    }
    
    public List<Usuario> getUsuarioSessionList(Usuario u) {
        if (usuarioSessionList == null || usuarioSessionList.isEmpty()) {
            usuarioSessionList = uDAO.findById(sessionUser());
        }
        return usuarioSessionList;
    }
    
    public List<EstadoUsuario> getEstadosUsuarioList() {
        if (estadosUsuarioList ==  null || estadosUsuarioList.isEmpty()) {
            estadosUsuarioList = euDAO.findAll();
        }
        return estadosUsuarioList;
    }

    public void usuarioSeleccionado(Usuario u) {
        System.out.println("Se seleccionó el usuario");
        System.out.println(u);
        usuarioSeleccionado = u;
    }

    public void registrar() {
        session = new SessionController();
        try {
            if (confirmarClave != null && confirmarClave.trim().length() >= 5 && confirmarClave.equals(nuevoUsuario.getContrasena())) {
                uDAO.register(nuevoUsuario);
                String cuerpo = "SE HA REGISTRADO EXITOSAMENTE EN NUESTRO SISTEMA. Ahora puedes ingresar con tu correo: " + nuevoUsuario.getCorreo() + "y contrasena: " + nuevoUsuario.getContrasena() + "Recuerda que estos datos son personales e intrasferibles";
                Mail.sendMail(nuevoUsuario.getCorreo(), "BIENVENIDO - DIGITAL EVENTS", cuerpo );
                MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "", false);
            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "Las claves no son identicas verifique y vuelva a intentarlo.", false);
            }
        } catch (Exception e) {
            MessageUtil.addErrorMessage(null, "Error al registrar el usuario", e.getMessage(), false);
        }
    }

    public void actualizar() {
        session = new SessionController();
        try {
            if (usuarioSeleccionado.getDocumento() > 0) {
                uDAO.update(usuarioSeleccionado);
                MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos del usuario se han actualizaco correctamente,", false);
            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "El documento no puede estar vacio.", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar el usuario", e.getMessage(), false);
        }
    }

    public void eliminar() {
        try {
            uDAO.delete(usuarioSeleccionado);
            MessageUtil.addInfoMessage(null, "Eliminación exitosa", "Los datos del usuario se han eliminado correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al eliminar el usuario", e.getMessage(), false);
        }
    }
}
