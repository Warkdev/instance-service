package eu.getmangos.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import eu.getmangos.entities.CharacterInstance;
import eu.getmangos.entities.Instance;

@ApplicationScoped
public class CharacterInstanceController {
    @Inject private Logger logger;

    @PersistenceContext(unitName = "CHAR_PU")
    private EntityManager em;

    @Inject private InstanceController instanceController;

    /**
     * Create a new character instance in the database.
     * @param link The character instance to be created.
     * @throws DAOException An exception is raised if the character instance can't be created.
     */
    @Transactional
    public void create(CharacterInstance link) throws DAOException {
        logger.debug("create() entry.");

        Instance i = instanceController.find(link.getCharacterInstancePK().getInstance());

        if (i == null) {
            logger.debug("Instance does not exist, can't create the respawn timer");
            throw new DAOException("Instance doesn't exist.");
        }

        try {
            em.persist(link);
        } catch (Exception e) {
            logger.debug("Exception raised while creating the character instance: "+e.getMessage());
            throw new DAOException("Error while creating the character instance.");
        }

        logger.debug("create() exit.");
    }

    /**
     * Update a character instance in the database.
     * @param link The character instance to be updated.
     * @throws DAOException An exception is raised if the character instance can't be updated.
     */
    @Transactional
    public void update(CharacterInstance link) throws DAOException {
        logger.debug("update() entry.");

        CharacterInstance exist = find(link.getCharacterInstancePK().getGuid(), link.getCharacterInstancePK().getInstance());

        if(exist == null) {
            throw new DAOException("The entity does not exist");
        }

        try {
            em.merge(link);
        } catch (Exception e) {
            logger.debug("Exception raised while updating the character instance: "+e.getMessage());
            throw new DAOException("Error while updating the character instance.");
        }

        logger.debug("update() exit.");
    }

    /**
     * Retrieves a character instance based on its ID.
     * @param guid The ID of the character instance to retrieve.
     * @param instance The instance into which the creature has to link.
     * @return The matching character instance or null if not found.
     * @throws DAOException
     */
    public CharacterInstance find(Integer guid, Integer instance) throws DAOException {
        logger.debug("find() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        try {
            CharacterInstance link = em.createNamedQuery("CharacterInstance.findById", CharacterInstance.class).setParameter("guid", guid).setParameter("instance", instance).getSingleResult();
            return link;
        } catch (NoResultException nre) {
            logger.debug("No result received for entity with guid "+guid+", instance "+instance);
            return null;
        } finally {
            logger.debug("find() exit.");
        }
    }

    /**
     * Delete a character instance based from the database.
     * @param id The ID of the character instance to be deleted.
     * @throws DAOException An exception is raised if the auction can't be deleted.
     */
    @Transactional
    public void delete(Integer guid, Integer instance) throws DAOException {
        logger.debug("delete() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        em.createNamedQuery("CharacterInstance.delete").setParameter("guid", guid).setParameter("instance", instance).executeUpdate();

        logger.debug("delete() exit.");
    }

    /**
     * Retrieves the list of all creature respawns using paging.
     * @param page The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of creature respawns corresponding to the search.
     */
    @SuppressWarnings("unchecked")
    public List<CharacterInstance> findAll(int page, int pageSize) {
        logger.debug("findAll() entry.");

        List<CharacterInstance> list = em.createNamedQuery("CharacterInstance.findAll")
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
    public List<CharacterInstance> findAllByInstance(Integer instance, int page, int pageSize) throws DAOException {
        logger.debug("findAll() entry.");

        if(instance == null) {
            throw new DAOException("Entity ID is null");
        }

        List<CharacterInstance> list = em.createNamedQuery("CharacterInstance.findByInstance")
                                .setParameter("instance", instance)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Delete a character instance for a given instance from the database.
     * @param instance The ID of the instance for which all respawns have to be deleted.
     * @throws DAOException An exception is raised if the respawns can't be deleted.
     */
    @Transactional
    public void deleteAllByInstance(Integer instance) throws DAOException {
        logger.debug("deleteAllByInstance() entry.");

        if (instance == null) {
            throw new DAOException("Instance ID is null.");
        }

        em.createNamedQuery("CharacterInstance.deleteByInstance").setParameter("instance", instance).executeUpdate();

        logger.debug("deleteAllByInstance() exit.");
    }
}
