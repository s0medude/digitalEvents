/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.dao.IDisponibilidadMaterialDAO;
import edu.digitalEvents.modelo.entities.DisponilidadMaterial;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = DisponilidadMaterial.class)
public class DisponibilidadMaterialConverter implements Converter {

    private final IDisponibilidadMaterialDAO dmDAO;
    
    public DisponibilidadMaterialConverter() {
        dmDAO = CDI.current().select(IDisponibilidadMaterialDAO.class).get();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return dmDAO.findByPK(Integer.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        try {
            return ((DisponilidadMaterial) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
    
}
