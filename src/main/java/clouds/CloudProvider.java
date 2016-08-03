package clouds;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import main.Accounts;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public abstract class CloudProvider {

    protected String user;
    protected String password;

    protected ComputeServiceContext context;
    protected ComputeService cloudInterface;

    protected final String initScript = "mkdir /home/logs" +
            "&& apt-get update" +
            "&& apt-get install maven git openjdk-7-jdk -y > /home/logs/install.txt" +
            "&& git clone https://github.com/ewolff/user-registration.git /home/app/ > /home/logs/git.txt" +
            "&& mvn -f /home/app/user-registration-application/pom.xml spring-boot:run > /home/logs/mvn.txt";

    public CloudProvider(String userKey, String passwordKey, String provider) {
        Accounts accounts = new Accounts();
        this.user = accounts.getValue(userKey);
        this.password = accounts.getValue(passwordKey);
        getComputeServiceContext(provider);
    }

    /**
     * Establishes a connection to a cloud.
     *
     * @param provider Name of the cloud provider.
     */
    protected void getComputeServiceContext(String provider) {
        if (context == null) {
            context = ContextBuilder.newBuilder(provider)
                    .credentials(user, password)
                    .modules(ImmutableSet.<Module>of(
                            new SshjSshClientModule(),
                            new SLF4JLoggingModule()))
                    .buildView(ComputeServiceContext.class);

            cloudInterface = context.getComputeService();
        }
    }

    public void closeContext() {
        context.close();
    }

    /**
     * Deploys a new instance.
     */
    protected void createNode() {
        Template template = getTemplate();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("ddMMyyyy-HHmmss");
        try {
            cloudInterface.createNodesInGroup("jclouds-" + format.format(date), 1, template);
        } catch (RunNodesException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a template for deploying a new instance.
     *
     * @return Instance template
     */
    protected abstract Template getTemplate();

    public void destroyAllNodes() {
        for (ComputeMetadata node : cloudInterface.listNodes()) {
            cloudInterface.destroyNode(node.getId());
        }
    }

    /**
     * Lists all nodes of this cloud.
     */
    public void listNodes() {
        Set<? extends ComputeMetadata> nodes = cloudInterface.listNodes();
        System.out.println(this.getClass().getName() + ":");
        if (nodes.size() == 0)
            System.out.println("    No nodes found.\n");

        for (ComputeMetadata node : nodes) {
            System.out.println("    Name        " + node.getName());
            System.out.println("    Type:       " + node.getType());
            System.out.println("    Location    " + node.getLocation() + "\n");
        }
    }

    /**
     * Removes all nodes and deploys a new one with an example project.
     */
    public void doTestOperations() {
        System.out.print("Removing all nodes...");
        destroyAllNodes();
        System.out.println("done.\n");
        listNodes();

        listNodes();
        System.out.print("Adding a node...");
        createNode();
        System.out.println("done.\n");
        listNodes();
    }

}
