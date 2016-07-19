package gce;

import abstractCloud.CloudProvider;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.logging.log4j.config.Log4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;

public class GoogleComputeEngine extends CloudProvider {

    public GoogleComputeEngine(String user, String password) {
        super(user, password);
    }

    @Override
    public ComputeServiceContext getComputeServiceContext() {
        if (context == null)
            context = ContextBuilder.newBuilder("google-compute-engine")
                    .credentials(user, password)
                    .modules(ImmutableSet.<Module>of(
                            new SshjSshClientModule(),
                            new Log4JLoggingModule()))
                    .buildView(ComputeServiceContext.class);

        cloudInterface = context.getComputeService();
        return context;
    }

    @Override
    public void createNode() {
        Template template = cloudInterface.templateBuilder()
                .osFamily(OsFamily.UBUNTU)
                .build();
        createNode(template);
    }
}
