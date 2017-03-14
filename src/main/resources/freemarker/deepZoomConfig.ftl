<?xml version="1.0" encoding="UTF-8"?>
<Collection MaxLevel="${maxLevel}" TileSize="${tileSize}" Format="${tileFormat}" NextItemId="${deepZoomFiles?size}" xmlns="http://schemas.microsoft.com/deepzoom/2009">
    <Items>
        <#list deepZoomFiles as file>
            <#if file??>
            <I Id="${file.fileId}" N="${file.fileId}"  Source="images/${file.fileId}.xml"><Size Width="${file.width?c}" Height="${file.height?c}"/></I>
            </#if>
        </#list>
    </Items>
</Collection>
