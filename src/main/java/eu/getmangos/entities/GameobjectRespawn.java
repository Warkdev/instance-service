package eu.getmangos.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * Grid Loading System
 */
@Data
@Entity
@Table(name = "gameobject_respawn")
@NamedQueries({
    @NamedQuery(name = "GameobjectRespawn.findAll", query = "SELECT g FROM GameobjectRespawn g"),
    @NamedQuery(name = "GameobjectRespawn.findById", query = "SELECT g FROM GameobjectRespawn g WHERE g.gameobjectRespawnPK.guid = :guid AND g.gameobjectRespawnPK.instance = :instance"),
    @NamedQuery(name = "GameobjectRespawn.pack", query = "UPDATE GameobjectRespawn g SET g.gameobjectRespawnPK.instance = :newinstance WHERE g.gameobjectRespawnPK.instance = :instance"),
    @NamedQuery(name = "GameobjectRespawn.delete", query = "DELETE FROM GameobjectRespawn g WHERE g.gameobjectRespawnPK.guid = :guid AND g.gameobjectRespawnPK.instance = :instance"),
    @NamedQuery(name = "GameobjectRespawn.findByGuid", query = "SELECT g FROM GameobjectRespawn g WHERE g.gameobjectRespawnPK.guid = :guid"),
    @NamedQuery(name = "GameobjectRespawn.findByRespawntime", query = "SELECT g FROM GameobjectRespawn g WHERE g.respawntime = :respawntime"),
    @NamedQuery(name = "GameobjectRespawn.findByInstance", query = "SELECT g FROM GameobjectRespawn g WHERE g.gameobjectRespawnPK.instance = :instance"),
    @NamedQuery(name = "GameobjectRespawn.deleteByInstance", query = "DELETE FROM GameobjectRespawn g WHERE g.gameobjectRespawnPK.instance = :instance")
})
public class GameobjectRespawn {
  @Data @Embeddable
  public static class GameobjectRespawnPK implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Global unique ID of the game object.
    */
    @Column(name = "guid", nullable = false)
    private Integer guid;
    /**
     * Instance ID that the game object will spawn in.
     */
    @Column(name = "instance", nullable = false)
    private Integer instance;
  }

  @EmbeddedId
  private GameobjectRespawnPK gameobjectRespawnPK;

  /**
   * Time till the game object should respawn.
   */
  @Column(name = "respawntime", nullable = false)
  private Long respawntime;
}