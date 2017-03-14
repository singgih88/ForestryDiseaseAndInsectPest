package com.jecyhw.core.upload;


import com.jecyhw.mvc.domain.Picture;

/**
 * 上传文件是图片
 * Created by jecyhw on 16-11-3.
 */
public class PictureFile extends UploadFile<Picture>{

    /**
     * 图片标题
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Picture transfer(String fileId) {
        Picture picture = new Picture();
        picture.setTitle(getTitle());
        picture.setFileId(fileId);
        picture.setName(getFile().getOriginalFilename());
        return picture;
    }
}
