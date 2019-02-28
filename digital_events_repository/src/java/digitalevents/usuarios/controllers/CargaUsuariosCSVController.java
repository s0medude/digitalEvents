/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.usuarios.controllers;

import digitalevents.utils.MensajesUtilCSV;
import digitalevents.utils.MessageUtil;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.UsuarioCSV;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.Part;

/**
 *
 * @author Juan
 */
@Named(value = "cargaUsuariosCSVController")
@ViewScoped
public class CargaUsuariosCSVController implements Serializable {

    /**
     * Creates a new instance of CargaUsuariosCSVController
     */
    private final static String KEY_SMTP_SERVER = "mail.smtp.host";
    private final static String KEY_FROM = "mail.smtp.user";
    private final static String KEY_PASSWORD = "mail.smtp.password";
    private static Properties props;
    private Part multipart;
    @EJB
    private IUsuarioDAO uDao;
    private UsuarioCSV csvUsuario;

    public CargaUsuariosCSVController() {
    }

    @PostConstruct
    public void init() {

    }

    public UsuarioCSV getCsvUsuario() {
        return csvUsuario;
    }

    public void setCsvUsuario(UsuarioCSV csvUsuario) {
        this.csvUsuario = csvUsuario;
    }

    public Part getMultipart() {
        return multipart;
    }

    public void setMultipart(Part multipart) {
        this.multipart = multipart;
    }

    private static void loadConfig() {
        if (props == null) {
            props = new Properties();
            props.put(KEY_SMTP_SERVER, "smtp.gmail.com");
            props.put(KEY_FROM, "digital.events.soft@gmail.com");
            props.put(KEY_PASSWORD, "digital12345");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        }

    }

    public static void sendMail(String destinatario, String asunto, String cuerpo) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendMailHtmlAdjunto(String destinatarios, String asunto, String cuerpoHTML, List<File> files) throws IOException {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipients(Message.RecipientType.TO, destinatarios);
            message.setSubject(asunto);
            Multipart parts = new MimeMultipart();
            BodyPart bodyMail = new MimeBodyPart();
            bodyMail.setContent(cuerpoHTML, "text/html");
            parts.addBodyPart(bodyMail);
            for (File file : files) {
                MimeBodyPart atched = new MimeBodyPart();
                atched.attachFile(file);
                parts.addBodyPart(atched);
            }
            message.setContent(parts);
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());//Destinatarios

            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendMailHtml(String destinatarios, String asunto, String cuerpoHTML) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipients(Message.RecipientType.TO, destinatarios);
            message.setSubject(asunto);
            Multipart parts = new MimeMultipart();
            BodyPart bodyMail = new MimeBodyPart();
            bodyMail.setContent(cuerpoHTML, "text/html");
            parts.addBodyPart(bodyMail);
            message.setContent(parts);
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER), props.getProperty(KEY_FROM), props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());//Destinatarios

            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void fileUploadCsv() {
        String ruta = "";
        if (multipart != null) {
            File carpeta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/cargaCSVUsuarios");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            try (InputStream archivo = multipart.getInputStream()) {
                Files.copy(archivo, (new File(carpeta, multipart.getSubmittedFileName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/cargaCSVUsuarios/" + multipart.getSubmittedFileName();
                registrarCSVUsuarios(ruta);
                MensajesUtilCSV.infoMensaje("", ruta, "");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void registrarCSVUsuarios(String ruta) {
        String r = ruta;
        BufferedReader bf = null;
        SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaNacimientoUsuario = null;
        Date fecha_actual=new Date();
        try {
            MensajesUtilCSV.infoMensaje(null, "Carga de datos exitosa", "");
            bf = new BufferedReader(new FileReader(r));
            String linea = bf.readLine();
            while (linea != null) {
                String[] val = linea.split(";");
                fechaNacimientoUsuario = s.parse(val[5]);
                System.out.println(fechaNacimientoUsuario);
                int idUsuario = uDao.count() + 1;
                UsuarioCSV uCsv = new UsuarioCSV(idUsuario, Integer.parseInt(val[0]), Integer.parseInt(val[1]), val[2], val[3], val[4], fechaNacimientoUsuario, val[6], val[7], val[8], val[9], Integer.parseInt(val[10]), Integer.parseInt(val[11]));
                uDao.cragaMasivaUsuarios(uCsv);
                linea = bf.readLine();
                MessageUtil.addInfoMessage(null, "Carga de datos exitosa", "Los datos del usuario se han cargado correctamente,", false);
                loadConfig();
                sendMail(val[6], "Sus datos han cargado correctamente desde el sistema DigitalEvents", "Se ha realizado la carga con los datos:\n id:" + "\n Tipo de Identificacion:" + val[1] + "\n Numero de Idntificacion: " + val[2] + "\n Fecha:" + val[4] + "En la fecha de hoy: " + new Date().toString());
                System.out.println("Carga de datos exitosa con los datos");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
