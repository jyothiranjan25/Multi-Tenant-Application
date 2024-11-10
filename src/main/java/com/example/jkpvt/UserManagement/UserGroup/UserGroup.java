package com.example.jkpvt.UserManagement.UserGroup;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "user_group")
@EntityListeners(UserGroupListener.class)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String groupName;

    @Column(name = "description", length = 1025)
    private String groupDescription;

    @Column(name = "qualified_name")
    private String qualifiedName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private UserGroup parentGroup;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private Set<UserGroup> childGroups;
}
