package api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pojo.recruit;

import java.util.ArrayList;
import java.util.List;

@RestController
public class apiRequests {

    private List<recruit> recruits = new ArrayList<>();

    private void addTestRecruit(){

        if(recruits.isEmpty()){
            recruits.add(new recruit("Ajith", "Keerikkattil", "ajith@yopmail.com", "Computer Science"));
            recruits.add(new recruit("Kari", "Dominguez", "kari@yopmail.com", "Information System"));
        }
    }

    /* ADD A RECRUIT */
    @RequestMapping(value="/recruit/", method = RequestMethod.POST)
    public HttpStatus addRecruit(@RequestBody recruit r){
        recruits.add(r);
        ResponseEntity<String> entity = new ResponseEntity<>("RECRUIT ADDED", HttpStatus.CREATED);

        HttpStatus statusCode = entity.getStatusCode();
        String body = entity.getBody();

        return statusCode;
    }

    /* GET ALL RECRUITS */
    @RequestMapping(value="/recruits/")
    public ResponseEntity getRecruits(){
        addTestRecruit();
        return new ResponseEntity<>(recruits, HttpStatus.OK);
    }

    /* GET SPECIFIED RECRUITS BY FIRSTNAME*/
    @RequestMapping(value = "/recruit/{firstName}")
    public ResponseEntity getRecruit(@PathVariable("firstName") String firstName) {

        for(recruit r: recruits){
            if(r.getFirstName().equals(firstName)){
                return new ResponseEntity<>(r, HttpStatus.OK);
            }
        }

        return null;
    }

    /* EMAIL THE RECRUIT */
    @RequestMapping(value = "/recruit/notify/{firstName}")
    public String emailRecruit(@PathVariable("firstName") String firstName){

        for(recruit r: recruits){
            if(r.getFirstName().equals(firstName)){
                String email = r.getEmail();
                //todo:: code to send email
            }
        }
        return null;
    }
}
