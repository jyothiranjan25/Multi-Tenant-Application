package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import com.example.jkpvt.UserManagement.Modules.Modules;
import com.example.jkpvt.UserManagement.Resources.Resources;
import com.example.jkpvt.UserManagement.Roles.Roles;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Table(name = "role_module_resources", indexes = {
        @Index(name = "idx_role_module_resources_role_id", columnList = "role_id"),
        @Index(name = "idx_role_module_resources_module_id", columnList = "module_id"),
        @Index(name = "idx_role_module_resources_resource_id", columnList = "resource_id"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_module_resources_role_id_module_id_resource_id", columnNames = {"role_id", "module_id", "resource_id"})
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class RoleModuleResources {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_module_resources_role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Roles role;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "fk_role_module_resources_module_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Modules module;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_role_module_resources_resource_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resources resource;

    @Column(name = "is_visible", columnDefinition = "boolean default true")
    private Boolean isVisible;
}
