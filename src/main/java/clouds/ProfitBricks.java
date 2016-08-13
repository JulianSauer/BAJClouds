package clouds;

import org.jclouds.compute.domain.Template;

public class ProfitBricks extends CloudProvider {

    public ProfitBricks() {
        super("profitBricksUser", "profitBricksPassword", "profitbricks");
    }

    @Override
    protected Template getTemplate() {
        return cloudInterface.templateBuilder()
                .imageNameMatches("Ubuntu-14.04")
                .options(cloudInterface.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript(initScript)
                )
                .build();
    }

}
