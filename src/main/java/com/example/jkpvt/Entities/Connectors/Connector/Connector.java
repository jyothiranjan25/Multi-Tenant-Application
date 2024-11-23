package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Entities.Connectors.ConnectorXref.ConnectorXref;
import com.example.jkpvt.Core.AbstractModel.AbstractModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "connector",indexes = {
        @Index(name = "idx_connector_name", columnList = "name"),
        @Index(name = "idx_connector_type", columnList = "type"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_connector_name", columnNames = "name"),
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Connector extends AbstractModel<Connector> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String connectorName;

    @Column(name = "description",length = 1025)
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ConnectorEnum type;

    @Column(name="status")
    private Boolean status;

    @Column(name = "icon",length = 4025)
    private String icon;

    @OneToMany(mappedBy = "connector",cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConnectorXref> connectorXrefs;
}
