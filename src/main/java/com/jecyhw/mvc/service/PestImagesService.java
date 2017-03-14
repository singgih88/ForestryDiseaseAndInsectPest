package com.jecyhw.mvc.service;

import com.jecyhw.core.response.Response;
import com.jecyhw.core.upload.PictureFile;
import com.jecyhw.core.upload.RecognitionFile;
import com.jecyhw.mvc.domain.Pest;

/**
 * Created by jecyhw on 16-11-29.
 */
public interface PestImagesService {

    String getPictureUrl();

    void create(Pest pest);

    void upload(String pestId, PictureFile pictureFile);

    Response<?> recognition(RecognitionFile recognitionFile);
}
