package com.example.jkpvt.UserManagement.Resources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Roles.RoleModuleResources.RoleModuleResources;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resources")
@EntityListeners(ResourcesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Resources {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name")
        private String resourceName;

        @Column(name = "full_name")
        private String resourceFullName;

        @Column(name = "description",length = 1025)
        private String resourceDescription;

        @Column(name = "url",length = 1025)
        private String resourceUrl;

        @Column(name="show_in_menu",columnDefinition = "boolean default true")
        private Boolean showInMenu;

        @Column(name="resource_order")
        private Long resourceOrder;

        @Column(name="resource_sub_order")
        private String resourceSubOrder;

        @ManyToOne
        @JoinColumn(name="parent_id")
        private Resources parentResource;

        @OneToMany(mappedBy = "parentResource" ,cascade = CascadeType.ALL)
        private Set<Resources> childResources = new HashSet<>();

        @ManyToMany(mappedBy = "resources", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
        private Set<Modules> modules = new HashSet<>();

        @OneToMany(mappedBy = "resource",cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<RoleModuleResources> roleModuleResources = new HashSet<>();
}
