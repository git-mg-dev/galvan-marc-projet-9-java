package com.medilabo.risk.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "medilabo-notes", url = "localhost:9002")
public interface NotesProxy {
    @GetMapping("/notes/trigger/{patientId}")
    List<String> getTriggersByPatientId(@PathVariable int patientId);
}
