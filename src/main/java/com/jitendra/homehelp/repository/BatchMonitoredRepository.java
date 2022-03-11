package com.jitendra.homehelp.repository;

import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BatchMonitoredRepository extends PagingAndSortingRepository<BatchMonitored,Long> {

    @Query("FROM BatchMonitored B WHERE B.date =:date")
    Page<BatchMonitored> findByDate(Date date, Pageable pageable);

    @Query("FROM BatchMonitored B WHERE B.date BETWEEN :start AND :end")
    Page<BatchMonitored> findByToAndFromDate(Date start, Date end, Pageable pageable);

    @Query("FROM BatchMonitored B WHERE B.status =:status AND B.batchEvent=:batchEvent AND B.date =:date")
    BatchMonitored findByBatchEventAndDate(BatchEvent batchEvent, Date date, Status status);
}
