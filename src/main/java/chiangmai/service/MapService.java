package chiangmai.service;

import chiangmai.domain.Landmark;
import chiangmai.dto.PositionDto;
import chiangmai.domain.User;
import chiangmai.dto.UserDto;
import chiangmai.dto.WalkDto;
import chiangmai.repository.UserRepository;
import chiangmai.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final UserRepository userRepository;
    private final LandmarkService landmarkService;
    private final UserUtil userUtil;

    @Transactional
    public void updateWhenStart(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        user.setStartX(positionDto.getStartX());
        user.setStartY(positionDto.getStartY());
        user.setCurrentX(positionDto.getCurrentX());
        user.setCurrentY(positionDto.getCurrentY());
        user.setEndX(positionDto.getEndX());
        user.setEndY(positionDto.getEndY());
        userRepository.save(user);
        recalculateRanks();
    }

    @Transactional
    public List<Landmark> updateWhileWalking(WalkDto walkDto){
        User user = userRepository.findUserByName("John");
        user.setCurrentX(walkDto.getCurrentX());
        user.setCurrentY(walkDto.getCurrentY());
        userRepository.save(user);
        return landmarkService.fetchNearbyLandmarks(walkDto);
    }
    @Transactional
    public void updateWhenEnd(PositionDto positionDto){
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
