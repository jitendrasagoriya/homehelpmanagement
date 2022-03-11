package com.jitendra.homehelp.dao.impl;

import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.repository.AttendanceRepository;
import com.jitendra.homehelp.dao.AttendanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceJdbcDao implements AttendanceDao {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public AttendanceRepository getRepository() {
        return attendanceRepository;
    }

    @Override
    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            attendanceRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception exception) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Attendance getById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Boolean markAttendance(Long id, Date date, Boolean present) {
        return attendanceRepository.markAttendance(id,date,present)>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    @Transactional
    public Boolean markAttendance(Long id, Date date, Long shiftId, Boolean present) {
        return attendanceRepository.markAttendance(id,date, shiftId,present)>0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public List<Attendance> getByHomeHelpId(Long id) {
        return attendanceRepository.getByHomeHelpId(id);
    }

    @Override
    public Page<Attendance> getByHomeHelpId(Long id, Pageable pageable) {
        return attendanceRepository.getByHomeHelpId(id,pageable);
    }

    @Override
    public List<Attendance> getByHomeHelpId(Long id, Date start, Date end) {
        return attendanceRepository.getByHomeHelpId(id,start,end);
    }

    @Override
    public Page<Attendance> getByHomeHelpId(Long id, Date start, Date end, Pageable pageable) {
        return attendanceRepository.getByHomeHelpId(id,start,end,pageable);
    }
}
