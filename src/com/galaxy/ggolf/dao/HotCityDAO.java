package com.galaxy.ggolf.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.HotCityRowMapper;
import com.galaxy.ggolf.domain.HotCity;

public class HotCityDAO extends GenericDAO<HotCity> {

	public HotCityDAO() {
		super(new HotCityRowMapper());
	}

	public boolean create(HotCity hc){
		String sql = "insert into hotcity(Province,City,IsHot,Updated_TS)values(?,?,?,?)";
		return executeUpdate(sql,hc.getProvince(),hc.getCity(),hc.getIsHot(),Time());
	}
	
	public boolean createAll(Collection<HotCity> hotCities){
		Collection<String> sqls = new ArrayList<String>();
		for(HotCity hc : hotCities){
			String sql = "insert into hotcity(Province,"
					+ "City,"
					+ "IsHot,"
					+ "Updated_TS)values('"+hc.getProvince()+"','"+hc.getCity()+"',"
							+ "'"+hc.getIsHot()+"','"+Time()+"')";
			sqls.add(sql);
		}
		return super.batchInsert(sqls);
	}
	
	public boolean update(String UID,String IsHot){
		String sql = "update hotcity set IsHot='"+IsHot+"' where UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	public Collection<HotCity> getIsHot(){
		String sql = "select * from hotcity where IsHot='1' order by City";
		return super.executeQuery(sql);
	}
	
	public Collection<HotCity> getCityByProvince(String Province){
		String sql = "select * from hotcity where Province='"+Province+"'";
		return super.executeQuery(sql);
	}
	
	public Collection<HotCity> getProvince(){
		String sql = "select * from hotcity Group by Province order by Province";
		return super.executeQuery(sql);
	}
}
