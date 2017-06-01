package com.galaxy.ggolf.service;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.ClubOrderDAO;
import com.galaxy.ggolf.dao.CoachScoreDAO;
import com.galaxy.ggolf.dao.CourseOrderDAO;
import com.galaxy.ggolf.dao.CourseTimeDAO;
import com.galaxy.ggolf.dao.MessageDAO;
import com.galaxy.ggolf.domain.CoachScore;
import com.galaxy.ggolf.domain.CourseOrder;


public class AutoEventService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService autoEventService = Executors.newScheduledThreadPool(1);
	
	private final MessageDAO messageDAO;
	private final CourseTimeDAO courseTimeDAO;
	private final ClubOrderDAO clubOrderDAO;
	private final CoachScoreDAO coachScoreDAO;
	private final CourseOrderDAO courseOrderDAO;
	
	public AutoEventService(MessageDAO messageDAO,
			CourseTimeDAO courseTimeDAO,
			ClubOrderDAO clubOrderDAO,
			CoachScoreDAO coachScoreDAO,
			CourseOrderDAO courseOrderDAO){
		this.messageDAO = messageDAO;
		this.courseTimeDAO = courseTimeDAO;
		this.clubOrderDAO = clubOrderDAO;
		this.coachScoreDAO = coachScoreDAO;
		this.courseOrderDAO = courseOrderDAO;
		startTask();
	}
	
	public void startTask(){
//		Runnable task = new MessageCloseRunner();
//		Runnable task1 = new CourseCloseRunner();
//		Runnable task2 = new CancelClubOrderRunner();
//		Runnable task3 = new CancelCourseOrderRunner();
//		Runnable task4 = new CoachTeachNoRunner();
//		autoEventService.scheduleWithFixedDelay(task, 0, 60, TimeUnit.MINUTES);
//		autoEventService.scheduleWithFixedDelay(task1, 0, 60, TimeUnit.MINUTES);
//		autoEventService.scheduleWithFixedDelay(task2, 0, 60, TimeUnit.MINUTES);
//		autoEventService.scheduleWithFixedDelay(task3, 0, 60, TimeUnit.MINUTES);
//		autoEventService.scheduleWithFixedDelay(task4, 0, 60, TimeUnit.MINUTES);
		
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
	
	/**
	 * 取消已过30分钟未付款的球场订单
	 * @author Administrator
	 *
	 */
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
	/**
	 * 取消已过30分钟未付款的教练订单
	 * @author Administrator
	 *
	 */
	class CancelCourseOrderRunner implements Runnable{

		@Override
		public void run() {
			try {
				DateTime dateTime = new DateTime();
				courseOrderDAO.cancelOrders(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			
		}
		
	}
	
	/**
	 * 修改已经上课的订单状态并修改教练教过的人数
	 * @author Administrator
	 *
	 */
	class CoachTeachNoRunner implements Runnable{

		@Override
		public void run() {
			try {
				DateTime dateTime = new DateTime().plusHours(1);
				boolean isTaught = courseOrderDAO.IsTaught(dateTime.toString("yyyy-MM-dd HH:mm"));
				if(isTaught){
					Collection<CourseOrder> coachs = courseOrderDAO.getIsTaughtCoach();
					String sqlString = "";
					if(coachs.size() > 0){
						for(CourseOrder co : coachs){
							CoachScore cs = coachScoreDAO.getByCoachID(co.getCoachID());
							int userCount = courseOrderDAO.getIsTaughtCount(co.getCoachID());
							if(cs!=null){
								if(userCount==Integer.parseInt(cs.getTeachNo())){
									continue;
								}
							}
							if(userCount > 0){
								sqlString = "and TeachNo='"+userCount+"' ";
								coachScoreDAO.update(sqlString, co.getCoachID());
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			
		}
		
	}
}
