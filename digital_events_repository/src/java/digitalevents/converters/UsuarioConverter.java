/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.converters;


import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.Usuario;

/**
 *
 * @author Nicolas PC
 */
@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

    private final IUsuarioDAO uDAO;

    public UsuarioConverter() {
        uDAO = CDI.current().select(IUsuarioDAO.class).get();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - USUARIO asObject");
            return uDAO.findByPK(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override 
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        try {
            System.out.println("fgdfsaghdhafgsdashfdghasfdgfaghsdhafghsdafdhasd - USUARIO asString");
            return ((Usuario) obj).getId().toString();
        } catch (Exception e) {
            return "";
        }
    }
}