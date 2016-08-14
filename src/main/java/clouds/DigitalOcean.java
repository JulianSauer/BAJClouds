package clouds;

import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;

public class DigitalOcean extends CloudProvider {

    public DigitalOcean() {
        super("doUser", "doPassword", "digitalocean2");
    }

    @Override
    protected Template getTemplate() {
        return connection.templateBuilder()
                .smallest()
                .osFamily(OsFamily.UBUNTU)
                .options(connection.templateOptions()
                        .runScript(initScript)
                )
                .build();
    }

}
