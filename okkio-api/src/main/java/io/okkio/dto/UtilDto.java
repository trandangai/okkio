package io.okkio.dto;

import io.okkio.domain.Util;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UtilDto {
    private List<Util> grinds;
    private List<Util> sizes;
    private List<Util> subscriptions;
}