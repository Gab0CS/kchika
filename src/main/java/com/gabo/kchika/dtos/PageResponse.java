package com.gabo.kchika.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageResponse {

    private String title;
    private LocalDateTime dateCreation;
    private List<PostResponse> posts;

}
