package com.galaxy.ggolf.jdbc;

public interface CommonConfig {

	public static final String DB_NAME = "GGolf";

	// public static final String DB_URL = "jdbc:mysql://112.124.17.20:3306/"
	// + DB_NAME + "?characterEncoding=UTF8";//
	public static final String DB_URL = "jdbc:mysql://localhost:3306/"
			+ DB_NAME + "?characterEncoding=UTF8&autoReconnect=true&rewriteBatchedStatements=TRUE";
	public static final int pageSize = 10;

	public static final String DB_ID = "root";

	public static final String DB_PWD = "root";

	public static final String DB_CLASS = "com.mysql.jdbc.Driver";

	public static final long SESSION_EXPIRY = 1800000;
	
	public static final long SESSION_LOCATION_EXPIRY = 3600000 * 24;
	
	
	
	public static final String DateFormat = "yyyy年MM月dd日   HH:mm:ss";
	
    public static final String DateFormat_Database = "yyyy-MM-dd HH:mm";
    
    public static final String DateFormatNum = "yyyyMMdd_HHmmss";
    
    
	
	public static final String FILE_UPLOAD_PATH = "c:\\ggolf_upload\\";
	
	public static final String FROALA_UPLOAD_PATH = "C:\\ggolf_upload\\froala\\";
	
	public static final String LOGO_PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ROOT\\file\\";
	
	public static final String CONNECT = "http://192.168.1.107:8085";
	
	public static final String FILE_DOWNLOAD = "/GGolfz/rest/file/download/";
	
	public static final String APP_NAME = "golf";
	
	public static final String WECHA_APPID = "";
	
	public static final String MSG_TYPE_SYS = "system";//系统类型信息
	
	public static final String MSG_TYPE_NOTSYS = "no_system";//不是系统类型信息
	
	public static final String MSG_TYPE_DYNAMIC = "dynamic";//动态类型信息
	
	public static final String MSG_TYPE_INVITED = "invited";//约球类型信息
 	
	public static final String TYPE_CLUB_COMMENT = "club";//球场
	
	public static final String TYPE_ARTICLE_COMMENT = "article";//文章
	
	public static final String TYPE_DYNAMIC_COMMENT = "dynamic";//动态
	
	public static final String TYPE_TRACK_COMMENT = "track";//足迹
	
	public static final String TYPE_USER_COMMENT = "user";//用户
	
	public static final String ACTION_COMMENT = "comment";
	
	public static final String ACTION_REPLY = "reply";
	
	public static String GAODEURL = "http://restapi.amap.com/v3/geocode/regeo?location=<gps>&key=218105e047dd5c735753ba5b78eafa31&extensions=base";
	
	public static final String USER_PATH = FILE_UPLOAD_PATH + "user\\";
	
	public static final String CLUB_PATH = FILE_UPLOAD_PATH + "club\\";
	
	public static final String ARTICLE_PATH = FILE_UPLOAD_PATH + "article\\";
	
	public static final String STAFF_PATH = FILE_UPLOAD_PATH + "staff\\";
	
	public static final String StaffHead = "SH";
	
	public static final String UserHead = "UH";
	
	public static final String UserPhotoList = "UPL";
	
	public static final String ArticlePhoto = "AP";
}
