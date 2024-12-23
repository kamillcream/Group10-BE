package chiangmai.service;

import chiangmai.dto.PositionDto;
import chiangmai.domain.User;
import chiangmai.dto.UserDto;
import chiangmai.repository.UserRepository;
import chiangmai.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    public MapService(UserRepository userRepository, UserUtil userUtil){
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Transactional
    public void updatePosition(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        user.setCurrentX(positionDto.getCurrentX());
        user.setCurrentY(positionDto.getCurrentY());
        userRepository.save(user);
    }
    @Transactional
    public void updateResult(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        user.setTotal(user.getTotal() + positionDto.getEndX() - positionDto.getCurrentX());
        userRepository.save(user);
    }
    public List<UserDto> fetchRanking(){
        List<UserDto> rankList = userRepository.findTop10ByOrderByRank(PageRequest.of(0, 10));
        return rankList;
    }
}
