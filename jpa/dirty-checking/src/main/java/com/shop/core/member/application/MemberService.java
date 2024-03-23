package com.shop.core.member.application;

import com.shop.common.exception.ErrorType;
import com.shop.core.member.application.dto.MemberRequest;
import com.shop.core.member.application.dto.MemberResponse;
import com.shop.core.member.domain.Member;
import com.shop.core.member.domain.MemberRepository;
import com.shop.core.member.exception.DuplicateEmailException;
import com.shop.core.member.exception.NotFoundMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        if (isEmailAlreadyRegistered(request.getEmail())) {
            throw new DuplicateEmailException(ErrorType.DUPLICATE_MEMBER_EMAIL);
        }
        return MemberResponse.of(memberRepository.save(request.toMember()));
    }

    public MemberResponse findMemberById(Long id) {
        return MemberResponse.of(findById(id));
    }

    @Transactional
    public void updateMember(Long id, MemberRequest request) {
        Member member = findById(id);
        member.update(request.toMember());
    }

    private Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundMemberException(ErrorType.NOT_FOUND_MEMBER));
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
}