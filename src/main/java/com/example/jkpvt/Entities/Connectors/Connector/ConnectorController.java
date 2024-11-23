package com.example.jkpvt.Entities.Connectors.Connector;

import com.example.jkpvt.Core.Json.JsonMap;
import com.example.jkpvt.Entities.SearchFilter.SearchFilterDTO;
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
    public List<ConnectorDTO> search(@RequestParam Map<String, String> queryParams) {
        SearchFilterDTO searchFilterDTO = JsonMap.toDto(queryParams, SearchFilterDTO.class);
        ConnectorDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorDTO.class);
        return service.search(searchFilterDTO, connectorDTO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ConnectorDTO> get(@RequestParam Map<String, String> queryParams) {
        ConnectorDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorDTO.class);
        return service.get(connectorDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ConnectorDTO create(@RequestBody ConnectorDTO connectorDTO) {
        return service.create(connectorDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ConnectorDTO update(@RequestBody ConnectorDTO connectorDTO) {
        return service.update(connectorDTO);
    }

}
