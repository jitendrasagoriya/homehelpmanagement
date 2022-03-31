package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.entity.HomeHelp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.dao.DuplicateKeyException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CleanUpStepSkipListener extends AbstractStepSkipListener<AttendanceDto,AttendanceDto> {


    public CleanUpStepSkipListener(File file) throws IOException {
        super(file);
    }

    @Override
    public String getError(Throwable throwable) {
        return throwable.getMessage();
    }

    @Override
    public AttendanceDto getPipeEliminatedBean(AttendanceDto attendanceDto) {
        return attendanceDto;
    }

}
