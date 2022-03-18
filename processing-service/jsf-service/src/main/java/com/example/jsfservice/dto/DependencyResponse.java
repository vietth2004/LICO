package com.example.jsfservice.dto;

import com.example.jsfservice.ast.dependency.Dependency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DependencyResponse {

    List<Dependency> allDependencies;
}
