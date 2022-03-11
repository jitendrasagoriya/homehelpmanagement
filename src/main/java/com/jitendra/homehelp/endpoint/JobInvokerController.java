package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.constants.AppConstants;
import io.swagger.annotations.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
    JobLauncher jobLauncher;

    @Autowired
    Job processJob;

    @GetMapping("invokejob/")
    @ApiOperation(value = "Run Batch manually"
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public String handle(@ApiIgnore @RequestAttribute(name = "username") String userName) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString(AppConstants.START_BY_KEY,userName)
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        return "Batch job has been invoked";
    }
}
