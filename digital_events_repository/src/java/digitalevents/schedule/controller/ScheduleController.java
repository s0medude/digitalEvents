/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalevents.schedule.controller;

import digitalevents.login.controllers.SessionController;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Nicolas PC
 */
@Named(value = "scheduleController")
@ViewScoped
public class ScheduleController implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private final SessionController session;

    /**
     * Creates a new instance of ScheduleController
     */
    public ScheduleController() {
        session = new SessionController();
    }

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        eventModel.addEvent(new DefaultScheduleEvent("EVENTO JULIANA HERRERA", previousDay8Pm(), previousDay11Pm()));
        lazyEventModel = new LazyScheduleModel() {
            
            @Override
            public void loadEvents(Date start, Date end) {
                Date random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("LAZY EVENT 1", random, random));

                random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("LAZY EVENT 2", random, random));
            }
        };
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30) + 1)); //Asigna un dia del mes aleatorio.        
        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.SEPTEMBER, calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);
        return t.getTime();
    }

    public void addEvent() {
        event = new DefaultScheduleEvent();
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
    }

    public void onEventSelect(SelectEvent select) {
        event = (ScheduleEvent) select.getObject();
    }

    public void onDateSelect(SelectEvent select) {
        event = new DefaultScheduleEvent("", (Date) select.getObject(), (Date) select.getObject());
    }
    
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "EVENTO MOVIDO", "Day Delta: " + event.getDayDelta());
        addMessage(fm);
        
    }
    
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "EVENTO REASIGNADO", "Day Delta: " + event.getDayDelta());
        addMessage(fm);
    }
    
    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
