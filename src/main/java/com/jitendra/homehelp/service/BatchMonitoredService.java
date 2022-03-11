package com.jitendra.homehelp.service;

import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public interface BatchMonitoredService {

    public BatchMonitored save(BatchMonitored batchMonitored);

    public BatchMonitored getByTypeAndDate(BatchEvent batchEvent, Date date, Status status);

    public BatchMonitored getByTypeAndDateBySuccess(BatchEvent batchEvent, Date date);

    public BatchMonitored getByTypeAndDateByFailed(BatchEvent batchEvent, Date date);

    public Page<BatchMonitored> get(Pageable pageable);

    public Page<BatchMonitored> getByDate(Date date , Pageable pageable);

    public Page<BatchMonitored> getByDate(Date start ,Date end, Pageable pageable);
}
