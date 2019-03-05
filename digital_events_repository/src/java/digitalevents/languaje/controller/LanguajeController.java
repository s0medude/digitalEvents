/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.languaje.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "languajeController")
@SessionScoped
public class LanguajeController implements Serializable {

    /**
     * Creates a new instance of LanguajeController
     */
    private String lenguaje;
    private Locale locale;
    private List<Locale> idiomaPredefinido;
    
    public LanguajeController() {
    }
    @PostConstruct
    public void init(){
         idiomaPredefinido=new ArrayList<Locale>();
        idiomaPredefinido.add(new Locale("es"));
        idiomaPredefinido.add(new Locale("en"));
    }

    public List<Locale> getIdiomaPredefinido() {
        return idiomaPredefinido;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    
}
