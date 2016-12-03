package org.srang.madness.manager.model.entities;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

/**
 * Created by srang on 11/5/2016.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    Integer taskId;
    String name;
    Integer userId;
    Integer bracketId;
    Long start;

}
