package tn.pi.ms.commande.entity;

import java.util.Date;

public interface Event<T> {
    T getData();
    void setData(T data);
    Date getCreationDate();
    void setCreationDate(Date creationDate);
    String getEventType();
    void setEventType(String type);
}
