package com.jitendra.homehelp.batch.writer;

import com.jitendra.homehelp.dto.HomeHelpDto;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


@Component
public class HomeHelpItemPreparedStatementSetter implements ItemPreparedStatementSetter<HomeHelpDto> {
    @Override
    public void setValues(HomeHelpDto homeHelpDto, PreparedStatement preparedStatement) throws SQLException {
        int i = 1;
        preparedStatement.setDate( i++, homeHelpDto.getDate());
        preparedStatement.setLong( i++,homeHelpDto.getId());
        preparedStatement.setBoolean( i++,Boolean.FALSE);
        preparedStatement.setLong( i++,homeHelpDto.getShiftId());
        preparedStatement.setString(i++,homeHelpDto.getStatus());
    }
}
