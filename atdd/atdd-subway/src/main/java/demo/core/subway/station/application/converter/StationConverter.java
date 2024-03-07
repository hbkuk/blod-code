package demo.core.subway.station.application.converter;


import demo.core.subway.station.application.dto.StationResponse;
import demo.core.subway.station.domain.Station;

public class StationConverter {

    public static StationResponse convertToResponse(Station station) {
        return new StationResponse(
                station.getId(),
                station.getName()
        );
    }
}
