package chiangmai.repository;

import chiangmai.domain.User;
import chiangmai.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u.status FROM User u WHERE u.name = :name")
//    int findStatusByName(String name);
    @Query("SELECT new chiangmai.dto.UserDto(u.userId, u.name, u.rank) FROM User u ORDER BY u.rank ASC")
    List<UserDto> findTop10ByOrderByRank(Pageable pageable);

    @Query("SELECT u FROM User u ORDER BY u.total DESC")
    List<User> findAllUsersOrderedByTotal();


    @Modifying
    @Query(value = "UPDATE user SET `rank` = (@rank := @rank + 1) ORDER BY total DESC", nativeQuery = true)
    void updateRankByTotal();

    User findUserByName(String name);
}
