package com.jitendra.homehelp.endpoint;

import io.swagger.annotations.Api;
import org.js.autenticationclient.auth.CommonAuthentication;
import org.js.autenticationclient.auth.impl.CommonAuthenticationImpl;
import org.js.autenticationclient.bean.Authentication;
import org.js.autenticationclient.registration.Registration;
import org.js.autenticationclient.registration.impl.RegistrationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/help/home/",produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "Register New Homes ",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeEndpoint {

    Registration registration = new RegistrationImpl();

    CommonAuthentication commonAuthentication = new CommonAuthenticationImpl();

    @PostMapping(path = "register",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registerYourself(@RequestBody Authentication authentication) {
        try{
            Authentication authentication1 =   registration.registerYouMember(authentication);
            return new ResponseEntity<>(authentication1,HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "login",produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> login(@RequestParam(name = "username" ,defaultValue = "jitendra.sagoriya") String userName, @RequestParam(name = "password",defaultValue = "J1tendra") String password) {
        try{
            String token =  commonAuthentication.getToken(userName,password);
            return new ResponseEntity<String>(token,HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
