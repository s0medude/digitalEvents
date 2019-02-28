/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import edu.digitalEvents.modelo.dao.IEvaluacionEventoDAO;
import edu.digitalEvents.modelo.entities.GrafricoServicioAlCliente;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan
 */
@Named(value = "grafricoServicioAlClienteController")
@ViewScoped
public class GrafricoServicioAlClienteController implements Serializable {

    /**
     * Creates a new instance of GrafricoServicioAlClienteController
     */
    @EJB
    private IEvaluacionEventoDAO eDao;
    private ArrayList<String> calificacion = new ArrayList<String>();
    private ArrayList<Long> total = new ArrayList<Long>();

    public GrafricoServicioAlClienteController() {
    }

    @PostConstruct
       public void init() {
        for (GrafricoServicioAlCliente e : eDao.findAllServicoAlCliente()) {
            //EntityGrafricoZingChar cuen = new EntityGrafricoZingChar(e.getId(), e.getNumeroEvaluacion());
            String califiacion=null;
            switch (e.getCalificacion()) {
                case 1:
                    califiacion="Bueno";
                    break;
                case 2:
                    califiacion="Medio";
                    break;
                case 3:
                    califiacion="Alto";
                    break;
                default:
                    throw new AssertionError();
            }
            calificacion.add(califiacion);
            total.add(e.getTotaldeeventos());
        }
    }


    public String graficoServicioAlCliente(ArrayList<?> lista, String delimitador) {

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

    public ArrayList<String> getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(ArrayList<String> calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Long> getTotal() {
        return total;
    }

    public void setTotal(ArrayList<Long> total) {
        this.total = total;
    }

    

}
