package com.makbe.ims.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemUserDto {
    private String id;
    private String name;
}
