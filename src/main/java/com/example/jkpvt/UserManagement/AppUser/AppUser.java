package com.example.jkpvt.UserManagement.AppUser;

import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_user" , indexes = {
        @Index(name = "idx_app_user_username", columnList = "username"),
        @Index(name = "idx_app_user_email", columnList = "email"),
        @Index(name = "idx_app_user_is_admin", columnList = "is_admin"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_app_user_username", columnNames = {"username"}),
        @UniqueConstraint(name = "uk_app_user_email", columnNames = {"email"})
})
@EntityListeners(AppUserListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password", length = 1025)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "is_admin", columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppUserRoles> appUserRoles;
}
