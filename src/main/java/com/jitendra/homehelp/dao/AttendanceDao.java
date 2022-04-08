package com.jitendra.homehelp.dao;

import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.repository.AttendanceRepository;
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
public interface AttendanceDao extends BaseDao<AttendanceRepository> {

    public Attendance save(Attendance attendance);

    public Boolean delete(Long id);

    public Attendance getById(Long id);

    public Boolean markAttendance(Long id, Date date, Boolean present);

    public Boolean markAttendance(Long id, Date date, Long shiftId , Boolean present);

    public List<Attendance> getByHomeHelpId(Long id);

    public Page<Attendance> getByHomeHelpId(Long id, Pageable pageable);

    public List<Attendance> getByHomeHelpId(Long id, Date start, Date end);

    public Page<Attendance> getByHomeHelpId(Long id, Date start, Date end, Pageable pageable);

    public List<AttendanceDto> getByHomeIdAndDate(String homeId, java.sql.Date requestedDate);

    public List<AttendanceDto> getByHomeIdAndThisMonth(String homeId, java.sql.Date toDate, java.sql.Date fromDate);

    public int markInTime(Long id, Date date, Long shiftId, Time inTime);

    public int markOutTime(Long id,Date date, Long shiftId, Time outTime);

    public int updateStatus(Long id, Date date, Long shiftId, String status);

    public int markCompleteAttendance( Long id,Date date,Long shiftId,Boolean present,Time inTime,String status);

    public int completeShift(Long id,Date date,Long shiftId,String status,Time inTime);

    public List<Attendance> getByDate( Date date);

    public Map<String, Map<String,Integer>> getMonthlyAttendance(Long homeHelpId, Long shiftId, Date startDate, Date endDate,Boolean isPresent);

    List<Pair<String, Integer>> getCurrentHelpStatusByUserId(String userId,Date date);


}
