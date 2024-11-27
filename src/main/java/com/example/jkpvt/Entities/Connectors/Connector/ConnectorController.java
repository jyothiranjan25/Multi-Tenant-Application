package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connectors")
@RequiredArgsConstructor
public class ConnectorController {

    private final ConnectorService service;

    @RequestMapping(method = RequestMethod.GET)
    private ConnectorDTO search(@QueryParams ConnectorDTO connectorDTO) {
        List<ConnectorDTO> connectors = service.search(connectorDTO);
        connectorDTO.setData(connectors);
        return connectorDTO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private ConnectorDTO get(@QueryParams ConnectorDTO connectorDTO) {
        List<ConnectorDTO> connectors = service.get(connectorDTO);
        connectorDTO.setData(connectors);
        return connectorDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ConnectorDTO create(@RequestBody ConnectorDTO connectorDTO) {
        return service.create(connectorDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private ConnectorDTO update(@RequestBody ConnectorDTO connectorDTO) {
        return service.update(connectorDTO);
    }

    @RequestMapping(value = "/getConnectorTypes", method = RequestMethod.GET)
    private ConnectorTypeEnum[] getConnectorTypes() {
        return ConnectorTypeEnum.get();
    }

    @RequestMapping(value = "/getConnectorNames", method = RequestMethod.GET)
    private ConnectorNameEnum[] getConnectorNames() {
        return ConnectorNameEnum.get();
    }

}
