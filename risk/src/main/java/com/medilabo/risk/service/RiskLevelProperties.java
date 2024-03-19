package com.medilabo.risk.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "levels")
public class RiskLevelProperties {

    private Map<String, Map<String, Integer>> earlyOnset;
    private Map<String, Map<String, Integer>> inDanger;
    private Map<String, Map<String, Integer>> borderline;

}
