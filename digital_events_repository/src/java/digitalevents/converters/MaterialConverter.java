/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;

import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.Material;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = Material.class)
public class MaterialConverter implements Converter {

    private final IMaterialDAO mDAO;

    public MaterialConverter() {
        mDAO = CDI.current().select(IMaterialDAO.class).get();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - MATERIAL asObject");
            return mDAO.findByPK(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override 
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - MATERIAL asString");
            return ((Material) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
}
