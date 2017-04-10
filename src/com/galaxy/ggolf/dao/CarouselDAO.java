package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.CarouselRowMapper;
import com.galaxy.ggolf.dao.mapper.RowMapper;
import com.galaxy.ggolf.domain.Carousel;

public class CarouselDAO extends GenericDAO<Carousel> {

	public CarouselDAO() {
		super(new CarouselRowMapper());
		// TODO Auto-generated constructor stub
	}
	//创建一条轮播信息
	public boolean create(Carousel cos){
		String sql = "insert into carousel(Title,Type,TypeID,Image,Url,Created_TS)values(?,?,?,?,?,?)";
		return super.sqlUpdate(sql, cos.getTitle(),cos.getType(),
				cos.getTypeID(),cos.getImage(),cos.getUrl(),Time());
	}
	//获取全部轮播信息
	public Collection<Carousel> getAll(){
		String sql = "select * from carousel where DeletedFlag is null";
		return super.executeQuery(sql);
	}
	//是否存在
	public Carousel getByUID(String UID){
		String sql = "select * from carousel where DeletedFlag is null and UID='"+UID+"'";
		Collection<Carousel> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Carousel) result.toArray()[0];
		}
		return null;
	}
	
	//修改轮播信息
	public boolean update(Carousel cos){
		String sql = "update carousel set Title=?,Type=?,TypeID=?,Image=?,Url=?,Updated_TS='"+Time()+"' where UID=?";
		return super.sqlUpdate(sql, cos.getTitle(),cos.getType(),
				cos.getTypeID(),cos.getImage(),cos.getUrl(),Time(),cos.getUID());
	}
	
	//删除
	public boolean delete(String UID){
		String sql = "update carousel set DeletedFlag='Y',Updated_TS='"+Time()+"' where UID='"+UID+"'";
		return super.executeUpdate(sql);
	}
	
	
}
