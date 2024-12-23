package chiangmai.util;

import chiangmai.dto.PositionDto;
import chiangmai.repository.UserRepository;
import chiangmai.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


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

}
