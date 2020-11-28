package eu.getmangos.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import eu.getmangos.entities.GameobjectRespawn;
import eu.getmangos.entities.Instance;

@ApplicationScoped
public class GameobjectRespawnController {
    @Inject private Logger logger;

    @PersistenceContext(unitName = "CHAR_PU")
    private EntityManager em;

    @Inject private InstanceController instanceController;

    /**
     * Create a new Gameobject respawn in the database.
     * @param respawn The Gameobject respawn to be created.
     * @throws DAOException An exception is raised if the Gameobject respawn can't be created.
     */
    @Transactional
    public void create(GameobjectRespawn respawn) throws DAOException {
        logger.debug("create() entry.");

        Instance i = instanceController.find(respawn.getGameobjectRespawnPK().getInstance());

        if (i == null) {
            logger.debug("Instance does not exist, can't create the respawn timer");
            throw new DAOException("Instance doesn't exist.");
        }

        try {
            em.persist(respawn);
        } catch (Exception e) {
            logger.debug("Exception raised while creating the Gameobject respawn: "+e.getMessage());
            throw new DAOException("Error while creating the Gameobject respawn.");
        }

        logger.debug("create() exit.");
    }

    /**
     * Update a Gameobject respawn in the database.
     * @param respawn The Gameobject respawn to be updated.
     * @throws DAOException An exception is raised if the Gameobject respawn can't be updated.
     */
    @Transactional
    public void update(GameobjectRespawn respawn) throws DAOException {
        logger.debug("update() entry.");

        GameobjectRespawn exist = find(respawn.getGameobjectRespawnPK().getGuid(), respawn.getGameobjectRespawnPK().getInstance());

        if(exist == null) {
            throw new DAOException("The entity does not exist");
        }

        try {
            em.merge(respawn);
        } catch (Exception e) {
            logger.debug("Exception raised while updating the Gameobject respawn: "+e.getMessage());
            throw new DAOException("Error while updating the Gameobject respawn.");
        }

        logger.debug("update() exit.");
    }

    /**
     * Retrieves a Gameobject respawn based on its ID.
     * @param guid The ID of the Gameobject respawn to retrieve.
     * @param instance The instance into which the Gameobject has to respawn.
     * @return The matching Gameobject respawn or null if not found.
     * @throws DAOException
     */
    public GameobjectRespawn find(Integer guid, Integer instance) throws DAOException {
        logger.debug("find() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        try {
            GameobjectRespawn respawn = em.createNamedQuery("GameobjectRespawn.findById", GameobjectRespawn.class).setParameter("guid", guid).setParameter("instance", instance).getSingleResult();
            return respawn;
        } catch (NoResultException nre) {
            logger.debug("No result received for entity with guid "+guid+", instance "+instance);
            return null;
        } finally {
            logger.debug("find() exit.");
        }
    }

    /**
     * Delete a Gameobject respawn based from the database.
     * @param id The ID of the Gameobject respawn to be deleted.
     * @throws DAOException An exception is raised if the auction can't be deleted.
     */
    @Transactional
    public void delete(Integer guid, Integer instance) throws DAOException {
        logger.debug("delete() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        em.createNamedQuery("GameobjectRespawn.delete").setParameter("guid", guid).setParameter("instance", instance).executeUpdate();

        logger.debug("delete() exit.");
    }

    /**
     * Retrieves the list of all Gameobject respawns using paging.
     * @param page The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of Gameobject respawns corresponding to the search.
     */
    @SuppressWarnings("unchecked")
    public List<GameobjectRespawn> findAll(int page, int pageSize) {
        logger.debug("findAll() entry.");

        List<GameobjectRespawn> list = em.createNamedQuery("GameobjectRespawn.findAll")
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Retrieves the list of all Gameobject respawns for a given instance using paging.
     * @param page     The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of Gameobject respawns corresponding to the search.
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public List<GameobjectRespawn> findAllByInstance(Integer instance, int page, int pageSize) throws DAOException {
        logger.debug("findAll() entry.");

        if(instance == null) {
            throw new DAOException("Entity ID is null");
        }

        List<GameobjectRespawn> list = em.createNamedQuery("GameobjectRespawn.findByInstance")
                                .setParameter("instance", instance)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Delete a Gameobject respawn for a given instance from the database.
     * @param instance The ID of the instance for which all respawns have to be deleted.
     * @throws DAOException An exception is raised if the respawns can't be deleted.
     */
    @Transactional
    public void deleteAllByInstance(Integer instance) throws DAOException {
        logger.debug("deleteAllByInstance() entry.");

        if (instance == null) {
            throw new DAOException("Instance ID is null.");
        }

        em.createNamedQuery("GameobjectRespawn.deleteByInstance").setParameter("instance", instance).executeUpdate();

        logger.debug("deleteAllByInstance() exit.");
    }
}
