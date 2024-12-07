package com.makechi.finviq.collections;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    @CreatedBy
    private ObjectId addedBy;
    @CreatedDate
    private LocalDateTime addedAt;
    @LastModifiedBy
    private ObjectId updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
