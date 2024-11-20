package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRoles;
import com.example.jkpvt.UserManagement.RoleModule.RoleModule;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources.RoleModuleResources;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_roles_name", columnList = "name"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_name", columnNames = "name"),
})
@EntityListeners(RolesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String roleName;

    @Column(name = "description", length = 1025)
    private String roleDescription;

    @Column(name = "icon", length = 4025)
    private String roleIcon;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleModule> roleModule = new HashSet<>();

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AppUserRoles> appUserRoles;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleModuleResources> roleModuleResources = new HashSet<>();
}
