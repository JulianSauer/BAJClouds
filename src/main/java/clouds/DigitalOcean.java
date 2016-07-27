package clouds;

import main.Accounts;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;

public class DigitalOcean extends CloudProvider {

    public DigitalOcean(Accounts accounts) {
        super(accounts.getValue("doUser"), accounts.getValue("doPassword"));
    }

    @Override
    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("digitalocean2");
    }

    @Override
    public void createNode() {
        Template template = cloudInterface.templateBuilder()
                .smallest()
                .options(cloudInterface.templateOptions()
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
