/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.inventario.controller;

import digitalevents.utils.MensajesUtilCSV;
import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IMaterialDAO;
import edu.digitalEvents.modelo.entities.InventarioCSV;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;

/**
 *
 * @author Juan
 */
@Named(value = "cargaMasivaInventarioCSVController")
@ViewScoped
public class CargaMasivaInventarioCSVController implements Serializable{
    @EJB
    private IMaterialDAO mDao;
    private Part multiPartInventario;
    private InventarioCSV csvInventario;
    
    /**
     * Creates a new instance of CargaMasivaInventarioCSVController
     */
    public CargaMasivaInventarioCSVController() {
    }
    @PostConstruct
    public void init(){
        
    }

    public Part getMultiPartInventario() {
        return multiPartInventario;
    }

    public void setMultiPartInventario(Part multiPartInventario) {
        this.multiPartInventario = multiPartInventario;
    }

    public InventarioCSV getCsvInventario() {
        return csvInventario;
    }

    public void setCsvInventario(InventarioCSV csvInventario) {
        this.csvInventario = csvInventario;
    }
    public void fileUploadCsvInventario(){
        String rutas="";
        if(multiPartInventario!=null){
            File carpetaInventario = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/CargaCSVInventario");
            if (!carpetaInventario.exists()) {
                carpetaInventario.mkdirs();
            }
            try(InputStream archivo = multiPartInventario.getInputStream()) {
                Files.copy(archivo, new File(carpetaInventario,multiPartInventario.getSubmittedFileName()).toPath(),StandardCopyOption.REPLACE_EXISTING);
                rutas=FacesContext.getCurrentInstance().getExternalContext().getRealPath("")+"/CargaCSVInventario/" + multiPartInventario.getSubmittedFileName();
                registrarInventarioCSV(rutas);
                MensajesUtilCSV.infoMensaje("", rutas, "");
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
        
        
    }
    public void registrarInventarioCSV(String ruta){
        String r=ruta;
        BufferedReader fr= null;
        try {
            MensajesUtilCSV.infoMensaje(null, "Carga de datos exitosa", "");
            fr=new BufferedReader(new FileReader(r));
            String linea=fr.readLine();
            while (linea!=null) {
                String[] array=linea.split(";");
                int id=mDao.count()+1;
                InventarioCSV inventario = new InventarioCSV(id, array[0], Integer.parseInt(array[1]), array[2], Integer.parseInt(array[3]), array[4]);
                mDao.cargaMasivaInventario(inventario);
                linea = fr.readLine();
                MessageUtil.addInfoMessage(null, "Carga de datos exitosa", "Los datos del usuario se han cargado correctamente,", false);
                 System.out.println("Carga de datos exitosa con los datos");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }
    
}
