package chiangmai.docs;

import chiangmai.domain.Landmark;
import chiangmai.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name = "지도 관련 통합 API")
public interface MapDocs {

    @Operation(summary = "도보 시작", description = "도보 시작 시 호출되고 시작점, 현재 위치, 목적지의 좌표를 DB에 저장합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<StartDto> handleStartRequest(@RequestBody PositionDto positionDto);

    @Operation(summary = "도보 중", description = "도보 중 위치를 최신화하고 주위 랜드마크를 확인하고 랜덤으로 이벤트 알림(쿠폰 등)을 발생시킵니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<List<Landmark>> handleWalkingRequest(@RequestBody WalkDto walkDto);


    @Operation(summary = "도보 끝", description = "목적지 도착 후 크레딧, 총 도보량 최신화.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<Object> handleEndRequest(@RequestBody PositionDto positionDto);

    @Operation(summary = "랭킹 및 보유 크레딧 불러오기", description = "사용자의 보유 크레딧과 랭킹을 크레딧 기준으로 불러옵니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<ResponseDto> getRanking();
    @Operation(summary = "위클리 리포트", description = "한 주간 도보량 기준으로 칼로리(kcal) 연소량, 지방(g) 연소량, CO2 감소량(kg)를 제공합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<ReportDto> getReport();


}