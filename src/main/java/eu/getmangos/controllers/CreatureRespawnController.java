package eu.getmangos.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import eu.getmangos.entities.CreatureRespawn;

@ApplicationScoped
public class CreatureRespawnController {
    @Inject private Logger logger;

    @PersistenceContext(name = "CHAR_PU")
    private EntityManager em;

    /**
     * Create a new creature respawn in the database.
     * @param respawn The creature respawn to be created.
     * @throws DAOException An exception is raised if the creature respawn can't be created.
     */
    @Transactional
    public void create(CreatureRespawn respawn) throws DAOException {
        logger.debug("create() entry.");

        try {
            em.persist(respawn);
        } catch (Exception e) {
            logger.debug("Exception raised while creating the creature respawn: "+e.getMessage());
            throw new DAOException("Error while creating the creature respawn.");
        }

        logger.debug("create() exit.");
    }

    /**
     * Update a creature respawn in the database.
     * @param respawn The creature respawn to be updated.
     * @throws DAOException An exception is raised if the creature respawn can't be updated.
     */
    @Transactional
    public void update(CreatureRespawn respawn) throws DAOException {
        logger.debug("update() entry.");

        CreatureRespawn exist = find(respawn.getCreatureRespawnPK().getGuid(), respawn.getCreatureRespawnPK().getInstance());

        if(exist == null) {
            throw new DAOException("The entity does not exist");
        }

        try {
            em.merge(respawn);
        } catch (Exception e) {
            logger.debug("Exception raised while updating the creature respawn: "+e.getMessage());
            throw new DAOException("Error while updating the creature respawn.");
        }

        logger.debug("update() exit.");
    }

    /**
     * Retrieves a creature respawn based on its ID.
     * @param guid The ID of the creature respawn to retrieve.
     * @param instance The instance into which the creature has to respawn.
     * @return The matching creature respawn or null if not found.
     * @throws DAOException
     */
    public CreatureRespawn find(Integer guid, Integer instance) throws DAOException {
        logger.debug("find() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        try {
            CreatureRespawn respawn = em.createNamedQuery("CreatureRespawn.findById", CreatureRespawn.class).setParameter("guid", guid).setParameter("instance", instance).getSingleResult();
            return respawn;
        } catch (NoResultException nre) {
            logger.debug("No result received for entity with guid "+guid+", instance "+instance);
            return null;
        } finally {
            logger.debug("find() exit.");
        }
    }

    /**
     * Delete a creature respawn based from the database.
     * @param id The ID of the creature respawn to be deleted.
     * @throws DAOException An exception is raised if the auction can't be deleted.
     */
    @Transactional
    public void delete(Integer guid, Integer instance) throws DAOException {
        logger.debug("delete() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        em.createNamedQuery("CreatureRespawn.delete").setParameter("guid", guid).setParameter("instance", instance).executeUpdate();

        logger.debug("delete() exit.");
    }

    /**
     * Retrieves the list of all creature respawns using paging.
     * @param page The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of creature respawns corresponding to the search.
     */
    @SuppressWarnings("unchecked")
    public List<CreatureRespawn> findAll(int page, int pageSize) {
        logger.debug("findAll() entry.");

        List<CreatureRespawn> list = em.createNamedQuery("CreatureRespawn.findAll")
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Retrieves the list of all creature respawns for a given instance using paging.
     * @param page     The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of creature respawns corresponding to the search.
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public List<CreatureRespawn> findAllByInstance(Integer instance, int page, int pageSize) throws DAOException {
        logger.debug("findAll() entry.");

        if(instance == null) {
            throw new DAOException("Entity ID is null");
        }

        List<CreatureRespawn> list = em.createNamedQuery("CreatureRespawn.findByInstance")
                                .setParameter("instance", instance)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Delete a creature respawn for a given instance from the database.
     * @param instance The ID of the instance for which all respawns have to be deleted.
     * @throws DAOException An exception is raised if the respawns can't be deleted.
     */
    @Transactional
    public void deleteAllByInstance(Integer instance) throws DAOException {
        logger.debug("deleteAllByInstance() entry.");

        if (instance == null) {
            throw new DAOException("Instance ID is null.");
        }

        em.createNamedQuery("CreatureRespawn.deleteByInstance").setParameter("instance", instance).executeUpdate();

        logger.debug("deleteAllByInstance() exit.");
    }
}
