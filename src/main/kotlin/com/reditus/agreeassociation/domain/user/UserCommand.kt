package com.reditus.agreeassociation.domain.user

import com.reditus.agreeassociation.domain.util.SelfValidating
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class UserCommand {
    class Create(
        @field:Email(message = "이메일 형식이 아닙니다.")
        val email: String,
        @field:NotBlank(message = "비밀번호를 입력해주세요.")
        val password: String,
        @field:NotBlank(message = "닉네임을 입력해주세요.")
        val nickname: String,
    ): SelfValidating(){
        init {
            this.validateSelf()
        }
    }
    class Patch(
        val nickname: String?,
        val profileImageUrl: String?,
    ){
        init {
            if(nickname == null && profileImageUrl == null){
                throw IllegalArgumentException("수정할 내용이 없습니다.")
            }
            nickname?.let {
                if(it.isBlank()){
                    throw IllegalArgumentException("닉네임을 입력해주세요.")
                }
            }
            profileImageUrl?.let {
                if(it.isBlank()){
                    throw IllegalArgumentException("프로필 이미지 URL이 올바르지 않습니다.")
                }
            }
        }
    }
}