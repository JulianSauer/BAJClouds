package main;

import clouds.*;
import org.jclouds.compute.domain.ComputeMetadata;

import java.util.Set;

public class Main {

    private static Accounts accounts;

    public static void main(String[] args) {

        accounts = new Accounts();

        aws();
        digitalOcean();
        gce();
        profitBricks();

    }

    private static void aws() {
        CloudProvider aws = new AmazonWebServices(accounts);
        doTestOperations(aws);
        aws.getComputeServiceContext().close();
    }

    private static void digitalOcean() {
        CloudProvider digitalOcean = new DigitalOcean(accounts);
        doTestOperations(digitalOcean);
        digitalOcean.getComputeServiceContext().close();
    }

    private static void gce() {
        CloudProvider gce = new GoogleComputeEngine(accounts);
        doTestOperations(gce);
        gce.getComputeServiceContext().close();
    }

    private static void profitBricks() {
        CloudProvider profitBricks = new ProfitBricks(accounts);
        doTestOperations(profitBricks);
        profitBricks.getComputeServiceContext().close();
    }

    /**
     * Lists all nodes of a cloud.
     *
     * @param cloud Cloud Provider
     */
    private static void listNodes(CloudProvider cloud) {
        Set<? extends ComputeMetadata> nodes = cloud.getCloudInterface().listNodes();
        System.out.println(cloud.getClass().getName() + ":");
        if (nodes.size() == 0)
            System.out.println("    No nodes found.\n");

        for (ComputeMetadata node : nodes) {
            System.out.println("    Name        " + node.getName());
            System.out.println("    Type:       " + node.getType());
            System.out.println("    Location    " + node.getLocation() + "\n");
        }
    }

    /**
     * Creates and removes nodes for testing
     *
     * @param cloud Cloud Provider
     */
    private static void doTestOperations(CloudProvider cloud) {
        System.out.print("Removing all nodes...");
        cloud.destroyAllNodes();
        System.out.println("done.\n");
        listNodes(cloud);

        listNodes(cloud);
        System.out.print("Adding a node...");
        cloud.createNode();
        System.out.println("done.\n");
        listNodes(cloud);


    }

}
