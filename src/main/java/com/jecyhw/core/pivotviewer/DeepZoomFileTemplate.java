package com.jecyhw.core.pivotviewer;

import com.jecyhw.core.file.GridFsFile;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.mvc.domain.DeepZoomMetaData;
import gov.nist.isg.archiver.DirectoryArchiver;
import gov.nist.isg.archiver.FilesArchiver;
import gov.nist.isg.pyramidio.BufferedImageReader;
import gov.nist.isg.pyramidio.PartialImageReader;
import gov.nist.isg.pyramidio.ScalablePyramidBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Created by jecyhw on 16-11-1.
 */
public class DeepZoomFileTemplate {

    @Autowired
    private GridFsFile fsFile;

    private final String rootPath;
    private final int tileSize;
    private final int overlap;
    private final String tileFormat;
    private final String descriptorExt;

    @PostConstruct
    public void initMethod() {
        File rootDirectory = new File(rootPath);
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
    }

    /**
     *
     * @param rootPath deepZoomFile文件的根目录
     * @param tileSize  图片切片的大小
     * @param overlap 图片切片间重叠宽度
     * @param tileFormat 图片切片的格式
     * @param descriptorExt 图片切片说明文件的格式
     */
    public DeepZoomFileTemplate(String rootPath, int tileSize, int overlap, String tileFormat, String descriptorExt) {
        this.rootPath = rootPath;
        this.tileSize = tileSize;
        this.overlap = overlap;
        this.tileFormat = tileFormat;
        this.descriptorExt = descriptorExt;
    }

    public boolean deepZoomFileExists(String fileId) {
        File file = Paths.get(rootPath, fileId + "." + descriptorExt).toFile();
        return file.exists() && file.isFile();
    }

    @Async
    public void asyncCreateDeepZoomFile(String fileId) {
        createDeepZoomFile(fileId);
    }

    /**
     * 对图片进行切图生成层级图片
     * @param fileId
     */
    public void createDeepZoomFile(String fileId) {
        InputStream inputStream = fsFile.findByFileIdAsInputStream(fileId);
        if (!ObjectUtils.isNull(inputStream)) {
            try {
                PartialImageReader pir = new BufferedImageReader(ImageIO.read(inputStream));
                ScalablePyramidBuilder spb = new ScalablePyramidBuilder(tileSize, overlap, tileFormat, descriptorExt);
                FilesArchiver archiver = new DirectoryArchiver(new File(rootPath));
                spb.buildPyramid(pir, fileId, archiver, 1, 1.0F);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDeepZoomFile(String fileId) {
        FileUtils.deleteQuietly(Paths.get(rootPath, fileId + "_files").toFile());
        FileUtils.deleteQuietly(Paths.get(rootPath, fileId + "." + descriptorExt).toFile());
    }

    public DeepZoomMetaData getDeepZoomFileMetaData(String fileId) {
        InputStream inputStream = fsFile.findByFileIdAsInputStream(fileId);
        if (!ObjectUtils.isNull(inputStream)) {
            try {
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                return createDeepZoomFileMetaData(fileId, bufferedImage.getWidth(), bufferedImage.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ObjectUtils.nullObj();
    }

    private DeepZoomMetaData createDeepZoomFileMetaData(String fileId, Integer width, Integer height) {
        DeepZoomMetaData deepZoomMetaData = new DeepZoomMetaData();
        deepZoomMetaData.setFileId(fileId);
        deepZoomMetaData.setWidth(width);
        deepZoomMetaData.setHeight(height);
        return deepZoomMetaData;
    }
}
