package com.jecyhw;

import com.jecyhw.core.file.GridFsFile;
import com.jecyhw.mvc.domain.Pest;
import com.jecyhw.mvc.domain.Picture;
import com.jecyhw.mvc.repository.PestRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jecyhw on 16-11-25.
 */
@Component
public class Download {
    static final Logger logger = LoggerFactory.getLogger(Download.class);
    @Autowired
    PestRepository pestRepository;

    @Autowired
    GridFsFile gridFsFile;

    public void download(String parentPath) {
        List<Pest> pests = pestRepository.findByPicturesExists(true);
        for (Pest pest : pests) {
            File file = Paths.get(parentPath, pest.getId()).toFile();
            logger.info(file.getAbsolutePath());
            for (Picture picture : pest.getPictures()) {
                InputStream inputStream = gridFsFile.findByFileIdAsInputStream(picture.getFileId());
                if (inputStream != null) {
                    try {
                        FileUtils.copyInputStreamToFile(inputStream, Paths.get(file.getPath(), picture.getFileId() + picture.getName().substring(picture.getName().indexOf('.'))).toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
