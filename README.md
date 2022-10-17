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

for example 

```
java -jar web/target/demo.jar StartEvent_1 reviewInvoice
```
and the result would be:
```
StartEvent_1,assignApprover,approveInvoice,invoice_approved,reviewInvoice,
```

Please specify the ID of the nodes you find to find a path.

In this project I take advantage of class **Graph** to convert the given bpmn to a traversable data structure.

For processing the fetched XML and finding the path I fired Event to make processes loose couple from each other.

In order to ensure the project working well in prod I add 
Unit tests: to ensure the methods and classes works fine
Integration test: to test all parts with context 
Wiremock test: to test entire project and not mocking any thing