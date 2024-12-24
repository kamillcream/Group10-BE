package chiangmai.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;
    private String password;
    @Column(name = "`name`")
    private String name;
    @Column(name = "`rank`")
    private Integer rank;
    private Long credit;
    private double total; // 총 도보량
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double currentX;
    private double currentY;
    private double prevX;
    private double prevY;
    private boolean detect;
}
