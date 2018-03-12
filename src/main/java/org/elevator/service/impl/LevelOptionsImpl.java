package org.elevator.service.impl;

import org.elevator.business.ElevatorMain;
import org.elevator.service.LevelOptions;
import org.springframework.stereotype.Component;


@Component
public class LevelOptionsImpl implements LevelOptions {
    public void up(int floorNum) {
        ElevatorMain.elevatorPublisher.up(floorNum);
    }

    public void down(int floorNum) {
        ElevatorMain.elevatorPublisher.down(floorNum);
    }
}
