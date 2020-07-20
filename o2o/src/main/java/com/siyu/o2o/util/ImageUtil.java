package com.siyu.o2o.util;

import com.siyu.o2o.dto.ImageHolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ImageUtil {

   private static String basePath = "/home/ec2-user/work";
   private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");
   private static final Random random = new Random();
   private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);


   public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
      File newFile = new File(cFile.getOriginalFilename());

      try {
         cFile.transferTo(newFile);
      } catch (IllegalStateException var3) {
         logger.error(var3.toString());
         var3.printStackTrace();
      } catch (IOException var4) {
         logger.error(var4.toString());
         var4.printStackTrace();
      }

      return newFile;
   }

   public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
      String realFileName = getRandomFileName();
      String extension = getFileExtension(thumbnail.getImageName());
      makeDirPath(targetAddr);
      String relativeAddr = targetAddr + realFileName + extension;
      logger.debug("current relativeAddr is:" + relativeAddr);
      File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
      logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);

      try {
         System.out.println(basePath + "/watermark.jpg");
         Thumbnails.of(new InputStream[]{thumbnail.getImage()}).size(500, 500).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25F).outputQuality(0.8F).toFile(dest);
      } catch (IOException var7) {
         logger.error(var7.toString());
         var7.printStackTrace();
      }

      return relativeAddr;
   }

   public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
      String realFileName = getRandomFileName();
      String extension = getFileExtension(thumbnail.getImageName());
      makeDirPath(targetAddr);
      String relativeAddr = targetAddr + realFileName + extension;
      logger.debug("current relativeAddr is :" + relativeAddr);
      File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
      logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);

      try {
         Thumbnails.of(new InputStream[]{thumbnail.getImage()}).size(650, 900).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25F).outputQuality(0.9F).toFile(dest);
         return relativeAddr;
      } catch (IOException var7) {
         logger.error(var7.toString());
         throw new RuntimeException("创建缩图片失败：" + var7.toString());
      }
   }

   private static void makeDirPath(String targetAddr) {
      String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
      File dirPath = new File(realFileParentPath);
      if(!dirPath.exists()) {
         dirPath.mkdirs();
      }

   }

   public static String getRandomFileName() {
      int randomNum = random.nextInt(89999) + 10000;
      String nowTimeStr = sDateFormat.format(new Date());
      return nowTimeStr + randomNum;
   }

   private static String getFileExtension(String fileName) {
      return fileName.substring(fileName.lastIndexOf("."));
   }

   public static void main(String[] args) throws IOException {}

   public static void deleteFileOrPath(String storePath) {
      File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
      if(fileOrPath.exists()) {
         if(fileOrPath.isDirectory()) {
            File[] files = fileOrPath.listFiles();

            for(int i = 0; i < files.length; ++i) {
               files[i].delete();
            }
         }

         fileOrPath.delete();
      }

   }

}
