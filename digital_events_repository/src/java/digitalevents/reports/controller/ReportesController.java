/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.reports.controller;

//import edu.digitalEvents.modelo.dao.IReporteUsuariosViewDAO;
import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.Material;
import edu.digitalEvents.modelo.entities.Usuario;
//import edu.digitalEvents.modelo.entities.ReporteUsuariosView;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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
import javax.mail.Message;
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
    private List<Usuario> usuarioList;

    /**
     * Creates a new instance of ReportesController
     */
    public ReportesController() {
    }

    @PostConstruct
    public void init() {
    }

    public List<Usuario> getUsuarioList() {
        if (usuarioList == null || usuarioList.isEmpty()) {
            usuarioList = uDAO.findAll();
            System.out.println("Jsds");
        }
        return usuarioList;
    }

    public void export(){
        try {
            System.out.println("VAMOS A IMPRIMIR EL REPORTE");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("/WEB-INF/classes/reportes/Reporte.jasper"));          
            //JRBeanCollectionDataSource jasperDS = new JRBeanCollectionDataSource(usuarioList);
            Map<String, Object> params = new HashMap<>();
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, new JRBeanCollectionDataSource(getUsuarioList(), false));
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
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