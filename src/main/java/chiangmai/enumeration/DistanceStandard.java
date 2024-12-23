package chiangmai.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
public enum DistanceStandard {

    TWO(2000,1500),
    ONEHALF(1500, 1000),
    ONE(1000, 600),
    HALF(500, 300),
    ZERO(0, 0);

    private final double distance;
    private final int credit;
}
