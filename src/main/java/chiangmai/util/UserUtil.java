package chiangmai.util;

import chiangmai.dto.PositionDto;
import chiangmai.enumeration.DistanceStandard;
import chiangmai.repository.UserRepository;
import chiangmai.domain.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static chiangmai.util.MapUtil.calculateDistance;


@Component
public class UserUtil {
    @Autowired
    private final UserRepository userRepository;


    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findUser(String name){
        return userRepository.findUserByName(name);
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

    public int calculateCredit(PositionDto positionDto) {
        // 거리 계산
        double distance = calculateDistance(positionDto.getStartY(), positionDto.getStartX(),
                positionDto.getEndY(), positionDto.getEndX());

        // 거리 기준에 따른 크레딧 반환
        for (DistanceStandard standard : DistanceStandard.values()) {
            if (distance >= standard.getDistance()) {
                return standard.getCredit();
            }
        }

        // 기본값 (거리가 기준을 초과할 경우)
        return 0;
    }
    public static double calculateKcal(double total){
        return total * 65 * 0.8;
    }
    public static double calculateFat(double total){
        return calculateKcal(total) * 0.4 / 0.9;
    }
    public static double calculateC02(double total){
        return total * 0.2;
    }
}
