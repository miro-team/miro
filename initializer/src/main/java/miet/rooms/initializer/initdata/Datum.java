package miet.rooms.initializer.initdata;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Day",
        "DayNumber",
        "Time",
        "Class",
        "Group",
        "Room"
})
public class Datum {

    @JsonProperty("Day")
    private long day;
    @JsonProperty("DayNumber")
    private long weekNumber;
    @JsonProperty("Time")
    private InnerTime pair;
    @JsonProperty("Class")
    private Discipline discipline;
    @JsonProperty("Group")
    private Group group;
    @JsonProperty("Room")
    private Room room;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Day")
    public long getDay() {
        return day;
    }

    @JsonProperty("Day")
    public void setDay(long day) {
        this.day = day;
    }

    @JsonProperty("DayNumber")
    public long getWeekNumber() {
        return weekNumber;
    }

    @JsonProperty("DayNumber")
    public void setWeekNumber(long weekNumber) {
        this.weekNumber = weekNumber;
    }

    @JsonProperty("Time")
    public InnerTime getPair() {
        return pair;
    }

    @JsonProperty("Time")
    public void setPair(InnerTime pair) {
        this.pair = pair;
    }

    @JsonProperty("Class")
    public Discipline getDiscipline() {
        return discipline;
    }

    @JsonProperty("Class")
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @JsonProperty("Group")
    public Group getGroup() {
        return group;
    }

    @JsonProperty("Group")
    public void setGroup(Group group) {
        this.group = group;
    }

    @JsonProperty("Room")
    public Room getRoom() {
        return room;
    }

    @JsonProperty("Room")
    public void setRoom(Room room) {
        this.room = room;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}