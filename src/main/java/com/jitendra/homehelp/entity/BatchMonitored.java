package com.jitendra.homehelp.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batchMonitored")
@ApiModel
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class BatchMonitored {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "batchEvent")
    @Enumerated(EnumType.STRING)
    private BatchEvent batchEvent;

    @Column(name = "batchExecutionId")
    private Long batchExecutionId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "insertedRecord")
    private Integer insertedRecord;

    @Column(name = "skipRecord")
    private Integer skipRecord;

    @Column(name = "totalRecord")
    private  Integer totalRecord;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;

    @Column(name = "startBy")
    private String startBy;

    @Column(name = "reason" ,length = 2000)
    private String failedReason;

    @Column(name = "runForDate")
    private Date dateFor;

    public static class Builder {
        private Long id;
        private Date date;
        private BatchEvent batchEvent;
        private Long batchExecutionId;
        private Status status;
        private Integer insertedRecord;
        private Integer skipRecord;
        private Integer totalRecord;
        private Time startTime;
        private Time endTime;
        private String startBy;

        public Builder(){}

        public Builder id(Long id) { this.id =id; return this;}
        public Builder date(Date date) { this.date = date;return this;}
        public Builder batchEvent(BatchEvent batchEvent) { this.batchEvent = batchEvent;return this;}
        public Builder batchExecutionId(Long batchExecutionId) { this.batchExecutionId = batchExecutionId;return this;}
        public Builder status(Status status) { this.status = status;return this;}
        public Builder insertedRecord(Integer insertedRecord) { this.insertedRecord = insertedRecord;return this;}
        public Builder skipRecord(Integer skipRecord) { this.skipRecord = skipRecord;return this;}
        public Builder totalRecord(Integer totalRecord) { this.totalRecord = totalRecord;return this;}
        public Builder startTime(Time startTime) { this.startTime = startTime;return this;}
        public Builder endTime(Time endTime) { this.endTime = endTime;return this;}
        public Builder startBy(String startBy) { this.startBy = startBy;return this;}

        public BatchMonitored build(){
            BatchMonitored batchMonitored = new BatchMonitored();
            batchMonitored.setId(this.id);
            batchMonitored.setDate(this.date);
            batchMonitored.setBatchEvent(this.batchEvent);
            batchMonitored.setBatchExecutionId(this.batchExecutionId);
            batchMonitored.setStatus(this.status);
            batchMonitored.setInsertedRecord(this.insertedRecord);
            batchMonitored.setSkipRecord(this.skipRecord);
            batchMonitored.setTotalRecord(this.totalRecord);
            batchMonitored.setStartTime(this.startTime);
            batchMonitored.setEndTime(this.endTime);
            batchMonitored.setStartBy(this.startBy);
            return  batchMonitored;
        }

        public BatchMonitored buildDefault() {
            BatchMonitored batchMonitored = new BatchMonitored();
            batchMonitored.setDate( new Date(System.currentTimeMillis()));
            batchMonitored.setBatchEvent(BatchEvent.ATTENDANCE);
            batchMonitored.setStatus(Status.INPROC);
            batchMonitored.setStartTime(new Time(System.currentTimeMillis()));
            batchMonitored.setInsertedRecord(0);
            batchMonitored.setSkipRecord(0);
            batchMonitored.setTotalRecord(0);
            batchMonitored.setStartBy("AUTORUN");
            return batchMonitored;
        }

    }


}
