package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;

public interface ThemeService {
    void update(Long id, ThemeUpdateRequest request);
}
