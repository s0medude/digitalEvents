/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.ventas.controller;

import digitalevents.login.controllers.SessionController;
import edu.digitalEvents.modelo.dao.IEventoDAO;
import edu.digitalEvents.modelo.dao.IEventoReservadoViewDAO;
import edu.digitalEvents.modelo.dao.ISolicitudDAO;
import edu.digitalEvents.modelo.dao.jpa.EventoDAO;
import edu.digitalEvents.modelo.entities.EventoReservadoView;
import edu.digitalEvents.modelo.entities.EventosRealizadosView;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "eventoController")
@ViewScoped
public class EventoController implements Serializable {

    @Inject
    private SessionController se;
    @EJB
    private IEventoReservadoViewDAO eDAO;
    @EJB
    private IEventoDAO eRealizadosDao;
    private List<EventoReservadoView> eventosList;
    private List<EventosRealizadosView> eventosRealizadosList;
    private List<EventosRealizadosView> eventosRealizadosAlClienteList;

    /**
     * Creates a new instance of EventoController
     */
    public EventoController() {
    }

    @PostConstruct
    public void init() {
    }

    public List<EventoReservadoView> getEventosList() {
        if (eventosList == null || eventosList.isEmpty()) {
            eventosList = eDAO.findAll();
        }
        return eventosList;
    }

    public List<EventosRealizadosView> getEventosRealizadosList() {
        if (eventosRealizadosList == null || eventosRealizadosList.isEmpty()) {
            eventosRealizadosList = eRealizadosDao.findAllEventsoRealizados();
        }
        return eventosRealizadosList;
    }

    public List<EventosRealizadosView> getEventosRealizadosAlClienteList() {
        if (eventosRealizadosAlClienteList == null || eventosRealizadosAlClienteList.isEmpty()) {
            eventosRealizadosList = eRealizadosDao.findAllEventsoRealizadosAlCliente(se.getUser().getNombres());
        }
        return eventosRealizadosAlClienteList;
    }

    public void export() {
        try {
            System.out.println("VAMOS A IMPRIMIR EL REPORTE");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("/WEB-INF/classes/reporteEventosRealizados/ReporteEventosRealizados.jasper"));
            //JRBeanCollectionDataSource jasperDS = new JRBeanCollectionDataSource(usuarioList);
            Map<String, Object> params = new HashMap<>();
            params.put("logosystem", ec.getRealPath("/resources/img/logo.png"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, new JRBeanCollectionDataSource(getEventosRealizadosList(), false));
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=reporteInventario.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void exportCliente() {
        try {
            System.out.println("VAMOS A IMPRIMIR EL REPORTE");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("/WEB-INF/classes/reporteEventosRealizados/ReporteEventosRealizados.jasper"));
            //JRBeanCollectionDataSource jasperDS = new JRBeanCollectionDataSource(usuarioList);
            Map<String, Object> params = new HashMap<>();
            params.put("logosystem", ec.getRealPath("/resources/img/logo.png"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, new JRBeanCollectionDataSource(getEventosRealizadosAlClienteList(), false));
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=reporteInventario.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
