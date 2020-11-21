package eu.getmangos.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * Grid Loading System
 */
@Data
@Entity
@Table(name = "creature_respawn")
@NamedQueries({
    @NamedQuery(name = "CreatureRespawn.findAll", query = "SELECT c FROM CreatureRespawn c"),
    @NamedQuery(name = "CreatureRespawn.findById", query = "SELECT c FROM CreatureRespawn c WHERE c.creatureRespawnPK.guid = :guid AND c.creatureRespawnPK.instance = :instance"),
    @NamedQuery(name = "CreatureRespawn.delete", query = "DELETE FROM CreatureRespawn c WHERE c.creatureRespawnPK.guid = :guid AND c.creatureRespawnPK.instance = :instance"),
    @NamedQuery(name = "CreatureRespawn.pack", query = "UPDATE CreatureRespawn c SET c.creatureRespawnPK.instance = :newinstance WHERE c.creatureRespawnPK.instance = :instance"),
    @NamedQuery(name = "CreatureRespawn.findByGuid", query = "SELECT c FROM CreatureRespawn c WHERE c.creatureRespawnPK.guid = :guid"),
    @NamedQuery(name = "CreatureRespawn.findByRespawntime", query = "SELECT c FROM CreatureRespawn c WHERE c.respawntime = :respawntime"),
    @NamedQuery(name = "CreatureRespawn.findByInstance", query = "SELECT c FROM CreatureRespawn c WHERE c.creatureRespawnPK.instance = :instance"),
    @NamedQuery(name = "CreatureRespawn.deleteByInstance", query = "DELETE FROM CreatureRespawn c WHERE c.creatureRespawnPK.instance = :instance")
})
public class CreatureRespawn {
    @Data @Embeddable
    public static class CreatureRespawnPK implements Serializable {

        private static final long serialVersionUID = 1L;
        /**
         * Global Unique Identifier
         */
        @Column(name = "guid", nullable = false)
        private Integer guid;
        /**
         * Instance ID of the instance the creature is respawning into.
         */
        @Column(name = "instance", nullable = false)
        private Integer instance;
    }

    @EmbeddedId
    protected CreatureRespawnPK creatureRespawnPK;

    /**
     * Time till the creature should respawn.
     */
    @Column(name = "respawntime", nullable = false)
    private Long respawntime;
}