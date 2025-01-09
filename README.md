import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DimensionResponse {
    private String dimension;
    private String income;
    private String rank;
    private List<Risk> riesgos;
}

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Risk {
    private String idTipoRiesgo;
    private String name;
    private String tope;
}
