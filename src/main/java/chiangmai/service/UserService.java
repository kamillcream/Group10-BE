package chiangmai.service;

import chiangmai.domain.User;
import chiangmai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public long fetchCredit(){
        User user = userRepository.findUserByName("John");
        return user.getCredit();
    }

}
