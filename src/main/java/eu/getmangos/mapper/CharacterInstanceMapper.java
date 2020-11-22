package eu.getmangos.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import eu.getmangos.dto.CharacterInstanceDTO;
import eu.getmangos.entities.CharacterInstance;

@Mapper(componentModel = "cdi")
public interface CharacterInstanceMapper {

    @Mapping(source = "character.characterInstancePK.guid", target = "guid")
    @Mapping(source = "character.characterInstancePK.instance", target = "instance")
    CharacterInstanceDTO map(CharacterInstance character);

    @InheritInverseConfiguration
    CharacterInstance map(CharacterInstanceDTO dto);
}
