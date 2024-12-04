package com.example.jkpvt.Entities.Connectors.ConnectorConfiguration;

import com.example.jkpvt.Entities.Connectors.ConnectorXref.ConnectorXref;
import com.example.jkpvt.Core.AbstractModel.BaseAbstractModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Table(name = "connector_configuration",indexes = {
        @Index(name = "idx_connector_configuration_key", columnList = "key"),
        @Index(name = "idx_connector_configuration_value", columnList = "value"),
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class ConnectorConfiguration extends BaseAbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hilo")
    @TableGenerator(name = "hilo", table = "hilo_generator", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "connector_xref_id", foreignKey = @ForeignKey(name = "fk_connector_configuration_connector_xref_id"))
    private ConnectorXref connectorXref;

    private String key;

    private String value;

    @Column(name = "is_secure", columnDefinition = "boolean default false")
    private boolean secure;
}
