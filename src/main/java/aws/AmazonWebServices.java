package aws;

import abstractCloud.CloudProvider;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.domain.InstanceType;

public class AmazonWebServices extends CloudProvider {

    public AmazonWebServices(String user, String password) {
        super(user, password);
    }

    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("aws-ec2");
    }

    public void createNode() {
        Template template = cloudInterface.templateBuilder().hardwareId(InstanceType.T2_MICRO).osFamily(OsFamily.AMZN_LINUX).build();
        template.getOptions().as(AWSEC2TemplateOptions.class).securityGroupIds("default");
        createNode(template);
    }

}
