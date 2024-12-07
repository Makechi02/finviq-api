package com.makechi.finviq.dto.category;

import com.makechi.finviq.dto.user.ModelUserDto;
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
