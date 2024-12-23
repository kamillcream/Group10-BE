package chiangmai.controller;

import chiangmai.docs.MapDocs;
import chiangmai.dto.PositionDto;
import chiangmai.dto.UserDto;
import chiangmai.dto.WalkDto;
import chiangmai.service.NotificationService;
import chiangmai.service.MapService;
import chiangmai.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class MapController implements MapDocs {

    @Autowired
    private MapService mapService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserUtil userUtil;


    // GET 요청 처리
    @PostMapping("/start")
    public ResponseEntity<PositionDto> handleStartRequest(@RequestBody PositionDto positionDto) {
        mapService.updateWhenStart(positionDto);
        return ResponseEntity.ok().body(positionDto);
    }
    @PostMapping("/end")
    public ResponseEntity<PositionDto> handleEndRequest(@RequestBody PositionDto positionDto) {
        mapService.updateWhenEnd(positionDto);
        return ResponseEntity.ok().body(positionDto);
    }
    @PatchMapping("/walking")
    public ResponseEntity<WalkDto> handleWalkingRequest(@RequestBody WalkDto walkDto) {
        System.out.println(walkDto.getCurrentX());
        System.out.println(walkDto.getCurrentY());
        mapService.updateWhileWalking(walkDto);
        return ResponseEntity.ok().body(walkDto);
    }
    @GetMapping("/rank")
    public ResponseEntity<List<UserDto>> getRanking() {
        return ResponseEntity.ok().body(mapService.fetchRanking());
    }
}
