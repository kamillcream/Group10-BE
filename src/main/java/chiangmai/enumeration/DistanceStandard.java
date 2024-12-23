package chiangmai.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
public enum DistanceStandard {

    HALF(300),
    ONE(600),
    ONEHALF(1000),
    TWO(1500);

    private final int credit;
}
