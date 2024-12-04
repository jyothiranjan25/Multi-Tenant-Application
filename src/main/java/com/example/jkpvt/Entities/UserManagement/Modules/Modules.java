package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Entities.UserManagement.Resources.Resources;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModule;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources.RoleModuleResources;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "modules", indexes = {
        @Index(name = "idx_module_name", columnList = "name"),
        @Index(name = "idx_module_url", columnList = "url"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_module_name", columnNames = "name"),
})
@EntityListeners(ModulesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
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
            inverseJoinColumns = @JoinColumn(name = "resource_id"),
            indexes = {
                    @Index(name = "idx_module_resources_module_id", columnList = "module_id"),
                    @Index(name = "idx_module_resources_resource_id", columnList = "resource_id")
            },
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_module_resources_module_id_resource_id", columnNames = {"module_id", "resource_id"})
            },
            foreignKey = @ForeignKey(name = "fk_module_resources_module_id"),
            inverseForeignKey = @ForeignKey(name = "fk_module_resources_resource_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Resources> resources = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleModule> roleModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleModuleResources> roleModuleResources = new HashSet<>();
}
