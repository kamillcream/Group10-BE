package chiangmai.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DistanceStandard{

    HALF(300),
    ONE(600),
    ONEHALF(1000),
    TWO(1500);

    private int credit;
}
