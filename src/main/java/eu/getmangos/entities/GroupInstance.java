package eu.getmangos.entities;

import java.io.Serializable;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "group_instance")
@NamedQueries({
    @NamedQuery(name = "GroupInstance.findAll", query = "SELECT g FROM GroupInstance g"),
    @NamedQuery(name = "GroupInstance.findById", query = "SELECT g FROM GroupInstance g WHERE g.groupInstancePK.leaderguid = :guid AND g.groupInstancePK.instance = :instance"),
    @NamedQuery(name = "GroupInstance.delete", query = "DELETE FROM GroupInstance g WHERE g.groupInstancePK.leaderguid = :guid AND g.groupInstancePK.instance = :instance"),
    @NamedQuery(name = "GroupInstance.pack", query = "UPDATE GroupInstance g SET g.groupInstancePK.instance = :newinstance WHERE g.groupInstancePK.instance = :instance"),
    @NamedQuery(name = "GroupInstance.findByLeaderGuid", query = "SELECT g FROM GroupInstance g WHERE g.groupInstancePK.leaderguid = :leaderGuid"),
    @NamedQuery(name = "GroupInstance.findByInstance", query = "SELECT g FROM GroupInstance g WHERE g.groupInstancePK.instance = :instance"),
    @NamedQuery(name = "GroupInstance.findByPermanent", query = "SELECT g FROM GroupInstance g WHERE g.permanent = :permanent")})
public class GroupInstance {
    @Data @Embeddable
    public static class PrimaryKeys implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         * The Guid of the group leader. (See characters.guid)
         */
        private Integer leaderguid;

        /**
         * ID of the Instance session the group has enterd.
         */
        private Integer instance;
    }

    @EmbeddedId
    private PrimaryKeys groupInstancePK;

    /**
     * Boolean flag if the group is bound to the Instance or not
     */
    @Column(name = "permanent", nullable = false)
    private boolean permanent;
}