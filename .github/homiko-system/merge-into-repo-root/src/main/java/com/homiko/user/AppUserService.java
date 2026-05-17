package com.homiko.user;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> list() {
        return appUserRepository.findAll().stream().map(UserResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getById(long id) {
        AppUser user =
                appUserRepository
                        .findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (appUserRepository.existsByEmailIgnoreCase(request.email().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exists");
        }
        AppUser user = new AppUser();
        user.setEmail(request.email().trim().toLowerCase());
        user.setDisplayName(request.displayName().trim());
        AppUser saved = appUserRepository.save(user);
        return UserResponse.from(saved);
    }

    @Transactional
    public void delete(long id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        appUserRepository.deleteById(id);
    }
}
