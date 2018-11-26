/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.ventas.controller;

import edu.digitalEvents.modelo.dao.IEventoReservadoViewDAO;
import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.entities.EventoReservadoView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "eventoController")
@RequestScoped
public class EventoController {

    @EJB
    private IEventoReservadoViewDAO eDAO;
    
    private List<EventoReservadoView> eventosList;

    /**
     * Creates a new instance of EventoController
     */
    public EventoController() {
    }

    @PostConstruct
    public void init() {
    }

    public List<EventoReservadoView> getEventosList() {
        if (eventosList == null || eventosList.isEmpty()) {
            eventosList = eDAO.findAll();
        }
        return eventosList;
    }   

}
