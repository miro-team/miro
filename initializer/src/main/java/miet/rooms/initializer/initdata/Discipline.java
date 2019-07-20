package miet.rooms.initializer.initdata;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Code",
        "Name",
        "TeacherFull",
        "Teacher"
})
public class Discipline {

    @JsonProperty("Code")
    private String code;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("TeacherFull")
    private String teacherFull;
    @JsonProperty("Teacher")
    private String teacher;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    @JsonProperty("Code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("TeacherFull")
    public String getTeacherFull() {
        return teacherFull;
    }

    @JsonProperty("TeacherFull")
    public void setTeacherFull(String teacherFull) {
        this.teacherFull = teacherFull;
    }

    @JsonProperty("Teacher")
    public String getTeacher() {
        return teacher;
    }

    @JsonProperty("Teacher")
    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
