package org.elevator.controller;

import org.elevator.service.LevelOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/level")
public class LevelController {

    @Autowired
    private LevelOptions levelOptions;

    @RequestMapping(value = "/up/{floorNum}", method = RequestMethod.PUT)
    public void up(@PathVariable int floorNum) {
    	levelOptions.up(floorNum);
    }

    @RequestMapping(value = "/down/{floorNum}", method = RequestMethod.PUT)
    public void down(@PathVariable int floorNum) {
    	levelOptions.down(floorNum);
    }
}
