package clouds;

import org.jclouds.compute.domain.Template;

public class GoogleComputeEngine extends CloudProvider {

    public GoogleComputeEngine() {
        super("gceUser", "gcePassword", "google-compute-engine");
    }

    @Override
    protected Template getTemplate() {
        return cloudInterface.templateBuilder()
                .imageNameMatches("ubuntu-1404")
                .options(cloudInterface.templateOptions()
                        .overrideLoginUser("root")
                        .overrideLoginPassword("123456789")
                        .runScript(initScript)
                )
                .build();
    }

}
