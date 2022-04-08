package com.jitendra.homehelp.dto;


import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReport {
    private String name;
    private String time;
    private List<Pair<String,Integer>> statusCount;
    private Integer totalCount;


    public static List<MonthlyReport> build(Map<String, Map<String, Map<String,Integer>>> monthlyReportResult) {
        AtomicReference<List<MonthlyReport>> monthlyReport = new AtomicReference<>(new ArrayList<MonthlyReport>());
        try {
            if (monthlyReportResult != null && !monthlyReportResult.isEmpty()) {
                monthlyReportResult.forEach((s, stringMapMap) -> {
                    MonthlyReport monthlyReport1 = new MonthlyReport();
                    monthlyReport1.setName(StringUtils.substring(s, 0, s.indexOf("-")));
                    stringMapMap.forEach((s1, stringIntegerMap) -> {
                        monthlyReport1.setTime(s1);
                        List<Pair<String, Integer>> result = new ArrayList<>();
                        monthlyReport1.setStatusCount(result);
                        AtomicInteger totalCount = new AtomicInteger();
                        stringIntegerMap.forEach((s2, integer) -> {
                            monthlyReport1.getStatusCount().add(Pair.of(s2, integer));
                            totalCount.getAndIncrement();
                        });
                        monthlyReport1.setTotalCount(totalCount.get());
                    });
                    monthlyReport.get().add(monthlyReport1);
                });
            }
            return monthlyReport.get();
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
