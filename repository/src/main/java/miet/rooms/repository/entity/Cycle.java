package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "cycles")
@SequenceGenerator(schema = "schedule", name = "cycles", sequenceName = "schedule.cycle_cycles_id_seq", allocationSize = 1)
public class Cycle {

    private Long id;
    private AllData allData;
    private Long seqNum;
    private String cycleId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.cycle_cycles_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "all_data_id")
    public AllData getAllData() {
        return allData;
    }

    public void setAllData(AllData allData) {
        this.allData = allData;
    }

    @Column(name = "seq_num", nullable = false)
    public Long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Long seqNum) {
        this.seqNum = seqNum;
    }

    @Column(name = "cycle_id", nullable = false)
    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }
}
