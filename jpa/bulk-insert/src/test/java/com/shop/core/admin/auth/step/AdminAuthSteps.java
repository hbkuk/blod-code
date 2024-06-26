package com.shop.core.admin.auth.step;

import com.shop.core.admin.auth.presentation.dto.AdminGithubCodeRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminAuthSteps {

    public static ExtractableResponse<Response> 깃허브_토큰_발급_요청(AdminGithubCodeRequest 깃허브_코드_정보) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(깃허브_코드_정보)
                .when()
                .post("/admin/login/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static void 관리자_토큰_발급_확인(ExtractableResponse<Response> 로그인_요청_응답) {
        String 토큰 = 로그인_요청_응답.jsonPath().getString("accessToken");

        assertThat(토큰).isNotBlank();
    }
}
