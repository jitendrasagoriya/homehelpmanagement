package com.jitendra.homehelp.endpoint;

import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.entity.Shift;
import com.jitendra.homehelp.service.HomeHelpService;
import com.jitendra.homehelp.service.ShiftService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/api/homehelp/shift/",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "Get Shift Information of Home Help",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE,authorizations = {
        @Authorization(HttpHeaders.AUTHORIZATION)
} )
@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-LOG-HEADER", value = "Add Your Access Token", paramType = "header",dataType = "string",required = true,dataTypeClass = String.class)
})
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private HomeHelpService homeHelpService;

    @GetMapping(path = {"{id}/","{id}"})
    @ApiOperation(value = "Get All Shift For Home Help"
            ,consumes = MediaType.APPLICATION_JSON_VALUE
            ,produces = MediaType.APPLICATION_JSON_VALUE
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> getAllShiftById(@PathVariable(name = "id") Long id) {
        try {
            return new ResponseEntity<>(shiftService.getByHomeHelp(id) ,HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = {"{id}/"},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get All Shift For Home Help"
            ,consumes = MediaType.APPLICATION_JSON_VALUE
            ,produces = MediaType.APPLICATION_JSON_VALUE
            ,nickname = "GetAllShiftById",authorizations = {
            @Authorization(HttpHeaders.AUTHORIZATION)
    } )
    public ResponseEntity<?> addNewShift(@PathVariable(name = "id") Long id, @RequestBody Shift shift ,@ApiIgnore @RequestAttribute(name = "userId") String userId)  {
        try {
            HomeHelp homeHelp = homeHelpService.getById(id);
            if(homeHelp==null)
                return new ResponseEntity<>("Invalid Request! Home Help in not valid." ,HttpStatus.BAD_REQUEST);
            if(!StringUtils.equals(homeHelp.getHomeId(),userId))
                return new ResponseEntity<>("Invalid Request! Home Help not belong to logged In Home." ,HttpStatus.BAD_REQUEST);

            shift.setHomeHelp(homeHelp);
            return new ResponseEntity<>(shiftService.save(shift) ,HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
