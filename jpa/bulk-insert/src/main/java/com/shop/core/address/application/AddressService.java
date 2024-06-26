package com.shop.core.address.application;

import com.shop.common.exception.ErrorType;
import com.shop.core.address.domain.Address;
import com.shop.core.address.domain.AddressRepository;
import com.shop.core.address.exception.NotFoundAddressException;
import com.shop.core.address.presentation.dto.AddressRequest;
import com.shop.core.address.presentation.dto.AddressResponse;
import com.shop.core.auth.domain.LoginUser;
import com.shop.core.member.application.MemberService;
import com.shop.core.member.domain.Member;
import com.shop.core.member.exception.NonMatchingMemberException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private AddressRepository addressRepository;
    private MemberService memberService;

    @Transactional
    public AddressResponse create(AddressRequest request, LoginUser loginUser) {
        Member member = memberService.findMemberByEmail(loginUser.getEmail());

        if (request.getIsDefault()) {
            updateExistingDefault(member);
        }

        Address savedAddress = addressRepository.save(request.toEntity(member.getId()));
        return convertAddressResponse(savedAddress);
    }

    public AddressResponse findById(Long addressId, LoginUser loginUser) {
        Member member = memberService.findMemberByEmail(loginUser.getEmail());
        Address address = findAddressById(addressId);

        if (!address.isOwn(member)) {
            throw new NonMatchingMemberException(ErrorType.NON_MATCHING_MEMBER);
        }

        return AddressResponse.of(address);
    }

    @Transactional
    public AddressResponse update(AddressRequest request, LoginUser loginUser) {
        Member member = memberService.findMemberByEmail(loginUser.getEmail());
        Address address = findAddressById(request.getId());

        if (!address.isOwn(member)) {
            throw new NonMatchingMemberException(ErrorType.NON_MATCHING_MEMBER);
        }
        if (request.getIsDefault()) {
            updateExistingDefault(member);
        }

        return AddressResponse.of(updateAddress(request, address));
    }

    @Transactional
    public void updateDefaultAddress(Long addressId, LoginUser loginUser) {
        Member member = memberService.findMemberByEmail(loginUser.getEmail());
        Address address = findAddressById(addressId);

        if (!address.isOwn(member)) {
            throw new NonMatchingMemberException(ErrorType.NON_MATCHING_MEMBER);
        }

        updateExistingDefault(member);
        address.updateDefault(true);
    }

    public List<AddressResponse> findAll(LoginUser loginUser) {
        Member member = memberService.findMemberByEmail(loginUser.getEmail());
        List<Address> addresses = addressRepository.findAllByMemberId(member.getId());

        return addresses.stream().map(AddressResponse::of).collect(Collectors.toList());
    }

    private Address updateAddress(AddressRequest request, Address address) {
        return address.update(request.getAddress(), request.getDetailedAddress(), request.getDescription(), request.getIsDefault());
    }

    private void updateExistingDefault(Member member) {
        Address defaultAddress = addressRepository.findDefaultAddressByMemberId(member.getId());
        if (defaultAddress != null) {
            defaultAddress.updateDefault(false);
        }
    }

    private Address findAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundAddressException(ErrorType.NOT_FOUND_ADDRESS));
    }

    private AddressResponse convertAddressResponse(Address savedAddress) {
        return AddressResponse.of(savedAddress.getId(), savedAddress.getAddress(), savedAddress.getDetailedAddress(), savedAddress.getDescription(), savedAddress.isDefault());
    }
}
