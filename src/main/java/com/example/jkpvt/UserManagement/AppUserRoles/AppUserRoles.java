package com.example.jkpvt.UserManagement.AppUserRoles;

import com.example.jkpvt.UserManagement.AppUser.AppUser;
import com.example.jkpvt.UserManagement.Roles.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Table(name = "app_user_roles")
@EntityListeners(AppUserRolesListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class AppUserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @Column(name = "user_group")
    private String userGroup;
}
