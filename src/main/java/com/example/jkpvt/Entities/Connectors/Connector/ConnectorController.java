package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/connectors")
@RequiredArgsConstructor
public class ConnectorController {

    private final ConnectorService service;

    @RequestMapping(method = RequestMethod.GET)
    private List<ConnectorDTO> search(@RequestParam Map<String, String> queryParams) {
        ConnectorDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorDTO.class);
        return service.search(connectorDTO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private List<ConnectorDTO> get(@RequestParam Map<String, String> queryParams) {
        ConnectorDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorDTO.class);
        return service.get(connectorDTO);
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
