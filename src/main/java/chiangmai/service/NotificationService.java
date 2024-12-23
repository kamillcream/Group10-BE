package chiangmai.service;

import chiangmai.domain.User;
import chiangmai.dto.ResponseDto;
import chiangmai.dto.PositionDto;
import chiangmai.handler.SensorWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    @Autowired
    private final SensorWebSocketHandler sensorWebSocketHandler;

    public NotificationService(SensorWebSocketHandler sensorWebSocketHandler) {
        this.sensorWebSocketHandler = sensorWebSocketHandler;
    }

//    public void sendRealTimeNotification(User user, PositionDto sensorDto) {
//        CompletableFuture.runAsync(() -> {
//            try {
//                ResponseDto.ResponseDtoBuilder responseDtoBuilder= ResponseDto.builder()
//                        .BPM(sensorDto.getBPM())
//                        .body(sensorDto.getBody())
//                        .air(sensorDto.getAir())
//                        .lan(sensorDto.getLan())
//                        .lon(sensorDto.getLon());
//                if(user.getStatus() != user.getPrevStatus()){
////                    message += "message: " + user.getName() + "님의 상태가 " + user.getPrevStatus() + "에서 " + user.getStatus() + "로 변경되었습니다."
////                            + "연락처: " + user.getPhone();
//                    responseDtoBuilder.message(user.getName() + "님의 상태가 " + user.getPrevStatus() + "단계에서 " + user.getStatus() + "단계로 변경되었습니다.")
//                            .phone(user.getPhone());
//                }
//                ResponseDto responseDto = responseDtoBuilder.build();
//                //sensorWebSocketHandler.broadcastMessage(message);
//                sensorWebSocketHandler.broadcastMessage(responseDto);
//                System.out.println("WebSocket Notification Sent.");
//            } catch (Exception e) {
//                System.err.println("Error in async notification: " + e.getMessage());
//                e.printStackTrace();
//            }
//        });
//    }

}
