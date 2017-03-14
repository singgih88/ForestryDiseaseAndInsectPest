package com.jecyhw.mvc.service.impl;

import com.jecyhw.core.pivotviewer.DeepZoomFileTemplate;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.mvc.domain.BaseModel;
import com.jecyhw.mvc.domain.DeepZoomMetaData;
import com.jecyhw.mvc.domain.Picture;
import com.jecyhw.mvc.repository.BaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by jecyhw on 16-11-5.
 */
abstract public class BaseService<T extends BaseModel> {

    @Autowired
    DeepZoomFileTemplate deepZoomFileTemplate;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    abstract  protected BaseOperations<T> getRepository();

    public void save(List<T> entities) {
        getRepository().save(entities);
    }

    public void save(T entity) {
        //先检查deepZoom和pictures是否相符
        List<Picture> pictures = entity.getPictures();
        boolean deleteFlag = true;
        if (!ObjectUtils.isNull(pictures)) {//图片列表存在
            if (pictures.size() == 0) {//图片列表不为空
                entity.setPictures(ObjectUtils.nullObj());
            } else {
                entity.setDeepZoom(getDeepZoom(pictures.get(0).getFileId(), entity.getDeepZoom()));
                deleteFlag = false;
            }
        }
        if (deleteFlag && !ObjectUtils.isNull(entity.getDeepZoom())) {
            deepZoomFileTemplate.deleteDeepZoomFile(entity.getDeepZoom().getFileId());
            entity.setDeepZoom(ObjectUtils.nullObj());
        }
        getRepository().save(entity);
    }

    private DeepZoomMetaData getDeepZoom(String newFileId, DeepZoomMetaData oldDeepZoom) {
        if (!ObjectUtils.isNull(oldDeepZoom)) {
            if (oldDeepZoom.getFileId().equals(newFileId)) {
                return oldDeepZoom;
            }
            deepZoomFileTemplate.deleteDeepZoomFile(oldDeepZoom.getFileId());
        }
        deepZoomFileTemplate.asyncCreateDeepZoomFile(newFileId);
        return deepZoomFileTemplate.getDeepZoomFileMetaData(newFileId);
    }
    /**
     * @return 返回所有含pictures列表不为空的记录
     */
    public List<T> picturesSizeNotEmpty() {
        return getRepository().findByPicturesExists(true);
    }

    public T findById(String id) {
        return getRepository().findOne(id);
    }

    public T findByChineseName(String name) {
        return getRepository().findByChineseName(name);
    }

    public List<T> findBySource(String source) {
        return getRepository().findBySource(source);
    }

    public boolean deleteById(String id) {
        T entity = getRepository().findOne(id);
        if (!ObjectUtils.isNull(entity)) {
            if (!ObjectUtils.isNull(entity.getDeepZoom())) {
                //删除相关的deepZoom文件
                deepZoomFileTemplate.deleteDeepZoomFile(entity.getDeepZoom().getFileId());
            }
            //删除记录
            getRepository().delete(id);
            return true;
        }
        return false;
    }

    /**
     * 在含有deepZoom字段的的列表中提取每个的deepZoom
     * @return
     */
    public List<DeepZoomMetaData> extractDeepZoomField() {
        List<DeepZoomMetaData> result = new ArrayList<>();
        List<T> entities = getRepository().findByPicturesExists(true);

        List<Callable<Object>> calls = new ArrayList<>();
        for (T entity : entities) {
            DeepZoomMetaData deepZoom = entity.getDeepZoom();
            if (!ObjectUtils.isNull(deepZoom)) {
                if (!deepZoomFileTemplate.deepZoomFileExists(deepZoom.getFileId())) {
                    calls.add(() -> {
                        deepZoomFileTemplate.createDeepZoomFile(deepZoom.getFileId());
                        return ObjectUtils.nullObj();
                    });
                }
                result.add(deepZoom);
            }
        }
        if (calls.size() > 0) {
            try {
                threadPoolTaskExecutor.getThreadPoolExecutor().invokeAll(calls);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public DataTablesOutput<T> list(DataTablesInput input) {
        return getRepository().findAll(input);
    }
}
