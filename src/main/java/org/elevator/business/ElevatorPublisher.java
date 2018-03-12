package org.elevator.business;

import org.elevator.common.Constants;
import org.elevator.common.FloorCall;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class ElevatorPublisher {

    private final Object MEX = new Object();
    private List<ElevatorSubscriber> elevatorSubscribers = new ArrayList<ElevatorSubscriber>();
    private Queue<FloorCall> pendingFloorCalls = new ArrayDeque<FloorCall>();

    public void register(List<ElevatorSubscriber> elevatorSubs) {
        for (ElevatorSubscriber elevatorSubscriber : elevatorSubs)
            this.elevatorSubscribers.add(elevatorSubscriber);
    }

    public void up(int floorNo) {
        if (floorNo > 0 && floorNo <= Constants.MAX_FLOORS) {
            FloorCall floorCall = new FloorCall(floorNo, true);
            synchronized (MEX) {
                if (!pendingFloorCalls.contains(floorCall)) pendingFloorCalls.add(floorCall);
            }
        } else throw new InvalidParameterException("Invalid floor: " + floorNo);
    }

    public void down(int floorNo) {
        if (floorNo > 0 && floorNo <= Constants.MAX_FLOORS) {
            FloorCall floorCall = new FloorCall(floorNo, false);
            synchronized (MEX) {
                if (!pendingFloorCalls.contains(floorCall)) pendingFloorCalls.add(floorCall);
            }
        } else throw new InvalidParameterException("Floor Number provided: " + floorNo + "is invalid");
    }

    public void stop(int elevatorId) {
        for (ElevatorSubscriber elevatorSubscriber : elevatorSubscribers) {
            if (elevatorId == elevatorSubscriber.getElevatorId()) {
                elevatorSubscriber.terminate();
                break;
            }
        }
    }

    public void gotoFloor(int floorNo, int elevatorId) {
        for (ElevatorSubscriber elevatorSubscriber : elevatorSubscribers) {
            if (elevatorId == elevatorSubscriber.getElevatorId()) {
                if (elevatorSubscriber.isUp() && elevatorSubscriber.getCurrentFloor().getFloorNo() < floorNo) {
                    FloorCall floorCall = new FloorCall(floorNo, true);
                    synchronized (MEX) {
                        if (!pendingFloorCalls.contains(floorCall)) pendingFloorCalls.add(floorCall);
                    }
                } else if (!elevatorSubscriber.isUp() && elevatorSubscriber.getCurrentFloor().getFloorNo() > floorNo) {
                    FloorCall floorCall = new FloorCall(floorNo, false);
                    synchronized (MEX) {
                        if (!pendingFloorCalls.contains(floorCall)) pendingFloorCalls.add(floorCall);
                    }
                } else throw new InvalidParameterException("Floor cannot be selected: " + floorNo);
                break;
            }
        }
    }

    public synchronized FloorCall getUpdate() {
        synchronized (MEX) {
            if (!pendingFloorCalls.isEmpty())
                return pendingFloorCalls.remove();
        }
        return null;
    }

    public FloorCall status(int elevatorId) {
        for (ElevatorSubscriber elevatorSubscriber : elevatorSubscribers) {
            if (elevatorId == elevatorSubscriber.getElevatorId())
                return elevatorSubscriber.getCurrentFloor();
        }

        throw new InvalidParameterException("Invalid elevator id: " + elevatorId);
    }
}
