package com.example.jkpvt.Connectors.Connector;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/connectors")
@RequiredArgsConstructor
public class ConnectorController {

    private final ConnectorService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ConnectorDTO> get(@RequestParam Map<String, String> queryParams) {
        ConnectorDTO connectorDTO = JsonMap.toDto(queryParams, ConnectorDTO.class);
        return service.get(connectorDTO);
    }

}
