package org.elevator.service.impl;

import org.elevator.business.ElevatorMain;
import org.elevator.common.FloorCall;
import org.elevator.service.ElevatorOptions;
import org.springframework.stereotype.Component;


@Component
public class ElevatorOptionsImpl implements ElevatorOptions {

    public void gotoFloor(int floorNo, int elevatorId) {
        ElevatorMain.elevatorPublisher.gotoFloor(floorNo, elevatorId);
    }

    public void stop(int elevatorId){
        ElevatorMain.elevatorPublisher.stop(elevatorId);
    }

    public FloorCall status(int elevatorId) {
        return ElevatorMain.elevatorPublisher.status(elevatorId);
    }
}
