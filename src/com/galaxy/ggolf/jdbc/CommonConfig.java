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
	
	public static final String DEFAULT_UPLOAD_PATH = "c:\\ggolf_upload\\default\\";
	
	public static final String LOGO_PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ROOT\\file\\";
	
	public static final String DEFAULT_PREVFIX = "thumb_";
	
	public static final String CONNECT = "http://192.168.1.107:8085";
//	public static final String CONNECT = "";
	
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
	
	public static final String TYPE_COACH_COMMENT = "coach";//教练
	
	public static final String TYPE_USER_COMMENT = "user";//用户
	
	//主题类型
	public static final String THEME_TYPE_A = "A";//文章
	
	public static final String THEME_TYPE_C = "C";//球场
	
	public static final String THEME_TYPE_D = "D";//动态

	public static final String THEME_TYPE_S = "S";//专题

	public static final String THEME_TYPE_CT = "CT";//评论

	public static final String THEME_TYPE_CH = "CH";//教练
	
	public static final String ACTION_COMMENT = "comment";//评论标识
	
	public static final String ACTION_REPLY = "reply";//回复标识
	
	public static String GAODEURL = "http://restapi.amap.com/v3/geocode/regeo?location=<gps>&key=218105e047dd5c735753ba5b78eafa31&extensions=base";
	
	public static final String USER_PATH = FILE_UPLOAD_PATH + "user\\";
	
	public static final String CLUB_PATH = FILE_UPLOAD_PATH + "club\\";
	
	public static final String ARTICLE_PATH = FILE_UPLOAD_PATH + "article\\";
	
	public static final String STAFF_PATH = FILE_UPLOAD_PATH + "staff\\";
	
	public static final String StaffHead = "SH";
	
	public static final String UserHead = "UH";
	
	public static final String UserPhotoList = "UPL";
	
	public static final String ArticlePhoto = "AP";
	
	public static final String PingPP_AppID = "app_uvnv505GOCyLivn1";
	
	public static final String PingPP_Apikey = "sk_test_WTOCKKejbzv90K4uXDvnrn9S";
	
	public static final String Umeng_AD_AppKey = "593df0d2677baa188600054a";
	
	public static final String Umeng_AD_AppMaster_Secret = "an8wabbklncalsao6iojzdg5z0bjires";
	
	public static final String Umeng_IOS_AppKey = "5902f50daed17963de001284";
	
	public static final String Umeng_IOS_AppMaster_Secret = "9nhfbkmys265obvicehhlnj54z3joztb";
	
	public static final int Umeng_Android_Byte = 44;
	
	public static final int Umeng_IOS_Byte = 64;
	
	public static final String Umeng_HTTP_SEND = "http://msg.umeng.com/api/send?sign=mysign";
	
	public static final String Umeng_HTTPS_SEND = "https://msgapi.umeng.com/api/send?sign=mysign";
	
	public static final String Send_Broadcast = "broadcast";
	
	public static final String Send_Unicast = "unicast";

	public static final String Send_Listcast = "listcast";
	
	public static final String Send_Groupcast = "groupcast";
	
	public static final String Send_Filecast = "filecast";
	
	public static final String Send_Customizedcast = "customizedcast";
	
	//自定义推送返回状态码
	public static final String Broadcast_Success = "100";
	public static final String Broadcast_Error_Andrid = "101";
	public static final String Broadcast_Error_IOS = "102";
	public static final String Broadcast_Error = "103";
	public static final String Unicast_Success = "200";
	public static final String Unicast_Error_Android = "201";
	public static final String Unicast_Error_IOS = "202";
	public static final String Unicast_Error = "203";
	public static final String	Filecast_Success = "300";
	public static final String	Filecast_Error_Android = "301";
	public static final String	Filecast_Error_IOS = "302";
	public static final String	Filecast_Error = "303";
	public static final String	Filecast_Error_Token = "304";
	
	//超级管理员信息
	public static final String SuperAdminName = "超级管理员";
	public static final String SuperAdminID = "1234";
	public static final String SuperAdminHead = "http://192.168.1.107:8085/GGolfz/rest/file/download/staff_SH_SH@1234.png?t=20170313_150314";
	
	
}
