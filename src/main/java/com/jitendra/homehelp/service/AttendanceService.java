package com.jitendra.homehelp.service;

import com.jitendra.homehelp.dao.AttendanceDao;
import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {

    public AttendanceDao getAttendanceDao();

    public Attendance save(Attendance attendance);

    public Boolean delete(Long id);

    public Attendance getById(Long id);

    public Boolean markPresent(Long id, Date date);

    public Boolean markAbsent(Long id, Date date);

    public Boolean markPresentForToday(Long id);

    public Boolean markAbsentForToday(Long id);

    public Boolean markPresentForTodayByShift(Long id,Long shiftId);

    public Boolean markAbsentForTodayByShift(Long id,Long shiftId);

    public List<Attendance> getByHomeHelpId(Long id);

    public Page<Attendance> getByHomeHelpId(Long id, Pageable pageable);

    public List<Attendance> getByHomeHelpId(Long id, Date start, Date end);

    public Page<Attendance> getByHomeHelpId(Long id, Date start, Date end, Pageable pageable);

    public List<AttendanceDto> getByHomeIdAndDate(String homeId, java.sql.Date requestedDate);

    public List<AttendanceDto> getByHomeIdAndToDate(String homeId);

    public int markInTime(Long id, Date date, Long shiftId, Time inTime);

    public int markOutTime(Long id,Date date, Long shiftId, Time outTime);

    public int updateStatus(Long id, Date date, Long shiftId, String status);

    public int markCompleteAttendance( Long id,Date date,Long shiftId,Boolean present,Time inTime,String status);

    public int markTodayAttendance( Long id,Long shiftId);

    public int completeShift(Long id,Date date,Long shiftId,String status,Time outTime);

    public int completeTodayShift(Long id,Long shiftId);

    public List<Attendance> getByDate(Date date);

    public Map<String, Map<String,Integer>>getMonthlyPresentAttendance(Long homeHelpId, Long shiftId, Date startDate, Date endDate );

    public List<Pair<String, Integer>> getCurrentHelpStatusByUserId(String userId);

}
