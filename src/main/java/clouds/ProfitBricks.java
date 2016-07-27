package clouds;

import main.Accounts;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.Template;

public class ProfitBricks extends CloudProvider {

    public ProfitBricks(Accounts accounts) {
        super(accounts.getValue("profitBricksUser"), accounts.getValue("profitBricksPassword"));
    }

    @Override
    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("profitbricks");
    }

    @Override
    public void createNode() {
        Template template = cloudInterface.templateBuilder()
                .imageNameMatches("Ubuntu-15.10-server-2016-07-01")
                .options(cloudInterface.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript("mkdir /home/logs" +
                                "&& apt-get update" +
                                "&& apt-get install maven -y" +
                                "&& apt-get install openjdk-7-jdk -y > /home/logs/install.txt" +
                                "&& git clone https://github.com/ewolff/user-registration.git /home/app/ > /home/logs/git.txt" +
                                "&& cd /home/app/user-registration-application/ > /home/logs/cd.txt" +
                                "&& mvn spring-boot:run > /home/logs/mvn.txt")
                )
                .build();

        createNode(template);
    }

}
