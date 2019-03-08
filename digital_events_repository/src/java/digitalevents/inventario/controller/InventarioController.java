/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.inventario.controller;

import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IDisponibilidadMaterialDAO;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.DisponilidadMaterial;
import edu.digitalEvents.modelo.entities.Material;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.util.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "inventarioController")
@ViewScoped
public class InventarioController implements Serializable {

    @EJB
    private IMaterialDAO mDAO;
    @EJB
    private IDisponibilidadMaterialDAO dmDAO;

    private Material nuevoMaterial;
    private Material materialSeleccionado;
    private Material current;
    private List<Material> materialList;

    private DisponilidadMaterial dispMaterial;
    private List<DisponilidadMaterial> estadoMaterialList;

    private UploadedFile file;
    private Part image;

    /**
     * Creates a new instance of inventarioController
     */
    public InventarioController() {
    }

    @PostConstruct
    public void init() {
    }

    public Material getNuevoMaterial() {
        if (nuevoMaterial == null) {
            nuevoMaterial = new Material();
        }
        return nuevoMaterial;
    }

    public Material getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public List<Material> getMaterialList() {
        if (materialList == null || materialList.isEmpty()) {
            materialList = mDAO.findAll();
            for (Material d : mDAO.findAll()) {
                System.out.println("Hola" + d.getEstado());
            }
        }
        return materialList;
    }

    public List<DisponilidadMaterial> getEstadoMaterialList() {
        if (estadoMaterialList == null || estadoMaterialList.isEmpty()) {
            estadoMaterialList = dmDAO.findAll();

        }
        return estadoMaterialList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public void materialSeleccionado(Material m) {
        System.out.println("Se selecciono el articulo: ");
        System.out.println(m);
        materialSeleccionado = m;
    }

    public void actualizar() {
        try {
            mDAO.update(materialSeleccionado);
            MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos del material se han actualizaco correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar el material", e.getMessage(), false);
        }
    }

    public void registrar() {
        try {
            if (nuevoMaterial.getCantidad() > 0) {
                mDAO.register(nuevoMaterial);
                MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "El material se inserto correctamente", false);

            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "Fallo algo al resgistrar", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al registrar el material", e.getMessage(), false);
        }
    }

    public void eliminar() {
        try {
            mDAO.delete(materialSeleccionado);
            MessageUtil.addInfoMessage(null, "Eliminación exitosa", "Los datos del material se han eliminado correctamente,", false);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al eliminar el material", e.getMessage(), false);

        }
    }

    public void export() {
        try {
            System.out.println("VAMOS A IMPRIMIR EL REPORTE");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File jasper = new File(ec.getRealPath("/WEB-INF/classes/reporteInventario/ReporteMateriales.jasper"));
            //JRBeanCollectionDataSource jasperDS = new JRBeanCollectionDataSource(usuarioList);
            Map<String, Object> params = new HashMap<>();
            params.put("logosystem", ec.getRealPath("/resources/img/logo.png"));
            params.put("grafricoimg", ec.getRealPath("/resources/img/ChartInventario.png"));
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), params, new JRBeanCollectionDataSource(getMaterialList(), false));
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

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String filename = uploadedFile.getFileName();
        String contentType = uploadedFile.getContentType();
        byte[] contents = uploadedFile.getContents();
    }

    public void doUpload() {
        try {
            InputStream in = image.getInputStream();
            File f = new File("/Users/Usuario/Desktop/CopiaProyecto/" + image.getSubmittedFileName());
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void uploadFile() {
        if (file != null) {
            try {
                byte[] data = file.getContents();
                mDAO.udpateByImage(data, materialSeleccionado.getId());
                MessageUtil.addInfoMessage(null, "Actualización exitosa", "La imagen ha sido cargada correctamente", false);

            } catch (Exception e) {
                e.printStackTrace();
                MessageUtil.addInfoMessage(null, "ERROR", "INTENTALO D ENUEVO PENDEJO", false);
            }
        }
    }

    public StreamedContent imageStream() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                // Renderizamos el html retornando un espacio con el contenido de la imagen obteniendo el correcto URL.   
                return new DefaultStreamedContent();
            } else {
                // El navegador requiere la imagen. Retorna un StramedContent real con la imgen byte
                String imageId = fc.getExternalContext().getRequestParameterMap().get("imageId");
                Material image = mDAO.findByPK(Integer.valueOf(imageId));
                return new DefaultStreamedContent(new ByteArrayInputStream(image.getImage()));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
