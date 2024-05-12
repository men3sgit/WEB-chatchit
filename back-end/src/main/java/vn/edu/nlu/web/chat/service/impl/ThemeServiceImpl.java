package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Theme;
import vn.edu.nlu.web.chat.repository.ThemeRepository;
import vn.edu.nlu.web.chat.service.ThemeService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    @Override
    public void update(Long id, ThemeUpdateRequest request) {
        Theme themeupdate = getThemById(id);
        themeupdate.setColor_id(request.getColor_id());
        themeupdate.setImage_id(request.getImage_id());
        themeRepository.save(themeupdate);
    }

    private Theme getThemById (Long id){
        return themeRepository.findThemesById(id).orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.not-exist")));
    }
}
