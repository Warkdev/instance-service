package eu.getmangos.dto;

import lombok.Setter;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;

@Setter @Getter
public class InstanceDTO {

  @Schema(description = "The instance ID. This number is unique to every instance.")
  private Integer id;

  @Schema(description = "The map ID the instance is in. (See Map.dbc)")
  private Integer map;
  /**
   * The time when the instance will be reset, in Unix time.
   */
  @Schema(description = "The time when the instance will be reset, in Unix time.")
  private Long resettime;
  /**
   * Specific data belonging to the individual instance.
   */
  @Schema(description = "Specific data belonging to the individual instance. Loaded in ScriptDev3. Each AI Event Type is represented by a state.")
  private List<EncounterState> state;
}