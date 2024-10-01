package com.example.jkpvt.UserManagement.Roles.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Roles.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Table(name = "role_module_resources")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class RoleModuleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Modules module;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resources resource;

    @Column(name = "model_order")
    private int modelOrder;

    @Column(name = "show_in_menu")
    private boolean ShowInMenu;

    @Transient
    private boolean isUpdating = false;
}
