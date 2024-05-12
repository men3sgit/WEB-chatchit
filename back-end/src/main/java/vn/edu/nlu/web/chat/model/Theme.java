package vn.edu.nlu.web.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "themes")
public class Theme extends AbstractEntity{
    @Column(name = "user_id", nullable = false)
    private Long user_id;
    @Column(name = "color_id", nullable = false)
    private Long color_id;
    @Column(name = "image_id", nullable = false)
    private Long image_id;

}
