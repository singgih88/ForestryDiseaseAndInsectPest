<?xml version="1.0" encoding="utf-8"?>
<Collection xmlns:p="http://schemas.microsoft.com/livelabs/pivot/collection/2009" SchemaVersion="1.0" Name="病害数据" xmlns="http://schemas.microsoft.com/collection/metadata/2009">
    <Items ImgBase="deepZoomFiles.xml">
    <#list diseases as disease>
        <#if (disease.pictures??) && (disease.pictures?size > 0) && (disease.deepZoom??)>
            <Item Id="${disease.deepZoom.fileId}" Img="#${disease.deepZoom.fileId}" Name="${disease.chineseName?html}">
                <Facets>
                    <Facet Name="中文名">
                        <String Value="${disease.chineseName?html}" />
                    </Facet>
                    <#if disease.alias??>
                        <Facet Name="别名">
                            <String Value="${disease.alias!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.scientificName??>
                        <Facet Name="英文名">
                            <String Value="${disease.scientificName!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.distributionArea??>
                        <Facet Name="分布(分布范围)">
                            <String Value="${disease.distributionArea!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.symptom??>
                        <Facet Name="症状(病害症状,危害情况)">
                            <String Value="${disease.symptom!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.briefIntroduction??>
                        <Facet Name="简介(基本信息)">
                            <String Value="${disease.briefIntroduction!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.pathogeny??>
                        <Facet Name="病原(病原中文名)">
                            <String Value="${disease.pathogeny!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.pathogenyEnglish??>
                        <Facet Name="病原英文名(病原拉丁学名,病原学名)">
                            <String Value="${disease.pathogenyEnglish!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.pathogenicType??>
                        <Facet Name="病原类型">
                            <String Value="${disease.pathogenicType!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.damagePlant??>
                        <Facet Name="为害植物(主要危害作物,寄主)">
                            <String Value="${disease.damagePlant!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.diseasePosition??>
                        <Facet Name="为害部位(主要为害部位)">
                            <String Value="${disease.diseasePosition!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.transmissionWay??>
                        <Facet Name="传播方式(传播途径)">
                            <String Value="${disease.transmissionWay!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.occurrenceRule??>
                        <Facet Name="发病规律(发生特点,发生规律)">
                            <String Value="${disease.occurrenceRule!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.preventionMethod??>
                        <Facet Name="防治方法">
                            <String Value="${disease.preventionMethod!?html}" />
                        </Facet>
                    </#if>
                    <#if disease.source??>
                        <Facet Name="来源">
                            <String Value="${disease.source!?html}" />
                        </Facet>
                    </#if>
                    <#if (disease.pictures?size > 1) >
                        <Facet Name="图片">
                            <Link Name="更多" Href="disease/pictures/${disease.id}"/>
                        </Facet>
                    </#if>
                </Facets>
            </Item>
        </#if>
    </#list>
    </Items>
    <FacetCategories>
        <FacetCategory Name="中文名" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />
        <FacetCategory Name="来源" Type="String" p:IsFilterVisible="true" p:IsWordWheelVisible="true" p:IsMetaDataVisible="true" />

        <FacetCategory Name="病原类型" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="别名" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="英文名" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="分布(分布范围)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="症状(病害症状,危害情况)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="简介(基本信息)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="病原" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="病原英文名(病原拉丁学名,病原学名)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="为害植物(主要危害作物,寄主)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="为害部位(主要为害部位)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="传播方式(传播途径)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="发病规律(发生特点,发生规律)" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="防治方法" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
        <FacetCategory Name="图片" Type="String" p:IsFilterVisible="false" p:IsWordWheelVisible="false" p:IsMetaDataVisible="true" />
    </FacetCategories>
</Collection>
