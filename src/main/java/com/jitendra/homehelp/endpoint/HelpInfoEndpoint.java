package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.enums.HelpType;
import com.jitendra.homehelp.exceptions.DuplicateHomeHelpException;
import com.jitendra.homehelp.service.HomeHelpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/help/",produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "Get Information about you home help",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,authorizations = {
        @Authorization(HttpHeaders.AUTHORIZATION)
} )
@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-LOG-HEADER", value = "Add Your Access Token", paramType = "header",dataType = "string",required = true,dataTypeClass = String.class)
})
public class HelpInfoEndpoint {

    @Autowired
    private HomeHelpService homeHelpService;

    @GetMapping(path = {"name/{name}/","name/{name}"})
    @ApiOperation(value = "Get Home Help By Name",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,nickname = "GetHeleInfoByName",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getHelpInfoByName(@PathVariable(name = "name")String name,@ApiIgnore @RequestAttribute(name = "userId") String userId) {
       try {
            return new ResponseEntity<>(homeHelpService.getByName(userId,name),HttpStatus.OK);
       }catch (Exception e) {
           return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping(path = {"/"})
    @ApiOperation(value = "Get All Home Help",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "getHomehelp",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getHomehelp(@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(homeHelpService.getHomeHelp(userId),HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = {"id/{id}/","id/{id}"})
    @ApiOperation(value = "Get Home Help By ID",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "GetHelpInfoById",
            authorizations = { @Authorization(HttpHeaders.AUTHORIZATION)  })
    public ResponseEntity<?> getHelpInfoById(@PathVariable(name = "id")Long id, @ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(homeHelpService.getById(id),HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Save New Home Help Here",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "GetHelpInfoById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    })
    public ResponseEntity<?> save(@RequestBody HomeHelp homeHelp, @ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            homeHelp.setHomeId(userId);
            homeHelp.setIsActive(Boolean.TRUE);
            if(homeHelp.getShifts()!=null)
                homeHelp.getShifts().forEach(shift -> shift.setHomeHelp(homeHelp));
            HomeHelp homeHelp1 =  homeHelpService.save(homeHelp);
            return new ResponseEntity<>(homeHelp1,HttpStatus.OK);
        } catch (DuplicateHomeHelpException duplicateHomeHelpException) {
            return new ResponseEntity<>(duplicateHomeHelpException.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Change You Existing Home Help",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "GetHelpInfoById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    })
    public ResponseEntity<?> update(@RequestBody HomeHelp homeHelp,@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            homeHelp.setHomeId(userId);
            homeHelp.getShifts().forEach(shift -> shift.setHomeHelp(homeHelp));
            HomeHelp homeHelp1 =  homeHelpService.save(homeHelp);
            return new ResponseEntity<>(homeHelp1,HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = {"{id}/","{id}"})
    @ApiOperation(value = "Good Bye! Delete Your Home Help",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "DeleteHomeHelp",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    })
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id,@ApiIgnore @RequestAttribute(name = "userId") String userId) {
        try {
            return new ResponseEntity<>(homeHelpService.delete(id),HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = {"json/","json"})
    @ApiOperation(value = "Generate Example Home Help Json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            nickname = "DeleteHomeHelp",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    })
    public ResponseEntity<?> getJson( ) {
        try {
            Shift shift = Shift.builder()
                    .time(new Time(System.nanoTime()))
                    .id(1L)
                    .build();

            HomeHelp homeHelp = HomeHelp.builder()
                    .id(1L)
                    .helpType(HelpType.NA)
                    .name("Jitendra")
                    .phone("9769160936")
                    .salary(new Double(200))
                    .shifts(Arrays.asList(shift))
                    .build();
            return new ResponseEntity<>(homeHelp,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
