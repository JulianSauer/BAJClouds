package profitbricks;

import abstractCloud.CloudProvider;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.Template;

public class ProfitBricks extends CloudProvider {

    public ProfitBricks(String user, String password) {
        super(user, password);
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
                        .overrideLoginPassword("123456789"))
                .build();

        createNode(template);
    }

}
