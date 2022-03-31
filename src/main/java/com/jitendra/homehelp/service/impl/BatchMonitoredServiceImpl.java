package com.jitendra.homehelp.service.impl;

import com.jitendra.homehelp.dao.BatchMonitoredDao;
import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import com.jitendra.homehelp.service.BatchMonitoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BatchMonitoredServiceImpl implements BatchMonitoredService {

    @Autowired
    private BatchMonitoredDao batchMonitoredDao;

    @Override
    public BatchMonitored save(BatchMonitored batchMonitored) {
        return batchMonitoredDao.save(batchMonitored);
    }

    @Override
    public List<BatchMonitored> getByTypeAndDate(BatchEvent batchEvent, Date date, Status status) {
        return batchMonitoredDao.getByTypeAndDate(batchEvent,date,status);
    }

    @Override
    public List<BatchMonitored> getByTypeAndDateBySuccess(BatchEvent batchEvent, Date date) {
        return batchMonitoredDao.getByTypeAndDate(batchEvent,date,Status.SUCCESS);
    }

    @Override
    public List<BatchMonitored> getByTypeAndDateByFailed(BatchEvent batchEvent, Date date) {
        return batchMonitoredDao.getByTypeAndDate(batchEvent,date,Status.FAILED);
    }

    @Override
    public Page<BatchMonitored> get(Pageable pageable) {
        return batchMonitoredDao.get(pageable);
    }

    @Override
    public Page<BatchMonitored> getByDate(Date date, Pageable pageable) {
        return batchMonitoredDao.getByDate(date,pageable);
    }

    @Override
    public Page<BatchMonitored> getByDate(Date start, Date end, Pageable pageable) {
        return batchMonitoredDao.getByDate(start,end,pageable);
    }
}
