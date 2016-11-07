package org.srang.bracketpicker.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.io.Serializable;

/**
 * Created by srang on 11/5/2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Status implements Serializable {
    Integer statusId;
    String status;
}