/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.ventas.controller;

import digitalevents.login.controllers.SessionController;
import digitalevents.utils.Mail;
import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IEstadoSolicitudDAO;
import edu.digitalEvents.modelo.dao.IEventoReservadoViewDAO;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.dao.IMaterialSolicitudDAO;
import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.dao.ITipoEventoDAO;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.EstadoSolicitud;
import edu.digitalEvents.modelo.entities.EventoReservadoView;
import edu.digitalEvents.modelo.entities.Material;
import edu.digitalEvents.modelo.entities.MaterialSolicitud;
import edu.digitalEvents.modelo.entities.Solicitud;
import edu.digitalEvents.modelo.entities.TipoEvento;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "solicitudController")
@SessionScoped
public class SolicitudController implements Serializable {

    @EJB
    private ISolicitudDAO sDAO;
    @EJB
    private IEstadoSolicitudDAO esDAO;
    @EJB
    private ITipoEventoDAO teDAO;
    @EJB
    private IMaterialDAO mDAO;
    @EJB
    private IMaterialSolicitudDAO msDAO;
    @EJB
    private IUsuarioDAO uDAO;

    private Solicitud nuevaSolicitud;
    private Solicitud solicitudSeleccionada;
    private List<Solicitud> solicitudList;
    private List<Solicitud> solicitudEstadoList;
    private List<EstadoSolicitud> estadoSolicitudList;
    private List<TipoEvento> tipoEventoList;
    private List<Material> materialList;
    private List<Material> material = new ArrayList();
    private List<MaterialSolicitud> mateSolicitudList = new ArrayList();
    private List<Material> listCarrito = new ArrayList();
    private SessionController session;
    private MaterialSolicitud materialSolicitudObj;
    private Material materialObj;
    private TipoEvento tipoEvento;
    private Date fechaReserva;
    private String direccion;
    private String especificaciones;
    private Integer idMaterial;
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

    public List<Material> getMaterialList() {
        if (materialList == null || materialList.isEmpty()) {
            materialList = mDAO.findAllByDisponilidad();
        }
        return materialList;
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
    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public List<Material> getListCarrito() {
        return listCarrito;
    }

    public void setListCarrito(List<Material> listCarrito) {
        this.listCarrito = listCarrito;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }

    public List<MaterialSolicitud> getMateSolicitudList() {
        return mateSolicitudList;
    }

    public void setMateSolicitudList(List<MaterialSolicitud> mateSolicitudList) {
        this.mateSolicitudList = mateSolicitudList;
    }

    public MaterialSolicitud getMaterialSolicitudObj() {
        return materialSolicitudObj;
    }

    public void setMaterialSolicitudObj(MaterialSolicitud materialSolicitudObj) {
        this.materialSolicitudObj = materialSolicitudObj;
    }

    public Material getMaterialObj() {
        return materialObj;
    }

    public void setMaterialObj(Material materialObj) {
        this.materialObj = materialObj;
    }    
    

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
            sDAO.register(nuevaSolicitud);
            MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "", false);

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

    public void limpiarCarrito(Material mate) {
        System.out.println("Entro");
        try {
            listCarrito.remove(mate);
            MessageUtil.addInfoMessage(null, "Material Eliminado", mate.getTipoMaterial(), true);
            System.out.println("Removio" + mate.getTipoMaterial());
        } catch (Exception e) {
        }
    }
    
    public void cargarCarrito(int i) {
        Material tempMaterial;
        try {
            tempMaterial = mDAO.findByPK(i);
            listCarrito.add(tempMaterial);
            MessageUtil.addInfoMessage(null, "Material Agregado", tempMaterial.getTipoMaterial(), true);
        } catch (Exception e) {
            MessageUtil.addErrorMessage(null, "Material Anulado", null, true);
            System.out.println("HUBO UN ERROR PARCEE OME GONORREAAA OMEEEE");
        }
    }

    public void agregarMaterial() {
        nuevaSolicitud.setUsuariosId(session.usuarioSession());
        nuevaSolicitud.setId(sDAO.findMaxId() + 1);
        materialObj.setId(idMaterial);        
        materialSolicitudObj.setSolicitudId(nuevaSolicitud);
        materialSolicitudObj.setMaterialesId(materialObj);
        mateSolicitudList.add(materialSolicitudObj);
        cargarCarrito(idMaterial);
        materialSolicitudObj = new MaterialSolicitud();
    }    

    public void registrarSolicitud() {
        try {
//            nuevaSolicitud.setTipoEvento(tipoEvento);
//            nuevaSolicitud.setFechaReserva(fechaReserva);
//            nuevaSolicitud.setDireccion(direccion);
//            nuevaSolicitud.setEspecificaciones(especificaciones);
            sDAO.register(nuevaSolicitud);
            msDAO.registrarCarrito(mateSolicitudList);
            MessageUtil.addInfoMessage(null, "Su solicitud: " + nuevaSolicitud.getId(), "se ha enviado...", true);
            Mail.sendMail(session.usuarioSession().getCorreo(),
                    "¡TU SOLCIITUD HA SIDO REGISTRADA!", "ID SOLICITUD:" + nuevaSolicitud.getId()
                    + "\n Fecha Reserva: " + nuevaSolicitud.getFechaReserva() + "\n Direccion: " 
                    + nuevaSolicitud.getDireccion());
        } catch (Exception e) {
            MessageUtil.addErrorMessage(direccion, "Error al Registrar", "Intentalo de Nuevo", true);
            System.out.println("ERROR AL REGISTRAR, DE NUEVO, VAMOS SE PUEDE!");
            System.out.println("NO TE RINDAS, VAMOS!!");
        }

    }

}
