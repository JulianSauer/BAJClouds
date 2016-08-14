package main;

import clouds.*;

public class Main {

    public static void main(String[] args) {
        //doTestOperationsFor(new AmazonWebServices());
        //doTestOperationsFor(new DigitalOcean());
        //doTestOperationsFor(new GoogleComputeEngine());
        doTestOperationsFor(new ProfitBricks());
    }

    private static void doTestOperationsFor(CloudProvider cloud) {
        cloud.doTestOperations();
        cloud.closeContext();
    }

}
