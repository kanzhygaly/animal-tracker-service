package kz.yerakh.animaltrackerservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kz.yerakh.animaltrackerservice.dto.LocationRequest;
import kz.yerakh.animaltrackerservice.model.Location;
import kz.yerakh.animaltrackerservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping(path = "/{pointId}")
    public ResponseEntity<Location> getLocation(@PathVariable("pointId") @Min(1) Long pointId) {
        return ResponseEntity.ok(locationService.getLocation(pointId));
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody @Valid LocationRequest locationRequest) {
        return new ResponseEntity<>(locationService.addLocation(locationRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{pointId}")
    public ResponseEntity<Location> updateLocation(@PathVariable("pointId") @Min(1) Long pointId,
                                                   @RequestBody @Valid LocationRequest locationRequest) {
        return ResponseEntity.ok(locationService.updateLocation(pointId, locationRequest));
    }

    @DeleteMapping(path = "/{pointId}")
    public ResponseEntity<String> deleteLocation(@PathVariable("pointId") @Min(1) Long pointId) {
        // TODO: check if Location connected to an Animal. If yes, return 400
        locationService.deleteLocation(pointId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
