package eu.getmangos.dto;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreatureRespawnDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "GUID of the creature.")
    private Integer guid;

    @Schema(description = "Instance ID of the instance the creature is respawning into.")
    private Integer instance;

    @Schema(description = "Time till the creature should respawn.")
    private Long respawntime;
}