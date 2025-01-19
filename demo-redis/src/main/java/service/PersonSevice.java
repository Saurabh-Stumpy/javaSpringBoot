package service;


import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PersonRepository;

@Service
public class PersonSevice {

    @Autowired
    PersonRepository personRepository;

    public void create(Person person){
        personRepository.set(person);
    }
}
