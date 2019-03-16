/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.usuarios.controllers;

import digitalevents.login.controllers.SessionController;
import digitalevents.utils.Mail;
import edu.digitalEvents.modelo.dao.IEstadoUsuarioDAO;
import edu.digitalEvents.modelo.dao.ITipoDocumentoDAO;
import edu.digitalEvents.modelo.dao.IUsuarioDAO;
import edu.digitalEvents.modelo.entities.EstadoUsuario;
//import edu.digitalEvents.modelo.entities.Rol;
import edu.digitalEvents.modelo.entities.Usuario;
import digitalevents.utils.MessageUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    @Inject
    private SessionController se;
    @EJB
    private IUsuarioDAO uDAO;
    @EJB
    private ITipoDocumentoDAO tDAO;
    @EJB
    private IEstadoUsuarioDAO euDAO;

    private String confirmarClave;
    private String correo;

    private Usuario nuevoUsuario;
    private Usuario usuarioSeleccionado;
    private List<Usuario> usuariosList;
    private List<Usuario> usuarioSessionList;
    private List<EstadoUsuario> estadosUsuarioList;
    private Usuario user;
    private SessionController session;
    private Mail mail;
    private String id;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
    }

    @PostConstruct
    public void init() {
        nuevoUsuario = new Usuario();
    }

    public String getCorreo() {
        return correo;
    }

    public SessionController getSe() {
        return se;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Usuario> getUsuariosList() {
        if (usuariosList == null || usuariosList.isEmpty()) {
            usuariosList = uDAO.findAll();
        }
       
        return usuariosList;
    }
    
    public Usuario sessionUser() {
        session = new SessionController();
        user = session.usuarioSession();
        return user;
    }

    public List<Usuario> getUsuarioSessionList(Usuario u) {
        if (usuarioSessionList == null || usuarioSessionList.isEmpty()) {
            usuarioSessionList = uDAO.findById(sessionUser());
        }
        return usuarioSessionList;
    }

    public List<EstadoUsuario> getEstadosUsuarioList() {
        if (estadosUsuarioList == null || estadosUsuarioList.isEmpty()) {
            estadosUsuarioList = euDAO.findAll();
        }
        return estadosUsuarioList;
    }

    public void usuarioSeleccionado(Usuario u) {
        System.out.println("Se seleccionó el usuario");
        System.out.println(u);
        this.usuarioSeleccionado = u;
        System.out.println(u.getId());
        
    }
    public  boolean renderedBooton(Usuario u){
        return (u==se.getUser());
    }

    public void registrar() {

        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String cuerpoHtml = "";
            cuerpoHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n"
                    + "<head>\n"
                    + "	<!--[if gte mso 9]>\n"
                    + "	<xml>\n"
                    + "		<o:OfficeDocumentSettings>\n"
                    + "		<o:AllowPNG/>\n"
                    + "		<o:PixelsPerInch>96</o:PixelsPerInch>\n"
                    + "		</o:OfficeDocumentSettings>\n"
                    + "	</xml>\n"
                    + "	<![endif]-->\n"
                    + "	<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />\n"
                    + "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\" />\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n"
                    + "	<meta name=\"format-detection\" content=\"date=no\" />\n"
                    + "	<meta name=\"format-detection\" content=\"address=no\" />\n"
                    + "	<meta name=\"format-detection\" content=\"telephone=no\" />\n"
                    + "	<meta name=\"x-apple-disable-message-reformatting\" />\n"
                    + "    <!--[if !mso]><!-->\n"
                    + "	<link href=\"https://fonts.googleapis.com/css?family=Roboto:400,400i,700,700i\" rel=\"stylesheet\" />\n"
                    + "    <!--<![endif]-->\n"
                    + "	<title>*|MC:SUBJECT|*</title>\n"
                    + "	<!--[if gte mso 9]>\n"
                    + "	<style type=\"text/css\" media=\"all\">\n"
                    + "		sup { font-size: 100% !important; }\n"
                    + "	</style>\n"
                    + "	<![endif]-->\n"
                    + "	\n"
                    + "\n"
                    + "	<style type=\"text/css\" media=\"screen\">\n"
                    + "		/* Linked Styles */\n"
                    + "		body { padding:0 !important; margin:0 !important; display:block !important; min-width:100% !important; width:100% !important; background:#f4f4f4; -webkit-text-size-adjust:none }\n"
                    + "		a { color:#66c7ff; text-decoration:none }\n"
                    + "		p { padding:0 !important; margin:0 !important } \n"
                    + "		img { -ms-interpolation-mode: bicubic; /* Allow smoother rendering of resized image in Internet Explorer */ }\n"
                    + "		.mcnPreviewText { display: none !important; }\n"
                    + "\n"
                    + "		.cke_editable,\n"
                    + "		.cke_editable a,\n"
                    + "		.cke_editable span,\n"
                    + "		.cke_editable a span { color: #000001 !important; }		\n"
                    + "		/* Mobile styles */\n"
                    + "		@media only screen and (max-device-width: 480px), only screen and (max-width: 480px) {\n"
                    + "			.mobile-shell { width: 100% !important; min-width: 100% !important; }\n"
                    + "			.bg { background-size: 100% auto !important; -webkit-background-size: 100% auto !important; }\n"
                    + "			\n"
                    + "			.text-header,\n"
                    + "			.m-center { text-align: center !important; }\n"
                    + "			\n"
                    + "			.center { margin: 0 auto !important; }\n"
                    + "			.container { padding: 0px 10px 10px 10px !important }\n"
                    + "			\n"
                    + "			.td { width: 100% !important; min-width: 100% !important; }\n"
                    + "\n"
                    + "			.text-nav { line-height: 28px !important; }\n"
                    + "			.p30 { padding: 15px !important; }\n"
                    + "\n"
                    + "			.m15 { height: 15px !important; }\n"
                    + "			.p30-15 { padding: 30px 15px !important; }\n"
                    + "			.p40 { padding: 20px !important; }\n"
                    + "\n"
                    + "			.m-td,\n"
                    + "			.m-hide { display: none !important; width: 0 !important; height: 0 !important; font-size: 0 !important; line-height: 0 !important; min-height: 0 !important; }\n"
                    + "\n"
                    + "			.m-block { display: block !important; }\n"
                    + "\n"
                    + "			.fluid-img img { width: 100% !important; max-width: 100% !important; height: auto !important; }\n"
                    + "\n"
                    + "			.column,\n"
                    + "			.column-top,\n"
                    + "			.column-empty,\n"
                    + "			.column-empty2,\n"
                    + "			.column-dir-top { float: left !important; width: 100% !important; display: block !important; }\n"
                    + "			.column-empty { padding-bottom: 10px !important; }\n"
                    + "			.column-empty2 { padding-bottom: 20px !important; }\n"
                    + "			.content-spacing { width: 15px !important; }\n"
                    + "		}\n"
                    + "	</style>\n"
                    + "</head>\n"
                    + "<body class=\"body\" style=\"background-image: url('" + ec.getRequestContextPath() + "/resources/img/personajes-transformers.png'); padding:0 !important; margin:0 !important; display:block !important; min-width:100% !important; width:100% !important; -webkit-text-size-adjust:none;>\n"
                    + "<!--*|IF:MC_PREVIEW_TEXT|*-->\n"
                    + "		<!--[if !gte mso 9]><!-->\n"
                    + "		<span class=\"mcnPreviewText\" style=\"display:none; font-size:0px; line-height:0px; max-height:0px; max-width:0px; opacity:0; overflow:hidden; visibility:hidden; mso-hide:all;\"></span>\n"
                    + "		<!--<![endif]-->\n"
                    + "	<!--*|END:IF|*-->\n"
                    + "	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "		<tr>\n"
                    + "			<td align=\"center\" valign=\"top\">\n"
                    + "				<table width=\"650\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mobile-shell\">\n"
                    + "					<tr>\n"
                    + "						<td class=\"td container\" style=\"width:650px; min-width:650px; font-size:0pt; line-height:0pt; margin:0; font-weight:normal; padding:0px 0px 40px 0px;\">\n"
                    + "							<!-- Header -->\n"
                    + "							<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "								<tr>\n"
                    + "									<td class=\"p30-15\" style=\"padding: 50px 0px 20px 0px;\" bgcolor=\"\">\n"
                    + "										<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "											<tr>\n"
                    + "												<th class=\"column-top\" width=\"145\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; vertical-align:top;\">\n"
                    + "													<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "														<tr>\n"
                    + "															<td class=\"img m-center\" style=\"font-size:0pt; line-height:0pt; text-align:left;\" width=\"100%\" ><img src=\"" + ec.getRequestContextPath() + "/resources/img/logo.png\" width=\"100%\" height=\"30%\" mc:edit=\"image_1\" style=\"max-width:117px;\" border=\"0\" alt=\"\" /></td>\n"
                    + "														</tr>\n"
                    + "													</table>\n"
                    + "												</th>\n"
                    + "												<th class=\"column-empty\" width=\"1\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; vertical-align:top;\"></th>\n"
                    + "												<th class=\"column\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal;\">\n"
                    + "													<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "														<tr>\n"
                    + "															<td class=\"text-header\" style=\"color:#999999; font-family:'Roboto', Arial,sans-serif; font-size:12px; line-height:16px; text-align:right; text-transform:uppercase;\"><div mc:edit=\"text_1\"><a class=\"link2\" target=\"_blank\" href=\"*|ARCHIVE|*\" style=\"color:#999999; text-decoration:none;\">Abrir en tu navegador</a></div></td>\n"
                    + "														</tr>\n"
                    + "													</table>\n"
                    + "												</th>\n"
                    + "											</tr>\n"
                    + "										</table>\n"
                    + "									</td>\n"
                    + "								</tr>\n"
                    + "							</table>\n"
                    + "							<!-- END Header -->\n"
                    + "\n"
                    + "							<!-- Title + Text Center -->\n"
                    + "							<div mc:repeatable=\"Select\" mc:variant=\"Title + Text Center\">\n"
                    + "								<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n"
                    + "									<tr>\n"
                    + "										<td class=\"img\" height=\"10\" bgcolor=\"#f44336\" style=\"font-size:0pt; line-height:0pt; text-align:left;\">&nbsp;</td>\n"
                    + "									</tr>\n"
                    + "									<tr>\n"
                    + "										<td class=\"p30-15\" style=\"padding: 45px 30px;\" bgcolor=\"#ffffff\" align=\"center\">\n"
                    + "											<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "												<tr>\n"
                    + "													<td class=\"h1 center pb20\" style=\"color:#050505; font-family:'Roboto', Arial,sans-serif; font-size:28px; line-height:34px; text-align:center; padding-bottom:20px;\"><div mc:edit=\"text_2\">Bienvenido a Digital Events..!</div></td>\n"
                    + "												</tr>\n"
                    + "												<tr>\n"
                    + "													<td class=\"text center\" style=\"color:#666666; font-family:'Roboto', Arial,sans-serif; font-size:16px; line-height:28px; text-align:center;\"><div mc:edit=\"text_3\">Te damos la bienvenida a nuestro sistema, tu registro ha sido exitoso recuerda que los mejores eventos estan aquí</div></td>\n"
                    + "												</tr>\n"
                    + "											</table>\n"
                    + "										</td>\n"
                    + "									</tr>\n"
                    + "								</table>\n"
                    + "							</div>\n"
                    + "							<!-- END Title + Text Center -->\n"
                    + "\n"
                    + "							<!-- Hero -->\n"
                    + "							<div mc:repeatable=\"Select\" mc:variant=\"Hero\">\n"
                    + "								<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "									<tr>\n"
                    + "										<td style=\"padding-bottom: 10px;\">\n"
                    + "											<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "												<tr>\n"
                    + "													<td class=\"fluid-img\" style=\"font-size:0pt; line-height:0pt; text-align:left;\"><img src=\"images/t12_image1.jpg\" width=\"650\" height=\"366\" mc:edit=\"image_2\" style=\"max-width:650px;\" border=\"0\" alt=\"\" /></td>\n"
                    + "												</tr>\n"
                    + "												<tr>\n"
                    + "													<td class=\"p30-15\" style=\"padding: 15px 30px;\" bgcolor=\"#f44336\">\n"
                    + "														<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "															<tr>\n"
                    + "																<th class=\"column\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal;\">\n"
                    + "																	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "																		<tr>\n"
                    + "																			<td class=\"text white m-center\" style=\"font-family:'Roboto', Arial,sans-serif; font-size:16px; line-height:28px; text-align:left; color:#ffffff;\"><div mc:edit=\"text_4\">Tu historia comienza con nosotrosc</div></td>\n"
                    + "																		</tr>\n"
                    + "																	</table>\n"
                    + "																</th>\n"
                    + "																<th class=\"column-empty\" width=\"1\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; vertical-align:top;\"></th>\n"
                    + "																<th class=\"column\" width=\"200\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal;\">\n"
                    + "																	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "																		<tr>\n"
                    + "																			<td align=\"right\">\n"
                    + "																				<table class=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"text-align:center;\">\n"
                    + "																					<tr>\n"
                    + "																						<td class=\"text-button white-button\" style=\"font-family:'Roboto', Arial,sans-serif; font-size:14px; line-height:18px; padding:12px 30px; text-align:center; border-radius:24px; color:#ffffff; border:2px solid #ffffff;\"><div mc:edit=\"text_5\"><a href=\"#\" target=\"_blank\" class=\"link-white\" style=\"color:#ffffff; text-decoration:none;\"><span class=\"link-white\" style=\"color:#ffffff; text-decoration:none;\">Iniciar Sesion</span> &nbsp; <img src=\"images/t12_white_arrow.jpg\" width=\"15\" height=\"13\" border=\"0\" alt=\"\" /></a></div></td>\n"
                    + "																					</tr>\n"
                    + "																				</table>\n"
                    + "																			</td>\n"
                    + "																		</tr>\n"
                    + "																	</table>\n"
                    + "																</th>\n"
                    + "															</tr>\n"
                    + "														</table>\n"
                    + "													</td>\n"
                    + "												</tr>\n"
                    + "											</table>\n"
                    + "										</td>\n"
                    + "									</tr>\n"
                    + "								</table>\n"
                    + "							</div>\n"
                    + "							<!-- END Hero -->\n"
                    + "\n"
                    + "							<\n"
                    + "							 \n"
                    + "							<!-- Footer -->\n"
                    + "							<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "								<tr>\n"
                    + "									<td class=\"p30-15\" style=\"padding: 50px 30px;\" bgcolor=\"#ffffff\">\n"
                    + "										<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "											<tr>\n"
                    + "												<td align=\"center\" style=\"padding-bottom: 30px;\">\n"
                    + "													<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "														<tr>\n"
                    + "															<td class=\"img\" width=\"55\" style=\"font-size:0pt; line-height:0pt; text-align:left;\"><a href=\"#\" target=\"_blank\"><img src=\"images/t8_ico_facebook.jpg\" width=\"38\" height=\"38\" mc:edit=\"image_6\" style=\"max-width:38px;\" border=\"0\" alt=\"\" /></a></td>\n"
                    + "															<td class=\"img\" width=\"55\" style=\"font-size:0pt; line-height:0pt; text-align:left;\"><a href=\"#\" target=\"_blank\"><img src=\"images/t8_ico_twitter.jpg\" width=\"38\" height=\"38\" mc:edit=\"image_7\" style=\"max-width:38px;\" border=\"0\" alt=\"\" /></a></td>\n"
                    + "															<td class=\"img\" width=\"55\" style=\"font-size:0pt; line-height:0pt; text-align:left;\"><a href=\"#\" target=\"_blank\"><img src=\"images/t8_ico_instagram.jpg\" width=\"38\" height=\"38\" mc:edit=\"image_8\" style=\"max-width:38px;\" border=\"0\" alt=\"\" /></a></td>\n"
                    + "															<td class=\"img\" width=\"38\" style=\"font-size:0pt; line-height:0pt; text-align:left;\"><a href=\"#\" target=\"_blank\"><img src=\"images/t8_ico_linkedin.jpg\" width=\"38\" height=\"38\" mc:edit=\"image_9\" style=\"max-width:38px;\" border=\"0\" alt=\"\" /></a></td>\n"
                    + "														</tr>\n"
                    + "													</table>\n"
                    + "												</td>\n"
                    + "											</tr>\n"
                    + "											<tr>\n"
                    + "												<td class=\"text-footer1 pb10\" style=\"color:#999999; font-family:'Roboto', Arial,sans-serif; font-size:16px; line-height:20px; text-align:center; padding-bottom:10px;\"><div mc:edit=\"text_27\">Digital Evenst - Tus mejores eventos</div></td>\n"
                    + "											</tr>\n"
                    + "											<tr>\n"
                    + "												<td class=\"text-footer2 pb30\" style=\"color:#999999; font-family:'Roboto', Arial,sans-serif; font-size:12px; line-height:26px; text-align:center; padding-bottom:30px;\"><div mc:edit=\"text_28\">East Pixel Bld. 99, Creative City 9000, Republic of Design</div></td>\n"
                    + "											</tr>\n"
                    + "											<tr>\n"
                    + "												<td class=\"text-footer3\" style=\"color:#c0c0c0; font-family:'Roboto', Arial,sans-serif; font-size:12px; line-height:18px; text-align:center;\"><div mc:edit=\"text_29\"><a class=\"link3-u\" target=\"_blank\" href=\"*|UNSUB|*\" style=\"color:#c0c0c0; text-decoration:underline;\">Unsubscribe</a> from this mailing list.</div></td>\n"
                    + "											</tr>\n"
                    + "											<tr>\n"
                    + "												<td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left;\">\n"
                    + "													<div mc:edit=\"text_30\">\n"
                    + "														<!--[if !mso]><!-->\n"
                    + "															*|LIST:DESCRIPTION|*\n"
                    + "															*|LIST:ADDRESS|*\n"
                    + "															*|REWARDS_TEXT|*\n"
                    + "														<!--<![endif]-->\n"
                    + "													</div>\n"
                    + "												</td>\n"
                    + "											</tr>\n"
                    + "										</table>\n"
                    + "									</td>\n"
                    + "								</tr>\n"
                    + "							</table>\n"
                    + "							<!-- END Footer -->\n"
                    + "						</td>\n"
                    + "					</tr>\n"
                    + "				</table>\n"
                    + "			</td>\n"
                    + "		</tr>\n"
                    + "	</table>\n"
                    + "</body>\n"
                    + "</html>s";
            confirmarClave = nuevoUsuario.getContrasena();
            uDAO.register(nuevoUsuario);
            MessageUtil.addInfoMessage(null, "REGISTRO EXITOSO", "", false);
            Mail.sendMailHtml(nuevoUsuario.getCorreo(), "REGISTRO USUARIO", cuerpoHtml);
            ec.redirect(ec.getRequestContextPath() + "/SI/index.xhtml");
        } catch (Exception e) {
            MessageUtil.addErrorMessage(null, "Error al registrar el usuario", e.getMessage(), false);
        }
    }

    public void actualizarUsuarioSeleccionado() {
        try {
            uDAO.update(usuarioSeleccionado);
            MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos del usuario se han actualizaco correctamente,", false);

        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar el usuario", e.getMessage(), false);
        }
    }

    public void actualizar(Usuario u) {
        usuarioSeleccionado(u);
        try {

            if (usuarioSeleccionado.getDocumento() > 0) {
                uDAO.update(usuarioSeleccionado);
                MessageUtil.addInfoMessage(null, "Actualización exitosa", "Los datos del usuario se han actualizaco correctamente,", false);
            } else {
                MessageUtil.addErrorMessage(null, "Error de validación", "El documento no puede estar vacio.", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al actualizar el usuario", e.getMessage(), false);
        }
    }
    
    public void eliminar() {
        System.out.println("Usuario Seleccionado:"+this.usuarioSeleccionado.getId());
        System.out.println("Incio Session"+se.getUser().getId());
        try {
             
            if (this.usuarioSeleccionado.getId() == se.getUser().getId()) {
                MessageUtil.addErrorMessage(null, "No es posible eliminar el usuario en sesion", "No es posible eliminar el usuario en sesion", false);
                System.out.println(usuarioSeleccionado==session.getUser());
            } else {
                uDAO.delete(usuarioSeleccionado);
                MessageUtil.addInfoMessage(null, "Eliminación exitosa", "Los datos del usuario se han eliminado correctamente,", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.addErrorMessage(null, "Error al eliminar el usuario", e.getMessage(), false);
        }
    }

    public void updateForgotPassword() throws IOException {
        try {
            ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
            String uuid = UUID.randomUUID().toString();
            uDAO.updateByContrasena(correo, uuid);
            Mail.sendMail(correo, "Su clave ha sido modificada.", "Su nueva contraseña es: " + uuid);
            MessageUtil.addInfoMessage(null, "Actualización exitosa", "La contrasena ha sido cambiada correctamente", false);
            ex.redirect(ex.getRequestContextPath() + "/index.xhtml");
        } catch (Exception e) {
            ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
            e.printStackTrace();
            MessageUtil.addInfoMessage(null, "ERROR", "INTENTALO D ENUEVO PENDEJO", false);
            ex.redirect(ex.getRequestContextPath() + "/SI/index.xhtml");

        }
    }
}
