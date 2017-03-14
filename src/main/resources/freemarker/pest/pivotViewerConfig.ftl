<?xml version="1.0" encoding="utf-8"?>
<Collection xmlns:p="http://schemas.microsoft.com/livelabs/pivot/collection/2009" SchemaVersion="1.0" Name="虫害数据" xmlns="http://schemas.microsoft.com/collection/metadata/2009">
    <Items ImgBase="deepZoomFiles.xml">
    <#list pests as pest>
        <#if (pest.pictures??) && (pest.pictures?size > 0) && (pest.deepZoom??)>
            <Item Id="${pest.deepZoom.fileId}" Img="#${pest.deepZoom.fileId}" Name="${pest.chineseName?html}">
                <Facets>
                    <Facet Name="中文学名">
                        <String Value="${pest.chineseName?html}" />
                    </Facet>
                    <#if pest.scientificName??>
                        <Facet Name="拉丁学名">
                            <String Value="${pest.scientificName!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.kingdom??>
                        <Facet Name="界">
                            <String Value="${pest.kingdom!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.phylum??>
                        <Facet Name="门">
                            <String Value="${pest.phylum!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.subPhylum??>
                        <Facet Name="亚门">
                            <String Value="${pest.subPhylum!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.pestClass??>
                        <Facet Name="纲">
                            <String Value="${pest.pestClass!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.pestSubClass??>
                        <Facet Name="亚纲">
                            <String Value="${pest.pestSubClass!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.order??>
                        <Facet Name="目">
                            <String Value="${pest.order!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.subOrder??>
                        <Facet Name="亚目">
                            <String Value="${pest.subOrder!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.family??>
                        <Facet Name="科">
                            <String Value="${pest.family!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.subFamily??>
                        <Facet Name="亚科">
                            <String Value="${pest.subFamily!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.genus??>
                        <Facet Name="属">
                            <String Value="${pest.genus!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.subGenus??>
                        <Facet Name="亚属">
                            <String Value="${pest.subGenus!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.species??>
                        <Facet Name="种">
                            <String Value="${pest.species!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.subSpecies??>
                        <Facet Name="亚种">
                            <String Value="${pest.subSpecies!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.briefIntroduction??>
                        <Facet Name="简介">
                            <String Value="${pest.briefIntroduction!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.distributionArea??>
                        <Facet Name="分布区域">
                            <String Value="${pest.distributionArea!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.morphologicalCharacteristic??>
                        <Facet Name="形态特征">
                            <String Value="${pest.morphologicalCharacteristic!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.occurrenceRule??>
                        <Facet Name="发生规律">
                            <String Value="${pest.occurrenceRule!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.preventionMethod??>
                        <Facet Name="防治方法">
                            <String Value="${pest.preventionMethod!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.livingHabit??>
                        <Facet Name="生活习性">
                            <String Value="${pest.livingHabit!?html}" />
                        </Facet>
                    </#if>
                    <#if pest.source??>
                        <Facet Name="来源">
                            <String Value="${pest.source!?html}" />
                        </Facet>
                    </#if>
                    <#if (pest.pictures?size > 1) >
                        <Facet Name="图片">
                            <Link Name="更多" Href="pest/pictures/${pest.id}"/>
                        </Facet>
                    </#if>
                </Facets>
            </Item>
        </#if>
    </#list>
    </Items>
    <FacetCategories>
        <FacetCategory Name="拉丁学名" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />

        <FacetCategory Name="界" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="门" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚门" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="纲" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚纲" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="目" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚目" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="科" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚科" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="属" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚属" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="中文学名" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="来源" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />

        <FacetCategory Name="种" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="亚种" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="简介" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="分布区域" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="形态特征" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="发生规律" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="防治方法" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="生活习性" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="图片" Type="Link" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
    </FacetCategories>
</Collection>
