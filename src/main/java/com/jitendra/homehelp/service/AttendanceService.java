package com.jitendra.homehelp.service;

import com.jitendra.homehelp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AttendanceService {

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

}
