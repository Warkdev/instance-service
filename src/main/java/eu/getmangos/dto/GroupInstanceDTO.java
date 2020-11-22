package eu.getmangos.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GroupInstanceDTO {
    /**
     * The Guid of the group leader. (See characters.guid)
     */
    @Schema(description = "The Guid of the group leader. (See characters.guid)")
    private Integer leaderguid;

    /**
     * ID of the Instance session the group has enterd.
     */
    @Schema(description = "ID of the Instance session the group has entered (See instance.id)")
    private Integer instance;

    /**
     * Boolean flag if the group is bound to the Instance or not
     */
    @Schema(description = "Boolean flag if the group is bound to the Instance or not.")
    private boolean permanent;
}