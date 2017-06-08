package com.galaxy.ggolf.rest;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.froala.editor.Image;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.dao.UserDetailDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.UserDetail;
import com.galaxy.ggolf.dto.FileList;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.UserDetailManager;
import com.galaxy.ggolf.manager.UserManager;
import com.galaxy.ggolf.tools.FileUtil;

@Path("/file")
public class FileService  extends BaseService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	private final UserManager userManager;
	private final UserDetailManager userDetailManager;
	
	public FileService(UserManager userManager,
			UserDetailManager userDetailManager) {
		super();
		this.userManager = userManager;
		this.userDetailManager = userDetailManager;
	}

	/**
	 * 空接口
	 * @return
	 */
	@POST
	@Path("/kong")
	public void kong(){
	}
	
	/**
	 * 下载
	 * @param fileName
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/download/{fileName}")
	public Response downloadFile(@PathParam("fileName") String fileName, @Context HttpHeaders headers) {
		logger.debug("文件路径-----{}",fileName);
		if(fileName.indexOf("_")!=-1){
			String[] path = fileName.split("_");
			String f = "";
			for(String p : path){
				f = f + p +"\\";
			}
			fileName = f.substring(0,f.lastIndexOf("\\"));
		}
		File file = new File(CommonConfig.FILE_UPLOAD_PATH + fileName);
		if(file.exists()){
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename="
					+ fileName);
			return response.build();
		}
		return null;
	}
	
	
	/**
	 * 上传文件
	 * @param filePath
	 * @param attachments
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/uploadImg/{filePath}/{w}/{h}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(@PathParam("filePath") String filePath,
			@PathParam("w") String w,
			@PathParam("h") String h,
			List<Attachment> attachments,
			@Context HttpHeaders headers) {
		String filePath1 = "";
		if(filePath!=null&&!filePath.equals("")){
			//
			if(filePath.indexOf("_")!=-1){
				String[] path = filePath.split("_");
				String f = "";
				for(String p : path){
					if(!p.equals("")){
						f = f + p +"\\";
					}
				}
				filePath1 = f;
			}else{
				filePath1 = filePath+"\\";
				filePath = filePath+"_";
			}
		}else{
			filePath="";
		}
		createDirIfNotExist(CommonConfig.FILE_UPLOAD_PATH+filePath1);
		for (Attachment attachment : attachments) {
			DataHandler handler = attachment.getDataHandler();
			try {
				InputStream stream = handler.getInputStream();
				MultivaluedMap<String, String> map = attachment.getHeaders();
				String fileName = updateFileName(map);
				logger.debug("fileName: ------{}", fileName);
				File file = new File(CommonConfig.FILE_UPLOAD_PATH + filePath1 + fileName);
				OutputStream out = new FileOutputStream(file);

				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = stream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				stream.close();
				out.flush();
				out.close();
//				SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
				String date = DateTime.now().getMillis()+"";
				String result = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + filePath + fileName+"?t="+date;
				logger.info("file:------{}",result);
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("File", result);
				if(w!=null&&h!=null){
					int height = Integer.parseInt(h);
					int width = Integer.parseInt(w);
					if(width>0 && height>0){
						String thumbName = FileUtil.thumbnailImage(file, width, height, CommonConfig.DEFAULT_PREVFIX, false);
						String thumbFile = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + filePath + thumbName+"?t="+date;
						fileMap.put("thumbImgFile", thumbFile);
					}
				}
				return getResponse(fileMap);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		return getErrorResponse();
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String upload(List<Attachment> attachments,
			@Context HttpHeaders headers) {
		createDirIfNotExist(CommonConfig.FILE_UPLOAD_PATH);
		for (Attachment attachment : attachments) {
			DataHandler handler = attachment.getDataHandler();
			try {
				InputStream stream = handler.getInputStream();
				MultivaluedMap<String, String> map = attachment.getHeaders();
				String fileName = updateFileName(map);
				logger.debug("fileName: ------{}", fileName);
				File file = new File(CommonConfig.FILE_UPLOAD_PATH + fileName);
				OutputStream out = new FileOutputStream(file);

				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = stream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				stream.close();
				out.flush();
				out.close();
//				SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
				String date = DateTime.now().getMillis()+"";
				String result = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD +  fileName+"?t="+date;
				return getResponse(result);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		return getErrorResponse();
	}
	
	/**
	 * Froala上传图片
	 * @return
	 */
	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadFile(List<Attachment> attachments)throws IOException{
		createDirIfNotExist(CommonConfig.FROALA_UPLOAD_PATH);
		for (Attachment attachment : attachments) {
			DataHandler handler = attachment.getDataHandler();
			try {
				InputStream stream = handler.getInputStream();
				MultivaluedMap<String, String> map = attachment.getHeaders();
				String fileName = updateFileName(map);
				logger.debug("headers: ------{}", attachment.getHeaders());
				OutputStream out = new FileOutputStream(new File(
						CommonConfig.FROALA_UPLOAD_PATH + fileName));

				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = stream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				stream.close();
				out.flush();
				out.close();
//				SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DateFormatNum);
				String date = DateTime.now().getMillis()+"";
				String result = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + "froala_" + fileName+"?t="+date;
				Map<String,String> fmap = new HashMap<String,String>();
				fmap.put("link", result);
				return getResponse(fmap);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		return getErrorResponse();
	}
	
	/**
	 * Froala获取图片列表
	 * @return
	 */
	@GET
	@Path("/load_images")
	public String load_images(){
		String fileRoute = CommonConfig.FROALA_UPLOAD_PATH;
		String downloadPath = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + "froala_";
		ArrayList<FileList> arrayList = new ArrayList<FileList>();
		try {
			ArrayList<String> fileLists = refreshFileList(fileRoute);
			if(fileLists!=null&&fileLists.size()>0){
				for(String filePath : fileLists){
					logger.debug("文件路径----{}",filePath);
					int j = filePath.lastIndexOf("."); 
	                String suf = filePath.substring(j+1);//得到文件后缀
	                if(suf.equalsIgnoreCase("png")||suf.equalsIgnoreCase("jpg")||
	                		suf.equalsIgnoreCase("jpeg")||suf.equalsIgnoreCase("gif")){
	                	int last = filePath.lastIndexOf("\\");
	                	String filename = filePath.substring(last+1);
	                	FileList fl = new FileList();
	                	fl.setUrl(downloadPath+filename);
	                	fl.setThumb(downloadPath+filename);
	                	fl.setName(filename);
	                	arrayList.add(fl);
	                }
				}
				
			}
			return getResponse(arrayList);
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	/**
	 * Froala 删除图片
	 * @param headers
	 * @return
	 * @throws IOException 
	 */
	@POST
	@Path("/delete_image")
	public String delete_image(String data,@Context HttpHeaders headers){
		String filename = null;
		String d = data.split("&")[1];
		filename = d.substring(d.lastIndexOf("=")+1);
		logger.debug("data-----{}",filename);
		String fileRoute = CommonConfig.FROALA_UPLOAD_PATH;
		try {
			File file = new File(fileRoute+filename);
			logger.debug("wenjian----{}",file);
			FileUtil.deleteFile(file);
			return getSuccessResponse();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@POST
	@Path("/deleteImage")
	public String deleteImage(@FormParam("filename") String filename, @Context HttpHeaders headers) throws IOException{
		String fileRoute = CommonConfig.FROALA_UPLOAD_PATH;
		try {
			File file = new File(fileRoute+filename);
			logger.debug("wenjian----{}",file);
			FileUtil.deleteFile(file);
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	/**
	 * 保存用户头像
	 * @return
	 */
	@POST
	@Path("/saveHeadApp{UserID}")
	public String saveHeadApp(List<Attachment> attachments,
			@PathParam("UserID") String UserID,
			@Context HttpHeaders headers){
		String fileName = CommonConfig.UserHead + "@" + UserID;
		String filePath = CommonConfig.USER_PATH + CommonConfig.UserHead + "\\";
		try {
			File fs = new File(filePath+fileName);
			if(fs.exists()){
				FileUtil.deleteFile(fs);
			}
			for (Attachment attachment : attachments) {
				DataHandler handler = attachment.getDataHandler();
				InputStream stream = handler.getInputStream();
				MultivaluedMap<String, String> map = attachment.getHeaders();
				fileName = FileUtil.getFileName(map, fileName);
				logger.debug("fileName: ------{}", fileName);
				OutputStream out = new FileOutputStream(new File(filePath + fileName));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = stream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				stream.close();
				out.flush();
				out.close();
				String date = DateTime.now().getMillis()+"";
				String result = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + "user_UH_" +  fileName+"?t="+date;
				logger.debug("result: ------{}", result);
				this.userManager.saveHead(result, UserID);
				UserDetail user = new UserDetail(UserID, null, result, null, null);
				this.userDetailManager.updateUserDetail(user);
				return getSuccessResponse();
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	
	/**
	 * 图片解码
	 * @param img
	 * @return
	 */
	@POST
	@Path("/imgDecoding")
	public String imgDecoding(@FormParam("Image") String img){
		try {
			logger.debug("文件-----{}",img);
			if(img.indexOf("data:image/png;base64")!=-1){
				String base64Img = img.split(",")[1];
				BufferedImage image = null;
				byte[] imgByte;
				BASE64Decoder decoder = new BASE64Decoder();
				imgByte = decoder.decodeBuffer(base64Img);
				ByteArrayInputStream bis = new ByteArrayInputStream(imgByte);
				image = ImageIO.read(bis);
				bis.close();
				String picnameString = DateTime.now().getMillis()+".png";
				File outputfile = new File(CommonConfig.FILE_UPLOAD_PATH+picnameString);
				ImageIO.write(image, "png", outputfile);
				String url = CommonConfig.CONNECT+CommonConfig.FILE_DOWNLOAD+picnameString;
				return getResponse(url);
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	private void createDirIfNotExist(String filepath) {
		File theDir = new File(filepath);
		// if the directory does not exist, create it
		logger.debug("creating directory: {}",filepath);
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
			se.printStackTrace();
		}
	}
	
	/**
	 * 获取文件夹下的文件和文件夹
	 * @param strPath
	 */
	public ArrayList<String> refreshFileList(String strPath){
		String filename;//文件名
        String suf;//文件后缀
        ArrayList<String> fileLists = new ArrayList<String>();
        File dir = new File(strPath);//文件夹dir
        File[] files = dir.listFiles();//文件夹下的所有文件或文件夹
 
        if (files == null)
            return null;
 
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                System.out.println("---" + files[i].getAbsolutePath());
                refreshFileList(files[i].getAbsolutePath());//递归文件夹！！！
            } else {
                filename = files[i].getName(); 
                int j = filename.lastIndexOf("."); 
                suf = filename.substring(j+1);//得到文件后缀
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                fileLists.add(files[i].getAbsolutePath());//对于文件才把它的路径加到filelist中
            }
             
        }
        return fileLists;
	}

	private String updateFileName(MultivaluedMap<String, String> header)  {
		logger.debug("headers-----{}",header);
		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				logger.debug("文件名-----{}",filename);
				String name = filename.split("=")[1].trim().replaceAll("\"", "");
				String exactFileName = DateTime.now().getMillis() + name.substring(name.lastIndexOf("."),name.length());
				return exactFileName;
			}
		}
		return getErrorResponse("unknown");
	}
	
	private String getFileName(MultivaluedMap<String, String> header)  {
		logger.debug("headers-----{}",header);
		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				logger.debug("文件名-----{}",filename);
				String name = filename.split("=")[1].trim().replaceAll("\"", "");
				String exactFileName = name;
				return exactFileName;
			}
		}
		return getErrorResponse("unknown");
	}
	
	
}