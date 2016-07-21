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
                .osFamily(OsFamily.UBUNTU)
                .smallest()
                .build();
        createNode(template);
    }

}
