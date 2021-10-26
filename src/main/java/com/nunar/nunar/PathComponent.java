package com.nunar.nunar;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class PathComponent {

    @Value("${nunar.path}")
    private String path;

}
