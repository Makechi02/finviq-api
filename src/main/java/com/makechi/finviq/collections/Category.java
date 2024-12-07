package com.makechi.finviq.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private ObjectId createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private ObjectId updatedBy;

}
