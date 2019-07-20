package miet.rooms.initializer.initdata;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Time",
        "Code",
        "TimeFrom",
        "TimeTo"
})
public class Time {

    @JsonProperty("Time")
    private String time;
    @JsonProperty("Code")
    private long code;
    @JsonProperty("TimeFrom")
    private String timeFrom;
    @JsonProperty("TimeTo")
    private String timeTo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Time")
    public String getTime() {
        return time;
    }

    @JsonProperty("Time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("Code")
    public long getCode() {
        return code;
    }

    @JsonProperty("Code")
    public void setCode(long code) {
        this.code = code;
    }

    @JsonProperty("TimeFrom")
    public String getTimeFrom() {
        return timeFrom;
    }

    @JsonProperty("TimeFrom")
    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    @JsonProperty("TimeTo")
    public String getTimeTo() {
        return timeTo;
    }

    @JsonProperty("TimeTo")
    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
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
