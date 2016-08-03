package clouds;

import org.jclouds.compute.domain.Template;

public class ProfitBricks extends CloudProvider {

    public ProfitBricks() {
        super("profitBricksUser", "profitBricksPassword", "profitbricks");
    }

    @Override
    public Template getTemplate() {
        return cloudInterface.templateBuilder()
                .imageNameMatches("Ubuntu")
                .options(cloudInterface.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript(initScript.replace("openjdk-7-jdk", "openjdk-8-jdk"))
                )
                .build();
    }

}
