package chiangmai.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
public enum DistanceStandard {

    HALF(0.5, 300),
    ONE(1.0, 600),
    ONEHALF(1.5, 1000),
    TWO(2.0,1500);

    private final double distance;
    private final int credit;
}
