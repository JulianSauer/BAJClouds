package digitalOcean;

import abstractCloud.CloudProvider;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;

public class DigitalOcean extends CloudProvider {

    public DigitalOcean(String user, String password) {
        super(user, password);
    }

    @Override
    public ComputeServiceContext getComputeServiceContext() {
        return initComputeServiceContext("digitalocean2");
    }

    @Override
    public void createNode() {
        Template template = cloudInterface.templateBuilder()
                .osFamily(OsFamily.UBUNTU)
                .smallest()
                .build();
        createNode(template);
    }

}
