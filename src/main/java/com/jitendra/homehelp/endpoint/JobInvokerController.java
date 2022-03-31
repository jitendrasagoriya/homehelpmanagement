package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.constants.AppConstants;
import io.swagger.annotations.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/help/batch/")
@Api(value = "Run Batch manually",authorizations = {
        @Authorization(HttpHeaders.AUTHORIZATION)
} )
@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-LOG-HEADER", value = "Add Your Access Token", paramType = "header",dataType = "string",required = true,dataTypeClass = String.class)
})
public class JobInvokerController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "nextDayJob")
    private Job processJob;

    @Autowired
    @Qualifier(value = "cleanupJob")
    private Job cleanupJob;

    @GetMapping("attendanceJob/")
    @ApiOperation(value = "Run Batch manually"
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public String attendanceJob(@ApiIgnore @RequestAttribute(name = "username") String userName ) throws Exception {


        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString(AppConstants.START_BY_KEY,userName)
                .addString(AppConstants.JOB_NAME,AppConstants.ATTENDANCE_JOB_NAME)
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        return "Batch job has been invoked";
    }

    @GetMapping("adhocAttendanceJob/")
    @ApiOperation(value = "Run Batch manually"
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public String adhoc(@ApiIgnore @RequestAttribute(name = "username") String userName, @RequestParam(name = "date" ,defaultValue = "2022-03-25") String date ) throws Exception {

        Date requestedDate = DateUtils.parseDate(date,"yyyy-MM-dd");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString(AppConstants.START_BY_KEY,userName)
                .addString(AppConstants.JOB_NAME,AppConstants.ADHOC_JOB_NAME)
                .addDate(AppConstants.JOB_DATE,requestedDate)
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        return "Batch job has been invoked";
    }

    @GetMapping("cleanupJob/")
    @ApiOperation(value = "Run Batch manually"
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public String cleanupJob(@ApiIgnore @RequestAttribute(name = "username") String userName) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString(AppConstants.START_BY_KEY,userName)
                .addString(AppConstants.JOB_NAME,AppConstants.CLEANUP_JOB_NAME)
                .toJobParameters();
        jobLauncher.run(cleanupJob, jobParameters);
        return "Batch job has been invoked";
    }
}
