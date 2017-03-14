package com.jecyhw.mvc.service.impl;

import com.jecyhw.config.imageserver.ImageServerProperties;
import com.jecyhw.core.response.Response;
import com.jecyhw.core.upload.PictureFile;
import com.jecyhw.core.upload.RecognitionFile;
import com.jecyhw.core.upload.UploadFile;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.core.util.UrlUtils;
import com.jecyhw.mvc.domain.Pest;
import com.jecyhw.mvc.service.PestImagesService;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by jecyhw on 16-11-29.
 */
@Configuration
@Service
@EnableConfigurationProperties(ImageServerProperties.class)
public class PestImagesServiceImpl implements PestImagesService {

    private final String createUrl;
    private final String uploadUrl;
    private final String recognitionUrl;
    private final String pictureUrl;
    private final RestTemplate restTemplate;
    private final AsyncRestTemplate asyncRestTemplate;

    @Override
    public String getPictureUrl() {
        return pictureUrl;
    }

    public PestImagesServiceImpl(ImageServerProperties imageServerProperties, HttpMessageConverters messageConverters) {
        String basePath = imageServerProperties.getBasePath();
        createUrl = UrlUtils.buildUrl(basePath, "pest", "create");
        uploadUrl = UrlUtils.buildUrl(basePath, "pest", "upload");
        recognitionUrl = UrlUtils.buildUrl(basePath, "pest", "recognition");
        pictureUrl = UrlUtils.buildUrl(basePath, "picture");
        restTemplate = new RestTemplate(messageConverters.getConverters());

        asyncRestTemplate = new AsyncRestTemplate();
        asyncRestTemplate.setMessageConverters(messageConverters.getConverters());
    }

    @Override
    public void create(Pest pest) {
        try {
            HttpEntity<Pest> pestHttpEntity = new HttpEntity<>(pest);
            asyncRestTemplate.postForLocation(createUrl, pestHttpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upload(String pestId, PictureFile pictureFile) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", pictureFile.getTitle());
        try {
            asyncRestTemplate.postForEntity(UrlUtils.buildUrl(uploadUrl, pestId), multipartForm(pictureFile, map), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<?> recognition(RecognitionFile recognitionFile) {
        try {
            return restTemplate.postForEntity(recognitionUrl, multipartForm(recognitionFile), Response.class).getBody();
        } catch (IOException e) {
            return Response.fail("图片识别失败");
        }
    }


    private HttpEntity<MultiValueMap<String, Object>> multipartForm(UploadFile<?> uploadFile) throws IOException {
        return multipartForm(uploadFile, uploadFile.getFile().getOriginalFilename(), ObjectUtils.nullObj());
    }

    private HttpEntity<MultiValueMap<String, Object>> multipartForm(UploadFile<?> uploadFile, String fileName) throws IOException {
        return multipartForm(uploadFile, fileName, ObjectUtils.nullObj());
    }

    private HttpEntity<MultiValueMap<String, Object>> multipartForm(UploadFile<?> uploadFile, MultiValueMap<String, Object> map) throws IOException {
        return multipartForm(uploadFile, uploadFile.getFile().getOriginalFilename(), map);
    }

    private HttpEntity<MultiValueMap<String, Object>> multipartForm(UploadFile<?> uploadFile, String fileName, MultiValueMap<String, Object> map) throws IOException {
        MultipartFile file = uploadFile.getFile();
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentDispositionFormData("file", URLEncoder.encode(fileName, "UTF-8"));

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("file", new HttpEntity<>(new ByteArrayResource(file.getBytes()), fileHeaders));
        if (!ObjectUtils.isNull(map)) {
            for (MultiValueMap.Entry<String, List<Object>> entry : map.entrySet()) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return multipartForm(params);
    }

    private HttpEntity<MultiValueMap<String, Object>> multipartForm(MultiValueMap<String, Object> params) throws IOException {
        return httpEntity(params, MediaType.MULTIPART_FORM_DATA);
    }

    private HttpEntity<MultiValueMap<String, Object>> httpEntity(MultiValueMap<String, Object> params, MediaType mediaType) {
        return new HttpEntity<>(params, httpHeaders(mediaType));
    }

    private HttpHeaders httpHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }
}
