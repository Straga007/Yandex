package day.of.Programmer.service;


import day.of.Programmer.storage.impliments.Storage;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
    private final Storage storage;

    @Autowired
    public Service(Storage storage) {
        this.storage = storage;
    }

}
