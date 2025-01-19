package controller;


import dto.PersonCreateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PersonSevice;

@RestController
@RequestMapping("/person")
public class PersonController {

    /*
    String Operations
     */

    @Autowired
    PersonSevice personSevice;

    @PostMapping("/")
    public void createPerson(@RequestBody @Valid PersonCreateRequest request){
        personSevice.create(request.to());
    }




    /*
     List Operations
     */

    /*
     Hash Operations
     */
}
