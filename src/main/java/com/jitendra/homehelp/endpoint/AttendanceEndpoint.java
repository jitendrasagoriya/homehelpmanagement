package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.dto.AttendanceDto;
import com.jitendra.homehelp.dto.DashBoardDto;
import com.jitendra.homehelp.dto.MonthlyReport;
import com.jitendra.homehelp.service.AttendanceService;
import com.jitendra.homehelp.service.DashBoardService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/help/attendance/",produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "All Rest Endpoint For Home Help Attendance",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,authorizations = {
        @Authorization(HttpHeaders.AUTHORIZATION)
} )
@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-LOG-HEADER", value = "Add Your Access Token", paramType = "header",dataType = "string",required = true,dataTypeClass = String.class)
})
public class AttendanceEndpoint {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private DashBoardService dashBoardService;

    @PostMapping(path = {"{id}/present"})
    @ApiOperation(value = "Marks To Attendance As Present",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> markTodayPresent(@PathVariable(name = "id") Long id) {
        try {
            return new ResponseEntity<>(attendanceService.markPresentForToday(id),HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Get Today Attendance",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    @PostMapping(path = {"today/"})
    public ResponseEntity<?> getTodayAttendance(@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(attendanceService.getByHomeIdAndToDate(userId), HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get Monthly Attendance",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "getMonthlyAttendance",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    @GetMapping(path = {"monthly/"})
    public ResponseEntity<?> getMonthlyAttendance(@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(attendanceService.getByHomeIdAndThisMonth(userId), HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get Today DashBoard",
            nickname = "Get DashBoard",consumes = MediaType.TEXT_PLAIN_VALUE,authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    @GetMapping  (path = {"dashboard/"} )
    public ResponseEntity<?> dashBoard(@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            DashBoardDto dashBoardDto = new DashBoardDto();
            List<AttendanceDto> attendanceDtos = attendanceService.getByHomeIdAndToDate(userId);
            dashBoardDto.setAttendances(attendanceDtos);
            Map<String,Integer> helpCurrentStatus =  dashBoardService.getHelpsCurrentStatus(attendanceDtos);
            dashBoardDto.setMonthlyReport(dashBoardService.getMonthlyReport(attendanceDtos));
            dashBoardDto.setJsonMonthlyReport(MonthlyReport.build(dashBoardDto.getMonthlyReport()));
            dashBoardDto.setHelpsCurrentStatus(dashBoardService.getCurrentStatus(userId));
            return new ResponseEntity<>(dashBoardDto, HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = {"{id}/shift/{shift}/present"})
    @ApiOperation(value = "Marks To Attendance As Present By Id And Shift Id.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> markTodayPresentWithShift(@PathVariable(name = "id") Long id , @PathVariable(name = "shift") Long shiftId) {
        try {
            return new ResponseEntity<>( attendanceService.markTodayAttendance(id, shiftId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(path = {"{id}/shift/{shift}/complete"})
    @ApiOperation(value = "Complete Today Shift By Id And Shift Id.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "CompleteTodayShift",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> completeTodayShift(@PathVariable(name = "id") Long id , @PathVariable(name = "shift") Long shiftId) {
        try {
            return new ResponseEntity<>( attendanceService.completeTodayShift(id, shiftId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(path = {"{id}/absent"})
    @ApiOperation(value = "Marks To Attendance As Absent",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> markTodayAbsent(@PathVariable(name = "id") Long id) {
        try {
            return new ResponseEntity<>(attendanceService.markPresentForToday(id),HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(path = {"{id}/shift/{shift}/absent"})
    @ApiOperation(value = "Marks To Attendance As Absent  Id And Shift Id.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> markTodayAbsentWithShift(@PathVariable(name = "id") Long id , @PathVariable(name = "shift") Long shiftId) {
        try {
            return new ResponseEntity<>( attendanceService.markAbsentForTodayByShift(id, shiftId),HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }



    @GetMapping(path = {"{id}/"})
    @ApiOperation(value = "Get All Attendance By ID.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "GetByHomeHelpId",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getByHomeHelpId(@PathVariable(name = "id") Long id  ) {
        try {
            return new ResponseEntity<>( attendanceService.getByHomeHelpId(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping(path = {"page/{id}/"})
    @ApiOperation(value = "Get All Attendance By ID With Pagination",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getByHomeHelpId(@PathVariable(name = "id") Long id , Pageable pageable) {
        try {
            return new ResponseEntity<>( attendanceService.getByHomeHelpId(id,pageable),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
    @GetMapping(path = {"/bydates/{id}/"})
    @ApiOperation(value = "Get All Attendance By ID And Given Dates",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getByHomeHelpId(@PathVariable(name = "id") Long id , @RequestParam(name = "start") Date start , @RequestParam(name = "end") Date end) {
        try {
            return new ResponseEntity<>( attendanceService.getByHomeHelpId(id,start,end),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping(path = {"/bydates/page/{id}/"})
    @ApiOperation(value = "Get All Attendance By ID And Given Dates With Pagination",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getByHomeHelpId(@PathVariable(name = "id") Long id , Date start , Date end,Pageable pageable) {
        try {
            return new ResponseEntity<>( attendanceService.getByHomeHelpId(id,start,end),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
