package main;

import abstractCloud.CloudProvider;
import aws.AmazonWebServices;
import org.jclouds.compute.domain.ComputeMetadata;
import profitbricks.ProfitBricks;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        String awsUser = "AKIAINIS46C4ASJUWM5A";
        String awsPassword = "ISVi0464ryQXCkeCubqLf7RGzQ023a0zK663/ERy";
        CloudProvider aws = new AmazonWebServices(awsUser, awsPassword);

        String profitBricksUser = "julian.sauer@adesso.de";
        String profitBricksPassword = "ProfitBricksPassword";
        CloudProvider profitBricks = new ProfitBricks(profitBricksUser, profitBricksPassword);

        listNodes(aws);
        aws.createNode();
        listNodes(aws);
        aws.destroyAllNodes();

        listNodes(profitBricks);
        profitBricks.createNode();
        listNodes(profitBricks);
        profitBricks.destroyAllNodes();

        aws.getComputeServiceContext().close();
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
            System.out.println("    No nodes found.");

        for (ComputeMetadata node : nodes) {
            System.out.println("    Name        " + node.getName());
            System.out.println("    Type:       " + node.getType());
            System.out.println("    Location    " + node.getLocation());
            System.out.println();
        }
    }

}
