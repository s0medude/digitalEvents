/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.inventario.controller;

import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IDisponibilidadMaterialDAO;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.DisponilidadMaterial;
import edu.digitalEvents.modelo.entities.Material;
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
@Named(value = "inventarioController")
@ViewScoped
public class InventarioController implements Serializable {

    @EJB
    private IMaterialDAO mDAO;
    @EJB
    private IDisponibilidadMaterialDAO dmDAO;

    private Material nuevoMaterial;
    private Material materialSeleccionado;
    private List<Material> materialList;

    private DisponilidadMaterial dispMaterial;
    private List<DisponilidadMaterial> estadoMaterialList;

    /**
     * Creates a new instance of inventarioController
     */
    public InventarioController() {
    }

    @PostConstruct
    public void init() {
    }

    public Material getNuevoMaterial() {
        if (nuevoMaterial == null) {
            nuevoMaterial = new Material();
        }
        return nuevoMaterial;
    }

    public Material getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public List<Material> getMaterialList() {
        if (materialList == null || materialList.isEmpty()) {
            materialList = mDAO.findAll();
        }
        return materialList;
    }

    public List<DisponilidadMaterial> getEstadoMaterialList() {
        if (estadoMaterialList == null || estadoMaterialList.isEmpty()) {
            estadoMaterialList = dmDAO.findAll();
        }
        return estadoMaterialList;
    }
    
    public void materialSeleccionado(Material m) {
        System.out.println("Se selecciono el articulo: ");
        System.out.println(m);
        materialSeleccionado = m;
    }
    
    public void actualizar() {
        try {
            mDAO.update(materialSeleccionado);
            MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos del material se han actualizaco correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar el material", e.getMessage(), false);
        }
    }
    
    public String registrar() {
        String rta = "";
        try {
            if (nuevoMaterial.getId() != null || nuevoMaterial.getDisponilidad().equals(dispMaterial.getId())) {
                mDAO.register(nuevoMaterial);
                MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "", false);
            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "", false);
            }
        } catch (Exception e) {
            MessageUtil.addErrorMessage(null, "Error al registrar el material", e.getMessage(), false);
        }
        return rta;
    }

    public void eliminar() {
        try {
            mDAO.delete(materialSeleccionado);
            MessageUtil.addInfoMessage(null, "Eliminación exitosa", "Los datos del material se han eliminado correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al eliminar el material", e.getMessage(), false);

        }
    }
    
}
