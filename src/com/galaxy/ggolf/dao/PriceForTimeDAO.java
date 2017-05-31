package com.galaxy.ggolf.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.PriceForTimeRowMapper;
import com.galaxy.ggolf.domain.PriceForTime;

public class PriceForTimeDAO extends GenericDAO<PriceForTime> {

	public PriceForTimeDAO() {
		super(new PriceForTimeRowMapper());
	}

	/**
	 * 创建时间段
	 * @param pft
	 * @return
	 */
	public boolean create(PriceForTime pft){
		String sql = "insert into pricefortime(ClubserveID,"
				+ "ClubID,"
				+ "Week,"
				+ "Time,"
				+ "DownPayment,"
				+ "OtherPrice,"
				+ "Type,"
				+ "IsPrivilege,"
				+ "IsDeposit,"
				+ "IsValid,"
				+ "DateTime,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.executeUpdate(sql,pft.getClubserveID(),pft.getClubID(),pft.getWeek(),
				pft.getTime(),pft.getDownPayment(),pft.getOtherPrice(),pft.getType(),
				pft.getIsPrivilege(),pft.getIsDeposit(),pft.getIsValid(),pft.getDateTime(),Time());
	}
	
	/**
	 * 批量创建时间段
	 * @param timeList
	 * @return
	 */
	public boolean create2(Collection<PriceForTime> timeList){
		Collection<String> sqls = new ArrayList<String>();
		for(PriceForTime pft : timeList){
			String sql = "insert into pricefortime(ClubserveID,"
					+ "ClubID,"
					+ "Week,"
					+ "Time,"
					+ "DownPayment,"
					+ "OtherPrice,"
					+ "Type,"
					+ "IsPrivilege,"
					+ "IsDeposit,"
					+ "IsValid,"
					+ "Created_TS)values('"+pft.getClubserveID()+"','"+pft.getClubID()+"','"+pft.getWeek()+"','"+pft.getTime()
					+ "','"+pft.getDownPayment()+"','"+pft.getOtherPrice()+"','"+pft.getType()+"','"+pft.getIsPrivilege()
					+ "','"+pft.getIsDeposit()+"','"+pft.getIsValid()+"','"+Time()+"')";
			sqls.add(sql);
		}
		return super.batchInsert(sqls);
	}
	
	/**
	 * 修改相关时段的价格与说明
	 * @param pft
	 * @return
	 */
	public boolean update(PriceForTime pft){
		String sql = "update pricefortime set DownPayment=?,"
				+ "OtherPrice=?,"
				+ "Type=?,"
				+ "IsDeposit=?,"
				+ "IsPrivilege=?,"
				+ "IsValid=?,"
				+ "DateTime=?,"
				+ "Updated_TS=?"
				+ " where DeletedFlag is null and ClubservePriceID=?";
		return super.executeUpdate(sql,pft.getDownPayment(),pft.getOtherPrice(),
				pft.getType(),pft.getIsDeposit(),pft.getIsPrivilege(),pft.getIsValid(),
				pft.getDateTime(),Time(),pft.getClubservePriceID());
	}
	
	/**
	 * 批量修改相关时段的价格与说明
	 * @param timeList
	 * @return
	 */
	public boolean update2(Collection<PriceForTime> timeList){
		Collection<String> sqls = new ArrayList<String>();
		for(PriceForTime pft : timeList){
			String sql = "update pricefortime set DownPayment='"+pft.getDownPayment()+"',"
					+ "OtherPrice='"+pft.getOtherPrice()+"',"
					+ "Type='"+pft.getType()+"',"
					+ "IsDeposit='"+pft.getIsDeposit()+"',"
					+ "IsPrivilege='"+pft.getIsPrivilege()+"',"
					+ "IsValid='"+pft.getIsValid()+"',"
					+ "Updated_TS='"+Time()+"'"
					+ " where DeletedFlag is null and ClubservePriceID='"+pft.getClubservePriceID()+"'";
			sqls.add(sql);
		}
		return super.batchInsert(sqls);
	}
	
	/**
	 * 获取对应当天所有时段价格
	 * @param Week
	 * @param ClubserveID
	 * @return
	 */
	public Collection<PriceForTime> getPricesByClubserveID(String Week,String ClubserveID,String sqlString){
		String sql = "select * from pricefortime where DeletedFlag is null a Week='"+Week+"' and ClubserveID='"+ClubserveID+"' "+sqlString+"";
		return super.executeQuery(sql);
	}
	
	
	/**
	 * 根据供应商编号获取当前时段的价格
	 * @param Week
	 * @param Time
	 * @param ClubserveID
	 * @return
	 */
	public PriceForTime getPriceByClubServeID(String Week, String Time, String ClubserveID,String sqlString){
		String sql = "select * from pricefortime where DeletedFlag is null and Week='"+Week+"' and Time='"+Time+"' and ClubserveID='"+ClubserveID+"' "+sqlString+"";
		Collection<PriceForTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (PriceForTime) result.toArray()[0];
		}
		return null;
	}
	
	/**
	 * 查找该时段是否存在
	 * @param ClubserveID
	 * @param DateTime
	 * @return
	 */
	public PriceForTime checkByDateTime(String ClubserveID,String DateTime){
		String sql = "select * from pricefortime where DeletedFlag is null and ClubserveID='"+ClubserveID+"' and DateTime='"+DateTime+"' Group by DateTime";
		Collection<PriceForTime> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (PriceForTime) result.toArray()[0];
		}
		return null;
	}
	/**
	 * 根据相关信息查看时段价格
	 * @param sqlString
	 * @return
	 */
	public Collection<PriceForTime> getByString(String sqlString,String rows,String pageNum){
		String limit = super.limit(pageNum, rows);
		String sql = "select * from pricefortime where DeletedFlag is null "+sqlString+" "+limit+"";
		return super.executeQuery(sql);
	}
	
	/**
	 * 删除有特殊日期的价格数据
	 * @param ClubserveID
	 * @param DateTime
	 * @return
	 */
	public boolean deleteDateTime(String ClubserveID,String DateTime){
		String sql = "update pricefortime set DeletedFlag='Y',Updated_TS='"+Time()+"'"
				+ " where ClubserveID='"+ClubserveID+"' and DateTime='"+DateTime+"' and DeletedFlag is null";
		return super.executeUpdate(sql);
	}
	
	
}

/**                                                                    
 *            .,,       .,:;;iiiiiiiii;;:,,.     .,,                   
 *          rGB##HS,.;iirrrrriiiiiiiiiirrrrri;,s&##MAS,                
 *         r5s;:r3AH5iiiii;;;;;;;;;;;;;;;;iiirXHGSsiih1,               
 *            .;i;;s91;;;;;;::::::::::::;;;;iS5;;;ii:                  
 *          :rsriii;;r::::::::::::::::::::::;;,;;iiirsi,               
 *       .,iri;;::::;;;;;;::,,,,,,,,,,,,,..,,;;;;;;;;iiri,,.           
 *    ,9BM&,            .,:;;:,,,,,,,,,,,hXA8:            ..,,,.       
 *   ,;&@@#r:;;;;;::::,,.   ,r,,,,,,,,,,iA@@@s,,:::;;;::,,.   .;.      
 *    :ih1iii;;;;;::::;;;;;;;:,,,,,,,,,,;i55r;;;;;;;;;iiirrrr,..       
 *   .ir;;iiiiiiiiii;;;;::::::,,,,,,,:::::,,:;;;iiiiiiiiiiiiri         
 *   iriiiiiiiiiiiiiiii;;;::::::::::::::::;;;iiiiiiiiiiiiiiiir;        
 *  ,riii;;;;;;;;;;;;;:::::::::::::::::::::::;;;;;;;;;;;;;;iiir.       
 *  iri;;;::::,,,,,,,,,,:::::::::::::::::::::::::,::,,::::;;iir:       
 * .rii;;::::,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,::::;;iri       
 * ,rii;;;::,,,,,,,,,,,,,:::::::::::,:::::,,,,,,,,,,,,,:::;;;iir.      
 * ,rii;;i::,,,,,,,,,,,,,:::::::::::::::::,,,,,,,,,,,,,,::i;;iir.      
 * ,rii;;r::,,,,,,,,,,,,,:,:::::,:,:::::::,,,,,,,,,,,,,::;r;;iir.      
 * .rii;;rr,:,,,,,,,,,,,,,,:::::::::::::::,,,,,,,,,,,,,:,si;;iri       
 *  ;rii;:1i,,,,,,,,,,,,,,,,,,:::::::::,,,,,,,,,,,,,,,:,ss:;iir:       
 *  .rii;;;5r,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,sh:;;iri        
 *   ;rii;:;51,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.:hh:;;iir,        
 *    irii;::hSr,.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,.,sSs:;;iir:         
 *     irii;;:iSSs:.,,,,,,,,,,,,,,,,,,,,,,,,,,,..:135;:;;iir:          
 *      ;rii;;:,r535r:...,,,,,,,,,,,,,,,,,,..,;sS35i,;;iirr:           
 *       :rrii;;:,;1S3Shs;:,............,:is533Ss:,;;;iiri,            
 *        .;rrii;;;:,;rhS393S55hh11hh5S3393Shr:,:;;;iirr:              
 *          .;rriii;;;::,:;is1h555555h1si;:,::;;;iirri:.               
 *            .:irrrii;;;;;:::,,,,,,,,:::;;;;iiirrr;,                  
 *               .:irrrriiiiii;;;;;;;;iiiiiirrrr;,.                    
 *                  .,:;iirrrrrrrrrrrrrrrrri;:.                        
 *                        ..,:::;;;;:::,,.                             
 */