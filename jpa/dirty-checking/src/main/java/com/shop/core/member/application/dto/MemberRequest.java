package com.shop.core.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.core.member.domain.Member;
import com.shop.core.member.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    private String email;
    private String password;
    private int age;
    @JsonIgnore
    private MemberType memberType;

    public Member toMember() {
        return new Member(email, password, age, memberType);
    }

    public MemberRequest(String password, Integer age) {
        this.password = password;
        this.age = age;
    }

    public MemberRequest(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public static MemberRequest merge(MemberRequest request, MemberType memberType) {
        return new MemberRequest(request.getEmail(), request.getPassword(), request.getAge(), memberType);
    }

    public static MemberRequest updateOf(String password, int age) {
        return new MemberRequest(password, age);
    }

    public static MemberRequest createOf(String email, String password, int age, MemberType memberType) {
        return new MemberRequest(email, password, age, memberType);
    }
}
