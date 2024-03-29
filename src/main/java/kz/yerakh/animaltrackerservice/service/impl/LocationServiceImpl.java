package kz.yerakh.animaltrackerservice.service.impl;

import kz.yerakh.animaltrackerservice.dto.LocationRequest;
import kz.yerakh.animaltrackerservice.exception.EntryAlreadyExistException;
import kz.yerakh.animaltrackerservice.exception.EntryNotFoundException;
import kz.yerakh.animaltrackerservice.exception.InvalidValueException;
import kz.yerakh.animaltrackerservice.model.Location;
import kz.yerakh.animaltrackerservice.repository.LocationRepository;
import kz.yerakh.animaltrackerservice.repository.VisitedLocationRepository;
import kz.yerakh.animaltrackerservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final VisitedLocationRepository visitedLocationRepository;

    @Override
    public Location getLocation(Long locationId) {
        return locationRepository.find(locationId).orElseThrow(EntryNotFoundException::new);
    }

    @Override
    public Location addLocation(LocationRequest payload) {
        try {
            Long id = locationRepository.save(payload);
            return locationRepository.find(id).orElseThrow(EntryNotFoundException::new);
        } catch (DuplicateKeyException ex) {
            String msg = "Location with the following latitude " + payload.latitude() + " and longitude "
                    + payload.longitude() + " already exist";
            log.warn(msg);
            throw new EntryAlreadyExistException(msg);
        }
    }

    @Override
    public Location updateLocation(Long locationId, LocationRequest payload) {
        checkIfLocationExist(locationId);
        try {
            locationRepository.update(locationId, payload);
        } catch (DuplicateKeyException ex) {
            String msg = "Location with the following latitude " + payload.latitude() + " and longitude "
                    + payload.longitude() + " already exist";
            log.warn(msg);
            throw new EntryAlreadyExistException(msg);
        }
        return Location.builder()
                .id(locationId)
                .latitude(payload.latitude())
                .longitude(payload.longitude())
                .build();
    }

    @Override
    public void deleteLocation(Long locationId) {
        checkIfLocationConnectedToAnimal(locationId);
        checkIfLocationExist(locationId);
        locationRepository.delete(locationId);
    }

    private void checkIfLocationExist(Long locationId) {
        if (locationRepository.find(locationId).isEmpty()) {
            throw new EntryNotFoundException();
        }
    }

    private void checkIfLocationConnectedToAnimal(Long locationId) {
        if (!visitedLocationRepository.findAnimals(locationId).isEmpty()) {
            throw new InvalidValueException("Location is connected to an animal");
        }
    }
}
