package org.srang.madness.manager.model.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by srang on 11/5/2016.
 */
@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id", unique = true, nullable = false)
    Integer userId;
    @Column(name = "username", unique = true, nullable = false)
    String username;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "email", nullable = false)
    String email;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    Status status;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "remember_token")
    String rememberToken;
    @OneToMany(mappedBy = "username")
    List<UserRole> userRoles;

    @Builder
    public User(String username,
                String firstName,
                String lastName,
                String email,
                Status status,
                String password,
                @Singular List<UserRole> userRoles,
                String rememberToken) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.password = password;
        this.userRoles = new ArrayList<>(userRoles);
        this.rememberToken = rememberToken;
    }

    public boolean isEnabled() {
        return Status.StatusType.ACTIVE.isEqual(this.getStatus());
    }

    public List<String> getRoles() {
        return this.getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toList());
    }
}
