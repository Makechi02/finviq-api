package com.makbe.ims.dto.customer;

import com.makbe.ims.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomerDto {
    private String id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private ModelUserDto addedBy;
    private LocalDateTime addedAt;
    private ModelUserDto updatedBy;
    private LocalDateTime updatedAt;
}
