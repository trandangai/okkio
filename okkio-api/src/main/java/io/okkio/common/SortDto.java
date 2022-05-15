package io.okkio.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortDto {
	
    /** The sort name. */
    @JsonProperty(value = "sortItem")
    private String sortItem;

    /** The sort type. */
    @JsonProperty(value = "sortType")
    private String sortType;
}
