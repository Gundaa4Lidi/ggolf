package com.galaxy.ggolf.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.MessageDAO;

public class MessageCloseService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ScheduledExecutorService messageCloseService = Executors.newScheduledThreadPool(1);
	
	private final MessageDAO messageDAO;
	
	public MessageCloseService(MessageDAO messageDAO){
		this.messageDAO = messageDAO;
		startTask();
	}
	
	public void startTask(){
		Runnable task = new MessageCloseRunner();
		logger.info("Start MessageClose()");
		messageCloseService.scheduleWithFixedDelay(task, 0, 1, TimeUnit.HOURS);
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
}
