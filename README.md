# Camunda BPMN Parser
This project can fetch BPMN from remote server and find the possible path between given start and end node ID.
The remote server is set in the application.yaml and it can update.

The project can run in CONSOLE and WEB mode.
To run the project in WEB mode please specify --web as an argument.

To run the project you need to build it first.

```mvn clean package```

You can run the project with the instruction below.

```
java -jar web/target/demo.jar from to
```

Please specify the ID of the nodes you find to find a path.

In this project I take advantage of class **Graph** to convert the given bpmn to a traversable data structure.

For processing the fetched XML and finding the path I fired Event to make processes loose couple from each other.
