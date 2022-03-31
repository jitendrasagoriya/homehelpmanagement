package com.jitendra.homehelp.dao.impl;

import com.jitendra.homehelp.dao.BatchMonitoredDao;
import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import com.jitendra.homehelp.repository.BatchMonitoredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BatchMonitoredJdbcDao implements BatchMonitoredDao {

    @Autowired
    private BatchMonitoredRepository batchMonitoredReposiotry;

    @Override
    public BatchMonitoredRepository getRepository() {
        return batchMonitoredReposiotry;
    }

    @Override
    public BatchMonitored save(BatchMonitored batchMonitored) {
        return batchMonitoredReposiotry.save(batchMonitored);
    }

    @Override
    public List<BatchMonitored> getByTypeAndDate(BatchEvent batchEvent, Date date, Status status) {
        return batchMonitoredReposiotry.findByBatchEventAndDate(batchEvent,date,status);
    }

    @Override
    public Page<BatchMonitored> get(Pageable pageable) {
        return batchMonitoredReposiotry.findAll(pageable);
    }

    @Override
    public Page<BatchMonitored> getByDate(Date date, Pageable pageable) {
        return batchMonitoredReposiotry.findByDate(date,pageable);
    }

    @Override
    public Page<BatchMonitored> getByDate(Date start, Date end, Pageable pageable) {
        return batchMonitoredReposiotry.findByToAndFromDate(start,end,pageable);
    }
}
