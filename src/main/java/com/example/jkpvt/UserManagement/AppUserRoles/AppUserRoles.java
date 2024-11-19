package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.AppUser.AppUser;
import com.example.jkpvt.UserManagement.Roles.Roles;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id", foreignKey = @ForeignKey(name = "fk_app_user_roles_app_user_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_app_user_roles_role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Roles roles;

    @Column(name = "user_group")
    private String userGroup;
}
