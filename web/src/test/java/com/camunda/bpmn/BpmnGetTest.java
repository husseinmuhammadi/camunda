package com.camunda.bpmn;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BpmnGetTest {
    void loadDiagram() throws IOException {
        URL url = new URL("https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

//        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//        out.writeBytes("");
//        out.flush();
//        out.close();

        int status = connection.getResponseCode();
        System.out.println(status);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        System.out.println(content);


        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("invoice.json"))))) {
            writer.write(content.toString());
        }

    }
}
