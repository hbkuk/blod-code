package com.shop.core.coupon.domain;

import com.shop.common.exception.ErrorType;
import com.shop.core.coupon.exception.CouponExhaustedException;
import com.shop.core.coupon.exception.CouponIssuanceNotAllowedException;
import com.shop.core.coupon.exception.CouponStatusChangeNotAllowedException;
import com.shop.core.issuedCoupon.domain.IssuedCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int maxDiscountAmount;

    private int discountAmount;

    private int remainingIssueCount;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @JoinColumn(name = "ADMIN_EMAIL")
    private String issuerAdminEmail;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<IssuedCoupon> issuedCoupons = new ArrayList<>();

    @Version
    private Integer version;

    public Coupon(String name, String description, int maxDiscountAmount, int discountAmount, int remainingIssueCount, LocalDateTime createdAt, CouponStatus couponStatus, String issuerAdminEmail) {
        this.name = name;
        this.description = description;
        this.maxDiscountAmount = maxDiscountAmount;
        this.discountAmount = discountAmount;
        this.remainingIssueCount = remainingIssueCount;
        this.createdAt = createdAt;
        this.couponStatus = couponStatus;
        this.issuerAdminEmail = issuerAdminEmail;
    }

    public void issueCoupon(List<IssuedCoupon> issuedCoupons) {
        checkRemainingIssueCoupon(issuedCoupons);
        checkIssuableStatus();

        this.issuedCoupons.addAll(issuedCoupons);
        this.remainingIssueCount -= issuedCoupons.size();
        if (this.remainingIssueCount == 0) {
            this.couponStatus = CouponStatus.EXHAUSTED;
        }
    }

    private void checkIssuableStatus() {
        if (this.couponStatus != CouponStatus.ISSUABLE) {
            throw new CouponIssuanceNotAllowedException(ErrorType.COUPON_ISSUANCE_NOT_ALLOWED);
        }
    }

    private void checkRemainingIssueCoupon(List<IssuedCoupon> issuedCoupons) {
        if (remainingIssueCount < issuedCoupons.size()) {
            throw new CouponExhaustedException(ErrorType.COUPON_EXHAUSTED);
        }
    }

    public Coupon updateStatus(CouponStatus status) {
        if (!this.couponStatus.isStatusChangeAllowed(status)) {
            throw new CouponStatusChangeNotAllowedException(ErrorType.COUPON_STATUS_CHANGE_NOT_ALLOWED);
        }

        this.couponStatus = status;
        return this;
    }
}
