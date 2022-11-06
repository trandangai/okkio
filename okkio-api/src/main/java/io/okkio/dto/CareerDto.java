package io.okkio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org.wildfly.common.annotation.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto extends IdDto {

    private String name;
    private String description;
    private Boolean isPartTime;
    private Boolean isFullTime;
    private String status;
}