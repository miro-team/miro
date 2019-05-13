package miet.rooms.api.schedule.data.frontdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferEvent {
    private Long fromAllDataId;
    private Long roomId;
}
