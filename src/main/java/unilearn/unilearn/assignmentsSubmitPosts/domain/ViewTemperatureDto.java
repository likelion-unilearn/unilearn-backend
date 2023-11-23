package unilearn.unilearn.assignmentsSubmitPosts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewTemperatureDto {
    Long user_id;
    double temperature;
}
