/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.usuarios.controllers;

import edu.digitalEvents.modelo.dao.ITipoDocumentoDAO;
import edu.digitalEvents.modelo.entities.TipoDocumento;
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
@Named(value = "tipoDocumentoController")
@ViewScoped
public class TipoDocumentoController implements Serializable {
    
    @EJB
    private ITipoDocumentoDAO tdDAO;
    
    private List<TipoDocumento> tiposDocumentoList;            

    /**
     * Creates a new instance of TipoDocumentoController
     */
    public TipoDocumentoController() {
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public List<TipoDocumento> getTiposDocumentoList() {
        if (tiposDocumentoList == null || tiposDocumentoList.isEmpty()) {
            tiposDocumentoList = tdDAO.findAll();
        }
        return tiposDocumentoList;
    }
}
