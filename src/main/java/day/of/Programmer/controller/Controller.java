package day.of.Programmer.controller;

import day.of.Programmer.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RequestMapping("")
public class Controller {
    private final Service service;


    @Autowired
    public Controller(Service service) {
        this.service = service;
    }
}
