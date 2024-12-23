package chiangmai.service;

import chiangmai.dto.SensorDto;
import chiangmai.domain.User;
import chiangmai.repository.UserRepository;
import chiangmai.util.StatusStandard;
import chiangmai.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static chiangmai.util.StatusStandard.*;

@Service
public class SensorService {
    private UserRepository userRepository;
    private UserUtil userUtil;

    public SensorService(UserRepository userRepository, UserUtil userUtil){
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Transactional
    public void updateStatus(SensorDto sensorDto, User user){
        user.setPrevStatus(user.getStatus());
        int overNum = userUtil.calculateValue(sensorDto, userUtil.judgeAge(user));
        user.setStatus(overNum);
        userRepository.save(user);
    }
}
