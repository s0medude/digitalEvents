/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.dao.ITipoEventoDAO;
import edu.digitalEvents.modelo.entities.TipoEvento;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = TipoEvento.class)
public class TipoEventoConverter implements Converter {

    private final ITipoEventoDAO tDAO;
    
    public TipoEventoConverter() {
        tDAO = CDI.current().select(ITipoEventoDAO.class).get();
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return tDAO.findByPK(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        try {
            return ((TipoEvento) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
    
}
