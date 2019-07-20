package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "pairs")
@SequenceGenerator(schema = "time_desc", name = "pairs", sequenceName = "time_desc.pairs_pair_id_seq", allocationSize = 1)
public class Pair {

    private Long id;
    private String name;
    private String timeFrom;
    private String timeTo;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.pairs_pair_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "time_from", nullable = false)
    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    @Column(name = "time_to", nullable = false)
    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
}
