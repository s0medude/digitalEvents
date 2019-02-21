/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.entities.Rol;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import edu.digitalEvents.modelo.dao.IRolDAO;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = Rol.class)
public class RolConverter implements Converter {

    private final IRolDAO rDAO;

    public RolConverter() {
        rDAO = CDI.current().select(IRolDAO.class).get();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - ROL asObject");
            return rDAO.findByPK(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override 
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - ROL asString");
            return ((Rol) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
}
