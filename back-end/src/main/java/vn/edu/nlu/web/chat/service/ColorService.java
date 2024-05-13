package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.color.response.ColorCreateResponse;

public interface ColorService {

    ColorCreateResponse create(ColorCreateRequest request);
}
