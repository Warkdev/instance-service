package eu.getmangos.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eu.getmangos.dto.CreatureRespawnDTO;
import eu.getmangos.entities.CreatureRespawn;

@Mapper(componentModel = "cdi")
public interface CreatureRespawnMapper {

    @Mapping(source = "respawn.creatureRespawnPK.guid", target = "guid")
    @Mapping(source = "respawn.creatureRespawnPK.instance", target = "instance")
    CreatureRespawnDTO map(CreatureRespawn respawn);

    @InheritInverseConfiguration
    CreatureRespawn map(CreatureRespawnDTO dto);
}
