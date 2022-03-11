package com.jitendra.homehelp.batch.reader;

import com.jitendra.homehelp.HomeHelpManagementApplication;
import com.jitendra.homehelp.dto.HomeHelpDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomeHelpRowMapper implements RowMapper<HomeHelpDto> {

    private static final Logger logger =   LogManager.getLogger(HomeHelpRowMapper.class);

    @Override
    public HomeHelpDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (logger.isDebugEnabled())
            logger.debug(String.format("Reading data from DB and Result is {}", rs));
        HomeHelpDto homeHelpDto = new HomeHelpDto();
        homeHelpDto.setId(rs.getLong("HP_ID"));
        homeHelpDto.setShiftId(rs.getLong("SHIFT_ID"));
        if (logger.isDebugEnabled())
            logger.debug(String.format("Result homeHelpDto is {}", homeHelpDto));
        return homeHelpDto;
    }
}
