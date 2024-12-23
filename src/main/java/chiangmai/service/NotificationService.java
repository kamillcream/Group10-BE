package chiangmai.service;

import chiangmai.domain.User;
import chiangmai.dto.ResponseDto;
import chiangmai.dto.PositionDto;
import chiangmai.handler.MapWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    @Autowired
    private final MapWebSocketHandler mapWebSocketHandler;

    public NotificationService(MapWebSocketHandler mapWebSocketHandler) {
        this.mapWebSocketHandler = mapWebSocketHandler;
    }



}
