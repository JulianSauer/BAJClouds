package clouds;

import org.jclouds.compute.domain.Template;

public class ProfitBricks extends CloudProvider {

    public ProfitBricks() {
        super("profitBricksUser", "profitBricksPassword", "profitbricks");
    }

    @Override
    protected Template getTemplate() {
        return connection.templateBuilder()
                .imageNameMatches("Ubuntu-14.04")
                .options(connection.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript(initScript)
                )
                .build();
    }

}
