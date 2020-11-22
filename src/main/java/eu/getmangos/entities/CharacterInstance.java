package eu.getmangos.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "character_instance")
@NamedQueries({
    @NamedQuery(name = "CharacterInstance.findAll", query = "SELECT c FROM CharacterInstance c"),
    @NamedQuery(name = "CharacterInstance.findById", query = "SELECT c FROM CharacterInstance c WHERE c.characterInstancePK.guid = :guid AND c.characterInstancePK.instance = :instance"),
    @NamedQuery(name = "CharacterInstance.delete", query = "DELETE FROM CharacterInstance c WHERE c.characterInstancePK.guid = :guid AND c.characterInstancePK.instance = :instance"),
    @NamedQuery(name = "CharacterInstance.pack", query = "UPDATE CharacterInstance c SET c.characterInstancePK.instance = :newinstance WHERE c.characterInstancePK.instance = :instance"),
    @NamedQuery(name = "CharacterInstance.findByGuid", query = "SELECT c FROM CharacterInstance c WHERE c.characterInstancePK.guid = :guid"),
    @NamedQuery(name = "CharacterInstance.findByGuid", query = "SELECT c FROM CharacterInstance c WHERE c.characterInstancePK.guid = :guid"),
    @NamedQuery(name = "CharacterInstance.findByInstance", query = "SELECT c FROM CharacterInstance c WHERE c.characterInstancePK.instance = :instance"),
    @NamedQuery(name = "CharacterInstance.findByPermanent", query = "SELECT c FROM CharacterInstance c WHERE c.permanent = :permanent")})
public class CharacterInstance {
    @Data @Embeddable
    public static class PrimaryKeys implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * The GUID (Global Unique Identifier) of the character.
     */
    private Integer guid;

    /**
     * The instance ID. (See instance.id).
     */
    private Integer instance;
    }

    @EmbeddedId
    private PrimaryKeys characterInstancePK;

    /**
     * Boolean 0 or 1 controlling if the player has been bound to the instance.
     */
    @Column(name = "permanent", nullable = false)
    private boolean permanent;
}