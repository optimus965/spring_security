package com.example.demo.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.user.Permissions.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Collections.EMPTY_SET),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_CREATE,
            ADMIN_DELETE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_CREATE,
            MANAGER_DELETE
    )),
    MANAGER(Set.of(
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_CREATE,
            MANAGER_DELETE
    ));
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream().map(permissions1 -> new SimpleGrantedAuthority(permissions1.name())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;

    }
}
