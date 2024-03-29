package kz.yerakh.animaltrackerservice.repository;

import java.util.List;

public interface TypeOfAnimalRepository {

    List<Long> findAnimalTypes(Long animalId);

    List<Long> findAnimals(Long typeId);

    boolean exist(Long animalId, Long typeId);

    int save(Long animalId, Long typeId);

    int delete(Long animalId, Long typeId);
}
