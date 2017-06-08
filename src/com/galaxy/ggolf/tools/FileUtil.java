package com.galaxy.ggolf.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.galaxy.ggolf.dto.FileList;
import com.galaxy.ggolf.jdbc.CommonConfig;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import com.sun.javafx.scene.layout.region.Margins.Converter;

public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	private static final int Max_FileLength = 1;
	
	/**
	 * 检查文件路径是否存在,否侧创建
	 * @param filePath
	 */
	public static void createDirIfNotExist(String filePath) {
		File theDir = new File(filePath);
		// if the directory does not exist, create it
		logger.debug("creating directory: {}",filePath);
		boolean result = false;

		try {
			if (theDir.exists() && theDir.isDirectory()) {

			} else {
				theDir.mkdirs();
				result = true;
			}
			if (result) {
				System.out.println("DIR created");
			}
		} catch (SecurityException se) {
			// handle it
		}
	}
	
	/**
	 * 修改文件名称
	 * @param header
	 * @param fname
	 * @return
	 */
	public static String getFileName(MultivaluedMap<String, String> header,String fname) {
		logger.debug("headers-----{}",header);
		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				logger.debug("文件名-----{}",filename);
				String name = filename.split("=")[1].trim().replaceAll("\"", "");
				String exactFileName = fname + name.substring(name.lastIndexOf("."),name.length());;
				return exactFileName;
			}
		}
		return null;
	}
	
	/**
	 * 图片转码
	 * @param imgFilePath 图片路径
	 * @return
	 */
	public static String GetImageStr(String imgFilePath){
		byte[] data = null;
		try {
			imgFilePath = CommonConfig.FILE_UPLOAD_PATH + imgFilePath.substring(imgFilePath.lastIndexOf("/")+1, imgFilePath.length());
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
	/**
	 * 保存图片
	 * @param base64Img 图片base码
	 * @param fileName 图片文件名称
	 * @param filePath 图片保存路径
	 * @return
	 */
	public static boolean GetImageUrl(String base64Img, String fileName, String filePath){
		boolean result = false;
		try {
			createDirIfNotExist(filePath);
			if(base64Img.indexOf("data:image/")!=-1){
				base64Img = base64Img.split(",")[1];
				BufferedImage image = null;
				byte[] imgByte;
				BASE64Decoder decoder = new BASE64Decoder();
				imgByte = decoder.decodeBuffer(base64Img);
				ByteArrayInputStream bis = new ByteArrayInputStream(imgByte);
				image = ImageIO.read(bis);
				bis.close();
				File outputfile = new File(filePath+fileName);
				deleteFile(outputfile);
				ImageIO.write(image, "png", outputfile);
				result = true;
			}
//			String url = CommonConfig.CONNECT+CommonConfig.FILE_DOWNLOAD+fileName;
//			return url;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 
	 * @param path 文件路径
	 * @param str  开始删除的字符
	 * @param con 追加的文本
	 */
	public static void replaceContentToFile(String path,String str,String con){
		try {
			FileReader read = new FileReader(path);
			BufferedReader br = new BufferedReader(read);
			StringBuilder content = new StringBuilder();
			while (br.ready()!=false) {
				content.append(br.readLine());
				content.append("\r\n");
			}
			logger.debug("文件内容----{}",content.toString());
			int dex = content.indexOf(str);
			if(dex != -1){
				content.delete(dex, content.length());
			}
			content.append(con);
			br.close();
			read.close();
			FileOutputStream fs = new FileOutputStream(path);
			fs.write(content.toString().getBytes());
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(File root){ //删除指定文件夹下的文件
        File files[] = root.listFiles();
        if(files != null &&files.length > Max_FileLength){
            for(File f : files){
                if(f.isDirectory()){ //判断是否为文件夹
                    deleteFile(f);
                    try {
                        f.delete();
                        logger.debug( "deleteAllFile: 文件：----{}",f.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    if(f.exists()){  //判断是否存在
                        deleteFile(f);
                        try {
                            f.delete();
                            logger.debug("deleteAllFile: 文件：----{}",f.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            logger.debug("deleteAllFile: 删除成功");
        }else{
        	if(root.exists()){
        		try {
        			root.delete();
                    logger.debug("deleteAllFile: 文件：----{}",root.toString());
                    logger.debug("deleteAllFile: 删除成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
        	}
        }
    }

	 /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
	public static String thumbnailImage(File imgFile, int w, int h, String prevfix, boolean force){
        if(imgFile.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if(imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0){
                	logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return null;
                }
                logger.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(imgFile);
                if(!force){
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                String thumbFile = p.substring(0,p.lastIndexOf(File.separator)) + File.separator + prevfix +imgFile.getName();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, new File(thumbFile));
                return prevfix + imgFile.getName();
            } catch (IOException e) {
            	logger.error("generate thumbnail image failed.",e);
            	return null;
            }
        }else{
        	logger.warn("the image is not exist.");
        	return "文件不存在";
        }
    }
}
