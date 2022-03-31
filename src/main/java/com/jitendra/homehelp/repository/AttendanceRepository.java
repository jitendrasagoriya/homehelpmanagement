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

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET PRESENT  = :present, IN_TIME = :inTime, STATUS = :status WHERE HOME_HELP_ID  = :id AND DATE  = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int markCompleteAttendance( @Param(value = "id") Long id,
                                @Param(value = "date") Date date,
                                @Param(value = "shiftId") Long shiftId,
                                @Param(value = "present") Boolean present,
                                @Param(value = "inTime") Time inTime,
                                @Param(value = "status") String status);


    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET IN_TIME = :inTime WHERE HOME_HELP_ID = :id AND DATE = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int markInTime( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "shiftId") Long shiftId,  @Param(value = "inTime") Time inTime);

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET OUT_TIME = :outTime WHERE HOME_HELP_ID = :id AND DATE = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int markOutTime( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "shiftId") Long shiftId,  @Param(value = "outTime") Time inTime);

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET STATUS = :status WHERE HOME_HELP_ID = :id AND DATE = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int updateStatus( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "shiftId") Long shiftId,  @Param(value = "status") String status);

    @Modifying
    @Query(value = "UPDATE ATTENDANCE  SET STATUS = :status,OUT_TIME = :outTime  WHERE HOME_HELP_ID = :id AND DATE = :date AND SHIFT_ID = :shiftId",nativeQuery = true)
    int completeShift( @Param(value = "id") Long id, @Param(value = "date") Date date, @Param(value = "shiftId") Long shiftId,  @Param(value = "status") String status,
                       @Param(value = "outTime") Time inTime);


    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId" )
    List<Attendance> getByHomeHelpId(@Param(value = "homeHelpId") Long id);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId" )
    Page<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id, Pageable pageable);

    @Query("FROM Attendance a WHERE a.date = :date" )
    List<Attendance> getByDate(  @Param(value = "date") Date date);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId AND a.date BETWEEN :start AND :end" )
    List<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id,  @Param(value = "start") Date start,  @Param(value = "end") Date end);

    @Query("FROM Attendance a WHERE a.homeHelpId = :homeHelpId AND a.date BETWEEN :start AND :end" )
    Page<Attendance> getByHomeHelpId( @Param(value = "homeHelpId") Long id,  @Param(value = "start") Date start,  @Param(value = "end") Date end,  Pageable pageable);


}
