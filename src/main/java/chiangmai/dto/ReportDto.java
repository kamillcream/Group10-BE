package chiangmai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReportDto {
    private double kcal;
    private double fat;
    private double co2;
}
