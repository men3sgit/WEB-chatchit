package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.chat.response.ChatCreateResponse;
import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.color.response.ColorCreateResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Chat;
import vn.edu.nlu.web.chat.model.Color;
import vn.edu.nlu.web.chat.repository.ColorRepository;
import vn.edu.nlu.web.chat.service.ColorService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.Date;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private ColorRepository colorRepository;
    @Override
    public ColorCreateResponse create(ColorCreateRequest request) {
        if(existColorByValue(request.getValue()))
            throw new ApiRequestException("Color existed");
        var newColor = DataUtils.copyProperties(request, Color.class);
        colorRepository.save(newColor);
        return DataUtils.copyProperties(newColor,ColorCreateResponse.class);
    }

    private boolean existColorByValue (String value){
        return colorRepository.existsByValueAndEntityStatusIsNot(value, EntityStatus.DELETED).isPresent();
    }
}
