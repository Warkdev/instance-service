package eu.getmangos.rest;

import javax.ws.rs.ApplicationPath;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationPath("/instance")
@OpenAPIDefinition(
    tags = {
        @Tag(name = "instance", description="Operations about instances"),
        @Tag(name = "creature", description="Operations about creature respawns"),
        @Tag(name = "gameobject", description="Operations about gameobject respawns"),
        @Tag(name = "group", description="Operations about groups linked with this instance"),
        @Tag(name = "character", description="Operations about character linked with this instance")
    },
    externalDocs = @ExternalDocumentation(
        description = "Instructions on how to deploy this WebApp",
        url = "https://github.com/Warkdev/instance-service/blob/master/README.md"
    ),
    info = @Info(
            title = "Mangos Instance API",
            version = "1.0",
            description = "API allowing to interact with Mangos Instances",
            license = @License(
                    name = "Apache 2.0"
            )
    )
)
public class OpenAPIConfig {
}
