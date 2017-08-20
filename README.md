# RoboHoover

Code which implements the Robot Hoover test specified [here](https://github.com/lampkicking/java-backend-test/blob/master/README.md)

The program requires that you have a mongo db running on the default port.

### Installing The Database
If you are using a mac you can install it using:
`brew install mongodb`

For other systems check [here](http://docs.mongodb.org/manual/installation/)

Once installed you can launch mongo from the command line, with the command: `mongod`

### Running The Code

clone the repo and issue the following command from the root folder of the application: `./gradlew bootRun`

you should now be able to reach the POST endpoint: `localhost:8080/cleanRoom`

Use a client like [PostMan](https://www.getpostman.com/) to POST your input json payload, and see the response json

To check the database audit you need to open a terminal window and start the mongo client with the command: `mongo`

navigate to the database with the command: `use YotiHooverDb`

Then show all the records that have been saved with the command: `db.roombaAudit.find()`
