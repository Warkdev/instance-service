package eu.getmangos.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import eu.getmangos.dto.EncounterState;
import eu.getmangos.dto.InstanceDTO;
import eu.getmangos.entities.Instance;

@Mapper(componentModel = "cdi")
public interface InstanceMapper {

    InstanceDTO map(Instance entity);

    Instance map(InstanceDTO dto);

    default String map(List<EncounterState> listStates) {
        StringBuilder sb = new StringBuilder();
        for(EncounterState s : listStates) {
            sb.append(s.code + " ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    default List<EncounterState> map(String instanceData) {
        String[] data = instanceData.split(" ");
        List<EncounterState> listStates = new ArrayList<>();
        for(String e : data) {
            listStates.add(EncounterState.convert(Integer.parseInt(e)));
        }
        return listStates;
    }
}
