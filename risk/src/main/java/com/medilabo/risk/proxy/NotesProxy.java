package com.medilabo.risk.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "medilabo-notes", url = "${proxy.notes.url}")
public interface NotesProxy {
    @GetMapping("/notes/trigger/{patientId}")
    List<String> getTriggersByPatientId(@RequestHeader(value = "medilabo-token", required = true) String token, @PathVariable int patientId);
}
