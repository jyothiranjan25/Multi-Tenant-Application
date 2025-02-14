package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Entities.UserManagement.Modules.Modules;
import com.example.jkpvt.Entities.UserManagement.Roles.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Table(name = "role_module", indexes = {
        @Index(name = "idx_role_module_role_id", columnList = "role_id"),
        @Index(name = "idx_role_module_module_id", columnList = "module_id"),
        @Index(name = "idx_role_module_module_order", columnList = "module_order"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_module_role_id_module_id", columnNames = {"role_id", "module_id"})
})
@EntityListeners(RoleModuleListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class RoleModule {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_module_role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "fk_role_module_module_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Modules module;

    @Column(name = "module_order")
    private Long moduleOrder;
}
