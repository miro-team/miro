package miet.rooms.api.model;

import lombok.Getter;
import lombok.Setter;
import miet.rooms.repository.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class Retriever {
    private List<AllDataFront> allDataList;
    private List<Room> rooms;
    private List<Scheme> schemes;
    private List<Group> groups;
    private List<RoomType> roomTypes;
    private List<Pair> pairs;
    private List<Transfer> transfers;
    private List<Cycle> cycles;
    private List<Engage> engages;
    private List<EngageType> engageTypes;
}
