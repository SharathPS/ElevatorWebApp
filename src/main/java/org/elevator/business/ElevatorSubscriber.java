package org.elevator.business;

import org.elevator.common.FloorCall;
import org.slf4j.LoggerFactory;
import org.test.elevator.api.ElevatorCallback;
import org.test.elevator.api.ElevatorFacade;
import org.test.elevator.api.ElevatorFactory;


public class ElevatorSubscriber extends Thread {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ElevatorSubscriber.class);
    private int elevatorId;
    private boolean up = true;
    private int currentFloor = 1;
    private ElevatorFacade elevatorFacade;
    private boolean running = true;
    private FloorCall nextFloorCall;
    private ElevatorPublisher elevatorPublisher;

    public ElevatorSubscriber(final int elevatorId, final ElevatorPublisher elevatorPublisher) {
        this.elevatorId = elevatorId;
        this.elevatorPublisher = elevatorPublisher;
        this.elevatorFacade = ElevatorFactory.getElevatorFacade(elevatorId, new ElevatorCallback() {
            public void elevatorArrived(int floor) {
                currentFloor = floor;
                if (nextFloorCall != null && floor == nextFloorCall.getFloorNo()) {
                    logger.info("Eelevator"+ elevatorId + ": has arrived :" + currentFloor);
                    elevatorFacade.lockBreaks();
                }
            }
        });

        Thread tElevator = new Thread(this);
        tElevator.start();
    }

    public void terminate() {
        running = false;
        elevatorFacade.lockBreaks();
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public FloorCall getCurrentFloor() {
        return new FloorCall(currentFloor, up);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void run() {
        while (running) {
            nextFloorCall = elevatorPublisher.getUpdate();
            while (nextFloorCall != null) {
                if (this.currentFloor != nextFloorCall.getFloorNo()) {
                    this.elevatorFacade.unlockBreaks();
                    while (this.currentFloor > nextFloorCall.getFloorNo()) {
                        this.elevatorFacade.moveDownOneFloor();
                    }

                    while (this.currentFloor < nextFloorCall.getFloorNo()) {
                        this.elevatorFacade.moveUpOneFloor();
                    }
                } else {
                    if (nextFloorCall.isUp()) elevatorPublisher.up(nextFloorCall.getFloorNo());
                    else elevatorPublisher.down(nextFloorCall.getFloorNo());
                }

                nextFloorCall = elevatorPublisher.getUpdate();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
