package miet.rooms.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import miet.rooms.initializer.initdata.TimetableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScheduleGetter {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final HttpEntity<String> httpEntity;

    private final String COMMON_URL = "https://miet.ru/schedule/groups";
    private final String GROUP_URL = "https://miet.ru/schedule/groups/data?group=";


    @Getter
    private List<TimetableData> timetableList = new ArrayList<>();

    @Getter
    private List<String> groups = new ArrayList<>();

    private List<String> rooms = new ArrayList<>();

    @Autowired
    public ScheduleGetter(RestTemplate restTemplate, ObjectMapper objectMapper, HttpEntity<String> httpEntity) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.httpEntity = httpEntity;
    }

    private void initialize() throws IOException {
        String response = getResponse();
        collectGroups(response);
    }

    public List<TimetableData> getScheduleFromServer() throws IOException {
        initialize();
        return timetableList;
    }

    private void collectGroups(String response) throws IOException {
        for (String group : response.split(",")) {
            String groupStr = group.trim().replace("\"", "");
            groups.add(groupStr);
            log.info("Found " + groupStr);
            addTimetableEntry(groupStr);
        }
    }

    public List<String> getRooms() {
        return timetableList.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(datum -> datum.getRoom().getName())
                .distinct()
                .collect(Collectors.toList());
    }

    private void addTimetableEntry( String groupStr) throws IOException {
        log.info("Adding " + groupStr);
        timetableList.add(getTimetableData(groupStr));
    }

    private TimetableData getTimetableData(String groupStr) throws IOException {
        String groupTimetable = restTemplate.exchange(GROUP_URL + groupStr, HttpMethod.POST, httpEntity, String.class).getBody();
        return objectMapper.readValue(groupTimetable, TimetableData.class);
    }

    private String getResponse() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(COMMON_URL, HttpMethod.POST, httpEntity, String.class);
        String response = responseEntity.getBody();
        return getRightResponse(response);
    }

    private String getRightResponse(String response) {
        return response != null ? response
                .replaceAll("\\[", "")
                .replaceAll("]", "") : "";
    }
}
