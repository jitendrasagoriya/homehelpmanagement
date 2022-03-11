package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.AttendanceDao;
import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.service.AttendanceService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Override
    public Attendance save(Attendance attendance) {
        return attendanceDao.save(attendance);
    }

    @Override
    public Boolean delete(Long id) {
        return attendanceDao.delete(id);
    }

    @Override
    public Attendance getById(Long id) {
        return attendanceDao.getById(id);
    }

    @Override
    public Boolean markPresent(Long id, Date date) {
        return attendanceDao.markAttendance(id,date,Boolean.TRUE);
    }

    @Override
    public Boolean markAbsent(Long id, Date date) {
        return attendanceDao.markAttendance(id,date,Boolean.FALSE);
    }

    @Override
    public Boolean markPresentForToday(Long id) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return attendanceDao.markAttendance(id, today,Boolean.TRUE);
    }

    @Override
    public Boolean markAbsentForToday(Long id) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return attendanceDao.markAttendance(id, new Date(),Boolean.FALSE);
    }

    @Override
    public Boolean markPresentForTodayByShift(Long id, Long shiftId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return attendanceDao.markAttendance(id, today,shiftId,Boolean.TRUE);
    }

    @Override
    public Boolean markAbsentForTodayByShift(Long id, Long shiftId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return attendanceDao.markAttendance(id, today,shiftId,Boolean.FALSE);
    }

    @Override
    public List<Attendance> getByHomeHelpId(Long id) {
        return attendanceDao.getByHomeHelpId(id);
    }

    @Override
    public Page<Attendance> getByHomeHelpId(Long id, Pageable pageable) {
        return attendanceDao.getByHomeHelpId(id,pageable);
    }

    @Override
    public List<Attendance> getByHomeHelpId(Long id, Date start, Date end) {
        return attendanceDao.getByHomeHelpId(id,start,end);
    }

    @Override
    public Page<Attendance> getByHomeHelpId(Long id, Date start, Date end, Pageable pageable) {
        return attendanceDao.getByHomeHelpId(id,start,end,pageable);
    }
}
