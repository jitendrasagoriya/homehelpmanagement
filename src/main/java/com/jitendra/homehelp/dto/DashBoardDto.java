package com.jitendra.homehelp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jitendra.homehelp.entity.Attendance;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashBoardDto {
    List<AttendanceDto> attendances;
    @JsonIgnore
    Map<String, Map<String,Map<String,Integer>>> monthlyReport = new HashMap<>();
    List<Pair<String,Integer>> helpsCurrentStatus = new ArrayList<>();
    private List<MonthlyReport> jsonMonthlyReport;

}
