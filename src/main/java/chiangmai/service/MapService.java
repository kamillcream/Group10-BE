package chiangmai.service;

import chiangmai.domain.Landmark;
import chiangmai.dto.*;
import chiangmai.domain.User;
import chiangmai.enumeration.DistanceStandard;
import chiangmai.repository.UserRepository;
import chiangmai.util.MapUtil;
import chiangmai.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static chiangmai.util.MapUtil.calculateDistance;
import static chiangmai.util.UserUtil.*;

@Service
@RequiredArgsConstructor
public class MapService {
    private final UserRepository userRepository;
    private final LandmarkService landmarkService;
    private final UserUtil userUtil;
    private final MapUtil mapUtil;
    private DistanceStandard distanceStandard;

    @Transactional
    public StartDto updateWhenStart(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        user.setStartX(positionDto.getStartX());
        user.setStartY(positionDto.getStartY());
        user.setCurrentX(positionDto.getCurrentX());
        user.setCurrentY(positionDto.getCurrentY());
        user.setPrevX(positionDto.getCurrentX());
        user.setPrevY(positionDto.getCurrentY());
        user.setEndX(positionDto.getEndX());
        user.setEndY(positionDto.getEndY());
        user.setDetect(0);
        userRepository.save(user);

        double distance = calculateDistance(positionDto.getStartY(), positionDto.getStartX(),
                positionDto.getEndY(), positionDto.getEndX());
        long credit = calculateCredit(positionDto);
        return StartDto.builder()
                .credit(credit)
                .distance(distance)
                .build();
    }

    @Transactional
    public List<Landmark> updateWhileWalking(WalkDto walkDto){
        User user = userRepository.findUserByName("John");
        user.setPrevX(user.getCurrentX());
        user.setPrevY(user.getCurrentY());
        user.setCurrentX(walkDto.getCurrentX());
        user.setCurrentY(walkDto.getCurrentY());
        if(calculateDistance(user.getPrevX(), user.getPrevY(), user.getCurrentX(), user.getCurrentY()) >= 100){
            user.setDetect(1);
        }
        userRepository.save(user);
        return landmarkService.fetchNearbyLandmarks(walkDto);
    }
    @Transactional
    public boolean updateWhenEnd(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        int credit = calculateCredit(positionDto);
        double distance = calculateDistance(positionDto.getStartY(), positionDto.getStartX(),
                positionDto.getEndY(), positionDto.getEndX());

        if(user.getDetect() == 1){
            return false;
        }
        user.setCredit(user.getCredit() + credit);
        user.setTotal(user.getTotal() + (distance / 1000));
        userRepository.save(user);
        recalculateRanks();

        return true;
    }
    /*@Transactional
    public double updateWhenEnd(double distance){
        User user = userRepository.findUserByName("John");
        int credit = calculateCredit(distance);
        user.setCredit(user.getCredit() + credit);
        user.setTotal(user.getTotal() + distance);
        userRepository.save(user);
        recalculateRanks();

        return calculateDistance(positionDto.getStartX(), positionDto.getStartY(),
                positionDto.getEndX(), positionDto.getEndY());
    }*/
    @Transactional
    public void recalculateRanks() {
        // total 기준으로 정렬된 사용자 리스트 가져오기
        List<User> users = userRepository.findAllUsersOrderedByCredit();

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

    public ResponseDto fetchRanking(){
        List<UserDto> rankList = userRepository.findTop10ByOrderByRank(PageRequest.of(0, 10));
        long credit = userRepository.findCreditByName("John");
        return ResponseDto.builder()
                .credit(credit)
                .userDtos(rankList)
                .build();
    }
    public ReportDto fetchReport(){
        User user = userRepository.findUserByName("John");
        return ReportDto.builder()
                .kcal(calculateKcal(user.getTotal()))
                .fat(calculateFat(user.getTotal()))
                .co2(calculateC02(user.getTotal()))
                .build();
    }

//    public int calculateCredit(double distance) {
//        // 거리 기준에 따른 크레딧 반환
//        for (DistanceStandard standard : DistanceStandard.values()) {
//            if (distance >= standard.getDistance()) {
//                return standard.getCredit();
//            }
//        }
//
//        // 기본값 (거리가 기준을 초과할 경우)
//        return 0;
//    }


}
