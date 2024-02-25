package com.epam.finalDemo.client;

import com.epam.finalDemo.dto.request.TrainerClientDTO;
import com.epam.finalDemo.dto.response.Schedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Component
@FeignClient(name = "trainer-service", url = "http://localhost:8070/api/v2/trainer")
public interface TrainerClient {

    @PostMapping("/save")
    void save(@RequestBody TrainerClientDTO request);

    @PostMapping("/saveAll")
    void saveAll(@RequestBody List<TrainerClientDTO> request);

    @GetMapping("/schedule")
    Schedule getSchedule(@RequestParam String username);
}
