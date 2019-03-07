/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.ventas.controller;

import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IEstadoSolicitudDAO;
import edu.digitalEvents.modelo.dao.IEventoReservadoViewDAO;
import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.dao.ITipoEventoDAO;
import edu.digitalEvents.modelo.entities.EstadoSolicitud;
import edu.digitalEvents.modelo.entities.EventoReservadoView;
import edu.digitalEvents.modelo.entities.Solicitud;
import edu.digitalEvents.modelo.entities.TipoEvento;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "solicitudController")
@ViewScoped
public class SolicitudController implements Serializable {

    @EJB
    private ISolicitudDAO sDAO;
    @EJB
    private IEstadoSolicitudDAO esDAO;
    @EJB
    private ITipoEventoDAO teDAO;

    private Solicitud nuevaSolicitud;
    private Solicitud solicitudSeleccionada;
    private List<Solicitud> solicitudList;
    private List<Solicitud> solicitudEstadoList;
    
    private List<EstadoSolicitud> estadoSolicitudList;    
    
    private List<TipoEvento> tipoEventoList;
    
    //private static DateFormat sdf; 

    /**
     * Creates a new instance of SolicitudController
     */
    public SolicitudController() {
        //SolicitudController.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }

    @PostConstruct
    public void init() {
    }

    public Solicitud getNuevaSolicitud() {
        if (nuevaSolicitud == null) {
            nuevaSolicitud = new Solicitud();
        }
        return nuevaSolicitud;
    }

    public Solicitud getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
        this.solicitudSeleccionada = solicitudSeleccionada;
    }

    public List<Solicitud> getSolicitudList() {
        if (solicitudList == null || solicitudList.isEmpty()) {
            solicitudList = sDAO.findAll();

        }
        return solicitudList;
    }

    public List<Solicitud> getSolicitudEstadoList() {
        if (solicitudEstadoList == null || solicitudEstadoList.isEmpty()) {
            solicitudEstadoList = (List<Solicitud>) sDAO.findByEstado();
        }
        return solicitudEstadoList;
    }    
    

    public List<EstadoSolicitud> getEstadoSolicitudList() {
        if (estadoSolicitudList == null || solicitudList.isEmpty()) {
            estadoSolicitudList = esDAO.findAll();
        }
        return estadoSolicitudList;
    }

    public List<TipoEvento> getTipoEventoList() {
        if (tipoEventoList == null || tipoEventoList.isEmpty()) {
            tipoEventoList = teDAO.findAll();
        }
        return tipoEventoList;
    }
        

    public void solicitudSeleccionada(Solicitud s) {
        System.out.println("Se selecciono la solicitud");
        System.out.println(s);
        solicitudSeleccionada = s;
    }

    /*public static DateFormat getSdf(Date date) {
        date = new Date();
        System.out.println(sdf.format(date));
        return sdf;
    }*/
    public void actualizar() {
        try {
            sDAO.update(solicitudSeleccionada);
            MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos de la solicitud se han actualizaco correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar la solicitud", e.getMessage(), false);
        }
    }

    public void registrar() {
        try {
            if (nuevaSolicitud.getId() != null) {
                sDAO.register(nuevaSolicitud);
                MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "", false);
            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "", false);
            }
        } catch (Exception e) {
            MessageUtil.addErrorMessage(null, "Error al registrar la Solicitud", e.getMessage(), false);
        }
    }

    public void eliminar() {
        try {
            sDAO.delete(solicitudSeleccionada);
            MessageUtil.addInfoMessage(null, "Eliminación exitosa", "Los datos de la solicitud se han eliminado correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al eliminar el usuario", e.getMessage(), false);

        }
    }
}
