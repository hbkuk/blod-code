package demo.core.subway.station.acceptance.refactor;

import demo.common.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static demo.core.subway.station.acceptance.refactor.step.StationSteps.*;

@DisplayName("지하철역 관련 기능")
public class StationAcceptanceTest extends AcceptanceTest {
    /**
     * When 지하철 역을 생성하면
     * Then 지하철 역이 생성된다
     * Then 지하철 역 목록 조회 시 생성한 역을 확인할 수 있다.
     */
    @DisplayName("지하철역을 생성한다.")
    @Test
    void 지하철_역_생성() {
        // given
        var 지하철_역_이름 = "가산디지털단지역";

        // when
        지하철_역_생성_요청(지하철_역_이름);

        // then
        지하철_역_이름_있음_확인(지하철_역_이름);
    }

    /**
     * Given 2개의 지하철 역을 생성하고
     * When 지하철 역 목록을 조회하면
     * Then 지하철 역 목록 조회 시 생성한 2개의 역을 확인할 수 있다.
     */
    @Test
    @DisplayName("지하철역 2개를 생성하고, 목록을 조회한다.")
    void 지하철_역_목록_조회() {
        //given
        var 가산디지털단지역 = "가산디지털단지역";
        var 구로디지털단지역 = "구로디지털단지역";

        지하철_역_생성_요청(가산디지털단지역);
        지하철_역_생성_요청(구로디지털단지역);

        // when, then
        지하철_역_이름_있음_확인(가산디지털단지역, 구로디지털단지역);
    }

    /**
     * Given 지하철 역을 생성하고
     * When  생성된 지하철 역을 삭제하면
     * Then  지하철 역 목록 조회 시 생성한 역을 확인할 수 없다.
     */
    @Test
    @DisplayName("지하철역이 생성되고, 삭제된다.")
    void deleteStation() {
        //given
        var 가산디지털단지역 = "가산디지털단지역";

        var 지하철_역_생성_요청_응답 = 지하철_역_생성_요청(가산디지털단지역);

        // when
        지하철_역_삭제_요청(지하철_역_생성_요청_응답);

        // then
        지하철_역_이름_없음_확인(가산디지털단지역);
    }
}