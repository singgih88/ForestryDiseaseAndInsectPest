package com.jecyhw.core.upload;

/**
 * Created by jecyhw on 16-11-23.
 */
public class RecognitionFile extends UploadFile<String> {
    @Override
    public String transfer(String fileId) {
        return fileId;
    }
}
