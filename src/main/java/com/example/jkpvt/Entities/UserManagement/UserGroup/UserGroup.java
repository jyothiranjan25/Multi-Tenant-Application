package com.example.jkpvt.Entities.UserManagement.UserGroup;


import com.example.jkpvt.Entities.UserManagement.AppUserRoles.AppUserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "user_group", indexes = {
        @Index(name = "idx_user_group_name", columnList = "name"),
        @Index(name = "idx_user_group_qualified_name", columnList = "qualified_name"),
        @Index(name = "idx_user_group_parent_id", columnList = "parent_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_group_name", columnNames = "name"),
        @UniqueConstraint(name = "uk_user_group_qualified_name", columnNames = "qualified_name")
})
@EntityListeners(UserGroupListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String groupName;

    @Column(name = "description", length = 1025)
    private String groupDescription;

    @Column(name = "qualified_name")
    private String qualifiedName;

    @ManyToOne
//    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_user_group_parent_id", foreignKeyDefinition = "FOREIGN KEY (parent_id) REFERENCES user_group(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_user_group_parent_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserGroup parentGroup;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserGroup> childGroups;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AppUserRoles> appUserRoles;
}
