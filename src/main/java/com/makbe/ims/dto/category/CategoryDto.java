package com.makbe.ims.dto.category;

import com.makbe.ims.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDto {
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private ModelUserDto createdBy;
    private LocalDateTime updatedAt;
    private ModelUserDto updatedBy;
}
