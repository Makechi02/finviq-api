package com.makechi.finviq.config;

import com.makechi.finviq.collections.user.User;
import com.makechi.finviq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<ObjectId> {

    private final UserRepository userRepository;

    @Override
    public Optional<ObjectId> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
            return Optional.of(new ObjectId(user.getId()));
        }

        return Optional.empty();
    }
}
