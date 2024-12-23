package chiangmai.service;

import chiangmai.domain.Landmark;
import chiangmai.dto.PositionDto;
import chiangmai.domain.User;
import chiangmai.dto.ResponseDto;
import chiangmai.dto.UserDto;
import chiangmai.dto.WalkDto;
import chiangmai.enumeration.DistanceStandard;
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
    private DistanceStandard distanceStandard;

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
    public double updateWhenEnd(PositionDto positionDto){
        User user = userRepository.findUserByName("John");
        int credit = calculateCredit(positionDto);
        user.setCredit(user.getCredit() + credit);
        user.setTotal(user.getTotal() + positionDto.getEndX() - positionDto.getCurrentX());
        userRepository.save(user);
        recalculateRanks();

        return calculateDistance(positionDto.getStartX(), positionDto.getStartY(),
                positionDto.getEndX(), positionDto.getEndY());
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

    public ResponseDto fetchRanking(){
        List<UserDto> rankList = userRepository.findTop10ByOrderByRank(PageRequest.of(0, 10));
        long credit = userRepository.findCreditByName("John");
        return ResponseDto.builder()
                .credit(credit)
                .userDtos(rankList)
                .build();
    }
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = 6371000;
        // 라디안 단위로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 위도와 경도의 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // 하버사인 공식 적용
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        System.out.println(EARTH_RADIUS * c);
        // 거리 계산
        return EARTH_RADIUS * c;
    }
    public int calculateCredit(PositionDto positionDto) {
        // 거리 계산
        double distance = calculateDistance(positionDto.getStartX(), positionDto.getStartY(),
                positionDto.getEndX(), positionDto.getEndY());

        // 거리 기준에 따른 크레딧 반환
        for (DistanceStandard standard : DistanceStandard.values()) {
            if (distance >= standard.getDistance()) {
                return standard.getCredit();
            }
        }

        // 기본값 (거리가 기준을 초과할 경우)
        return 0;
    }

}
