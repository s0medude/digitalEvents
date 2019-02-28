/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.UsuarioEstadoView;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan
 */
@Named(value = "grafricoUsuariosController")
@ViewScoped
public class GrafricoUsuariosController implements Serializable {

    /**
     * Creates a new instance of GrafricoUsuariosController
     */
    @EJB
    private IUsuarioDAO uDao;
    private ArrayList<String> estadoUsuario = new ArrayList<String>();
    private ArrayList<String> mesActualizacion = new ArrayList<String>();
    private ArrayList<Long> cuentasUsuarios = new ArrayList<Long>();

    public GrafricoUsuariosController() {
    }

    @PostConstruct
    public void init() {
        String mesUsuario = null;
        for (UsuarioEstadoView e : uDao.findAllUsuarios()) {
            //EntityGrafricoZingChar cuen = new EntityGrafricoZingChar(e.getId(), e.getNumeroEvaluacion());
            estadoUsuario.add(e.getEstado());
            switch (e.getMesEstado()) {
                case "January":
                    mesUsuario = "Enero";
                    break;
                case "February":
                    mesUsuario = "Febrero";
                    break;
                case "March":
                    mesUsuario = "Marzo";
                    break;
                case "April":
                    mesUsuario = "Abril";
                    break;
                case "May":
                    mesUsuario = "Mayo";
                    break;
                case "June":
                    mesUsuario = "Junio";
                    break;
                case "July":
                    mesUsuario = "Julio";
                    break;
                case "August":
                    mesUsuario = "Agosto";
                    break;
                case "September":
                    mesUsuario = "Septiembre";
                    break;
                case "October":
                    mesUsuario = "Octubre";
                    break;
                case "November":
                    mesUsuario = "Noviembre";
                    break;
                case "December":
                    mesUsuario = "Diciembre";
                    break;
                default:
                    throw new AssertionError();
            }
            estadoUsuario.add(e.getEstado());
            cuentasUsuarios.add(e.getCuentaUsuarios());
            mesActualizacion.add(mesUsuario);
        }
    }

    public String graficoUsuario(ArrayList<?> lista, String delimitador) {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) {
                output.append(delimitador);
            }
            // --- Quote strings, only, for JS syntax
            if (lista.get(i) instanceof String) {
                output.append("\"");
            }
            output.append(lista.get(i));
            if (lista.get(i) instanceof String) {
                output.append("\"");
            }
        }
        return output.toString();
    }

    public ArrayList<String> getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(ArrayList<String> estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public ArrayList<String> getMesActualizacion() {
        return mesActualizacion;
    }

    public void setMesActualizacion(ArrayList<String> mesActualizacion) {
        this.mesActualizacion = mesActualizacion;
    }

    public ArrayList<Long> getCuentasUsuarios() {
        return cuentasUsuarios;
    }

    public void setCuentasUsuarios(ArrayList<Long> cuentasUsuarios) {
        this.cuentasUsuarios = cuentasUsuarios;
    }

    
}
