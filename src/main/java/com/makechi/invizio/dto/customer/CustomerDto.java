package com.makechi.invizio.dto.customer;

import com.makechi.invizio.dto.user.ModelUserDto;
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
