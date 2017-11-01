package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class ObjectNodeTest {

    public static void main(String[] args) throws IOException {

        File fileTxt = new File("/Users/yl/source/tmproject/activitispringmvc/src/main/java/test/json.json");

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = (ObjectNode)objectMapper.readTree(fileTxt);

        objectNode.findValue("excutionListeners");

        String x="";


    }
}
