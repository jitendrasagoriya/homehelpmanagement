package com.jitendra.homehelp.dao;

import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import com.jitendra.homehelp.repository.BatchMonitoredRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface BatchMonitoredDao extends BaseDao<BatchMonitoredRepository> {

    public BatchMonitored save(BatchMonitored batchMonitored);

    public List<BatchMonitored> getByTypeAndDate(BatchEvent batchEvent, Date date, Status status);

    public Page<BatchMonitored> get(Pageable pageable);

    public Page<BatchMonitored> getByDate(Date date , Pageable pageable);

    public Page<BatchMonitored> getByDate(Date start ,Date end, Pageable pageable);
}
