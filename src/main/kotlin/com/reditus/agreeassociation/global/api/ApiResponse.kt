package com.reditus.agreeassociation.global.api

class ApiResponse<T>(
    val result: Result,
    val data: T?,
    val message: String?,
    val errorCode: String?,
) {

    /**
     * API 요청 결과
     * SUCCESS : 성공
     * FAIL : 실패
     * [API status code]가 200이더라도 [result]가 [FAIL]일 수 있음
     * 해당 경우는 요청은 성공했지만, 비즈니스 로직상의 문제로 실패한 경우에 해당
     */
    enum class Result{
        SUCCESS,
        FAIL
    }

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(Result.SUCCESS, data, null, null)
        }

        fun <T> success(data: T, message: String): ApiResponse<T> {
            return ApiResponse(Result.SUCCESS, data, message, null)
        }

        fun logicFail(message: String, errorCode: String): ApiResponse<Unit> {
            return ApiResponse(Result.FAIL, null, message, errorCode)
        }

        fun fail(message: String, errorCode: String): ApiResponse<Unit> {
            return ApiResponse(Result.FAIL, null, message, errorCode)
        }
    }
}