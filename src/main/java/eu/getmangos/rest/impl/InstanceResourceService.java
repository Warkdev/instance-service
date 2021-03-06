package eu.getmangos.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import eu.getmangos.controllers.CharacterInstanceController;
import eu.getmangos.controllers.CreatureRespawnController;
import eu.getmangos.controllers.DAOException;
import eu.getmangos.controllers.GameobjectRespawnController;
import eu.getmangos.controllers.GroupInstanceController;
import eu.getmangos.controllers.InstanceController;
import eu.getmangos.dto.CharacterInstanceDTO;
import eu.getmangos.dto.CreatureRespawnDTO;
import eu.getmangos.dto.GameobjectRespawnDTO;
import eu.getmangos.dto.GroupInstanceDTO;
import eu.getmangos.dto.InstanceDTO;
import eu.getmangos.dto.MessageDTO;
import eu.getmangos.entities.CharacterInstance;
import eu.getmangos.entities.CreatureRespawn;
import eu.getmangos.entities.GameobjectRespawn;
import eu.getmangos.entities.GroupInstance;
import eu.getmangos.entities.Instance;
import eu.getmangos.mapper.CharacterInstanceMapper;
import eu.getmangos.mapper.CreatureRespawnMapper;
import eu.getmangos.mapper.GameobjectRespawnMapper;
import eu.getmangos.mapper.GroupInstanceMapper;
import eu.getmangos.mapper.InstanceMapper;
import eu.getmangos.rest.InstanceResource;

@ApplicationScoped
@Path("/instance/v1")
@Tag(name = "instance")
public class InstanceResourceService implements InstanceResource {

    @Inject
    private Logger logger;

    @Inject
    private InstanceController instanceController;
    @Inject
    private CreatureRespawnController creatureRespawnController;
    @Inject
    private GameobjectRespawnController gameobjectRespawnController;
    @Inject
    private CharacterInstanceController characterInstanceController;
    @Inject
    private GroupInstanceController groupInstanceController;

    @Inject
    private InstanceMapper instanceMapper;
    @Inject
    private CreatureRespawnMapper creatureMapper;
    @Inject
    private GameobjectRespawnMapper gameobjectMapper;
    @Inject
    private CharacterInstanceMapper characterMapper;
    @Inject
    private GroupInstanceMapper groupMapper;

    @Override
    @Tag(name = "instance")
    public Response findAllInstances(Integer page, Integer pageSize) {
        logger.debug("findAllInstances() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<InstanceDTO> list = new ArrayList<>();

        for (Instance instance : instanceController.findAll(page, pageSize)) {
            list.add(instanceMapper.map(instance));
        }

        logger.debug("findAllInstances() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "instance")
    public Response findInstance(Integer instanceId) {
        logger.debug("findInstance() entry.");

        Instance entity = null;

        try {
            entity = instanceController.find(instanceId);
        } catch (DAOException dao) {
            logger.debug("Error while retrieving the instance " + dao.getMessage());
            return Response.status(400).entity(new MessageDTO(dao.getMessage())).build();
        } finally {
            logger.debug("findInstance() exit.");
        }

        if (entity == null) {
            return Response.status(404).entity(new MessageDTO("Instance does not exist.")).build();
        }

        return Response.status(200).entity(instanceMapper.map(entity)).build();
    }

    @Override
    @Tag(name = "instance")
    public Response findAllInstancesByMap(Integer map, Integer page, Integer pageSize) {
        logger.debug("findAllInstances() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<InstanceDTO> list = new ArrayList<>();

        try {
            for (Instance instance : instanceController.findAllByMap(map, page, pageSize)) {
                list.add(instanceMapper.map(instance));
            }
        } catch (DAOException dao) {
            return Response.status(404).entity(list).build();
        }

        logger.debug("findAllInstances() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "instance")
    public Response createInstance(InstanceDTO entity) {
        try {
            this.instanceController.create(instanceMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Instance has been created.")).build();
    }

    @Override
    @Tag(name = "instance")
    public Response updateInstance(Integer instanceId, InstanceDTO entity) {
        try {
            entity.setId(instanceId);
            this.instanceController.create(instanceMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Instance has been created.")).build();
    }

    @Override
    @Tag(name = "instance")
    public Response deleteInstance(Integer instanceId) {
        try {
            this.instanceController.delete(instanceId);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).build();
    }

    @Override
    @Tag(name = "instance")
    public Response packInstances() {
        logger.debug("packInstances() entry.");
        try {
            this.instanceController.packInstances();
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        } finally {
            logger.debug("packInstances() exit.");
        }
        return Response.status(200).build();
    }

    @Override
    @Tag(name = "creature")
    public Response findAllCreatureRespawn(Integer instanceId, Integer page, Integer pageSize) {
        logger.debug("findAllCreatureRespawn() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<CreatureRespawnDTO> list = new ArrayList<>();

        try {
            for (CreatureRespawn respawn : creatureRespawnController.findAllByInstance(instanceId, page, pageSize)) {
                list.add(creatureMapper.map(respawn));
            }
        } catch (DAOException dao) {
            return Response.status(404).entity(list).build();
        }

        logger.debug("findAllCreatureRespawn() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "creature")
    public Response findCreatureRespawn(Integer instanceId, Integer guid) {
        logger.debug("findCreatureRespawn() entry.");

        CreatureRespawn entity = null;

        try {
            entity = creatureRespawnController.find(guid, instanceId);
        } catch (DAOException dao) {
            logger.debug("Error while retrieving the timer " + dao.getMessage());
            return Response.status(400).entity(new MessageDTO(dao.getMessage())).build();
        } finally {
            logger.debug("findCreatureRespawn() exit.");
        }

        if (entity == null) {
            return Response.status(404).entity(new MessageDTO("Respawn Timer not found.")).build();
        }

        return Response.status(200).entity(creatureMapper.map(entity)).build();
    }

    @Override
    @Tag(name = "creature")
    public Response createCreatureRespawn(Integer instanceId, CreatureRespawnDTO entity) {
        try {
            entity.setInstance(instanceId);
            this.creatureRespawnController.create(creatureMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Respawn Timer has been created.")).build();
    }

    @Override
    @Tag(name = "creature")
    public Response updateCreatureRespawnTimer(Integer instanceId, Integer guid, CreatureRespawnDTO entity) {
        try {
            entity.setGuid(guid);
            entity.setInstance(instanceId);
            this.creatureRespawnController.update(creatureMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(200).entity("Respawn Timer has been updated.").build();
    }

    @Override
    @Tag(name = "creature")
    public Response deleteCreatureRespawnTimer(Integer instanceId, Integer guid) {
        try {
            this.creatureRespawnController.delete(instanceId, guid);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).build();
    }

    @Override
    @Tag(name = "creature")
    public Response deleteAllCreatureRespawnTimer(Integer instanceId) {
        try {
            this.creatureRespawnController.deleteAllByInstance(instanceId);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response findAllGameobjectRespawn(Integer instanceId, Integer page, Integer pageSize) {
        logger.debug("findAllGameobjectRespawn() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<GameobjectRespawnDTO> list = new ArrayList<>();

        try {
            for (GameobjectRespawn respawn : gameobjectRespawnController.findAllByInstance(instanceId, page,
                    pageSize)) {
                list.add(gameobjectMapper.map(respawn));
            }
        } catch (DAOException dao) {
            return Response.status(404).entity(list).build();
        }

        logger.debug("findAllGameobjectRespawn() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response findGameobjectRespawn(Integer instanceId, Integer guid) {
        logger.debug("findGameobjectRespawn() entry.");

        GameobjectRespawn entity = null;

        try {
            entity = gameobjectRespawnController.find(guid, instanceId);
        } catch (DAOException dao) {
            logger.debug("Error while retrieving the timer " + dao.getMessage());
            return Response.status(400).entity(new MessageDTO(dao.getMessage())).build();
        } finally {
            logger.debug("findGameobjectRespawn() exit.");
        }

        if (entity == null) {
            return Response.status(404).entity(new MessageDTO("Respawn Timer not found.")).build();
        }

        return Response.status(200).entity(gameobjectMapper.map(entity)).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response createGameobjectRespawn(Integer instanceId, GameobjectRespawnDTO entity) {
        try {
            entity.setInstance(instanceId);
            this.gameobjectRespawnController.create(gameobjectMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Respawn Timer has been created.")).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response updateGameobjectRespawnTimer(Integer instanceId, Integer guid, GameobjectRespawnDTO entity) {
        try {
            entity.setGuid(guid);
            entity.setInstance(instanceId);
            this.gameobjectRespawnController.update(gameobjectMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(200).entity(new MessageDTO("Respawn Timer has been updated.")).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response deleteGameobjectRespawnTimer(Integer instanceId, Integer guid) {
        try {
            this.gameobjectRespawnController.delete(instanceId, guid);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).entity(new MessageDTO("Respawn Timer has been deleted.")).build();
    }

    @Override
    @Tag(name = "gameobject")
    public Response deleteAllGameobjectRespawnTimer(Integer instanceId) {
        try {
            this.gameobjectRespawnController.deleteAllByInstance(instanceId);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(daoEx.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(500).entity(ex.getMessage()).build();
        }
        return Response.status(204).entity(new MessageDTO("Respawn Timers have been deleted.")).build();
    }

    @Override
    @Tag(name = "character")
    public Response findAllCharacterInstance(Integer instanceId, Integer page, Integer pageSize) {
        logger.debug("findAllCharacterInstance() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<CharacterInstanceDTO> list = new ArrayList<>();

        try {
            for (CharacterInstance link : characterInstanceController.findAllByInstance(instanceId, page,
                    pageSize)) {
                list.add(characterMapper.map(link));
            }
        } catch (DAOException dao) {
            return Response.status(404).entity(list).build();
        }

        logger.debug("findAllCharacterInstance() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "character")
    public Response findCharacterInstance(Integer instanceId, Integer guid) {
        logger.debug("findCharacterInstance() entry.");

        CharacterInstance entity = null;

        try {
            entity = characterInstanceController.find(guid, instanceId);
        } catch (DAOException dao) {
            logger.debug("Error while retrieving the character link " + dao.getMessage());
            return Response.status(400).entity(new MessageDTO(dao.getMessage())).build();
        } finally {
            logger.debug("findCharacterInstance() exit.");
        }

        if (entity == null) {
            return Response.status(404).entity(new MessageDTO("No link found.")).build();
        }

        return Response.status(200).entity(characterMapper.map(entity)).build();
    }

    @Override
    @Tag(name = "character")
    public Response createCharacterInstance(Integer instanceId, CharacterInstanceDTO entity) {
        try {
            entity.setInstance(instanceId);
            this.characterInstanceController.create(characterMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Character link has been created.")).build();
    }

    @Override
    @Tag(name = "character")
    public Response updateCharacterInstance(Integer instanceId, Integer guid, CharacterInstanceDTO entity) {
        try {
            entity.setGuid(guid);
            entity.setInstance(instanceId);
            this.characterInstanceController.update(characterMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(200).entity(new MessageDTO("Character link has been updated.")).build();
    }

    @Override
    @Tag(name = "character")
    public Response deleteCharacterInstance(Integer instanceId, Integer guid) {
        try {
            this.characterInstanceController.delete(instanceId, guid);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).entity(new MessageDTO("Link has been deleted")).build();
    }

    @Override
    @Tag(name = "character")
    public Response deleteAllCharacterInstance(Integer instanceId) {
        try {
            this.characterInstanceController.deleteAllByInstance(instanceId);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).entity(new MessageDTO("All links have been deleted.")).build();
    }

    @Override
    @Tag(name = "group")
    public Response findAllGroupInstance(Integer instanceId, Integer page, Integer pageSize) {
        logger.debug("findAllGroupInstance() entry.");

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        List<GroupInstanceDTO> list = new ArrayList<>();

        try {
            for (GroupInstance link : groupInstanceController.findAllByInstance(instanceId, page,
                    pageSize)) {
                list.add(groupMapper.map(link));
            }
        } catch (DAOException dao) {
            return Response.status(404).entity(list).build();
        }

        logger.debug("findAllGroupInstance() exit.");

        return Response.status(200).entity(list).build();
    }

    @Override
    @Tag(name = "group")
    public Response findGroupInstance(Integer instanceId, Integer guid) {
        logger.debug("findGroupInstance() entry.");

        GroupInstance entity = null;

        try {
            entity = groupInstanceController.find(guid, instanceId);
        } catch (DAOException dao) {
            logger.debug("Error while retrieving the group link " + dao.getMessage());
            return Response.status(400).entity(new MessageDTO(dao.getMessage())).build();
        } finally {
            logger.debug("findGroupInstance() exit.");
        }

        if (entity == null) {
            return Response.status(404).entity(new MessageDTO("Link does not exist.")).build();
        }

        return Response.status(200).entity(groupMapper.map(entity)).build();
    }

    @Override
    @Tag(name = "group")
    public Response createGroupInstance(Integer instanceId, GroupInstanceDTO entity) {
        try {
            entity.setInstance(instanceId);
            this.groupInstanceController.create(groupMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(201).entity(new MessageDTO("Group link has been created.")).build();
    }

    @Override
    @Tag(name = "group")
    public Response updateGroupInstance(Integer instanceId, Integer guid, GroupInstanceDTO entity) {
        try {
            entity.setLeaderguid(guid);
            entity.setInstance(instanceId);
            this.groupInstanceController.update(groupMapper.map(entity));
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(x.getMessage())).build();
        }
        return Response.status(200).entity(new MessageDTO("Group link has been updated.")).build();
    }

    @Override
    @Tag(name = "group")
    public Response deleteGroupInstance(Integer instanceId, Integer guid) {
        try {
            this.groupInstanceController.delete(instanceId, guid);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).entity(new MessageDTO("Link has been deleted.")).build();
    }

    @Override
    @Tag(name = "group")
    public Response deleteAllGroupInstance(Integer instanceId) {
        try {
            this.groupInstanceController.deleteAllByInstance(instanceId);
        } catch (DAOException daoEx) {
            return Response.status(400).entity(new MessageDTO(daoEx.getMessage())).build();
        } catch (Exception ex) {
            return Response.status(500).entity(new MessageDTO(ex.getMessage())).build();
        }
        return Response.status(204).entity(new MessageDTO("Links have been deleted.")).build();
    }
}
