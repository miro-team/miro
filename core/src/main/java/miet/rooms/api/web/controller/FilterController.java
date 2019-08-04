package miet.rooms.api.web.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.FilterService;
import miet.rooms.api.web.income.FilterCycleIncome;
import miet.rooms.api.web.income.FilterSingleIncome;
import miet.rooms.repository.jdbc.model.FilteredDataCycle;
import miet.rooms.repository.jdbc.model.FilteredDataSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/filter")
public class FilterController {

    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(value = "/single")
    public FilteredDataSingle getFilteredSingleData(@RequestParam(required = false) Long roomId,
                                                    @RequestParam(required = false) Long weekType,
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
                .weekType(weekType)
                .pairId(pairId)
                .weekNum(weekNum)
                .date(date)
                .building(building)
                .floor(floor)
                .roomTypeId(roomTypeId)
                .capacity(capacity)
                .weekDay(weekDay)
                .build();
        return filterService.getFilteredDataSingle(singleIncome, pageNum, pageSize);
    }

    @GetMapping(value = "/cycle")
    public FilteredDataCycle getFilteredCycleData(@RequestParam(required = false) Long roomId,
                                                  @RequestParam Long weekType,
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
                .weekType(weekType)
                .pairId(pairId)
                .building(building)
                .floor(floor)
                .roomTypeId(roomTypeId)
                .capacity(capacity)
                .weekDay(weekDay)
                .build();
        return filterService.getFilteredDataCycle(cycleIncome, pageNum, pageSize);
    }
}
