package chiangmai.controller;

import chiangmai.docs.MapDocs;
import chiangmai.domain.Landmark;
import chiangmai.dto.PositionDto;
import chiangmai.dto.ReportDto;
import chiangmai.dto.ResponseDto;
import chiangmai.dto.WalkDto;
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

    // GET 요청 처리
    @PostMapping("/start")
    public ResponseEntity<Double> handleStartRequest(@RequestBody PositionDto positionDto) {
        return ResponseEntity.ok().body(mapService.updateWhenStart(positionDto));
    }
    @PostMapping("/end")
    public ResponseEntity<Double> handleEndRequest(@RequestBody PositionDto positionDto) {
        return ResponseEntity.ok().body(mapService.updateWhenEnd(positionDto));
    }
    @PatchMapping("/walking")
    public ResponseEntity<List<Landmark>> handleWalkingRequest(@RequestBody WalkDto walkDto) {
        return ResponseEntity.ok().body(mapService.updateWhileWalking(walkDto));
    }
    @GetMapping("/rank")
    public ResponseEntity<ResponseDto> getRanking() {
        return ResponseEntity.ok().body(mapService.fetchRanking());
    }
    @GetMapping("/report")
    public ResponseEntity<ReportDto> getReport() {
        return ResponseEntity.ok().body(mapService.fetchReport());
    }

}
