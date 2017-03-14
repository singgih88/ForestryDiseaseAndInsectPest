package com.jecyhw.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jecyhw on 16-11-6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel implements Serializable{
    @Id
    private String id;

    /**
     * pivotViewer的展示图片,图片如果未指定,默认使用pictures里面第一张图片,如果存在的话。
     */
    @Field("deep_zoom")
    private DeepZoomMetaData deepZoom;
    /**
     * 图片列表
     */

    private List<Picture> pictures;

    /**
     * 记录的来源.祁连/北山/百度百科
     */
    private String source;

    /**
     * 记录创建人
     */
    private String creator;
}
