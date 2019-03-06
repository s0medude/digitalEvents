/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import com.google.gson.Gson;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.EntityGrafricoGlobal;
import edu.digitalEvents.modelo.entities.GrafricoInventarioView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan
 */
@Named(value = "grafricoInventarioController")
@ViewScoped
public class GrafricoInventarioController implements Serializable {

    /**
     * Creates a new instance of GraficoInventarioController
     */
    @EJB
    private IMaterialDAO mDao;
    private List<EntityGrafricoGlobal> datosMaterialesBuenaCalidad;
    private List<EntityGrafricoGlobal> datosMaterialesMalaCalidad;

    @PostConstruct
    public void init() {

    }

    public List<EntityGrafricoGlobal> getDatosMaterialesBuenaCalidad() {
        datosMaterialesBuenaCalidad = new ArrayList<>();
        int contador = 1;
        for (GrafricoInventarioView e : mDao.findAllInventarioGrafrico()) {
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCantidad(), e.getEstadoMaterial(), e.getTipoMaterial());
            datosMaterialesBuenaCalidad.add(cuen);
        }
        return datosMaterialesBuenaCalidad;
    }

    public List<EntityGrafricoGlobal> getDatosMaterialesMalaCalidad() {
        datosMaterialesMalaCalidad = new ArrayList<>();
        int contador = mDao.findAllInventarioGrafrico().size()+1;
        for (GrafricoInventarioView e : mDao.findAllInventarioMalEstadoGrafrico()) {
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCantidad(), e.getEstadoMaterial(), e.getTipoMaterial());
            datosMaterialesMalaCalidad.add(cuen);
        }
        return datosMaterialesMalaCalidad;
    }

    public String getDatosJsonBuenaCalidad() {
        Gson gson = new Gson();
        return gson.toJson(getDatosMaterialesBuenaCalidad());
    }

    public String getDatosJsonMalaCalidad() {
        Gson gson = new Gson();
        return gson.toJson(getDatosMaterialesMalaCalidad());
    }
    
}
