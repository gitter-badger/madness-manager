package org.srang.bracketpicker.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by srang on 11/5/2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BonusRule implements Serializable {
    Integer ruleId;
    Integer rulesetId;
    String value;
}