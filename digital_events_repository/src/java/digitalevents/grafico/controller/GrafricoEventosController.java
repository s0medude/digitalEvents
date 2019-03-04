/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import com.google.gson.Gson;
import edu.digitalEvents.modelo.dao.IEventoDAO;
import edu.digitalEvents.modelo.entities.GrafricoEventosView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import edu.digitalEvents.modelo.entities.EntityGrafricoGlobal;

/**
 *
 * @author Juan
 */
@Named(value = "grafricoEventosController")
@ViewScoped
public class GrafricoEventosController implements Serializable{
    
    /**
     * Creates a new instance of GraficoEventosController
     */
    @EJB
    private IEventoDAO eDao;
    private List<EntityGrafricoGlobal> datosGrafricoEmpresarial;
    private List<EntityGrafricoGlobal> datosGrafricoInfantil;
    private List<EntityGrafricoGlobal> datosGrafricoFiestaTematica;

    @PostConstruct
    public void init() {

    }

    public List<EntityGrafricoGlobal> getDatosGrafricoEmpresarial() {
        datosGrafricoEmpresarial =new ArrayList<>();
        int contador = 1;
        String mesUsuario = null;
        for (GrafricoEventosView e : eDao.findAllEventosGrafricoEMPRESARIAL()) {
            switch (e.getMeses()) {
                case "January":
                    mesUsuario = "Enero";
                    break;
                case "February":
                    mesUsuario = "Febrero";
                    break;
                case "March":
                    mesUsuario = "Marzo";
                    break;
                case "April":
                    mesUsuario = "Abril";
                    break;
                case "May":
                    mesUsuario = "Mayo";
                    break;
                case "June":
                    mesUsuario = "Junio";
                    break;
                case "July":
                    mesUsuario = "Julio";
                    break;
                case "August":
                    mesUsuario = "Agosto";
                    break;
                case "September":
                    mesUsuario = "Septiembre";
                    break;
                case "October":
                    mesUsuario = "Octubre";
                    break;
                case "November":
                    mesUsuario = "Noviembre";
                    break;
                case "December":
                    mesUsuario = "Diciembre";
                    break;
                default:
                    throw new AssertionError();
            }
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCuentaEventos(), e.getTipoevento(), mesUsuario);
            datosGrafricoEmpresarial.add(cuen);
        }
        return datosGrafricoEmpresarial;
    }

    public List<EntityGrafricoGlobal> getDatosGrafricoInfantil() {
        datosGrafricoInfantil =new ArrayList<>();
        int contador = 1;
        String mesUsuario = null;
        for (GrafricoEventosView e : eDao.findAllEventosGrafricoINFANTILES()) {
            switch (e.getMeses()) {
                case "January":
                    mesUsuario = "Enero";
                    break;
                case "February":
                    mesUsuario = "Febrero";
                    break;
                case "March":
                    mesUsuario = "Marzo";
                    break;
                case "April":
                    mesUsuario = "Abril";
                    break;
                case "May":
                    mesUsuario = "Mayo";
                    break;
                case "June":
                    mesUsuario = "Junio";
                    break;
                case "July":
                    mesUsuario = "Julio";
                    break;
                case "August":
                    mesUsuario = "Agosto";
                    break;
                case "September":
                    mesUsuario = "Septiembre";
                    break;
                case "October":
                    mesUsuario = "Octubre";
                    break;
                case "November":
                    mesUsuario = "Noviembre";
                    break;
                case "December":
                    mesUsuario = "Diciembre";
                    break;
                default:
                    throw new AssertionError();
            }
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCuentaEventos(), e.getTipoevento(), mesUsuario);
            datosGrafricoInfantil.add(cuen);
        }
        return datosGrafricoInfantil;
    }

    public List<EntityGrafricoGlobal> getDatosGrafricoFiestaTematica() {
        datosGrafricoFiestaTematica =new ArrayList<>();
        int contador = 1;
        String mesUsuario = null;
        for (GrafricoEventosView e : eDao.findAllEventosGrafrico()) {
            switch (e.getMeses()) {
                case "January":
                    mesUsuario = "Enero";
                    break;
                case "February":
                    mesUsuario = "Febrero";
                    break;
                case "March":
                    mesUsuario = "Marzo";
                    break;
                case "April":
                    mesUsuario = "Abril";
                    break;
                case "May":
                    mesUsuario = "Mayo";
                    break;
                case "June":
                    mesUsuario = "Junio";
                    break;
                case "July":
                    mesUsuario = "Julio";
                    break;
                case "August":
                    mesUsuario = "Agosto";
                    break;
                case "September":
                    mesUsuario = "Septiembre";
                    break;
                case "October":
                    mesUsuario = "Octubre";
                    break;
                case "November":
                    mesUsuario = "Noviembre";
                    break;
                case "December":
                    mesUsuario = "Diciembre";
                    break;
                default:
                    throw new AssertionError();
            }
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCuentaEventos(), e.getTipoevento(), mesUsuario);
            datosGrafricoFiestaTematica.add(cuen);
        }

        return datosGrafricoFiestaTematica;
    }
     public String getDatosJsonEmpresarial() {
        Gson gson = new Gson();
        return gson.toJson(getDatosGrafricoEmpresarial());
    }
     public String getDatosJsonInfantil() {
        Gson gson = new Gson();
        return gson.toJson(getDatosGrafricoInfantil());
    }
     public String getDatosJsonTematica() {
        Gson gson = new Gson();
        return gson.toJson(getDatosGrafricoFiestaTematica());
    }
}
