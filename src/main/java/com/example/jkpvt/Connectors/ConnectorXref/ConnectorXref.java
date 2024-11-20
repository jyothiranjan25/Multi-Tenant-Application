package com.example.jkpvt.Connectors.ConnectorXref;

import com.example.jkpvt.Connectors.Connector.Connector;
import com.example.jkpvt.Connectors.ConnectorConfiguration.ConnectorConfiguration;
import com.example.jkpvt.Core.AbstractModel.BaseAbstractModel;
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
@Table(name = "connector_xref",indexes = {
        @Index(name = "idx_connector_xref_name", columnList = "name"),
        @Index(name = "idx_connector_xref_status", columnList = "status"),
        @Index(name = "idx_connector_xref_connector_id", columnList = "connector_id"),
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class ConnectorXref extends BaseAbstractModel<ConnectorXref> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "description",length = 1025)
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ConnectorXrefEnum status;

    @ManyToOne
    @JoinColumn(name = "connector_id", foreignKey = @ForeignKey(name = "fk_connector_xref_connector_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Connector connector;

    @OneToMany(mappedBy = "connectorXref",cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConnectorConfiguration> connectorConfigurations;
}
