package com.jecyhw.mvc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Document(collection = "pest")
@Data
@NoArgsConstructor
@AllArgsConstructor
final public class Pest extends BaseModel {

    /**
     * 中文学名
     */
    @NotBlank
    @Indexed(unique = true)
    @Field("chinese_name")
    private String chineseName;

    /**
     * 拉丁学名
     */
    @Field("scientific_name")
    private String scientificName;

    /**
     * 界
     */
    private String kingdom;

    /**
     * 门
     */
    private String phylum;

    /**
     * 亚门
     */
    @Field("subphylum")
    private String subPhylum;

    /**
     * 纲
     */
    @Field("class")
    @JsonProperty("class")
    private String pestClass;

    /**
     * 亚纲
     */
    @Field("subclass")
    @JsonProperty("subClass")
    private String pestSubClass;

    /**
     * 目
     */
    private String order;

    /**
     * 亚目
     */
    @Field("suborder")
    private String subOrder;

    /**
     * 科
     */
    private String family;

    /**
     * 亚科
     */
    @Field("subfamily")
    private String subFamily;

    /**
     * 属
     */
    private String genus;

    /**
     * 亚属
     */
    @Field("subgenus")
    private String subGenus;

    /**
     * 种
     */
    private String species;

    /**
     * 亚种
     */
    @Field("subspecies")
    private String subSpecies;

    /**
     * 简介
     */
    @Field("brief_introduction")
    private String briefIntroduction;

    /**
     * 分布区域
     */
    @Field("distribution_area")
    private String distributionArea;

    /**
     * 形态特征
     */
    @Field("morphological_characteristic")
    private String morphologicalCharacteristic;

    /**
     * 发生规律
     */
    @Field("occurrence_rule")
    private String occurrenceRule;

    /**
     * 防治方法
     */
    @Field("prevention_method")
    private String preventionMethod;

    /**
     * 生活习性
     */
    @Field("living_habit")
    private String livingHabit;
}
