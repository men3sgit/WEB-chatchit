package vn.edu.nlu.web.chat.dto.common.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PageResponse<T> implements Serializable {
    private int page;
    private int size;
    private int total;
    private T items;
}
