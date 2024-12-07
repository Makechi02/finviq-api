package com.makechi.finviq.collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ITEM_CREATE("item:create"),
    ITEM_READ("item:read"),
    ITEM_UPDATE("item:update"),
    ITEM_DELETE("item:delete"),

    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete");

    private final String permission;
}
