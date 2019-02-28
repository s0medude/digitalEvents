/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import edu.digitalEvents.modelo.dao.IEventoDAO;
import edu.digitalEvents.modelo.entities.GrafricoEventosView;
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
@Named(value = "grafricoEventosController")
@ViewScoped
public class GrafricoEventosController implements Serializable{

    /**
     * Creates a new instance of GrafricoEventosController
     */
    @EJB
    private IEventoDAO eDao;     
    private ArrayList<Long> cuentaEventos=new ArrayList<Long>();
    private ArrayList<String> mesesEvento = new ArrayList<String>();
    private ArrayList<String> tipoEvento=new ArrayList<String>();
    
    
    public GrafricoEventosController() {
    }
    @PostConstruct
    public void init(){
       String mesesEs=null;
        for (GrafricoEventosView grafrico : eDao.findAllEventosGrafrico()) {
            switch (grafrico.getMeses()) {
                case "January":
                    mesesEs = "Enero";
                    break;
                case "February":
                     mesesEs = "Febrero";
                    break;
                case "March":
                    mesesEs = "Marzo";
                    break;
                case "April":
                     mesesEs = "Abril";
                    break;
                case "May":
                     mesesEs = "Mayo";
                    break;
                case "June":
                    mesesEs = "Junio";
                    break;
                case "July":
                     mesesEs = "Julio";
                    break;
                case "August":
                     mesesEs = "Agosto";
                    break;
                case "September":
                    mesesEs = "Septiembre";
                    break;
                case "October":
                    mesesEs = "Octubre";
                    break;
                case "November":
                    mesesEs = "Noviembre";
                    break;
                case "December":
                    mesesEs = "Diciembre";
                    break;
                default:
                    throw new AssertionError();
            }
            cuentaEventos.add(grafrico.getCuentaEventos());
            tipoEvento.add(grafrico.getTipoevento());
            mesesEvento.add(mesesEs);
        }
    }

    public ArrayList<Long> getCuentaEventos() {
        return cuentaEventos;
    }

    public void setCuentaEventos(ArrayList<Long> cuentaEventos) {
        this.cuentaEventos = cuentaEventos;
    }

    public ArrayList<String> getMesesEvento() {
        return mesesEvento;
    }

    public void setMesesEvento(ArrayList<String> mesesEvento) {
        this.mesesEvento = mesesEvento;
    }

    public ArrayList<String> getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(ArrayList<String> tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}
