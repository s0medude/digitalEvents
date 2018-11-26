/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;


import edu.digitalEvents.modelo.dao.IEstadoUsuarioDAO;
import edu.digitalEvents.modelo.entities.EstadoUsuario;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = EstadoUsuario.class)
public class EstadoUsuarioConverter implements Converter {

    private final IEstadoUsuarioDAO uDAO;
    
    public EstadoUsuarioConverter() {
        uDAO = CDI.current().select(IEstadoUsuarioDAO.class).get();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return uDAO.findByPK(Integer.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        try {
            return ((EstadoUsuario) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
    
}
