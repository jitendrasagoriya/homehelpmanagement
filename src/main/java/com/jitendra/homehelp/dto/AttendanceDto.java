package com.jitendra.homehelp.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDto extends PipeDeliminatorBean {

    private Long id;

    private Long homeHelpId;

    private Long shiftId;

    private Date date;

    private String present;

    private String name;

    private Time inTime;

    private Time outTime;

    private Time shiftTime;

    private String status;

    private String helpType;

    private String currentStatus;

    @Override
    public String toPipeSeparatedString() {
        return shiftId+"|"+homeHelpId+"|"+id+"|"+date+"|"+present+"|"+name+"|"+shiftTime+"|"+inTime+"|"+outTime+"|"+helpType+"|"+currentStatus;
    }
}
