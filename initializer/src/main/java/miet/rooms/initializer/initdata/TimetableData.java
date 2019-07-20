package miet.rooms.initializer.initdata;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Times",
        "Data",
        "Semestr"
})
public class TimetableData {

    @JsonProperty("Times")
    private List<Time> times = null;
    @JsonProperty("Data")
    private List<Datum> data = null;
    @JsonProperty("Semestr")
    private String semestr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Times")
    public List<Time> getTimes() {
        return times;
    }

    @JsonProperty("Times")
    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @JsonProperty("Data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("Data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @JsonProperty("Semestr")
    public String getSemestr() {
        return semestr;
    }

    @JsonProperty("Semestr")
    public void setSemestr(String semestr) {
        this.semestr = semestr;
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
