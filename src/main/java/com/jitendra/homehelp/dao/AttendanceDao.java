package com.jitendra.homehelp.dao;

import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.repository.AttendanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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


}
