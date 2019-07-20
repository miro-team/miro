package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "transfers")
@SequenceGenerator(schema = "schedule", name = "transfers", sequenceName = "schedule.transfer_transfers_id_seq", allocationSize = 1)
public class Transfer {

    private Long id;
    private AllData fromAllData;
    private AllData toAllData;
    private Long seqNum;
    private String cycleId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.transfer_transfers_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "from_all_data_id")
    public AllData getFromAllData() {
        return fromAllData;
    }

    public void setFromAllData(AllData fromAllData) {
        this.fromAllData = fromAllData;
    }

    @ManyToOne
    @JoinColumn(name = "to_all_data_id")
    public AllData getToAllData() {
        return toAllData;
    }

    public void setToAllData(AllData toAllData) {
        this.toAllData = toAllData;
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
