package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Entities.UserManagement.Modules.Modules;
import com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources.RoleModuleResources;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resources", indexes = {
        @Index(name = "idx_resource_name", columnList = "name"),
        @Index(name = "idx_resource_resource_order", columnList = "resource_order"),
        @Index(name = "idx_resource_url", columnList = "url"),
        @Index(name = "idx_resources_parent_id", columnList = "parent_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "unique_resource_name", columnNames = "name"),
})
@EntityListeners(ResourcesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String resourceName;

    @Column(name = "full_name")
    private String resourceFullName;

    @Column(name = "description", length = 1025)
    private String resourceDescription;

    @Column(name = "url", length = 1025)
    private String resourceUrl;

    @Column(name = "show_in_menu", columnDefinition = "boolean default true")
    private Boolean showInMenu;

    @Column(name = "resource_order")
    private Long resourceOrder;

    @Column(name = "resource_sub_order")
    private String resourceSubOrder;

    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_resources_parent_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resources parentResource;

    @OneToMany(mappedBy = "parentResource", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Resources> childResources = new HashSet<>();

    @ManyToMany(mappedBy = "resources", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Modules> modules = new HashSet<>();

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleModuleResources> roleModuleResources = new HashSet<>();
}
