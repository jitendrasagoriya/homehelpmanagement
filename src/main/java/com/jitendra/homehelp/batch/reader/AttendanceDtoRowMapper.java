package com.jitendra.homehelp.batch.reader;

import com.jitendra.homehelp.dto.AttendanceDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDtoRowMapper implements RowMapper<AttendanceDto> {

    private static final Logger logger =   LogManager.getLogger(AttendanceDtoRowMapper.class);

    @Override
    public AttendanceDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setId(rs.getLong("ID"));
        attendanceDto.setDate(rs.getDate("DATE"));
        attendanceDto.setPresent(rs.getString("PRESENT"));
        attendanceDto.setName(rs.getString("NAME"));
        attendanceDto.setShiftTime(rs.getTime("SHIFT_TIME"));
        logger.info(attendanceDto.toPipeSeparatedString());
        return attendanceDto;
    }
}
