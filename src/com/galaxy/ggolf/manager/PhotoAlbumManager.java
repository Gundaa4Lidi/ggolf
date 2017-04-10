package com.galaxy.ggolf.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.PhotoAlbumDAO;
import com.galaxy.ggolf.domain.PhotoAlbum;

public class PhotoAlbumManager {

	public GenericCache<String, PhotoAlbum> photoAlbumCache;
	public PhotoAlbumDAO photoAlbumDAO;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public PhotoAlbumManager(PhotoAlbumDAO photoAlbumDAO) {
		// TODO Auto-generated constructor stub
		this.photoAlbumCache = new GenericCache<String, PhotoAlbum>();
		this.photoAlbumDAO = photoAlbumDAO;
	}
	
	public boolean savePhoto(PhotoAlbum photoAllbum){
		String status = "";
		Collection<PhotoAlbum> photo = photoAlbumDAO.getPhotoByPhotoID(photoAllbum.getPhotoID(), photoAllbum.getType());
		if(photo == null){
			return photoAlbumDAO.create(photoAllbum);
		}else{
			return photoAlbumDAO.savePhoto(photoAllbum);
		}
		
	}

}
