package com.example.social.Contoller;

import com.example.social.repo.MessageRepo;
import com.example.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;
    private final UserService userService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(MessageRepo messageRepo, UserService userService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        HashMap<Object, Object> data = new HashMap<>();

        if (oidcUser != null) {
            System.out.println("Authenticated user: " + oidcUser.getEmail());
            System.out.println("User attributes: " + oidcUser.getAttributes());
            userService.saveUser(oidcUser);
            data.put("profile", oidcUser);
        } else {
            System.out.println("User is null");
            data.put("profile", null);
        }

        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }


}

