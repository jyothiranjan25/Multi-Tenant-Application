package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Entities.Connectors.Connector.Connector;
import com.example.jkpvt.Entities.Connectors.ConnectorConfiguration.ConnectorConfiguration;
import com.example.jkpvt.Core.AbstractModel.BaseAbstractModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "connector_xref",indexes = {
        @Index(name = "idx_connector_xref_name", columnList = "name"),
        @Index(name = "idx_connector_xref_status", columnList = "status"),
        @Index(name = "idx_connector_xref_connector_id", columnList = "connector_id"),
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class ConnectorXref extends BaseAbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;

    @Column(length = 1025)
    private String description;

    @Enumerated(EnumType.STRING)
    private ConnectorXrefStatusEnum status;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "connector_id", foreignKey = @ForeignKey(name = "fk_connector_xref_connector_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Connector connector;

    @OneToMany(mappedBy = "connectorXref",cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConnectorConfiguration> connectorConfigurations;
}
