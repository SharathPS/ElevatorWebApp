# Elevator WebApp

Run elevator application on tomcat :
``
mvn clean install -Dmaven.tomcat.port=8292 tomcat:run-war
``

## Elevator status 
1. ``<applicationhosted>/login``: Login page to select the elevator for status check
   Login with credentials as elevatorad and password as root123
2. ``<applicationhosted>/elevator/status``: Request redirected from login page to display elevator status, when clicked on the button elevator status is displayed (GET)

## Commuter in the level reaches the eleveator with options either up or down
1. ``<applicationhosted>/level/up/<floorNumber>``: To commute upwards (PUT)
2. ``<applicationhosted>/level/down/<floorNumber>``: To commute downwards (PUT)

## Elevator Options
1. ``<applicationhosted>/elevator/<elevatorId>/goto/<floorNumber>``: Commuter to select the floorNumber (PUT)
2. ``<applicationhosted>/elevator/<elevatorId>/stop``: To stop the elevator identified by the ``elevatorId`` (PUT)

### <applicationhosted>: http://localhost:8292/ElevatorWebApp
