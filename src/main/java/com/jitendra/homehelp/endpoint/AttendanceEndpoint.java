package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.service.AttendanceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping(path = {"{id}/shift/{shift}/present"})
    @ApiOperation(value = "Marks To Attendance As Present By Id And Shift Id.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "MarkTodayPresent",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> markTodayPresentWithShift(@PathVariable(name = "id") Long id , @PathVariable(name = "shift") Long shiftId) {
        try {
            return new ResponseEntity<>( attendanceService.markPresentForTodayByShift(id, shiftId),HttpStatus.OK);

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
