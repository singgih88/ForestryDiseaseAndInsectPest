package com.jecyhw.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by jecyhw on 16-10-28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
final public class DeepZoomMetaData {
    /**
     * pivotViewer的DeepZoom的展示封面图的id
     */
    @Field("file_id")
    private String fileId;

    /**
     * 图片的宽
     */
    private Integer width;

    /**
     * 图片的高
     */
    private Integer height;
}
