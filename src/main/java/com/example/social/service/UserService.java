package com.example.social.service;

import com.example.social.domain.User;
import com.example.social.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    public void saveUser(OAuth2User oAuth2User) {
        User user = new User();
        user.setId(oAuth2User.getName());
        user.setName(oAuth2User.getAttribute("name"));
        user.setUserpic(oAuth2User.getAttribute("picture"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setGender(oAuth2User.getAttribute("gender"));
        user.setLocale(oAuth2User.getAttribute("locale"));
        user.setLastVisit(LocalDateTime.now());

        userDetailsRepo.save(user);
    }

//    public OidcUser loadUser(OidcUserRequest userRequest) {
//        OidcUserService delegate = new OidcUserService();
//        OidcUser oidcUser = delegate.loadUser(userRequest);
//
//        // Сохранение пользователя в базе данных
//        saveUser(oidcUser);
//
//        String email = oidcUser.getAttribute("email");
//        return new CustomOidcUser(oidcUser, email);
//    }

}
