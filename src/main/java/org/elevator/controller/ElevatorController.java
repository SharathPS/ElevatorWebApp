package org.elevator.controller;

import org.elevator.common.FloorCall;
import org.elevator.service.ElevatorOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/elevator")
public class ElevatorController {
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ElevatorController.class);
	private static final String VIEW_INDEX = "index";
	
    @Autowired
    private ElevatorOptions elevatorOptions;

    @RequestMapping(value = "/{elevatorId}/goto/{floorNo}", method = RequestMethod.PUT)
    public void gotoFloor(@PathVariable int elevatorId, @PathVariable int floorNo) {
        logger.info("FloorNo:" + floorNo + " ElevatorId " + elevatorId);
        elevatorOptions.gotoFloor(floorNo, elevatorId);
    }

    @RequestMapping(value = "/{elevatorId}/status", method = RequestMethod.GET)
    public FloorCall status(@PathVariable int elevatorId) {
        return elevatorOptions.status(elevatorId);
    }

    @RequestMapping(value = "/{elevatorId}/stop", method = RequestMethod.PUT)
    
    public void stop(@PathVariable int elevatorId) {
    	
    	elevatorOptions.stop(elevatorId);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ModelAndView Welcome(Model model) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName(VIEW_INDEX);
		return mav;
    }
    
}
