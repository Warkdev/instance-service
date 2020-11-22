package eu.getmangos.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eu.getmangos.dto.GroupInstanceDTO;
import eu.getmangos.entities.GroupInstance;

@Mapper(componentModel = "cdi")
public interface GroupInstanceMapper {

    @Mapping(source = "character.groupInstancePK.leaderguid", target = "leaderguid")
    @Mapping(source = "character.groupInstancePK.instance", target = "instance")
    GroupInstanceDTO map(GroupInstance character);

    @InheritInverseConfiguration
    GroupInstance map(GroupInstanceDTO dto);
}
