/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.entities.Solicitud;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = Solicitud.class)
public class SolicitudConverter implements Converter {

    private final ISolicitudDAO sDAO;

    public SolicitudConverter() {
        sDAO = CDI.current().select(ISolicitudDAO.class).get();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - SOLICITUD asObject");
            return sDAO.findByPK(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override 
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - SOLICITUD asString");
            return ((Solicitud) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
}
