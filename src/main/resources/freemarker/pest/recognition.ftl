<#if (pests??) && (pests?size == 0)>
<div>未识别出图片</div>
<#else>
<div class="row">
    <#assign count = 1>
    <#list pests as pest>
        <#if (pest.pictures?size > 1) >
            <div class="col-xs-6 col-sm-4 col-md-3">
                <div class="thumbnail">
                    <a class="picture" href="#" title="查看更多图片"><img style="height: 200px;" src="${pictureUrl}/${pest.pictures[0].fileId}">
                        <div class="more">
                            <#list pest.pictures as p>
                                <span data-url="${pictureUrl}/${p.fileId}" data-title="${p.title!?html}"></span>
                            </#list>
                        </div>
                    </a>
                    <a href="#" class="btn btn-link" title="查看详细信息" data-toggle="modal" data-target="#${pest.id}">(${count}). ${pest.chineseName?html}</a>
                    <#assign count++>
                    <div class="modal fade" id="${pest.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                                    <h4 class="modal-title">${pest.chineseName?html}</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <#if pest.scientificName??>
                                            <div class="col-sm-3">
                                                <h4>拉丁学名</h4>
                                                <p>${pest.scientificName!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.kingdom??>
                                            <div class="col-sm-3">
                                                <h4>界</h4>
                                                <p>${pest.kingdom!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.phylum??>
                                            <div class="col-sm-3">
                                                <h4>门</h4>
                                                <p>${pest.phylum!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.subPhylum??>
                                            <div class="col-sm-3">
                                                <h4>亚门</h4>
                                                <p>${pest.subPhylum!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.pestClass??>
                                            <div class="col-sm-3">
                                                <h4>纲</h4>
                                                <p>${pest.pestClass!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.pestSubClass??>
                                            <div class="col-sm-3">
                                                <h4>亚纲</h4>
                                                <p>${pest.pestSubClass!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.order??>
                                            <div class="col-sm-3">
                                                <h4>目</h4>
                                                <p>${pest.order!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.subOrder??>
                                            <div class="col-sm-3">
                                                <h4>亚目</h4>
                                                <p>${pest.subOrder!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.family??>
                                            <div class="col-sm-3">
                                                <h4>科</h4>
                                                <p>${pest.family!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.subFamily??>
                                            <div class="col-sm-3">
                                                <h4>亚科</h4>
                                                <p>${pest.subFamily!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.genus??>
                                            <div class="col-sm-3">
                                                <h4>属</h4>
                                                <p>${pest.genus!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.subGenus??>
                                            <div class="col-sm-3">
                                                <h4>亚属</h4>
                                                <p>${pest.subGenus!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.species??>
                                            <div class="col-sm-3">
                                                <h4>种</h4>
                                                <p>${pest.species!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.subSpecies??>
                                            <div class="col-sm-3">
                                                <h4>亚种</h4>
                                                <p>${pest.subSpecies!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.briefIntroduction??>
                                            <div class="col-sm-12">
                                                <h4>简介</h4>
                                                <p>${pest.briefIntroduction!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.distributionArea??>
                                            <div class="col-sm-12">
                                                <h4>分布区域</h4>
                                                <p>${pest.distributionArea!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.morphologicalCharacteristic??>
                                            <div class="col-sm-12">
                                                <h4>形态特征</h4>
                                                <p>${pest.morphologicalCharacteristic!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.occurrenceRule??>
                                            <div class="col-sm-12">
                                                <h4>发生规律</h4>
                                                <p>${pest.occurrenceRule!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.preventionMethod??>
                                            <div class="col-sm-12">
                                                <h4>防治方法</h4>
                                                <p>${pest.preventionMethod!?html}</p>
                                            </div>
                                        </#if>
                                        <#if pest.livingHabit??>
                                            <div class="col-sm-12">
                                                <h4>生活习性</h4>
                                                <p>${pest.livingHabit!?html}</p>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
    </#list>
</div>
</#if>
