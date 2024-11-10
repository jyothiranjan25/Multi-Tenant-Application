package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Roles.Roles;
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
@Table(name = "role_module")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class RoleModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Modules module;

    @Column(name = "model_order")
    private Integer modelOrder;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "role_module_resources",
            joinColumns = @JoinColumn(name = "role_module_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private Set<Resources> resources = new HashSet<>();
}
