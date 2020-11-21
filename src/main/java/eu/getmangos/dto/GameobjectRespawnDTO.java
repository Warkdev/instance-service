package eu.getmangos.dto;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GameobjectRespawnDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "GUID of the gameobject.")
    private Integer guid;

    @Schema(description = "Instance ID of the instance the gameobject is respawning into.")
    private Integer instance;

    @Schema(description = "Time till the gameobject should respawn.")
    private Long respawntime;
}