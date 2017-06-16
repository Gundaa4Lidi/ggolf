package com.galaxy.ggolf.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;

public class LocationUtil {
	private static final double EARTH_RADIUS = 6378137;//赤道半径(单位m)  
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    /** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d){  
       return d * Math.PI / 180.0;  
    }  
    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位km 
     * */  
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2){  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lon1) - rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 10000) / 10000;  
       return s;  
    }
    
    /**
     * 以经纬度为中心和距离获取方圆范围
     * @param lon
     * @param lat
     * @param radius
     * @return
     */
    public static Rectangle GetRange(double lon, double lat, int radius){
    	SpatialContext geo = SpatialContext.GEO;
    	Rectangle rec = geo.getDistCalc().calcBoxByDistFromPt(
    			geo.makePoint(lon, lat),radius*DistanceUtils.KM_TO_DEG,geo,null);
    	/*logger.debug("经度范围---{}---{}",rec.getMinX(),rec.getMaxX());
    	logger.debug("纬度范围---{}---{}",rec.getMinY(),rec.getMaxY());*/
    	return rec;
    }
    
   
}
