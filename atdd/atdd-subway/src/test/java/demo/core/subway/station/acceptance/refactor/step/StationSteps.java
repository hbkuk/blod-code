package demo.core.subway.station.acceptance.refactor.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import demo.core.subway.station.application.dto.StationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static demo.common.utils.HttpResponseUtils.getCreatedLocationId;
import static org.assertj.core.api.Assertions.assertThat;

public class StationSteps {

    public static void 지하철_역_삭제_요청(ExtractableResponse<Response> 지하철_역_생성_요청_응답) {
        RestAssured
                .when()
                .delete("/stations/" + getCreatedLocationId(지하철_역_생성_요청_응답))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 지하철_역_생성_요청(String stationName) {
        return RestAssured
                .given()
                .body(new StationRequest(stationName))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/stations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static List<String> 지하철_역_이름_목록_조회() {
        return RestAssured.given()
                .when()
                .get("/stations")
                .then()
                .extract().jsonPath().getList("name", String.class);
    }

    public static void 지하철_역_이름_있음_확인(String... 지하철_역_이름) {
        assertThat(지하철_역_이름_목록_조회()).containsExactly(지하철_역_이름);
    }

    public static void 지하철_역_이름_없음_확인(String... 지하철_역_이름) {
        assertThat(지하철_역_이름_목록_조회()).doesNotContain(지하철_역_이름);
    }
}
