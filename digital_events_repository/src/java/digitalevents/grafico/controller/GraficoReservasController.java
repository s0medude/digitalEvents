/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import edu.digitalEvents.modelo.dao.IEventoDAO;
import edu.digitalEvents.modelo.dao.IEventoReservadoViewDAO;
import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.entities.EventoReservadoView;
import edu.digitalEvents.modelo.entities.Solicitud;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Aprendiz
 */
@Named(value = "graficoReservasController")
@ViewScoped
public class GraficoReservasController implements  Serializable{

    /**
     * Creates a new instance of GrafricoReservasController
     */
    @EJB
    private IEventoReservadoViewDAO IER;
    @EJB
    private ISolicitudDAO solicituadDao;
    
    private ArrayList<String> fechaReserva = new ArrayList<String>();
    private ArrayList<Integer> numeroEventos = new ArrayList<Integer>();
    private ArrayList<EventoReservadoView> eventoreserva;

    public GraficoReservasController() {

    }

    @PostConstruct
    public void init() {
        for (Solicitud e : solicituadDao.findAll()) {
            //EntityGrafricoZingChar cuen = new EntityGrafricoZingChar(e.getId(), e.getNumeroEvaluacion());
            SimpleDateFormat nn = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = nn.format(e.getFechaReserva());
            fechaReserva.add(fecha);
            System.out.println(e.getFechaReserva());
            numeroEventos.add(e.getId());
        }
    }

    public String graficoReserva(ArrayList<?> lista, String delimitador) {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) {
                output.append(delimitador);
            }
            // --- Quote strings, only, for JS syntax
            if (lista.get(i) instanceof String) {
                output.append("\"");
            }
            output.append(lista.get(i));
            if (lista.get(i) instanceof String) {
                output.append("\"");
            }
        }
        return output.toString();
    }

    public ArrayList<String> getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(ArrayList<String> fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public ArrayList<Integer> getNumeroEventos() {
        return numeroEventos;
    }

    public void setNumeroEventos(ArrayList<Integer> numeroEventos) {
        this.numeroEventos = numeroEventos;
    }


}
