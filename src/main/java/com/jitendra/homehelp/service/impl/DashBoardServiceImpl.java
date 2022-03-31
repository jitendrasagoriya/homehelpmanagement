package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.impl.AttendanceJdbcDao;
import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.service.AttendanceService;
import com.jitendra.homehelp.service.DashBoardService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    private static final Logger logger =   LogManager.getLogger(DashBoardServiceImpl.class);

    @Autowired
    private AttendanceService attendanceService;

    @Override
    public Map<String, Integer> getHelpsCurrentStatus(List<AttendanceDto> attendanceDtos) {
        Map<String, Integer> helpCurrentStatus = new HashMap<>();
        if (attendanceDtos!=null) {
            attendanceDtos.forEach(attendanceDto -> {
                if (!StringUtils.isBlank(attendanceDto.getStatus())) {
                    String key = attendanceDto.getStatus().trim().toUpperCase(Locale.ROOT);
                    helpCurrentStatus.put(key,1 + helpCurrentStatus.getOrDefault(key,0));
                }
            });
        }
        return helpCurrentStatus;
    }

    @Override
    public Map<String, Map<String,Map<String,Integer>>> getMonthlyReport(List<AttendanceDto> attendanceDtos) {

        if(attendanceDtos==null || attendanceDtos.isEmpty())
            return null;

        AtomicReference<Long> homeHelpId = new AtomicReference<>(0l);
        Map<String, Map<String,Map<String,Integer>>> monthlyReport = new HashMap<>();
        LocalDate firstDayOfMonth =    LocalDate.now().with( TemporalAdjusters.firstDayOfMonth());
        Date start = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int daysInCurrentMonth = LocalDate.now().lengthOfMonth();
        LocalDate currentDate = LocalDate.now();
        Date end = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        attendanceDtos.forEach(attendanceDto -> {
            if (attendanceDto.getId()!=null) {
                Map<String,Map<String,Integer>> monthlyPresent = attendanceService.getMonthlyPresentAttendance(attendanceDto.getHomeHelpId(), attendanceDto.getShiftId(),start,end);
                monthlyReport.put(attendanceDto.getName() +"-"+ attendanceDto.getShiftTime(),monthlyPresent);

            }
        });
        return monthlyReport;
    }

    @Override
    public List<Pair<String, Integer>> getCurrentStatus(String usrId) {
        return attendanceService.getCurrentHelpStatusByUserId(usrId);
    }


}
