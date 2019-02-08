/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.reports.controller;

import edu.digitalEvents.modelo.dao.IReporteUsuariosViewDAO;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.ReporteUsuariosView;
import edu.digitalEvents.modelo.entities.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
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
@Named(value = "reportesController")
@ViewScoped
public class ReportesController implements Serializable {

    @EJB
    private IUsuarioDAO uDAO;
    @EJB
    private IReporteUsuariosViewDAO rDAO;

    private List<ReporteUsuariosView> usuariosList;
    private JasperPrint jasper;
    Collection collection;

    /**
     * Creates a new instance of ReportesController
     */
    public ReportesController() {
    }

   
    @PostConstruct
    public void init() {
        collection = usuariosList;
    }

    public List<ReporteUsuariosView> getUsuariosList() {
        if (usuariosList == null || usuariosList.isEmpty()) {
            usuariosList = rDAO.findAll();
        }
        return usuariosList;
    }

    public void exportarPDF() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File fileJasper = new File(ec.getRealPath("C:\\Users\\Nicolas PC\\Documents\\NetBeansProjects\\digitalevents\\web\\WEB-INF\\includes\\reportes\\usuariosReport.jasper"));
            Map<String, Object> params = new HashMap<>();
            jasper = JasperFillManager.fillReport(fileJasper.getPath(), params, new JRBeanCollectionDataSource(usuariosList, false));
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
            OutputStream out = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasper, out);
            out.flush();
            out.close();
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            JRBeanCollectionDataSource bods = new JRBeanCollectionDataSource(usuariosList, false);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/includes/reportes/usuariosReport.jasper");
            System.out.println(reportPath);
            jasper = JasperFillManager.fillReport(reportPath, null, bods);
        } catch (JRException e) {
            Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportPDF() throws IOException, JRException {
        loadConfig();
        ServletOutputStream out;
        String contextType = "application/pdf";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletResponse servlet = (HttpServletResponse) ec.getResponse();
        out = servlet.getOutputStream();
        servlet.setContentType(contextType);
        servlet.addHeader("Content-disposition", "attachment; filename=ReporteUsuarios.pdf");
        JasperExportManager.exportReportToPdfStream(jasper, out);
        out.flush();
        out.close();
        fc.responseComplete();
    }

    public void exportXLS() throws IOException, JRException {
        loadConfig();
        ServletOutputStream out;
        String contextType = "application/xls";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletResponse servlet = (HttpServletResponse) ec.getResponse();
        out = servlet.getOutputStream();
        servlet.setContentType(contextType);
        servlet.addHeader("Content-disposition", "attachment; filename=ReporteUsuarios.xls");
        JasperExportManager.exportReportToPdfStream(jasper, out);
        out.flush();
        out.close();
        fc.responseComplete();
    }
    
    public void export() throws JRException {        
        try {            
            System.out.println("VAMOS A IMPRIMIR EL REPORTE");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasperF = new File(ec.getRealPath("/WEB-INF/includes/reportes/usuariosReport.jasper"));
            //Map<String, Object> params = new HashMap<>();
            JasperPrint jp = JasperFillManager.fillReport(jasperF.getPath(), null, new JRBeanCollectionDataSource(usuariosList, false));
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
            try (OutputStream os = hsr.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jp, os);
                os.flush();
            }
            fc.responseComplete();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

}