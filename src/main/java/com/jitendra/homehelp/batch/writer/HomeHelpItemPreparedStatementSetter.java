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
        preparedStatement.setDate( 1, Date.valueOf(LocalDate.now().plusDays(1l)));
        preparedStatement.setLong(2,homeHelpDto.getId());
        preparedStatement.setBoolean(3,Boolean.FALSE);
        preparedStatement.setLong(4,homeHelpDto.getShiftId());
    }
}
