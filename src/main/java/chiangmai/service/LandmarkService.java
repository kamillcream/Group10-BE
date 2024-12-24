package chiangmai.service;

import chiangmai.domain.Landmark;
import chiangmai.dto.WalkDto;
import chiangmai.repository.LandmarkRepository;
import chiangmai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandmarkService {
    private final LandmarkRepository landmarkRepository;
    public List<Landmark> fetchNearbyLandmarks(WalkDto walkDto){
        return landmarkRepository.findStoresWithin100M(walkDto.getCurrentY(), walkDto.getCurrentX());
    }
}
