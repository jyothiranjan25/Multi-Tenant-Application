package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Entities.UserManagement.AppUserRoles.AppUserRoles;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "username")
    private String userName;

    @Column(length = 1025)
    private String password;

    @NaturalId
    private String email;

    @Column(name = "is_admin", columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AppUserRoles> appUserRoles;
}
