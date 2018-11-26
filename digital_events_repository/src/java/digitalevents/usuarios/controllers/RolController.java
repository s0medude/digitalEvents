/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.usuarios.controllers;

import edu.digitalEvents.modelo.dao.IRolDAO;
import edu.digitalEvents.modelo.entities.Rol;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "rolController")
@ViewScoped
public class RolController implements Serializable{
    
    @EJB
    private IRolDAO rDAO;
    
    private Rol nuevoRol;
    private List<Rol> rolList;

    /**
     * Creates a new instance of RolController
     */
    public RolController() {
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public Rol getNuevoRol() {
        if (nuevoRol == null) {
            nuevoRol = new Rol();
        }
        return nuevoRol;
    }
    
    public List<Rol> getRolList() {
        if (rolList == null || rolList.isEmpty()) {
            rolList = rDAO.findAll();
        }
        return rolList;
    }
    
}
