/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.GrafricoInventarioView;
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
@Named(value = "grafricoInventarioController")
@ViewScoped
public class GrafricoInventarioController implements Serializable {

    /**
     * Creates a new instance of GrafricoInventarioController
     */
    @EJB
    private IMaterialDAO mDao;
    private ArrayList<String> estado_material = new ArrayList<String>();
    private ArrayList<String> tipoMaterial = new ArrayList<String>();
    private ArrayList<Long> cantidad_articulo = new ArrayList<Long>();

    public GrafricoInventarioController() {
    }

    @PostConstruct
    public void init() {
        for (GrafricoInventarioView g : mDao.findAllInventarioGrafrico()) {
            estado_material.add(g.getEstadoMaterial());
            tipoMaterial.add(g.getTipoMaterial());
            cantidad_articulo.add(g.getCantidad());
        }
    }

    public ArrayList<String> getEstado_material() {
        return estado_material;
    }

    public void setEstado_material(ArrayList<String> estado_material) {
        this.estado_material = estado_material;
    }

    public ArrayList<String> getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(ArrayList<String> tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public ArrayList<Long> getCantidad_articulo() {
        return cantidad_articulo;
    }

    public void setCantidad_articulo(ArrayList<Long> cantidad_articulo) {
        this.cantidad_articulo = cantidad_articulo;
    }
    
}
