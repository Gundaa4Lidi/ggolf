package com.galaxy.ggolf.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.dao.CourseTimeDAO;
import com.galaxy.ggolf.dao.MessageDAO;


public class AutoEventService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService autoEventService = Executors.newScheduledThreadPool(1);
	
	private final MessageDAO messageDAO;
	private final CourseTimeDAO courseTimeDAO;
	private final ClubOrderDAO clubOrderDAO;
	
	public AutoEventService(MessageDAO messageDAO,CourseTimeDAO courseTimeDAO,ClubOrderDAO clubOrderDAO){
		this.messageDAO = messageDAO;
		this.courseTimeDAO = courseTimeDAO;
		this.clubOrderDAO = clubOrderDAO;
		startTask();
	}
	
	public void startTask(){
		Runnable task = new MessageCloseRunner();
		Runnable task1 = new CourseCloseRunner();
		Runnable task2 = new CancelClubOrderRunner();
		autoEventService.scheduleWithFixedDelay(task, 0, 5, TimeUnit.MINUTES);
		autoEventService.scheduleWithFixedDelay(task1, 0, 60, TimeUnit.SECONDS);
		autoEventService.scheduleWithFixedDelay(task2, 0, 60, TimeUnit.SECONDS);
		
	}
	
	class MessageCloseRunner implements Runnable{

		@Override
		public void run() {
			try {
				DateTime twoHourBefore = new DateTime().plusHours(-24);
				messageDAO.closeMessage(twoHourBefore.toString("yyyy-MM-dd HH:mm:ss"));//关闭24小时后的约球消息
			} catch (Exception ex) {
				logger.error("Exception", ex);
			}
		}
		
	}
	
	
	class CourseCloseRunner implements Runnable{

		@Override
		public void run() {
			try {
				DateTime dateTime = new DateTime();
				courseTimeDAO.CloseTime(dateTime.toString("yyyy-MM-dd HH:mm"));
			} catch (Exception ex) {
				logger.error("Exception", ex);
			}
		}
		
	}
	
	class CancelClubOrderRunner implements Runnable{

		@Override
		public void run() {
			try {
				DateTime dateTime = new DateTime();
				clubOrderDAO.cancelOrders(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			
		}
		
	}
}
