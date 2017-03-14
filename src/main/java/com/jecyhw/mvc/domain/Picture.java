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
final public class Picture {
    /**
     *  图片在mongo数据库的文件索引,引用的fs.files的_id
     */
    @Field("file_id")
    private String fileId;

    /**
     * 图片名
     */
    private String name;

    /**
     * 图片标题
     */
    private String title;
}
