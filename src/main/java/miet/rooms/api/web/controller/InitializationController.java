package miet.rooms.api.web.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/initialize")
@Slf4j
public class InitializationController {

    private final InitializationService initializationService;

    @Autowired
    public InitializationController(InitializationService initializationService) {
        this.initializationService = initializationService;
    }


    @PostMapping
    public void initializeAll(@RequestParam(value = "startDate")
                              @DateTimeFormat(pattern = "dd.MM.yyyy")
                                      LocalDate startDate) throws IOException {
        initializationService.initializeSchedule(startDate);
    }
}
