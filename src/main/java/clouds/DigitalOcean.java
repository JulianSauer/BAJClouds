package clouds;

import org.jclouds.compute.domain.Template;

public class DigitalOcean extends CloudProvider {

    public DigitalOcean() {
        super("doUser", "doPassword", "digitalocean2");
    }

    @Override
    public Template getTemplate() {
        return cloudInterface.templateBuilder()
                .smallest()
                .options(cloudInterface.templateOptions()
                        .runScript(initScript.replace("openjdk-7-jdk", "openjdk-8-jdk"))
                )
                .build();
    }

}
