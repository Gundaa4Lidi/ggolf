package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.PhotoAlbumRowMapper;
import com.galaxy.ggolf.domain.PhotoAlbum;

public class PhotoAlbumDAO extends GenericDAO<PhotoAlbum> {

	public PhotoAlbumDAO() {
		super(new PhotoAlbumRowMapper());
		// TODO Auto-generated constructor stub
	}
	
	public boolean create(PhotoAlbum photo){
		String sql = "insert into photo_album (PhotoID,Type,Url,Sort,Created_TS)values"
				+ "('"+photo.getPhotoID()+"','"+photo.getType()+"','"+photo.getUrl()+"','"+photo.getSort()+"','"+Time()+"')";
		return super.executeUpdate(sql);
	}
	
	public Collection<PhotoAlbum> getPhotoByPhotoID(String photoID, String type){
		String sql = "select * from photo_album where PhotoID = '"+ photoID +"' and "
				+ "Type = '"+ type +"' and DeletedFlag is null order by (Updated_TS or Created_TS) desc";
		return super.executeQuery(sql);
	}
	
	public boolean savePhoto(PhotoAlbum photo){
		String sql = "update photo_album set PhotoID = '"+ photo.getPhotoID() +""
						+ "', Type = '"+ photo.getType() +""
						+ "', Url = '"+ photo.getUrl() +""
						+ "', Sort = '"+ photo.getSort() +""
						+ "', Updated_TS = '"+ Time() +"'";
		return super.executeUpdate(sql);
	}
	
	public boolean deletePhoto(String photoID,String url){
		String sql = "update photo_album set DeletedFlag = 'Y' where PhotoID = '"+photoID+"' and Url = '"+url+"'";
		return super.executeUpdate(sql);
	}
	
}
