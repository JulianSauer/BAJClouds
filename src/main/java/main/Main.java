package main;

import abstractCloud.CloudProvider;
import aws.AmazonWebServices;
import digitalOcean.DigitalOcean;
import gce.GoogleComputeEngine;
import org.jclouds.compute.domain.ComputeMetadata;
import profitbricks.ProfitBricks;

import java.util.Set;

public class Main {

    private static Accounts accounts;

    public static void main(String[] args) {

        accounts = new Accounts();

        digitalOcean();

    }

    private static void aws() {
        String awsUser = accounts.getValue("awsUser");
        String awsPassword = accounts.getValue("awsPassword");
        CloudProvider aws = new AmazonWebServices(awsUser, awsPassword);

        doTestOperations(aws);

        aws.getComputeServiceContext().close();
    }

    private static void profitBricks() {
        String profitBricksUser = accounts.getValue("profitBricksUser");
        String profitBricksPassword = accounts.getValue("profitBricksPassword");
        CloudProvider profitBricks = new ProfitBricks(profitBricksUser, profitBricksPassword);

        doTestOperations(profitBricks);

        profitBricks.getComputeServiceContext().close();
    }

    private static void gce() {
        String gceUser = accounts.getValue("gceUser");
        String gcePassword = accounts.getValue("gcePassword");
        CloudProvider gce = new GoogleComputeEngine(gceUser, gcePassword);

        doTestOperations(gce);

        gce.getComputeServiceContext().close();
    }

    private static void digitalOcean() {
        String doUser = accounts.getValue("doUser");
        String doPassword = accounts.getValue("doPassword");
        CloudProvider digitalOcean = new DigitalOcean(doUser, doPassword);

        doTestOperations(digitalOcean);

        digitalOcean.getComputeServiceContext().close();
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
        listNodes(cloud);
        System.out.print("Adding a node...");
        cloud.createNode();
        System.out.println("done.\n");
        listNodes(cloud);

        System.out.print("Removing all nodes...");
        cloud.destroyAllNodes();
        System.out.println("done.\n");
        listNodes(cloud);
    }

}
