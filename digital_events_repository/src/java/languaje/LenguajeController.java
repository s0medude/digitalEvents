/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languaje;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;

/**
 *
 * @author Juan
 */
@Named(value = "lenguajeController")
@SessionScoped
public class LenguajeController implements Serializable {
private String languaje;
private Locale locale;
private List<Locale> idiomaPredefinido;
    /**
     * Creates a new instance of LenguajeController
     */
    public LenguajeController() {
    }
    @PostConstruct
    public void init(){
        idiomaPredefinido=new ArrayList<Locale>();
        idiomaPredefinido.add(new Locale("es"));
        idiomaPredefinido.add(new Locale("en"));
    }
  
    public String getLanguaje() {
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
        locale=new Locale(languaje);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<Locale> getIdiomaPredefinido() {
        return idiomaPredefinido;
    }
    
    
}
