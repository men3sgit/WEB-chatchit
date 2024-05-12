package vn.edu.nlu.web.chat.service.impl;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;
import vn.edu.nlu.web.chat.enums.EntityStatus;
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
        themeupdate.setColorId(request.getColor_id());
        themeupdate.setImageId(request.getImage_id());
        themeRepository.save(themeupdate);
    }

    @Override
    public void delete(Long id) {
        Theme themeupdate = getThemById(id);
        themeupdate.setColorId(1L);
        themeupdate.setImageId(1L);
        themeRepository.save(themeupdate);
    }

    private Theme getThemById (Long id){
        return themeRepository.findThemesByIdAndEntityStatusIsNot(id, EntityStatus.DELETED).orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.not-exist")));
    }
}
