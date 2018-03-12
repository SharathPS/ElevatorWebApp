package org.elevator.business;

import java.util.ArrayList;
import java.util.List;

import org.elevator.common.Constants;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;


@Component
public class ElevatorMain {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ElevatorMain.class);
    public final static ElevatorPublisher elevatorPublisher = createElevatorPub();

    public static ElevatorPublisher createElevatorPub() {
        ElevatorPublisher elevatorPublisher = new ElevatorPublisher();
        List<ElevatorSubscriber> eSub= new ArrayList<ElevatorSubscriber>();

        for (int i = 0; i < Constants.MAX_ELEVATORS; i++) {
            ElevatorSubscriber elevatorSubscriber = new ElevatorSubscriber(i + 1, elevatorPublisher);
            eSub.add(elevatorSubscriber);
            elevatorPublisher.register(eSub);
            elevatorSubscriber.start();
        }

		logger.info("Elevators started");
        return elevatorPublisher;
    }
}
