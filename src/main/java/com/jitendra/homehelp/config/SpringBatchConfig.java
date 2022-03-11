package com.jitendra.homehelp.config;

import com.jitendra.homehelp.batch.listener.CustomChunkListener;
import com.jitendra.homehelp.batch.listener.JobResultListener;
import com.jitendra.homehelp.batch.listener.StepItemWriteListener;
import com.jitendra.homehelp.batch.listener.StepSkipListener;
import com.jitendra.homehelp.batch.reader.HomeHelpRowMapper;
import com.jitendra.homehelp.batch.tasklet.BatchStartTasklet;
import com.jitendra.homehelp.batch.writer.HomeHelpItemPreparedStatementSetter;
import com.jitendra.homehelp.dao.HomeHelpDao;
import com.jitendra.homehelp.dto.HomeHelpDto;
import com.jitendra.homehelp.utils.Utils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private  StepItemWriteListener stepItemWriteListener;

    @Autowired
    private CustomChunkListener customChunkListener;

    @Autowired
    private JobResultListener jobResultListener;

    @Autowired
    private HomeHelpDao homeHelpDao;

    @Autowired
    private BatchStartTasklet batchStartTasklet;

    public static final String QUERY_INSERT_ATTENDANCE = "INSERT INTO ATTENDANCE (DATE ,HOME_HELP_ID ,PRESENT ,SHIFT_ID ) VALUES (?,?,?,?);";

    public static final String QUERY_SELECT_HOME_HELP = "SELECT HP.ID HP_ID, SH.ID SHIFT_ID,  FROM HOME_HELP HP JOIN SHIFT SH ON HP.ID = SH.HOMEHELP_ID;";

    @Autowired
    private HomeHelpRowMapper homeHelpRowMapper;

    @Autowired
    private HomeHelpItemPreparedStatementSetter homeHelpItemPreparedStatementSetter;

    @Value("D:\\homehelpmanagement\\homehelpmanagement\\src\\main\\resources\\skip\\skip.txt")
    private String filePath;

    File file = new File("D:\\homehelpmanagement\\homehelpmanagement\\src\\main\\resources\\skip\\SKIP-"+ Utils.getTodayAsStringByGivenFormat("dd-MM-yyyy")+".txt");


    @Bean
    public ItemReader<HomeHelpDto> itemReader() {
        return new JdbcCursorItemReaderBuilder<HomeHelpDto>()
                .name("itemReader")
                .sql(QUERY_SELECT_HOME_HELP)
                .dataSource(dataSource)
                .rowMapper(homeHelpRowMapper ).build();
    }

    @Bean
    public ItemProcessor<HomeHelpDto,HomeHelpDto> itemProcessor() {
        return  new ItemProcessor<HomeHelpDto, HomeHelpDto>() {
            @Override
            public HomeHelpDto process(HomeHelpDto homeHelpDto) throws Exception {
                return homeHelpDto;
            }
        };
    }

    @Bean
    public ItemWriter<HomeHelpDto> itemWriter() {
        return  new JdbcBatchItemWriterBuilder<HomeHelpDto>()
                .sql(QUERY_INSERT_ATTENDANCE)
                .dataSource(dataSource)
                .itemPreparedStatementSetter(homeHelpItemPreparedStatementSetter)
                .build();
    }

    @Bean
    protected Step attendanceTasklet() {
        return stepBuilderFactory
                .get("attendanceBatchStartTasklet")
                .tasklet(batchStartTasklet)
                .build();
    }

    @Bean
    @JobScope
    public SkipListener skipListener() throws IOException {
        return new StepSkipListener(file,homeHelpDao);
    }

    @Bean
    public Step nextDayStep() throws IOException {
        return stepBuilderFactory
                .get("nextDayStep")
                .<HomeHelpDto,HomeHelpDto>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(Integer.MAX_VALUE)
                .listener(skipListener())
                .listener(customChunkListener)
                .listener(stepItemWriteListener)
                .build();
    }

    @Bean
    public Job nextDayJob() throws IOException {
        return jobBuilderFactory
                .get("nextDayAttendanceJob")
                .start(attendanceTasklet())
                .next(nextDayStep())
                .listener(jobResultListener)
                .build();
    }
}
