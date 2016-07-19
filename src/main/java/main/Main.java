package main;

import abstractCloud.CloudProvider;
import aws.AmazonWebServices;
import gce.GoogleComputeEngine;
import org.jclouds.compute.domain.ComputeMetadata;
import profitbricks.ProfitBricks;

import java.util.Set;

public class Main {

    private static Accounts accounts;

    public static void main(String[] args) {

        accounts = new Accounts();

    }

    private static void aws() {
        String awsUser = accounts.getValue("awsUser");
        String awsPassword = accounts.getValue("awsPassword");
        CloudProvider aws = new AmazonWebServices(awsUser, awsPassword);

        listNodes(aws);
        aws.createNode();
        listNodes(aws);
        aws.destroyAllNodes();

        aws.getComputeServiceContext().close();
    }

    private static void profitBricks() {
        String profitBricksUser = accounts.getValue("profitBricksUser");
        String profitBricksPassword = accounts.getValue("profitBricksPassword");
        CloudProvider profitBricks = new ProfitBricks(profitBricksUser, profitBricksPassword);

        listNodes(profitBricks);
        profitBricks.createNode();
        listNodes(profitBricks);
        profitBricks.destroyAllNodes();

        profitBricks.getComputeServiceContext().close();
    }

    private static void gce() {
        String gceUser = accounts.getValue("gceUser");
        String gcePassword = accounts.getValue("gcePassword");
        CloudProvider gce = new GoogleComputeEngine(gceUser, gcePassword);

        listNodes(gce);
        gce.createNode();
        listNodes(gce);

        gce.getComputeServiceContext().close();
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
            System.out.println("    No nodes found.");

        for (ComputeMetadata node : nodes) {
            System.out.println("    Name        " + node.getName());
            System.out.println("    Type:       " + node.getType());
            System.out.println("    Location    " + node.getLocation());
            System.out.println();
        }
    }

}
