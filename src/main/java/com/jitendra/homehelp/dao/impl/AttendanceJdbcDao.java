package com.jitendra.homehelp.dao.impl;

import com.jitendra.homehelp.batch.reader.AttendanceDtoRowMapper;
import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.entity.Attendance;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.repository.AttendanceRepository;
import com.jitendra.homehelp.dao.AttendanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

@Service
public class AttendanceJdbcDao implements AttendanceDao {

    private static final Logger logger =   LogManager.getLogger(AttendanceJdbcDao.class);


    @Autowired
    protected JdbcTemplate jdbcTemplate;

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


    public Map<String, Map<String,Integer>> getMonthlyAttendance(Long homeHelpId,  Date startDate, Date endDate,Boolean isPresent) {
        String sql = "SELECT SHIFT_TIME,PRESENT , AA.DAYS  FROM  SHIFT  S INNER JOIN  ( SELECT  SHIFT_ID,PRESENT  ,count (*)  DAYS FROM ATTENDANCE WHERE" +
                " HOME_HELP_ID  = ?  AND DATE BETWEEN ? and ?  AND PRESENT = ?  Group by SHIFT_ID,PRESENT  ) AA ON S.ID = AA.SHIFT_ID ORDER BY SHIFT_TIME";
        logger.debug("Sql : {}, PARAMS : homeHelpId {} and startDate {} and endDate {} and isPresent {}",sql,homeHelpId,startDate,endDate,isPresent);
        Map<String, Map<String,Integer>> result = new HashMap<>();
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1,homeHelpId);
                ps.setDate(2,new java.sql.Date(startDate.getTime()) ) ;
                ps.setDate(3,new java.sql.Date(endDate.getTime()) ) ;
                ps.setBoolean(4,isPresent);
            }
        }, new ResultSetExtractor<Map<String, Map<String,Integer>>>() {
            @Override
            public Map<String, Map<String,Integer>> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()){
                    Map<String,Integer> presentMap = new HashMap<>();
                    presentMap.put(rs.getString("PRESENT"), rs.getInt("DAYS"));
                    result.put(rs.getString("SHIFT_TIME"),presentMap);
                }
                return result;
            }
        });
    }



    @Override
    public List<AttendanceDto> getByHomeIdAndDate(String homeId, java.sql.Date requestedDate) {
        String sql = "SELECT AAT.*,S.SHIFT_TIME   FROM ( SELECT A.*,HH.NAME,HH.HELP_TYPE   FROM ATTENDANCE A INNER JOIN HOME_HELP HH ON A.HOME_HELP_ID = HH.ID " +
                " WHERE  HH. HOME_ID =  ? AND  DATE = ?) AAT INNER JOIN SHIFT S ON S.ID = AAT.SHIFT_ID;";

        if(logger.isDebugEnabled())
            logger.debug("Sql : {}, PARAMS : homeId {} and requestedDate {} ",sql,homeId,requestedDate );

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,homeId);
                ps.setDate(2,requestedDate);
            }
        }, new RowMapper<AttendanceDto>() {
            @Override
            public AttendanceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                AttendanceDto attendanceDto = new AttendanceDto();
                attendanceDto.setId(rs.getLong("ID"));
                attendanceDto.setDate(rs.getDate("DATE"));
                attendanceDto.setPresent(rs.getString("PRESENT"));
                attendanceDto.setName(rs.getString("NAME"));
                attendanceDto.setShiftTime(rs.getTime("SHIFT_TIME"));
                attendanceDto.setInTime(rs.getTime("IN_TIME"));
                attendanceDto.setOutTime(rs.getTime("OUT_TIME"));
                attendanceDto.setStatus(rs.getString("STATUS"));
                attendanceDto.setHelpType(HelpType.getById(rs.getInt("HELP_TYPE")).getValue());
                attendanceDto.setHomeHelpId(rs.getLong("HOME_HELP_ID"));
                attendanceDto.setShiftId(rs.getLong("SHIFT_ID"));
                logger.info(attendanceDto.toPipeSeparatedString());
                return attendanceDto;
            }
        });
    }

    @Override
    public int markInTime(Long id, Date date, Long shiftId, Time inTime) {
        return attendanceRepository.markInTime(id,date,shiftId,inTime);
    }

    @Override
    public int markOutTime(Long id, Date date, Long shiftId, Time outTime) {
        return attendanceRepository.markOutTime(id,date,shiftId,outTime);
    }

    @Override
    public int updateStatus(Long id, Date date, Long shiftId, String status) {
        return attendanceRepository.updateStatus(id,date,shiftId,status);
    }

    @Override
    public int markCompleteAttendance(Long id, Date date, Long shiftId, Boolean present, Time inTime, String status) {
        return attendanceRepository.markCompleteAttendance(id,date,shiftId,present,inTime,status);
    }

    @Override
    public int completeShift(Long id, Date date, Long shiftId, String status, Time inTime) {
        return attendanceRepository.completeShift(id,date,shiftId,status,inTime);
    }

    @Override
    public List<Attendance> getByDate( Date date) {
        return attendanceRepository.getByDate(date);
    }

    @Override
    public Map<String, Map<String, Integer>> getMonthlyAttendance(Long homeHelpId, Long shiftId, Date startDate, Date endDate, Boolean isPresent) {
        String sql = "SELECT SHIFT_TIME,PRESENT , AA.DAYS  FROM  SHIFT  S INNER JOIN  ( SELECT  SHIFT_ID,PRESENT  ,count (*)  DAYS FROM ATTENDANCE WHERE" +
                " HOME_HELP_ID  = ? AND SHIFT_ID = ?  AND DATE BETWEEN ? and ?  Group by SHIFT_ID,PRESENT  ) AA ON S.ID = AA.SHIFT_ID ORDER BY SHIFT_TIME";
        Map<String, Map<String,Integer>> result = new HashMap<>();
        logger.debug("Sql : {}, PARAMS : homeHelpId {} and startDate {} and endDate {} and isPresent {}",sql,homeHelpId,startDate,endDate,isPresent);
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i =1;
                ps.setLong(i++,homeHelpId);
                ps.setLong(i++,shiftId);
                ps.setDate(i++,new java.sql.Date(startDate.getTime()) ) ;
                ps.setDate(i++,new java.sql.Date(endDate.getTime()) ) ;
            }
        }, new ResultSetExtractor<Map<String, Map<String,Integer>>>() {
            @Override
            public Map<String, Map<String,Integer>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String,Integer> presentMap = null;
                while (rs.next()){
                   if(result.get(rs.getString("SHIFT_TIME")) == null) {
                       presentMap = new HashMap<>();
                       presentMap.put(rs.getString("PRESENT"), rs.getInt("DAYS"));
                       result.put(rs.getString("SHIFT_TIME"), presentMap);
                   }else {
                       result.get(rs.getString("SHIFT_TIME")).put(rs.getString("PRESENT"), rs.getInt("DAYS"));
                   }

                }
                return result;
            }
        });
    }

    @Override
    public List<Pair<String, Integer>> getCurrentHelpStatusByUserId(String userId,Date date) {
        String sql = "SELECT  STATUS ,COUNT(*) COUNTS FROM ATTENDANCE INNER JOIN HOME_HELP  HH ON  HOME_HELP_ID= HH.ID WHERE HOME_ID = ? AND DATE = ? GROUP BY STATUS;";
        List<Pair<String,Integer>> result = new ArrayList<>();
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i =1;
                ps.setString(i++,userId);
                ps.setDate(i++,new java.sql.Date(date.getTime()));

            }
        }, new ResultSetExtractor<List<Pair<String, Integer>>>() {
            @Override
            public List<Pair<String, Integer>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    result.add(Pair.of(rs.getString("STATUS"),rs.getInt("COUNTS")));
                }
                return result;
            }
        });
    }
}
