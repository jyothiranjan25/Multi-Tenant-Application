package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connectorsXref")
@RequiredArgsConstructor
public class ConnectorXrefController {

    private final ConnectorXrefService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private ConnectorXrefDTO get(@QueryParams ConnectorXrefDTO connectorDTO) {
        List<ConnectorXrefDTO> connectors = service.get(connectorDTO);
        connectorDTO.setData(connectors);
        return connectorDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ConnectorXrefDTO create(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.create(connectorDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private ConnectorXrefDTO update(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.update(connectorDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.delete(connectorDTO);
    }

    @RequestMapping(value = "/getConnectorXrefStatus", method = RequestMethod.GET)
    private ConnectorXrefStatusEnum[] getConnectorXrefStatus() {
        return ConnectorXrefStatusEnum.get();
    }
}
