package com.galaxy.ggolf.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.ArticleDAO;
import com.galaxy.ggolf.dao.ClubDAO;
import com.galaxy.ggolf.dao.CoachDAO;
import com.galaxy.ggolf.dao.CoachScoreDAO;
import com.galaxy.ggolf.dao.CommentDAO;
import com.galaxy.ggolf.dao.LikeDAO;
import com.galaxy.ggolf.dao.MessageDAO;
import com.galaxy.ggolf.dao.UserDAO;
import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.Comment;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Likes;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.dto.CommentData;
import com.galaxy.ggolf.dto.CommentGroupData;
import com.galaxy.ggolf.dto.GroupData;
import com.galaxy.ggolf.jdbc.CommonConfig;

public class CommentManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private GenericCache<String, CommentData> cache;
	private CommentDAO commentDAO;
	private LikeDAO likeDAO;
	private ClubDAO clubDAO;
	private ArticleDAO articleDAO;
	private MessageDAO messageDAO;
	private UserDAO userDAO;
	private CoachDAO coachDAO;
	private CoachScoreDAO coachScoreDAO;
	
	public CommentManager(CommentDAO commentDAO,LikeDAO likeDAO,
			ClubDAO clubDAO,ArticleDAO articleDAO,
			MessageDAO messageDAO,UserDAO userDAO,
			CoachDAO coachDAO,CoachScoreDAO coachScoreDAO) {
		this.cache = new GenericCache<String,CommentData>();
		this.commentDAO = commentDAO;
		this.likeDAO = likeDAO;
		this.clubDAO = clubDAO;
		this.articleDAO = articleDAO;
		this.messageDAO = messageDAO;
		this.userDAO = userDAO;
		this.coachDAO = coachDAO;
		this.coachScoreDAO = coachScoreDAO;
		
	}

	public CommentManager(CommentDAO commentDAO, LikeDAO likeDAO, ClubDAO clubDAO, ArticleDAO articleDAO, MessageDAO messageDAO, UserDAO userDAO) {
	}

	/**
	 * 获取全部评论
	 * @param keyword
	 * @param rows
	 * @return
	 * @throws GalaxyLabException
	 */
	public CommentGroupData getAll(String keyword,String rows,int days)throws GalaxyLabException{
		String sqlString = "";
		int row = Integer.parseInt(rows);
		if(keyword!=null&&!keyword.equals("")&&!keyword.equalsIgnoreCase("null")){
			sqlString = getSqlString(keyword);
		}
		Collection<GroupData<Comment>> Data = new ArrayList<GroupData<Comment>>();
		if(days > 0){
			DateTime time = DateTime.now().minusDays(days);
			sqlString += "and Created_TS > '"+time.toString("yyyy-MM-dd")+"' ";
		}
		Collection<Comment> dtGroup = this.commentDAO.getDTGroup(sqlString, rows);
		
		int count = this.commentDAO.getCount(sqlString);
		
		if(dtGroup.size() > 0){
			for(Comment com : dtGroup){
				String date = com.getCreated_TS().substring(0, 10);
				
				String dateFormat = "and date_format(Created_TS,'%Y-%m-%d')='"+date+"' ";
				
				Collection<Comment> result = this.commentDAO.getCommentBySearch(sqlString+dateFormat, row+"");
				
				if(result.size() <= row && row > 0){
					
					for(Comment com1 : result){
						int likeCount = this.likeDAO.getCountByThemeID(com1.getCommentID(), CommonConfig.THEME_TYPE_CT);
						com1.setLikeCount(likeCount);
						if(com1.getAction().equalsIgnoreCase("reply")){
							
							Comment com2 = this.commentDAO.getByCommentID(com1.getParentID());
							
							com1.setParentObj(com2);
							
						}else if(com1.getAction().equalsIgnoreCase("comment")){
							
							if(com1.getParentType().equalsIgnoreCase(CommonConfig.TYPE_CLUB_COMMENT)){
								
								Club club = this.clubDAO.getClubByClubID(com1.getParentID());
								
								com1.setParentObj(club);
							}
							if(com1.getParentType().equalsIgnoreCase(CommonConfig.TYPE_DYNAMIC_COMMENT)){
								
								Message msg = this.messageDAO.getBy(com1.getParentID());
								
								com1.setParentObj(msg);
							}
							if(com1.getParentType().equalsIgnoreCase(CommonConfig.TYPE_ARTICLE_COMMENT)){
								
								Article article = this.articleDAO.getByArticleID(com1.getParentID());
								
								com1.setParentObj(article);
							}
							if(com1.getParentType().equalsIgnoreCase(CommonConfig.TYPE_COACH_COMMENT)){
								
//								Article article = this.articleDAO.getByArticleID(com1.getParentID());
								Coach coach = this.coachDAO.getCoachByCoachID(com1.getParentID(), "1");
								
								com1.setParentObj(coach);
							}
							
						}
						
					}
					row -=result.size();
					GroupData<Comment> co = new GroupData<Comment>(date,result);
					Data.add(co);
				}
			}
			
			
		}
		CommentGroupData groupData = new CommentGroupData(count, Data);
		return groupData;
	}
	
	/**
	 * 创建评论
	 * @param comment
	 * @throws GalaxyLabException
	 */
	public void create(Comment comment)throws GalaxyLabException{
		if(!this.commentDAO.create(comment)){
			throw new GalaxyLabException("Error in create Comment");
		}
	}
	/**
	 * 根据类型及ID获取评论
	 * @param keyword
	 * @param rows
	 * @param RootID
	 * @param RootType
	 * @return
	 */
	public CommentData getByRoot(String keyword,String rows,String RootID,String RootType)throws Exception{
		String sqlString = "";
		if(keyword!=null){
			sqlString = getSqlString(keyword);
		}
		Collection<Comment> comment = this.commentDAO.getCommentByRoot(rows, sqlString, RootID, RootType);
		if(comment!=null && comment.size() > 0){
			for(Comment com : comment){
				int likeCount = this.likeDAO.getCountByThemeID(com.getCommentID(), CommonConfig.THEME_TYPE_CT);
				com.setLikeCount(likeCount);
				int replyRows = this.commentDAO.getCountByReply(com.getUserID(), com.getCommentID());
				if(replyRows > 0){
					String replyRow = "5";
					Collection<Comment> replyList = this.commentDAO.getCommentByReply(replyRow, com.getUserID(), com.getCommentID());
					CommentData replyData = new CommentData(replyRows, replyList);
					com.setReplyData(replyData);
				}
			}
		}
		int count = this.commentDAO.getCountByRoot(sqlString,RootID,RootType);
		int commentCount = this.commentDAO.getCommentCountByRoot(sqlString, RootID, RootType);
		CommentData commentData = new CommentData(count,commentCount, comment);
		return commentData;
	}
	
	/**
	 *根据用户编号和评论编号获取回复
	 * @param rows
	 * @param UserID
	 * @param CommentID
	 * @return
	 * @throws Exception
	 */
	public CommentData getReplyList(String rows, String UserID, String CommentID)throws Exception{
		Collection<Comment> replyList = this.commentDAO.getCommentByReply(rows, UserID, CommentID);
		int replyRows = this.commentDAO.getCountByReply(UserID, CommentID);
		CommentData replyData = new CommentData(replyRows, replyList);
		return replyData;
	}
	
	/**
	 * 根据App请求类型获取评论和回复
	 * @param rows
	 * @param keyword
	 * @param RootID
	 * @param RootType
	 * @return
	 * @throws Exception
	 */
	public CommentData getCommentByApp(String rows, String keyword, String RootID, String RootType)throws Exception{
		String sqlString = "";
		if(keyword!=null){
			sqlString = getSqlString(keyword);
		}
		Collection<Comment> comment = this.commentDAO.getCommentByApp(rows, sqlString, RootID, RootType);
		for(Comment com : comment){
			int likeCount = this.likeDAO.getCountByThemeID(com.getCommentID(), CommonConfig.THEME_TYPE_CT);
			com.setLikeCount(likeCount);
		}
		int count = this.commentDAO.getCountByApp(sqlString,RootID,RootType);
		CommentData commentData = new CommentData(count, comment);
		return commentData;
		
	}
	/**
	 * 为评论点赞
	 * @param like
	 * @throws Exception
	 */
	public boolean like(Likes like)throws Exception{
		boolean result = false;
		Likes li = this.likeDAO.getLikeByUserID(like.getUserID(), like.getThemeID(), like.getType());
		if(li == null){
			if(this.likeDAO.create(like)){
				result = true;
			}
		}
		return result;
	}
	
	
	
	/**
	 * 删除评论
	 * @param commentID
	 * @throws Exception
	 */
	public void delete(String commentID)throws Exception{
		Comment comment = this.commentDAO.getByCommentID(commentID);
		if(comment!=null){
			if(comment.getAction().equals(CommonConfig.ACTION_COMMENT)){
				int rows = this.commentDAO.getCountByReply(comment.getUserID(), comment.getCommentID());
				Collection<Comment> replyList = this.commentDAO.getCommentByReply(rows+"", comment.getUserID(), comment.getCommentID());
				if(replyList.size() > 0){
					for(Comment c : replyList){
						if(!this.commentDAO.delete(c.getCommentID())){
							throw new GalaxyLabException("Error in delete reply");
						}
					}
				}
				
			}
			if(!this.commentDAO.delete(comment.getCommentID())){
				throw new GalaxyLabException("Error in delete comment");
			}
		}
	}
	
	
	public String getSqlString(String keyword){
		String sql = "and (RootType like '%"
				+ keyword
				+ "%' or RootID like '%"
				+ keyword 
				+ "%' or UserName like '%"
				+ keyword 
				+ "%' or UserID like '%"
				+ keyword 
				+ "%' or Memo like '%"
				+ keyword 
				+ "%' or ReplyID like '%"
				+ keyword 
				+ "%' or ReplyName like '%"
				+ keyword 
				+ "%' or date_format(`Created_TS`,'%Y-%m-%d') like '%"
				+ keyword +"%') ";
		return sql;
	}
	
}
