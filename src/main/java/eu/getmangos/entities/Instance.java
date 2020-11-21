package eu.getmangos.entities;


import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instance")
@NamedQueries({
    @NamedQuery(name = "Instance.findAll", query = "SELECT i FROM Instance i"),
    @NamedQuery(name = "Instance.findById", query = "SELECT i FROM Instance i WHERE i.id = :id"),
    @NamedQuery(name = "Instance.delete", query = "DELETE FROM Instance i WHERE i.id = :id"),
    @NamedQuery(name = "Instance.pack", query = "UPDATE Instance i SET i.id = :newid WHERE i.id = :id"),
    @NamedQuery(name = "Instance.findByMap", query = "SELECT i FROM Instance i WHERE i.map = :map"),
    @NamedQuery(name = "Instance.findByResettime", query = "SELECT i FROM Instance i WHERE i.resettime = :resettime"),
})
public class Instance {

  /**
   * The instance ID. This number is unique to every instance.
   */
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;
  /**
   * The map ID the instance is in. (See Map.dbc)
   */
  @Column(name = "map", nullable = false)
  private Integer map;
  /**
   * The time when the instance will be reset, in Unix time.
   */
  @Column(name = "resettime", nullable = false)
  private Long resettime;
  /**
   * Specific data belonging to the individual instance.
   */
  @Column(name = "data", nullable = true)
  private String data;
}