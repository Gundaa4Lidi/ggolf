package com.galaxy.ggolf.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.ClubServe;
import com.galaxy.ggolf.domain.PriceForTime;

public class SortUtil {
	private final static Logger logger = LoggerFactory.getLogger(SortUtil.class);
	
	/**
	 * 比较球场之间的距离或价格大小
	 * @param sortFlag 0:距离,1:价格
	 * @param lon
	 * @param lat
	 * @param clubList
	 * @return
	 */
	public static Collection<Club> sortForDistanceOrPrice(String sortFlag, String lon, String lat, Collection<Club> clubList){
		ArrayList<Club> clubs = new ArrayList<Club>();
		if(clubList.size()>0){
			clubs = (ArrayList<Club>) clubList;
			if(sortFlag.equals("0")&&lon!=null&&lat!=null){
				double lon1 = Double.parseDouble(lon);
				double lat1 = Double.parseDouble(lat);
				for(Club club : clubs){
					if(club.getLongitude()!=null && club.getLatitude()!=null){
						double lon2 = Double.parseDouble(club.getLongitude());//用户的经度
						double lat2 = Double.parseDouble(club.getLatitude());//用户的纬度
						double distance = LocationUtil.GetDistance(lon1, lat1, lon2, lat2);//获取距离
						club.setDistance(String.valueOf(distance));
					}
				}
			}
			long bt = System.currentTimeMillis();//开始排序
			Collections.sort(clubs,new Comparator<Club>() {

				@Override
				public int compare(Club o1, Club o2) {
					if(sortFlag.equals("0")){
						if(o1.getDistance() != null && o2.getDistance() != null){
							double distance1 = Double.parseDouble(o1.getDistance());
							double distance2 = Double.parseDouble(o2.getDistance());
							if(distance1 < distance2){
								return -1;
							}else if(distance1 > distance2){
								return 1;
							}else{
								return 0;
							}
						}
					}else if(sortFlag.equals("1")){
						//按价格比较供应商
						Collection<ClubServe> clubServe1 = sortClubServe(o1.getClubServes());
						Collection<ClubServe> clubServe2 = sortClubServe(o2.getClubServes());
						
						
						if(clubServe1!=null&&clubServe1.size()>0&&clubServe2!=null&&clubServe2.size()>0){
							
							ClubServe cs1 = (ClubServe) clubServe1.toArray()[0];
							ClubServe cs2 = (ClubServe) clubServe2.toArray()[0];
							
							PriceForTime pft1 = (PriceForTime) cs1.getPriceForTimes().toArray()[0];
							PriceForTime pft2 = (PriceForTime) cs2.getPriceForTimes().toArray()[0];
							double price1 = Double.parseDouble(pft1.getDownPayment());
							double price2 = Double.parseDouble(pft2.getDownPayment());
							if(price1 < price2){
								return -1;
							}else if(price1 > price2){
								return 1;
							}else{
								return 0;
							}
						}
					}
					return 0;
				}
			});
			long et = System.currentTimeMillis();//结束排序
			logger.info("球场比较耗时----{}毫秒",((bt - et)));
			
		}
		return clubs;
	}
	
	/**
	 * 按价格排序供应商
	 * @param clubServeList
	 * @return
	 */
	public static Collection<ClubServe> sortClubServe(Collection<ClubServe> clubServeList){
		ArrayList<ClubServe> clubServes = new ArrayList<ClubServe>();
		if(clubServeList.size()>0){
			clubServes = (ArrayList<ClubServe>) clubServeList;
			long bt = System.currentTimeMillis();//开始排序
			Collections.sort(clubServes,new Comparator<ClubServe>() {

				@Override
				public int compare(ClubServe o1, ClubServe o2) {
					// 按价格比较时段
					Collection<PriceForTime> pfts1 = sortPriceForTime(o1.getPriceForTimes());
					Collection<PriceForTime> pfts2 = sortPriceForTime(o2.getPriceForTimes());
					if(pfts1!=null&&pfts2!=null){
						PriceForTime pft1 = (PriceForTime) pfts1.toArray()[0];
						PriceForTime pft2 = (PriceForTime) pfts2.toArray()[0];
						double price1 = Double.parseDouble(pft1.getDownPayment());
						double price2 = Double.parseDouble(pft2.getDownPayment());
						if(price1 < price2){
							return -1;
						}else if(price1 > price2){
							return 1;
						}else{
							return 0;
						}
					}
					return 0;
				}

				
			});
			long et = System.currentTimeMillis();//结束排序
			logger.info("供应商比较耗时----{}毫秒",((bt - et)));
		}
		return clubServes;
	}
	
	//按价格排序时段
	public static Collection<PriceForTime> sortPriceForTime(Collection<PriceForTime> priceForTimes){
		ArrayList<PriceForTime> pfts = new ArrayList<PriceForTime>();
		if(priceForTimes.size()>0){
			pfts = (ArrayList<PriceForTime>) priceForTimes;
			long bt = System.currentTimeMillis();//开始排序
			Collections.sort(pfts,new Comparator<PriceForTime>() {

				@Override
				public int compare(PriceForTime o1, PriceForTime o2) {
					double price1 = Double.parseDouble(o1.getDownPayment());
					double price2 = Double.parseDouble(o2.getDownPayment());
					if(price1 < price2){
						return -1;
					}else if(price1 > price2){
						return 1;
					}
					return 0;
				}
				
			});
			long et = System.currentTimeMillis();//结束排序
			logger.info("时段比较耗时----{}毫秒",((bt - et)));
		}
		return pfts;
	}
}
