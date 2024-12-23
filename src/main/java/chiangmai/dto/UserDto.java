package chiangmai.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "`name`")
    private String name;
    @Column(name = "`rank`")
    private int rank;
}
