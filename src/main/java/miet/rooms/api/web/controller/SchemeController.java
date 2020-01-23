package miet.rooms.api.web.controller;

import miet.rooms.api.service.SchemeService;
import miet.rooms.repository.jpa.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheme")
public class SchemeController {

    private final SchemeService schemeService;

    @Autowired
    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping
    public Scheme getSchemeByFloorAndBuilding(@RequestParam Long floor, @RequestParam String building) {
        return schemeService.getSchemeByFloorAndBuilding(floor, building);
    }
}
