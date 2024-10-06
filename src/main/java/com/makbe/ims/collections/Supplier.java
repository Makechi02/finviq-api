package com.makbe.ims.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "suppliers")
public class Supplier {
    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    @CreatedDate
    private LocalDateTime addedAt;
    @CreatedBy
    private ObjectId addedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private ObjectId updatedBy;
}
