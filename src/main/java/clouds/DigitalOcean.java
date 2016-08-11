package clouds;

import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;

public class DigitalOcean extends CloudProvider {

    public DigitalOcean() {
        super("doUser", "doPassword", "digitalocean2");
    }

    @Override
    public Template getTemplate() {
        return cloudInterface.templateBuilder()
                .smallest()
                .osFamily(OsFamily.UBUNTU)
                .options(cloudInterface.templateOptions()
                        .runScript(initScript)
                )
                .build();
    }

}
