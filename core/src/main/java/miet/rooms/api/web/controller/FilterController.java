package miet.rooms.api.web.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.FilterService;
import miet.rooms.api.web.income.FilterCycleIncome;
import miet.rooms.api.web.income.FilterSingleIncome;
import miet.rooms.repository.jdbc.model.FilteredDataCycle;
import miet.rooms.repository.jdbc.model.FilteredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/filter")
public class FilterController {

    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(value = "/single")
    public FilteredData getFilteredSingleData(@RequestParam(required = false) Long roomId,
                                              @RequestParam(required = false) Long periodicity,
                                              @RequestParam(required = false) Long pairId,
                                              @RequestParam(required = false) Long weekNum,
                                              @RequestParam String date,
                                              @RequestParam(required = false) Long building,
                                              @RequestParam(required = false) Long floor,
                                              @RequestParam(required = false) Long roomTypeId,
                                              @RequestParam(required = false) Long capacity,
                                              @RequestParam(required = false) Long weekDay,
                                              @RequestParam Long pageSize,
                                              @RequestParam Long pageNum) {
        FilterSingleIncome singleIncome = FilterSingleIncome.builder()
                .roomId(roomId)
                .periodicity(periodicity)
                .pairId(pairId)
                .weekNum(weekNum)
                .date(date)
                .building(building)
                .floor(floor)
                .roomTypeId(roomTypeId)
                .capacity(capacity)
                .weekDay(weekDay)
                .build();
        return filterService.getFilteredData(singleIncome, pageNum, pageSize);
    }

    @GetMapping(value = "/cycle")
    public FilteredData getFilteredCycleData(@RequestParam(required = false) Long roomId,
                                                  @RequestParam Long periodicity,
                                                  @RequestParam(required = false) Long pairId,
                                                  @RequestParam(required = false) Long building,
                                                  @RequestParam(required = false) Long floor,
                                                  @RequestParam(required = false) Long roomTypeId,
                                                  @RequestParam(required = false) Long capacity,
                                                  @RequestParam(required = false) Long weekDay,
                                                  @RequestParam Long pageSize,
                                                  @RequestParam Long pageNum) {
        FilterCycleIncome cycleIncome = FilterCycleIncome.builder()
                .roomId(roomId)
                .periodicity(periodicity)
                .pairId(pairId)
                .building(building)
                .floor(floor)
                .roomTypeId(roomTypeId)
                .capacity(capacity)
                .weekDay(weekDay)
                .build();
        return filterService.getFilteredData(cycleIncome, pageNum, pageSize);
    }
}
