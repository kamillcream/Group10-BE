package chiangmai.controller;

import chiangmai.repository.UserRepository;
import chiangmai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public long getCredit(){
        return userService.fetchCredit();
    }

}
