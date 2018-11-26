/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.dao.IEstadoSolicitudDAO;
import edu.digitalEvents.modelo.entities.EstadoSolicitud;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = EstadoSolicitud.class)
public class EstadoSolicitudConverter implements Converter {

    private final IEstadoSolicitudDAO esDAO;
    
    public EstadoSolicitudConverter() {
        esDAO = CDI.current().select(IEstadoSolicitudDAO.class).get();
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return esDAO.findByPK(Integer.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        try {
            return ((EstadoSolicitud) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
    
}
