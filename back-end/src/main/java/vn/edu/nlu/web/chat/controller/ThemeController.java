package vn.edu.nlu.web.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.ThemeService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/themes")
public class ThemeController {
    private final ThemeService themeService;
}
