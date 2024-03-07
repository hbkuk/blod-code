package demo.core.subway.station.acceptance.nonRefactor;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import demo.core.subway.station.application.dto.StationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철역 관련 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StationAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    /**
     * When 지하철 역을 생성하면
     * Then 지하철 역이 생성된다
     * Then 지하철 역 목록 조회 시 생성한 역을 확인할 수 있다.
     */
    @DisplayName("지하철 역을 생성한다.")
    @Test
    void createStation() {
        // given
        String 가산디지털단지역 = "가산디지털단지역";

        Map<String, String> params = new HashMap<>();
        params.put("name", 가산디지털단지역);

        // when
        RestAssured
                .given().log().all()
                    .body(params)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/stations")
                .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());

        // then
        List<String> stationNames = RestAssured.given()
                .when()
                    .get("/stations")
                .then()
                    .extract().jsonPath().getList("name", String.class);

        assertThat(stationNames).containsExactly(가산디지털단지역);
    }

    /**
     * Given 2개의 지하철 역을 생성하고
     * When 지하철 역 목록을 조회하면
     * Then 지하철 역 목록 조회 시 생성한 2개의 역을 확인할 수 있다.
     */
    @Test
    @DisplayName("지하철역 2개를 생성하고, 목록을 조회한다.")
    void showStations() {
        //given
        String 가산디지털단지역 = "가산디지털단지역";

        Map<String, String> firstParams = new HashMap<>();
        firstParams.put("name", 가산디지털단지역);

        RestAssured
                .given().log().all()
                    .body(firstParams)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/stations")
                .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());

        String 구로디지털단지역 = "구로디지털단지역";

        Map<String, String> secondParams = new HashMap<>();
        secondParams.put("name", 구로디지털단지역);

        RestAssured
                .given().log().all()
                    .body(secondParams)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/stations")
                .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());

        // when
        List<String> stationNames = RestAssured.given()
                .when()
                .get("/stations")
                .then()
                .extract().jsonPath().getList("name", String.class);

        // then
        assertThat(stationNames).containsExactly(가산디지털단지역, 구로디지털단지역);
    }

    /**
     * Given 지하철 역을 생성하고
     * When  생성된 지하철 역을 삭제하면
     * Then  지하철 역 목록 조회 시 생성한 역을 확인할 수 없다.
     */
    @Test
    @DisplayName("지하철역이 생성되고, 삭제한다.")
    void deleteStation() {
        //given
        String 가산디지털단지역 = "가산디지털단지역";

        Map<String, String> params = new HashMap<>();
        params.put("name", 가산디지털단지역);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .body(params)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/stations")
                .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                .extract();

        long createdLocationId = Long.parseLong(response.header(HttpHeaders.LOCATION)
                .substring(response.header(HttpHeaders.LOCATION).lastIndexOf('/') + 1));

        List<String> namesBeforeDelete = RestAssured.given()
                .when()
                .get("/stations")
                .then().log().all()
                .extract().jsonPath().getList("name", String.class);

        assertThat(namesBeforeDelete).containsExactly(가산디지털단지역);

        // when
        RestAssured
                .when()
                    .delete("/stations/" + createdLocationId)
                .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        List<String> namesAfterDelete = RestAssured.given()
                .when()
                .get("/stations")
                .then().log().all()
                .extract().jsonPath().getList("name", String.class);

        assertThat(namesAfterDelete).doesNotContain(가산디지털단지역);

    }
}