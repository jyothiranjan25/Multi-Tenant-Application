package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Entities.UserManagement.AppUser.AppUser;
import com.example.jkpvt.Entities.UserManagement.Roles.Roles;
import com.example.jkpvt.Entities.UserManagement.UserGroup.UserGroup;
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
@Table(name = "app_user_roles", indexes = {
        @Index(name = "idx_app_user_roles_app_user_id", columnList = "app_user_id"),
        @Index(name = "idx_app_user_roles_role_id", columnList = "role_id"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_app_user_roles_app_user_id_role_id_user_group", columnNames = {"app_user_id", "role_id","user_group"})
})
@EntityListeners(AppUserRolesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class AppUserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "app_user_id", foreignKey = @ForeignKey(name = "fk_app_user_roles_app_user_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser appUser;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_app_user_roles_role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Roles roles;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "user_group_id", foreignKey = @ForeignKey(name = "fk_app_user_roles_user_group_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserGroup userGroup;
}
