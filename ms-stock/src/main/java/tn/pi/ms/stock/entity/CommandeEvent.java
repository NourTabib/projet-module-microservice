package tn.pi.ms.stock.entity;

import java.util.Date;

public class CommandeEvent implements Event<Commande> {
    private Commande data;
    private Date creationDate;
    private String eventType;

    @Override
    public Commande getData() {
        return data;
    }

    @Override
    public void setData(Commande data) {
        this.data= data;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate= creationDate;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType= eventType;
    }
}
