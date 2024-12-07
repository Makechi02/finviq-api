package com.makechi.finviq.collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.makechi.finviq.collections.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(ITEM_CREATE, ITEM_READ, ITEM_UPDATE, ITEM_DELETE, USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE)
    ),
    USER(
            Set.of(ITEM_READ, USER_READ, USER_UPDATE, USER_DELETE)
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
