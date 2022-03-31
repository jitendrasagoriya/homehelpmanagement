package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.dao.HomeHelpDao;
import com.jitendra.homehelp.dto.HomeHelpDto;
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


public class AttendanceStepSkipListener extends AbstractStepSkipListener<HomeHelpDto, HomeHelp> {

    private static final Logger logger =   LogManager.getLogger(AttendanceStepSkipListener.class);

    private HomeHelpDao homeHelpDao;

    public AttendanceStepSkipListener(File file,HomeHelpDao homeHelpDao) throws IOException {
       super(file);
        this.homeHelpDao =homeHelpDao;
    }


    @Override
    public String getError(Throwable throwable) {
        if(throwable instanceof DuplicateKeyException) {
            return  "Duplicate Key Exception";
        }
        return throwable.getMessage();
    }

    @Override
    public HomeHelp getPipeEliminatedBean(HomeHelpDto homeHelpDto) {
        return homeHelpDao.getById(homeHelpDto.getId());
    }


}
