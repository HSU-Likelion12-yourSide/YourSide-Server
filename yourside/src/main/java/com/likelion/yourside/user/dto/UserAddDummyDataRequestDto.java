package com.likelion.yourside.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class UserAddDummyDataRequestDto {
    private Long userId;
    private boolean isExpert;
    private Long totalComments;
    private Long totalLikes;
    private Long totalPostings;
    private int deleteComments;
    private int tier;
}
