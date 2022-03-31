package com.jitendra.homehelp.config;

import com.jitendra.homehelp.batch.listener.*;
import com.jitendra.homehelp.batch.processor.AttendanceItemProcessor;
import com.jitendra.homehelp.batch.reader.CleanupDataReader;
import com.jitendra.homehelp.batch.reader.HomeHelpRowMapper;
import com.jitendra.homehelp.batch.tasklet.BatchStartTasklet;
import com.jitendra.homehelp.batch.tasklet.CleanupAttendanceTasklet;
import com.jitendra.homehelp.batch.writer.CleanupWriter;
import com.jitendra.homehelp.batch.writer.HomeHelpItemPreparedStatementSetter;
import com.jitendra.homehelp.dao.HomeHelpDao;
import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.dto.HomeHelpDto;
import com.jitendra.homehelp.utils.Utils;
import lombok.SneakyThrows;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private  StepItemWriteListener stepItemWriteListener;

    @Autowired
    private CustomChunkListener customChunkListener;

    @Autowired
    private JobResultListener jobResultListener;

    @Autowired
    private HomeHelpDao homeHelpDao;

    @Autowired
    private CleanupDataReader cleanupDataReader;

    @Autowired
    private CleanupWriter cleanupWriter;

    @Autowired
    private BatchStartTasklet batchStartTasklet;

    @Autowired
    private AttendanceItemProcessor attendanceItemProcessor;

    public static final String QUERY_INSERT_ATTENDANCE = "INSERT INTO ATTENDANCE (DATE ,HOME_HELP_ID ,PRESENT ,SHIFT_ID,STATUS ) VALUES (?,?,?,?,?);";

    public static final String QUERY_SELECT_HOME_HELP = "SELECT HP.ID HP_ID, SH.ID SHIFT_ID,  FROM HOME_HELP HP JOIN SHIFT SH ON HP.ID = SH.HOMEHELP_ID;";

    @Autowired
    private HomeHelpRowMapper homeHelpRowMapper;

    @Autowired
    private HomeHelpItemPreparedStatementSetter homeHelpItemPreparedStatementSetter;

    @Value("D:\\homehelpmanagement\\homehelpmanagement\\src\\main\\resources\\skip\\")
    private String filePath;

    File file = new File("D:\\homehelpmanagement\\homehelpmanagement\\src\\main\\resources\\skip\\SKIP-"+ Utils.getTodayAsStringByGivenFormat("dd-MM-yyyy")+".txt");

    public BatchStartTasklet getBatchStartTasklet() {
        return batchStartTasklet;
    }

    @Bean
    public ExecutionContextSerializer customSerializer() {
        return new Jackson2ExecutionContextStringSerializer( Date.class.getName());
    }


    @Bean
    public ItemReader<HomeHelpDto> itemReader() {
        return new JdbcCursorItemReaderBuilder<HomeHelpDto>()
                .name("itemReader")
                .sql(QUERY_SELECT_HOME_HELP)
                .dataSource(dataSource)
                .rowMapper(homeHelpRowMapper).build();
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
    @JobScope
    protected Step attendanceBatchStartTasklet() {
        return stepBuilderFactory
                .get("attendanceBatchStartTasklet")
                .tasklet(batchStartTasklet)
                .build();
    }

    @Bean
    @JobScope
    protected Step attendanceCleanUpTasklet() {
        return stepBuilderFactory
                .get("attendanceBatchStartTasklet")
                .tasklet(cleanupAttendanceTasklet)
                .build();
    }

    @Bean
    @JobScope
    protected Step cleanupBatchStartTasklet() {
        return stepBuilderFactory
                .get("cleanupBatchStartTasklet")
                .tasklet(batchStartTasklet)
                .build();
    }


    @Bean
    @JobScope
    public AttendanceStepSkipListener attendanceStepSkipListener() throws IOException {
        return new AttendanceStepSkipListener(new File(filePath+"ATTENDANCE-SKIP-"+ Utils.getTodayAsStringByGivenFormat("dd-MM-yyyy")+".txt"),homeHelpDao);
    }

    @Bean
    @JobScope
    public CleanUpStepSkipListener cleanUpStepSkipListener() throws IOException {
        return new CleanUpStepSkipListener(new File(filePath+"CLEANUP-SKIP-"+ Utils.getTodayAsStringByGivenFormat("dd-MM-yyyy")+".txt"));
    }

    @Autowired
    private CleanupAttendanceTasklet cleanupAttendanceTasklet;

    @Bean
    public Step nextDayStep() throws IOException {
        return stepBuilderFactory
                .get("nextDayStep")
                .<HomeHelpDto,HomeHelpDto>chunk(10)
                .reader(itemReader())
                .processor(attendanceItemProcessor)
                .writer(itemWriter())
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(Integer.MAX_VALUE)
                .listener(attendanceStepSkipListener())
                .listener(customChunkListener)
                .listener(stepItemWriteListener)
                .build();
    }

    @Bean
    @Qualifier(value = "nextDayJob")
    public Job nextDayJob() throws IOException {
        return jobBuilderFactory
                .get("nextDayAttendanceJob")
                .start(attendanceBatchStartTasklet())
                .next(attendanceCleanUpTasklet())
                .next(nextDayStep())
                .listener(jobResultListener)
                .build();
    }



    @Bean
    public Step cleanUpStep() throws IOException {
        return stepBuilderFactory
                .get("cleanUpStep")
                .<AttendanceDto,AttendanceDto>chunk(10)
                .reader(cleanupDataReader)
                .writer(cleanupWriter)
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(Integer.MAX_VALUE)
                .listener(cleanUpStepSkipListener())
                .listener(customChunkListener)
                .listener(stepItemWriteListener)
                .build();
    }

    @Bean
    @Qualifier(value = "cleanupJob")
    public Job cleanupJob() throws IOException {
        return jobBuilderFactory
                .get("cleanupJob")
                .start(cleanupBatchStartTasklet())
                .next(cleanUpStep())
                .listener(jobResultListener)
                .build();
    }

    @SneakyThrows
    @Override
    public JobRepository getJobRepository()  {
        Jackson2ExecutionContextStringSerializer jackson2ExecutionContextStringSerializer = new Jackson2ExecutionContextStringSerializer("java.sql.Date","java.sql.Time");
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix("BATCH_");
        factory.setMaxVarCharLength(1200);
        factory.setSerializer(jackson2ExecutionContextStringSerializer);
        factory.afterPropertiesSet();
        return factory.getObject();
    }



    @SneakyThrows
    @Override
    public JobLauncher getJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }



    @SneakyThrows
    @Override
    public JobExplorer getJobExplorer()   {
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        Jackson2ExecutionContextStringSerializer jackson2ExecutionContextStringSerializer = new Jackson2ExecutionContextStringSerializer("java.sql.Date","java.sql.Time");
        factory.setDataSource(dataSource);
        factory.setSerializer(jackson2ExecutionContextStringSerializer);
        factory.setJdbcOperations(jdbcTemplate);
        factory.setTablePrefix("BATCH_");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

     @Autowired
    public JdbcTemplate jdbcTemplate;
}
