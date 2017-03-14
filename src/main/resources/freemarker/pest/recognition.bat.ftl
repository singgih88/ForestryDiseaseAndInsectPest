<#if (pests??) && (pests?size == 0)>
<div>未识别出图片</div>
<#else>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <#list pests as pest>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="${pest.id}h">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#${pest.id}" aria-expanded="true" aria-controls="${pest.id}">
                    ${pest.chineseName?html}
                    </a>
                </h4>
            </div>
            <div id="${pest.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${pest.id}h">
                <div class="panel-body">
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
                        <#if (pest.pictures?size > 1) >
                            <div class="col-xs-6 col-sm-3">
                                <h4>图片</h4>
                                <div>
                                    <a href="#" class="thumbnail" title="查看更多图片">
                                        <img src="${pictureUrl}/${pest.pictures[0].fileId}">
                                        <div class="more">
                                            <#list pest.pictures as p>
                                                <span data-url="${pictureUrl}/${p.fileId}" data-title="${p.title!?html}"></span>
                                            </#list>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
</#if>
