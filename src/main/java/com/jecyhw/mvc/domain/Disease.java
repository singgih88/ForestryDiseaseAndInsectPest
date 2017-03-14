package com.jecyhw.mvc.domain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by jecyhw on 16-9-6.
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
final public class Disease extends BaseModel{
    /**
     * 中文名
     */
    @NotBlank
    @Indexed(unique = true)
    @Field("chinese_name")
    private String chineseName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 英文名
     */
    @Field("scientific_name")
    private String scientificName;

    /**
     * 分布(分布范围)
     */
    @Field("distribution_area")
    private String distributionArea;

    /**
     * 症状(病害症状,危害情况)
     */
    private String symptom;

    /**
     * 简介(基本信息)
     */
    @Field("brief_introduction")
    private String briefIntroduction;

    /**
     * 病原(病原中文名)
     */
    private String pathogeny;


    /**
     * 病原英文名(病原拉丁学名,病原学名)
     */
    @Field("pathogeny_english")
    private String pathogenyEnglish;

    /**
     * 病原类型
     */
    @Field("pathogenic_type")
    private String pathogenicType;

    /**
     * 为害植物(主要危害作物,寄主)
     */
    @Field("damage_plant")
    private String damagePlant;

    /**
     * 为害部位(主要为害部位)
     */
    @Field("disease_position")
    private String diseasePosition;


    /**
     * 传播方式(传播途径)
     */
    @Field("transmission_way")
    private String transmissionWay;

    /**
     * 发病规律(发生特点,发生规律)
     */
    @Field("occurrence_rule")
    private String occurrenceRule;

    /**
     * 防治方法
     */
    @Field("prevention_method")
    private String preventionMethod;
}
