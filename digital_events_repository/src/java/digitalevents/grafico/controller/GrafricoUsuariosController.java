/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.grafico.controller;

import com.google.gson.Gson;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.EntityGrafricoGlobal;
import edu.digitalEvents.modelo.entities.UsuarioEstadoView;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
     * Creates a new instance of GraficoUsuariosController
     */
    @EJB
    private IUsuarioDAO uDao;
    private List<EntityGrafricoGlobal> datosGrafricoEstadoActivo;
    private List<EntityGrafricoGlobal> datosGrafricoEstadoBloqueado;

    @PostConstruct
    public void init() {

    }

    public List<EntityGrafricoGlobal> getDatosGrafricoEstadoActivo() {
        datosGrafricoEstadoActivo = new ArrayList<>();
        int contador = 1;
        String mesUsuario = null;
        for (UsuarioEstadoView e : uDao.findAllUsuarios()) {
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
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCuentaUsuarios(), e.getEstado(), mesUsuario);
            datosGrafricoEstadoActivo.add(cuen);
        }
        return datosGrafricoEstadoActivo;
    }

    public List<EntityGrafricoGlobal> getDatosGrafricoEstadoBloqueado() {
        datosGrafricoEstadoBloqueado = new ArrayList<>();
        int contador = uDao.findAllUsuarios().size()+1;
        String mesUsuario = null;
       
        for (UsuarioEstadoView e : uDao.findAllUsuariosBloqueados()) {
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
            EntityGrafricoGlobal cuen = new EntityGrafricoGlobal(contador++, e.getCuentaUsuarios(), e.getEstado(), mesUsuario);
            datosGrafricoEstadoBloqueado.add(cuen);
        }
        return datosGrafricoEstadoBloqueado;
    }

    public String getDatosJsonUsuariosActivos() {
        Gson gson = new Gson();
        return gson.toJson(getDatosGrafricoEstadoActivo());
    }

    public String getDatosJsonUsuariosBloqueados() {
        Gson gson = new Gson();
        return gson.toJson(getDatosGrafricoEstadoBloqueado());
    }
}
