package com.jecyhw.mvc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jecyhw.core.response.Code;
import com.jecyhw.core.response.Response;
import com.jecyhw.core.upload.PictureFile;
import com.jecyhw.core.upload.RecognitionFile;
import com.jecyhw.mvc.domain.Pest;
import com.jecyhw.mvc.service.PestImagesService;
import com.jecyhw.mvc.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jecyhw on 16-10-28.
 * FIXME: 增删改
 */
@RestController
@RequestMapping(value = "pest")
public class PestController extends BaseController<Pest> {

    @Autowired
    BaseService<Pest> pestService;

    @Override
    BaseService<Pest> getService() {
        return pestService;
    }

    @Autowired
    PestImagesService pestImagesService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    String getType() {
        return BaseController.PEST;
    }

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public ModelAndView show() {
        return super.show();
    }

    @RequestMapping(value = "pivotViewer", method = RequestMethod.GET)
    public ModelAndView pivotViewer() {
        return super.pivotViewer();
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView createGet() {
        return super.createGet();
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editGet() {
        return super.editGet();
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView view() {
        return super.view();
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataTablesOutput<Pest> list(@Valid DataTablesInput input) {
        return super.list(input);
    }

    @RequestMapping(value = "recognition", method = RequestMethod.GET)
    public ModelAndView recognitionPage() {
        return modelAndViewWithPrefix("recognition");
    }

    @RequestMapping(value = "uploadFile/{id}", method = RequestMethod.POST)
    public Response<?> uploadFile(@PathVariable("id") String pestId, PictureFile pictureFile) {
        Response<?> response = super.uploadFile(pestId, pictureFile);
        if (response.getCode().equals(Code.SUCCESS.getCode())) {
            pestImagesService.upload(pestId, pictureFile);
        }
        return response;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Response<?> createPost(@Valid Pest pest, BindingResult bindingResult) {
        Response<?> response = super.createPost(pest, bindingResult);
        if (response.getCode().equals(Code.SUCCESS.getCode())) {
            pestImagesService.create(pest);
        }
        return response;
    }

    @RequestMapping(value = "delete/{id}/picture/{fileId}", method = RequestMethod.POST)
    public Response<?> deletePicture(@PathVariable String id, @PathVariable String fileId) throws IOException {
        return super.deletePicture(id, fileId);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public Response<?> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public Response<?> queryByName(@RequestParam String name) {
        return super.queryByName(name);
    }

    @RequestMapping(value = "deepZoomFiles.xml", method = RequestMethod.GET)
    public ResponseEntity<?> deepZoomConfig() {
        return super.deepZoomConfig();
    }

    /**
     * @return
     */
    @RequestMapping(value = "pivotViewer.xml", method = RequestMethod.GET)
    public ResponseEntity<?> pivotViewerConfig() {
        return super.pivotViewerConfig();
    }

    @RequestMapping(value = "pictures/{pestId}", method = { RequestMethod.GET })
    public Response<?> pictures(@PathVariable String pestId) {
        return super.pictures(pestId);
    }

    @RequestMapping(value = "recognition", method = RequestMethod.POST)
    public Response<?> recognition(RecognitionFile recognitionFile) {
        Response<?> response = pestImagesService.recognition(recognitionFile);
        if (response.getCode().equals(Code.SUCCESS.getCode())) {
            Map<String, Object> root = new HashMap<>();
            root.put("pests", response.getMessage());
            root.put("pictureUrl", pestImagesService.getPictureUrl());
            return Response.success(root);
        }
        return response;
    }

}
