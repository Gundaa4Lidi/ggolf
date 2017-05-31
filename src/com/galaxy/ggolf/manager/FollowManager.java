package com.galaxy.ggolf.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.FollowDAO;
import com.galaxy.ggolf.dao.FollowUserDAO;
import com.galaxy.ggolf.domain.Follow;
import com.galaxy.ggolf.domain.FollowUser;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.User;

public class FollowManager {

//	public GenericCache<String, Collection<Follow>> cache;
	public FollowDAO followDAO;
	public FollowUserDAO followUserDAO;
	
	private static final String YI_GUAN_ZHU = "已关注";
	private static final String HU_XIANG_GUAN_ZHU = "互相关注";
	private static final String YI_BEI_GUAN_ZHU = "已被关注";
	private static final String QU_XIAO_GUAN_ZHU = "取消关注";
	private static final String BLACKLIST = "黑名单";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public FollowManager(FollowDAO followDAO,FollowUserDAO followUserDAO) {
//		this.cache = new GenericCache<String, Collection<Follow>>();
		this.followDAO = followDAO;
		this.followUserDAO = followUserDAO;
	}
	
	//获取好友列表
	public Collection<Follow> getFollowByUserID(String userID)throws GalaxyLabException{
		/*if(cache.get(userID)!=null){
			logger.info("Follow exist");
			return cache.get(userID);
		}else{
			Collection<Follow> fo =  this.followDAO.getByUserID(userID);
			if(fo!=null){
				cache.put(userID,fo);
			}
			return fo;
		}*/
		Collection<Follow> fo =  this.followDAO.getByUserID(userID);
		return fo;
	}
	
	//获取好友列表
	public Collection<FollowUser> getFriendList(String UserID)throws Exception{
		return this.followUserDAO.getFollow(UserID, YI_GUAN_ZHU, HU_XIANG_GUAN_ZHU);
	}
	
	//获取已被关注列表
	public Collection<FollowUser> getNewFriendList(String UserID)throws Exception{
		return this.followUserDAO.getFollow(UserID, YI_BEI_GUAN_ZHU, YI_BEI_GUAN_ZHU);
	}
	
	//获取黑名单
	public Collection<FollowUser> getBlackList(String UserID)throws Exception{
		return this.followUserDAO.getFollow(UserID, BLACKLIST, BLACKLIST);
	}
	
	//添加关注
	public String follow(Follow fol)throws Exception{
		String result = "";
		Follow follow = followDAO.getFollow(fol.getUserID(), fol.getFenID());//查询我关注的人是否存在
		Follow follow1 = followDAO.getFollow(fol.getFenID(), fol.getUserID());//查询关注的人,是否关注我
		if(follow==null){
			fol.setRelation(YI_GUAN_ZHU);
			fol.setStatus("1");
			if(!followDAO.create(fol)){
				throw new GalaxyLabException("Errow in create follow");
			}
			result = YI_GUAN_ZHU;
			follow = followDAO.getFollow(fol.getUserID(), fol.getFenID());//重新赋值
		}else{
			String relation = follow.getRelation();
			if(relation.equals(YI_GUAN_ZHU)||relation.equals(HU_XIANG_GUAN_ZHU)){
				result = "已经关注此人,无需多次关注";
			}else if(relation.equals(YI_BEI_GUAN_ZHU)){//若是已被关注,修改成已关注
				if(!followDAO.update(YI_GUAN_ZHU, "1", follow.getUID())){
					throw new GalaxyLabException("Error in update follow");
				}
			}
		}
		if(follow1 != null){//查询关注的人,是否关注我
			String relation = HU_XIANG_GUAN_ZHU;
			if(follow1.getRelation().equals(YI_GUAN_ZHU)){//对方是已关注我的话,修改互相关注
				if(!followDAO.update(relation, "1", follow.getUID())){
					throw new GalaxyLabException("Errow in update follow relation");
				}
				if(!followDAO.update(relation, "1", follow1.getUID())){
					throw new GalaxyLabException("Errow in update follow1 relation");
				}
				result = relation;
			}
		}else{//如果对方不存在,创建一条已被关注数据,用于提醒对方,我方关注了对方
			follow1 = new Follow(fol.getFenID(),fol.getUserID(),YI_BEI_GUAN_ZHU,"0");
			if(!followDAO.create(follow1)){
				throw new GalaxyLabException("Error in create follow1");
			}
		}
		return result;
	}
	
	//取消关注
	public boolean cancel(Follow fo)throws Exception{
		boolean result = false;
		if(followDAO.getByUID(fo.getUID())!=null){
			if(followDAO.cancel(fo.getUID())){
				Follow fol = followDAO.getFollow(fo.getFenID(), fo.getUserID());
				if(fol!=null){//查询关注的人,是否关注我
					String relation = YI_GUAN_ZHU;
					if(!this.followDAO.update(relation,"1",fol.getUID())){
						throw new GalaxyLabException("Errow in update follow relation");
					}
				}
//				cache.remove(fo.getUserID());
//				Collection<Follow> fol = followDAO.getByUserID(fo.getUserID());
//				this.cache.put(fo.getUserID(), fol);
				result = true;
			}
		}
		return result;
	}
	
	//修改好友备注
	public void updateRemark(String remark,String uid)throws Exception{
		if(!this.followDAO.updateRemark(remark, uid)){
			throw new GalaxyLabException("Error in update remark");
		}
	}
	
	//获取粉丝数量
	public int getFensCount(String userID)throws Exception{
		return this.followDAO.getFensCount(userID);
	}
	//获取关注数量
	public int getFollowCount(String userID)throws Exception{
		return this.followDAO.getFollowCount(userID);
	}
	
	//已查阅新的好友
	public void updateStatus(String UID)throws Exception{
		if(!this.followDAO.updateStatus(UID)){
			throw new GalaxyLabException("Error in update status");
		}
	}
	//拉入黑名单
	public void DrawInBlackList(String UID)throws Exception{
		if(!this.followDAO.DrawInblackList(UID)){
			throw new GalaxyLabException("Error in draw in blackList");
		}
	}
	
	//移出黑名单
	public void removeBlackList(String UID)throws Exception{
		if(!this.followDAO.removeBlackList(UID)){
			throw new GalaxyLabException("Error in remove blackList");
		}
	}
	
	
	
	/*private Collection<Follow> getFollow(String uid){
		if(cache.get(uid)!=null){
			logger.debug("cache hit");
			return cache.get(uid);
		}else{
			logger.debug("cache missed,looking up from DB");
			Collection<Follow> f = followDAO.getByUserID(userID);
			if(f != null){
				this.cache.put(f.getUID(),f);
			}
			return f;
		}
	}*/
	
	
}
/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 *  O\ = /O
 * ___/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 *          .............................................
 *           佛曰：bug泛滥，我已瘫痪！
 */
