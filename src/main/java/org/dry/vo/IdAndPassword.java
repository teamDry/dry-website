package org.dry.vo;

import lombok.*;

import java.util.Objects;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IdAndPassword {
    private String id;
    private String password;
}
