package eu.getmangos.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CharacterInstanceDTO {

    @Schema(description = "The GUID (Global Unique Identifier) of the character.")
    private Integer guid;

    @Schema(description = "The instance ID. (See instance.id).")
    private Integer instance;

    @Schema(description = "Boolean true or false controlling if the player has been bound to the instance.")
    private boolean permanent;
}