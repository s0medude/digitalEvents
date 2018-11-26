/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;


import edu.digitalEvents.modelo.dao.ITipoDocumentoDAO;
import edu.digitalEvents.modelo.entities.TipoDocumento;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = TipoDocumento.class)
public class TipoDocumentoConverter implements Converter {

    private final ITipoDocumentoDAO tDAO;
    
    public TipoDocumentoConverter() {
        tDAO = CDI.current().select(ITipoDocumentoDAO.class).get();
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
            return ((TipoDocumento) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
    
}
