package com.jitendra.homehelp.repository;

import com.jitendra.homehelp.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance,Long> {

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET PRESENT  = :present WHERE HOME_HELP_ID  = :id AND DATE  = :date" ,nativeQuery = true)
    int markAttendance( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "present") Boolean present);

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET PRESENT  = :present WHERE HOME_HELP_ID  = :id AND DATE  = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int markAttendance( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "shiftId") Long shiftId, @Param(value = "present") Boolean present);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId" )
    List<Attendance> getByHomeHelpId(@Param(value = "homeHelpId") Long id);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId" )
    Page<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id, Pageable pageable);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId AND a.date BETWEEN :start AND :end" )
    List<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id,  @Param(value = "start") Date start,  @Param(value = "end") Date end);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId AND a.date BETWEEN :start AND :end" )
    Page<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id,  @Param(value = "start") Date start,  @Param(value = "end") Date end,  Pageable pageable);
}
