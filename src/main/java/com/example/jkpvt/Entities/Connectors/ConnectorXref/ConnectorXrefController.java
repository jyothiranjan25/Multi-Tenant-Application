package com.example.jkpvt.Entities.Connectors.ConnectorXref;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/connectorsXref")
@RequiredArgsConstructor
public class ConnectorXrefController {

    private final ConnectorXrefService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ConnectorXrefDTO> get(@RequestParam Map<String, String> queryParams) {
        ConnectorXrefDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorXrefDTO.class);
        return service.get(connectorDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ConnectorXrefDTO create(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.create(connectorDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ConnectorXrefDTO update(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.update(connectorDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestBody ConnectorXrefDTO connectorDTO) {
        return service.delete(connectorDTO);
    }
}
