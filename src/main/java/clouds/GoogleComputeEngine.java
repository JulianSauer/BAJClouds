package clouds;

import main.Accounts;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.Template;

public class GoogleComputeEngine extends CloudProvider {

    public GoogleComputeEngine(Accounts accounts) {
        super(accounts.getValue("gceUser"), accounts.getValue("gcePassword"));
    }

    @Override
    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("google-compute-engine");
    }

    @Override
    public void createNode() {
        Template template = cloudInterface.templateBuilder()
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
