package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.UserManagement.Resources.Resources;
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
@Table(name = "modules")
@EntityListeners(ModulesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String moduleName;

    @Column(name = "description", length = 1025)
    private String moduleDescription;

    @Column(name = "url", length = 1025)
    private String moduleUrl;

    @Column(name = "icon", length = 4025)
    private String moduleIcon;

    @ManyToMany
    @JoinTable(
            name = "module_resources",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private Set<Resources> resources = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleModule> roleModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleModuleResources> roleModuleResources = new HashSet<>();
}
