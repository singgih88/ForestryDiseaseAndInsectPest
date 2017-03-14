package com.jecyhw.mvc.controller;

import com.jecyhw.core.response.Response;
import com.jecyhw.core.upload.PictureFile;
import com.jecyhw.mvc.domain.Disease;
import com.jecyhw.mvc.domain.Pest;
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

/**
 * Created by jecyhw on 16-11-5.
 */
@RequestMapping(value = "disease")
@RestController
public class DiseaseController extends BaseController<Disease> {

    @Autowired
    BaseService<Disease> diseaseService;

    @Override
    BaseService<Disease> getService() {
        return diseaseService;
    }

    @Override
    String getType() {
        return BaseController.DISEASE;
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
    public DataTablesOutput<Disease> list(@Valid DataTablesInput input) {
        return super.list(input);
    }

    @RequestMapping(value = "uploadFile/{id}", method = RequestMethod.POST)
    public Response<?> uploadFile(@PathVariable("id") String diseaseId, PictureFile pictureFile) {
        return super.uploadFile(diseaseId, pictureFile);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Response<?> createPost(@Valid Disease disease, BindingResult bindingResult) {
        return super.createPost(disease, bindingResult);
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

    @RequestMapping(value = "pictures/{diseaseId}", method = { RequestMethod.GET })
    public Response<?> pictures(@PathVariable String diseaseId) {
        return super.pictures(diseaseId);
    }
}
