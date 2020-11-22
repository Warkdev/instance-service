package eu.getmangos.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import eu.getmangos.entities.Instance;

@ApplicationScoped
public class InstanceController {
    @Inject private Logger logger;

    @PersistenceContext(name = "CHAR_PU")
    private EntityManager em;

    /**
     * Create a new instance in the database.
     * @param instance The Instance object to be created
     * @throws DAOException An exception is raised if the instance can't be created.
     */
    @Transactional
    public void create(Instance instance) throws DAOException {
        logger.debug("create() entry.");

        try {
            em.persist(instance);
        } catch (Exception e) {
            logger.debug("Exception raised while creating the Instance: "+e.getMessage());
            throw new DAOException("Error while creating the Instance.");
        }

        logger.debug("create() exit.");
    }

    /**
     * Update a Instance in the database.
     * @param Instance The Instance to be updated.
     * @throws DAOException An exception is raised if the Instance can't be updated.
     */
    @Transactional
    public void update(Instance Instance) throws DAOException {
        logger.debug("update() entry.");

        Instance exist = find(Instance.getId());

        if(exist == null) {
            throw new DAOException("The entity does not exist");
        }

        try {
            em.merge(Instance);
        } catch (Exception e) {
            logger.debug("Exception raised while updating the Instance: "+e.getMessage());
            throw new DAOException("Error while updating the Instance.");
        }

        logger.debug("update() exit.");
    }

    /**
     * Retrieves a Instance based on its ID.
     * @param instance The instance into which the Gameobject has to Instance.
     * @return The matching Instance or null if not found.
     * @throws DAOException
     */
    public Instance find(Integer instance) throws DAOException {
        logger.debug("find() entry.");

        if (instance == null) {
            throw new DAOException("ID is null.");
        }

        try {
            Instance Instance = em.createNamedQuery("Instance.findById", Instance.class).setParameter("id", instance).getSingleResult();
            return Instance;
        } catch (NoResultException nre) {
            logger.debug("No result received for entity with id "+instance);
            return null;
        } finally {
            logger.debug("find() exit.");
        }
    }

    /**
     * Delete a Instance based from the database.
     * @param id The ID of the Instance to be deleted.
     * @throws DAOException An exception is raised if the auction can't be deleted.
     */
    @Transactional
    public void delete(Integer instance) throws DAOException {
        logger.debug("delete() entry.");

        if (instance == null) {
            throw new DAOException("ID is null.");
        }

        em.createNamedQuery("Instance.delete").setParameter("id", instance).executeUpdate();

        logger.debug("delete() exit.");
    }

    /**
     * Retrieves the list of all Instances using paging.
     * @param page The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of Instances corresponding to the search.
     */
    @SuppressWarnings("unchecked")
    public List<Instance> findAll(int page, int pageSize) {
        logger.debug("findAll() entry.");

        List<Instance> list = em.createNamedQuery("Instance.findAll")
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Retrieves the list of all Instances for a given instance using paging.
     * @param map The ID of the map for which you're searching an instance.
     * @param page     The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of Instances corresponding to the search.
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public List<Instance> findAllByMap(Integer map, int page, int pageSize) throws DAOException {
        logger.debug("findAll() entry.");

        if(map == null) {
            throw new DAOException("Map is null");
        }

        List<Instance> list = em.createNamedQuery("Instance.findByMap")
                                .setParameter("map", map)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * This routine renumbers player instance associations in such a way so they start from 1 and go up.
     * @throws DAOException An exception is raised if the Instances can't be packed.
     * @see https://github.com/mangoszero/server/blob/fedd8b9288e9cdf7c08e22da38846ff665c1f38a/src/game/WorldHandlers/MapPersistentStateMgr.cpp#L833
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public void packInstances() throws DAOException {
        logger.debug("packInstances() entry.");

        int index = 1;

        for(Instance i : (List<Instance>)em.createNamedQuery("Instance.findAll").getResultList()) {
            if(index != i.getId()) {
                em.createNamedQuery("Instance.pack").setParameter("newid", index).setParameter("id", i.getId()).executeUpdate();
                em.createNamedQuery("CreatureRespawn.pack").setParameter("newinstance", index).setParameter("instance", i.getId()).executeUpdate();
                em.createNamedQuery("GameobjectRespawn.pack").setParameter("newinstance", index).setParameter("instance", i.getId()).executeUpdate();
                // TODO - Add corpse, character_instance, group_instance
                em.createNamedQuery("CharacterInstance.pack").setParameter("newinstance", index).setParameter("instance", i.getId()).executeUpdate();
                em.createNamedQuery("GroupInstance.pack").setParameter("newinstance", index).setParameter("instance", i.getId()).executeUpdate();
            }
            index++;
        }

        logger.debug("packInstances() exit.");
    }
}
