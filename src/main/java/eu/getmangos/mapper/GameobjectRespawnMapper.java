package eu.getmangos.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eu.getmangos.dto.GameobjectRespawnDTO;
import eu.getmangos.entities.GameobjectRespawn;

@Mapper(componentModel = "cdi")
public interface GameobjectRespawnMapper {

    @Mapping(source = "respawn.gameobjectRespawnPK.guid", target = "guid")
    @Mapping(source = "respawn.gameobjectRespawnPK.instance", target = "instance")
    GameobjectRespawnDTO map(GameobjectRespawn respawn);

    @InheritInverseConfiguration
    GameobjectRespawn map(GameobjectRespawnDTO dto);
}
