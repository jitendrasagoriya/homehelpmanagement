package com.jitendra.homehelp.service;

import com.jitendra.homehelp.dto.AttendanceDto;
import io.swagger.models.auth.In;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface DashBoardService {
    public Map<String,Integer> getHelpsCurrentStatus(List<AttendanceDto> attendanceDtos);
    public Map<String, Map<String,Map<String,Integer>>> getMonthlyReport(List<AttendanceDto> attendanceDtos);
    public List<Pair<String, Integer>> getCurrentStatus(String usrId);

}
