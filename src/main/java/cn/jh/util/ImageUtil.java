package cn.jh.util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.jh.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;

import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;


public class ImageUtil {
        /*String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        basePath= URLDecoder.decode(basePath,"utf-8");*/
       /* Thumbnails.of(new File("I:\\我的文件\\图片\\图标2\\QQ.png")).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File( "F:/o2o/shuiyin.png")),0.25f).outputQuality(0.8f).toFile("I:\\我的文件\\图片\\图标2\\QQ2.png");

    }*/
        public static String generateThumbnail(ImageHolder imageHolder, String targetAddr) {
            String realFileName = FileUtil.getRandomFileName();
            String extension = getFileExtension(imageHolder.getImageName());
            makeDirPath(targetAddr);
            String relativeAddr = targetAddr + realFileName + extension;
            File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
            try {
                Thumbnails.of(imageHolder.getImage()).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File( "E:/Maven_Product/o2o/shuiyin.png")),0.25f).outputQuality(0.8f).toFile(dest);
            } catch (IOException e) {
                throw new RuntimeException("创建缩略图失败：" + e.toString());
            }
            return relativeAddr;
        }

      /*  public static String generateNormalImg(CommonsMultipartFile thumbnail, String targetAddr) {
            String realFileName = FileUtil.getRandomFileName();
            String extension = getFileExtension(thumbnail);
            makeDirPath(targetAddr);
            String relativeAddr = targetAddr + realFileName + extension;
            File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
            try {
                Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
            } catch (IOException e) {
                throw new RuntimeException("创建缩略图失败：" + e.toString());
            }
            return relativeAddr;
        }

        public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
            int count = 0;
            List<String> relativeAddrList = new ArrayList<String>();
            if (imgs != null && imgs.size() > 0) {
                makeDirPath(targetAddr);
                for (CommonsMultipartFile img : imgs) {
                    String realFileName = FileUtil.getRandomFileName();
                    String extension = getFileExtension(img);
                    String relativeAddr = targetAddr + realFileName + count + extension;
                    File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
                    count++;
                    try {
                        Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
                    } catch (IOException e) {
                        throw new RuntimeException("创建图片失败：" + e.toString());
                    }
                    relativeAddrList.add(relativeAddr);
                }
            }
            return relativeAddrList;
        }*/

        private static void makeDirPath(String targetAddr) {
            String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
            File dirPath = new File(realFileParentPath);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        }

        private static String getFileExtension(String fileName) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        /**
        * 删除目标文件或目标目录下的所有文件
        */
        public static void deleteFileOrPath(String storePath){
            File file=new File(storePath);
            if (file.isDirectory()){
                File files[]=file.listFiles();
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            file.delete();
        }

}
