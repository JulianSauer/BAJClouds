package clouds;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Template;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CloudProvider {

    protected String user;
    protected String password;

    protected ComputeServiceContext context;
    protected ComputeService cloudInterface;

    public CloudProvider(String user, String password) {
        this.user = user;
        this.password = password;
        getComputeServiceContext();
    }

    protected ComputeServiceContext initComputeServiceContext(String cloud) {
        if (context == null)
            context = ContextBuilder.newBuilder(cloud)
                    .credentials(user, password)
                    .buildView(ComputeServiceContext.class);

        cloudInterface = context.getComputeService();
        return context;
    }

    public abstract ComputeServiceContext getComputeServiceContext();

    public ComputeService getCloudInterface() {
        if (cloudInterface == null || context == null)
            getComputeServiceContext();
        return cloudInterface;
    }

    protected void createNode(Template template) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("ddMMyyyy-HHmmss");
        try {
            cloudInterface.createNodesInGroup("jclouds-" + format.format(date), 1, template);
        } catch (RunNodesException e) {
            e.printStackTrace();
        }
    }

    public abstract void createNode();

    public void destroyAllNodes() {
        for (ComputeMetadata node : cloudInterface.listNodes()) {
            cloudInterface.destroyNode(node.getId());
        }
    }

}
