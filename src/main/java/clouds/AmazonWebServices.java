package clouds;

import main.Accounts;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.domain.InstanceType;

public class AmazonWebServices extends CloudProvider {

    public AmazonWebServices(Accounts accounts) {
        super(accounts.getValue("awsUser"),
                accounts.getValue("awsPassword"));
    }

    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("aws-ec2");
    }

    public void createNode() {
        Template template = cloudInterface.templateBuilder()
                .hardwareId(InstanceType.T2_MICRO)
                .options(cloudInterface.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript("mkdir /home/logs" +
                                "&& apt-get update" +
                                "&& apt-get install maven git openjdk-7-jdk -y > /home/logs/install.txt" +
                                "&& git clone https://github.com/ewolff/user-registration.git /home/app/ > /home/logs/git.txt" +
                                "&& cd /home/app/user-registration-application/ > /home/logs/cd.txt" +
                                "&& mvn spring-boot:run > /home/logs/mvn.txt")
                )
                .build();
        createNode(template);
    }

}
