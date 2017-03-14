package com.jecyhw.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jecyhw on 17-2-13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalLink {
    private String id;
    private String href;
}
