package org.srang.madness.manager.model.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by srang on 11/5/2016.
 */
@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teams")
public class Team implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "team_id", unique = true, nullable = false)
    Integer teamId;
    @Column(name = "name")
    String name;
    @Column(name = "rank")
    Integer rank;
    @JoinColumn(name = "region_id")
    Region region;
    @Column(name = "mascot")
    String mascot;
    @Column(name = "icon_path", nullable = false)
    String iconPath;
    @Column(name = "primary_color", nullable = false)
    String primaryColor;
    @Column(name = "secondary_color", nullable = false)
    String accentColor;

    @Builder
    public Team(String name, Integer rank, Region region, String mascot, String iconPath, String primaryColor, String accentColor) {
        this.name = name;
        this.rank = rank;
        this.region = region;
        this.mascot = mascot;
        this.iconPath = iconPath;
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
    }
}
