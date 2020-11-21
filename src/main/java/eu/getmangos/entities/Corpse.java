package eu.getmangos.entities;

import javax.persistence.*;
import lombok.Data;

/**
 * Death System
 */
@Data
@Entity
@Table(name = "corpse")
@NamedQueries({
    @NamedQuery(name = "Corpse.findAll", query = "SELECT c FROM Corpse c"),
    @NamedQuery(name = "Corpse.findByGuid", query = "SELECT c FROM Corpse c WHERE c.guid = :guid"),
    @NamedQuery(name = "Corpse.findByPlayer", query = "SELECT c FROM Corpse c WHERE c.player = :player"),
    @NamedQuery(name = "Corpse.findByPositionX", query = "SELECT c FROM Corpse c WHERE c.positionX = :positionX"),
    @NamedQuery(name = "Corpse.findByPositionY", query = "SELECT c FROM Corpse c WHERE c.positionY = :positionY"),
    @NamedQuery(name = "Corpse.findByPositionZ", query = "SELECT c FROM Corpse c WHERE c.positionZ = :positionZ"),
    @NamedQuery(name = "Corpse.findByOrientation", query = "SELECT c FROM Corpse c WHERE c.orientation = :orientation"),
    @NamedQuery(name = "Corpse.findByMap", query = "SELECT c FROM Corpse c WHERE c.map = :map"),
    @NamedQuery(name = "Corpse.findByTime", query = "SELECT c FROM Corpse c WHERE c.time = :time"),
    @NamedQuery(name = "Corpse.findByCorpseType", query = "SELECT c FROM Corpse c WHERE c.corpseType = :corpseType"),
    @NamedQuery(name = "Corpse.findByInstance", query = "SELECT c FROM Corpse c WHERE c.instance = :instance")})
public class Corpse {

  /**
   * The character global unique identifier of the corpse.
   */
  @Id
  @Column(name = "guid", nullable = false)
  private Integer guid;
  /**
   * The character global unique identifier. (See characters.guid).
   */
  @Column(name = "player", nullable = false)
  private Integer player;
  /**
   * The x position of the character's corpse location.
   */
  @Column(name = "position_x", nullable = false)
  private Float positionX;
  /**
   * The y position of the character's corpse location.
   */
  @Column(name = "position_y", nullable = false)
  private Float positionY;
  /**
   * The z position of the character's corpse location.
   */
  @Column(name = "position_z", nullable = false)
  private Float positionZ;
  /**
   * The orientation of the corpse. (North = 0.0, South = 3.14159)
   */
  @Column(name = "orientation", nullable = false)
  private Float orientation;
  /**
   * The map ID the corpse is in. (See maps.dbc)
   */
  @Column(name = "map", nullable = false)
  private Integer map;
  /**
   * Time of death.
   */
  @Column(name = "time", nullable = false)
  private Long time;
  /**
   * The Display Type of the corpse.
   */
  @Column(name = "corpse_type", nullable = false)
  private Byte corpseType;
  /**
   * The instance ID that the corpse is in.
   */
  @Column(name = "instance", nullable = false)
  private Integer instance;
}