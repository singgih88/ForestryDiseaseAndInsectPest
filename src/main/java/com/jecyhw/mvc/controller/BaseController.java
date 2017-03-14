package com.jecyhw.mvc.controller;

import com.jecyhw.config.App;
import com.jecyhw.core.freemarker.Freemarker;
import com.jecyhw.core.response.Response;
import com.jecyhw.core.upload.PictureFile;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.core.validation.ValidationMessageCodeResolver;
import com.jecyhw.mvc.domain.BaseModel;
import com.jecyhw.mvc.domain.Picture;
import com.jecyhw.mvc.service.PictureService;
import com.jecyhw.mvc.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jecyhw on 16-11-6.
 */
abstract class BaseController<T extends BaseModel> {

    final static String DISEASE = "disease";
    final static String PEST = "pest";

    @Autowired
    App app;

    @Autowired
    PictureService pictureService;

    @Autowired
    Freemarker freemarker;

    @Autowired
    ValidationMessageCodeResolver validationMessageCodeResolver;

    abstract BaseService<T> getService();

    /**
     * 控制器的所属类型,是disease还是pest
     * @return
     */
    abstract String getType();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    ModelAndView modelAndViewWithPrefix(String view) {
        return modelAndView(addTypeAtStringPrefix("/" + view));
    }

    ModelAndView modelAndView(String view) {
        return new ModelAndView(view, "type", getType());
    }

    public ModelAndView show() {
        return modelAndView("show");
    }

    public ModelAndView pivotViewer() {
        return modelAndView("pivotViewer");
    }

    public ModelAndView view() {
        return modelAndView("view");
    }

    public ModelAndView createGet() {
        return modelAndView("create");
    }

    public ModelAndView editGet() {
        return modelAndView("edit");
    }

    public DataTablesOutput<T> list(DataTablesInput input) {
        return getService().list(input);
    }

    public Response<?> uploadFile(String id, PictureFile pictureFile) {
        T entity = getService().findById(id);
        if (ObjectUtils.isNull(entity)) {
            return Response.notFound("未找到图片的所属记录");
        }
        Picture picture =  pictureService.create(pictureFile);
        if (ObjectUtils.isNull(picture)) {
            return Response.fail("图片保存失败");
        }
        List<Picture> pictures = entity.getPictures();
        if (ObjectUtils.isNull(pictures)) {
            pictures = new ArrayList<>();
            entity.setPictures(pictures);
        }
        pictures.add(picture);
        getService().save(entity);
        return Response.success(picture);
    }

    public Response<?> createPost(T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.bindingError(validationMessageCodeResolver.bindingResultToSimple(bindingResult));
        }
        if (ObjectUtils.isNull(entity.getId())) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            entity.setSource(app.getName());
            entity.setCreator(user.getUsername());
        } else {//更新
            T queryEntity = getService().findById(entity.getId());
            entity.setDeepZoom(queryEntity.getDeepZoom());
            entity.setPictures(queryEntity.getPictures());
            entity.setSource(queryEntity.getSource());
            entity.setCreator(queryEntity.getCreator());
        }
        getService().save(entity);
        return Response.success(entity);
    }

    public Response<?> deletePicture(String id, String fileId) throws IOException {
        T entity = getService().findById(id);
        if (!ObjectUtils.isNull(entity)) {
            List<Picture> pictures = entity.getPictures();
            Picture picture;
            if (!ObjectUtils.isNull(pictures)) {
                for (int i = 0; i < pictures.size(); i++) {
                    picture = pictures.get(i);
                    if (picture.getFileId().equals(fileId)) {
                        pictures.remove(i);
                        getService().save(entity);
                        return Response.success("记录包含的图片删除成功");
                    }
                }
            }
            return Response.notFound("未找到图片的所属记录");
        }
        return Response.notFound("未找到记录");
    }

    public Response<?> delete(String id) {
        getService().deleteById(id);
        return Response.success("删除成功");
    }

    public Response<?> queryByName(String name) {
        T entity = getService().findByChineseName(name);
        if (ObjectUtils.isNull(entity)) {
            return Response.notFound("未找到记录");
        }
        return Response.success(entity);
    }

    public ResponseEntity<?> deepZoomConfig() {
        Map<String, Object> root = new HashMap<>();
        root.put("deepZoomFiles", getService().extractDeepZoomField());
        return ResponseEntity.ok().body(freemarker.process("deepZoomConfig.ftl", root));
    }

    /**
     * @return
     */
    public ResponseEntity<?> pivotViewerConfig() {
        Map<String, Object> root = new HashMap<>();
        List<T> entities = getService().picturesSizeNotEmpty();
        for (T entity : entities) {
            entity.setSource(entity.getSource().equals(app.getName()) ? app.getAlias() : "其它");
        }
        root.put(addTypeAtStringPrefix("s"), entities);
        return ResponseEntity.ok().body(freemarker.process(addTypeAtStringPrefix("/pivotViewerConfig.ftl"), root));
    }

    public Response<?> pictures(String id) {
        T entity = getService().findById(id);
        if (!ObjectUtils.isNull(entity)) {
            return Response.success(entity.getPictures());
        } else {
            return Response.notFound("未找到记录");
        }
    }

    /**
     * @param value
     * @return 给字符串添加类型前缀
     */
    private String addTypeAtStringPrefix(String value) {
        return getType() + value;
    }
}
