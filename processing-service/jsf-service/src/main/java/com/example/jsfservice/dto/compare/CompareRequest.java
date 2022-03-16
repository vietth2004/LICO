package com.example.jsfservice.dto.compare;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompareRequest {
    private String oldPath;
    private String newPath;
}
