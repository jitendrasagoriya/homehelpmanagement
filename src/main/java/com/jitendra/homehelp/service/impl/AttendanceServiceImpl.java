package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.AttendanceDao;
import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.enums.ProgressStatus;
import com.jitendra.homehelp.enums.Status;
import com.jitendra.homehelp.service.AttendanceService;
import com.jitendra.homehelp.utils.Utils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger logger =   LogManager.getLogger(AttendanceServiceImpl.class);

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

    @Override
    public List<AttendanceDto> getByHomeIdAndDate(String homeId, java.sql.Date requestedDate) {
        return attendanceDao.getByHomeIdAndDate(homeId,requestedDate);
    }

    @Override
    public List<AttendanceDto> getByHomeIdAndThisMonth(String homeId) {

        Date firstDateOfMonth =   Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant()) ;
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);

        return attendanceDao.getByHomeIdAndThisMonth(homeId, new java.sql.Date(firstDateOfMonth.getTime()) , new java.sql.Date(today.getTime()));
    }

    @Override
    public List<AttendanceDto> getByHomeIdAndToDate(String homeId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return getByHomeIdAndDate(homeId, new java.sql.Date(today.getTime()) );
    }

    @Override
    public int markInTime(Long id, Date date, Long shiftId, Time inTime) {
        return 0;
    }

    @Override
    public int markOutTime(Long id, Date date, Long shiftId, Time outTime) {
        return 0;
    }

    @Override
    public int updateStatus(Long id, Date date, Long shiftId, String status) {
        return 0;
    }

    @Override
    @Transactional
    public int markCompleteAttendance(Long id, Date date, Long shiftId, Boolean present, Time inTime, String status) {
        return attendanceDao.markCompleteAttendance(id,date,shiftId,present,inTime,status);
    }

    @Override
    @Transactional
    public int markTodayAttendance(Long id, Long shiftId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        Time time = Time.valueOf(LocalTime.now());
        return markCompleteAttendance(id,today,shiftId,Boolean.TRUE,time, Status.INPROC.getValue());
    }

    @Override
    @Transactional
    public int completeShift(Long id, Date date, Long shiftId, String status, Time inTime) {
        return attendanceDao.completeShift(id,date,shiftId, status,inTime);
    }

    @Override
    @Transactional
    public int completeTodayShift(Long id,  Long shiftId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        Time time = Time.valueOf(LocalTime.now());
        return completeShift(id,today,shiftId,ProgressStatus.COMPLETED.getValue(),time);
    }

    @Override
    public List<Attendance> getByDate( Date date) {
        return attendanceDao.getByDate(date);
    }

    @Override
    public Map<String, Map<String,Integer>> getMonthlyPresentAttendance(Long homeHelpId, Long shiftId, Date startDate, Date endDate) {
        return attendanceDao.getMonthlyAttendance(homeHelpId,shiftId,startDate,endDate,Boolean.TRUE);
    }

    @Override
    public List<Pair<String, Integer>> getCurrentHelpStatusByUserId(String userId) {
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return attendanceDao.getCurrentHelpStatusByUserId(userId,today);
    }

    @Override
    public AttendanceDao getAttendanceDao() {
        return attendanceDao;
    }
}
