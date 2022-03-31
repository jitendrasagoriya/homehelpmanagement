package com.jitendra.homehelp.batch.reader;

import com.jitendra.homehelp.batch.listener.AbstractStepSkipListener;
import com.jitendra.homehelp.dto.AttendanceDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@StepScope
public class CleanupDataReader extends JdbcCursorItemReader<AttendanceDto>  implements ItemReader<AttendanceDto> {

    private static final Logger logger =   LogManager.getLogger(CleanupDataReader.class);

    private ExecutionContext jobContext;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        jobContext = jobExecution.getExecutionContext();
    }

    public CleanupDataReader(@Autowired DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT AAT.ID,AAT.DATE,AAT.PRESENT,AAT.NAME,S.SHIFT_TIME  FROM ( SELECT A.*,HH.NAME FROM ATTENDANCE A INNER JOIN HOME_HELP HH ON A.HOME_HELP_ID = HH.ID  " +
                "WHERE  DATE < ? ) AAT INNER JOIN SHIFT S ON S.ID = AAT.SHIFT_ID;");
        setFetchSize(100);
        setPreparedStatementSetter(new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setDate(1,new Date(System.currentTimeMillis()));
            }
        });
        setRowMapper(new AttendanceDtoRowMapper());
    }
}
