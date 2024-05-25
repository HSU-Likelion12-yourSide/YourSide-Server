package com.likelion.yourside.domain;

import com.likelion.yourside.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Worksheet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private String title; // 제목
    @Column(columnDefinition = "TEXT")
    private String content; // 내용
    @Column(name = "total_pay")
    private int totalPay; // 총급여(월급)
    @Column(name ="extra_pay")
    private boolean extraPay; // 가산 수당 발생 여부
    @Column(name ="week_pay")
    private boolean weekPay; // 주휴 수당 발생 여부
    @Column(name="night_pay")
    private boolean nightPay; // 야간 수당 발생 여부
    @Column(name="overtime_pay")
    private boolean overtimePay; // 연장 근로 수당 발생 여부
    @Column(name="holiday_pay")
    private boolean holidayPay; // 휴일 근로 수당 발생 여부
    private Boolean isOpen; // 결과지 공개 여부
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void changeIsOpen() {this.isOpen = true;}
}
