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
        recalculateRanks();
    }
    @Transactional
    public void recalculateRanks() {
        // total 기준으로 정렬된 사용자 리스트 가져오기
        List<User> users = userRepository.findAllUsersOrderedByTotal();

        int newRank = 1; // 새로운 순위 시작
        for (User user : users) {
            // 기존 rank와 비교
            if (user.getRank() == null || user.getRank() != newRank) {

                // 새로운 rank 설정
                user.setRank(newRank);
            }
            newRank++; // 다음 순위로 증가
        }

        // 모든 변경사항 저장
        userRepository.saveAll(users);
    }
    public List<UserDto> fetchRanking(){
        List<UserDto> rankList = userRepository.findTop10ByOrderByRank(PageRequest.of(0, 10));
        return rankList;
    }
}
