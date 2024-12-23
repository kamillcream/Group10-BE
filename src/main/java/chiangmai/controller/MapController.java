package chiangmai.controller;

import chiangmai.docs.MapDocs;
import chiangmai.domain.User;
import chiangmai.dto.PositionDto;
//import iot.service.KafkaRequestProducer;
import chiangmai.dto.UserDto;
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
        mapService.updatePosition(positionDto);
        return ResponseEntity.ok().body(positionDto);
    }
    @PostMapping("/end")
    public ResponseEntity<PositionDto> handleEndRequest(@RequestBody PositionDto positionDto) {
        mapService.updateResult(positionDto);
        return ResponseEntity.ok().body(positionDto);
    }
    @GetMapping("/rank")
    public ResponseEntity<List<UserDto>> getRanking() {
        return ResponseEntity.ok().body(mapService.fetchRanking());
    }
//    @PostMapping("/start")
//    public ResponseEntity<PositionDto> handleStartRequest(@RequestBody PositionDto positionDto) {
//        kafkaTemplate.send("start", "correlationId-123", positionDto)
//                .whenComplete((result, ex) -> {
//                    if (ex == null) {
//                        System.out.println("Message sent successfully: " + result.getProducerRecord());
//                    } else {
//                        System.err.println("Message sending failed: " + ex.getMessage());
//                    }
//                });
//
//        return ResponseEntity.ok().body(positionDto);
//    }
//    @PostMapping("/end")
//    public ResponseEntity<PositionDto> handleEndRequest(@RequestBody PositionDto positionDto) {
//        kafkaTemplate.send("end", "correlationId-123", positionDto)
//                .whenComplete((result, ex) -> {
//                    if (ex == null) {
//                        System.out.println("Message sent successfully: " + result.getProducerRecord());
//                    } else {
//                        System.err.println("Message sending failed: " + ex.getMessage());
//                    }
//                });
//
//        return ResponseEntity.ok().body(positionDto);
//    }
    @PostMapping("/post/kafkaless")
    public ResponseEntity<PositionDto> handleGetRequestKafkaless(
            @RequestBody PositionDto sensorDto) {
        User user = userUtil.findUser("John");
        mapService.updatePosition(sensorDto);
        //notificationService.sendRealTimeNotification(user, sensorDto);
        return ResponseEntity.ok().body(sensorDto);
    }

}
