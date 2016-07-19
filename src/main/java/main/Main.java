package main;

import abstractCloud.CloudProvider;
import aws.AmazonWebServices;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import gce.GoogleComputeEngine;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.logging.log4j.config.Log4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;
import profitbricks.ProfitBricks;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

    }

    private static void aws() {
        String awsUser = "AKIAINIS46C4ASJUWM5A";
        String awsPassword = "ISVi0464ryQXCkeCubqLf7RGzQ023a0zK663/ERy";
        CloudProvider aws = new AmazonWebServices(awsUser, awsPassword);

        listNodes(aws);
        aws.createNode();
        listNodes(aws);
        aws.destroyAllNodes();

        aws.getComputeServiceContext().close();
    }

    private static void profitBricks() {
        String profitBricksUser = "julian.sauer@adesso.de";
        String profitBricksPassword = "ProfitBricksPassword";
        CloudProvider profitBricks = new ProfitBricks(profitBricksUser, profitBricksPassword);

        listNodes(profitBricks);
        profitBricks.createNode();
        listNodes(profitBricks);
        profitBricks.destroyAllNodes();

        profitBricks.getComputeServiceContext().close();
    }

    private static void gce() {
        String gceUser = "";
        String gcePassword = "";
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
