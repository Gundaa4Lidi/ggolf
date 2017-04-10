package com.galaxy.ggolf.dao;

import java.util.Collection;

import com.galaxy.ggolf.dao.mapper.ClubRowMapper;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.tools.FileUtil;
import com.galaxy.ggolf.tools.ListUtil;

public class ClubDAO extends GenericDAO<Club> {

	public ClubDAO() {
		super(new ClubRowMapper());
	}
	
	public int getCount(){
		String sql = "select count(*) from club where DeletedFlag is null ";
		return super.count(sql);
	}
	
	public Collection<Club> getAll(){
		String sql = "select * from club WHERE DeletedFlag is NULL order by Created_TS desc";
		return super.executeQuery(sql);
	}
	
	public Collection<Club> getSearch(String pageNum, String size, String sqlString){
		int pages = 1;
		String limit = "";
		if(pageNum!=null&&!pageNum.equals("null")){
			pages = Integer.parseInt(pageNum);
		}
		if(size!=null && !size.equals("null")){
			limit ="limit " +((pages - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
		}
		String sql = "select * from club WHERE DeletedFlag is NULL "+sqlString+" order by Created_TS desc "+limit+"";
		return super.executeQuery(sql);
				
	}
	
	//生成球场编号
	public int getClubID(){
		String[] s = new String[2];
		s[0] = "insert into clubcount(`Num`)values(NULL)";
		s[1] = "select max(Num) as a  from clubcount";
		return super.getId(s);
	}
	
	public int getSearchCount(String sqlString){
		String sql = "select count(*) from club where DeletedFlag is null "+sqlString+"";
		return super.count(sql);
	}
	
	public Collection<Club> getSearchClub(String sqlString, String rows, String pageNum){
		String sql = "select * from club where DeletedFlag is null "+sqlString+""
				+ "order by Created_TS desc limit "
				+ ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(rows)) + " ," + Integer.parseInt(rows) + " ";
		return super.executeQuery(sql);
	}
	
	public boolean create(Club club){
		Collection<String> photos = club.getClubPhoto();
		String photo = "";
		if(club.getLogo()!=null){
//			club.setLogo(FileUtil.GetImageUrl(base64Img, fileName, filePath));
		}
		if(photos != null){
			for(String str : photos){
				photo = photo + ";" + str;
			}
			if(photo.indexOf(";")!=-1){
				photo = photo.substring(1,photo.length());
			}
		}
		String sql = "insert into club (ClubID,"
				+ "ClubName,"
				+ "ClubPhoneNumber,"
				+ "ClubAddress,"
				+ "ClubPhoto,"
				+ "Logo,"
				+ "ClubType,"
				+ "Province,"
				+ "City,"
				+ "Area,"
				+ "Price,"
				+ "DiscountPrice,"
				+ "TotalStemNum,"
				+ "TotalHole,"
				+ "Longitude,"
				+ "Latitude,"
				+ "Created_TS)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.sqlUpdate(sql,club.getClubID(),club.getClubName(),club.getClubPhoneNumber(),
							club.getClubAddress(),photo,club.getLogo(),club.getClubType(),club.getProvince(),club.getCity(),
							club.getArea(),club.getPrice(),club.getDiscountPrice(),club.getTotalStemNum(),
							club.getTotalHole(),club.getLongitude(),club.getLatitude(),Time());
	}
	
	public boolean updateClub(Club club){
		Collection<String> photos = club.getClubPhoto();
		String photo = "";
		if(photos != null && photos.size()>0){
			photo = new ListUtil().ListToString(photos);
			logger.debug("photos----{}",photo);
		}
		String sql = "update club set ClubName=?,"
				+ "ClubPhoneNumber=?,"
				+ "ClubAddress=?,"
				+ "ClubType=?,"
				+ "ClubPhoto=?,"
				+ "Logo=?,"
				+ "Province=?,"
				+ "City=?,"
				+ "Area=?,"
				+ "Price=?,"
				+ "DiscountPrice=?,"
				+ "TotalStemNum=?,"
				+ "TotalHole=?,"
				+ "Longitude=?,"
				+ "Latitude=?,"
				+ "Updated_TS=? "
				+ "where ClubID=?";
		return super.executeUpdate(sql, club.getClubName(),club.getClubPhoneNumber(),
				club.getClubAddress(),club.getClubType(),photo,club.getLogo(),club.getProvince(),
				club.getCity(),club.getArea(),club.getPrice(),club.getDiscountPrice(),
				club.getTotalStemNum(),club.getTotalHole(),club.getLongitude(),
				club.getLatitude(),Time(),club.getClubID());
	}
	
	public Club getClubByClubID(String clubID){
		String sql = "select * from club where DeletedFlag is null and ClubID='"+ clubID +"'";
		Collection<Club> result = super.executeQuery(sql);
		if(result.size() > 0){
			return (Club) result.toArray()[0]; 
		}else{
			return null;
		}
	}
	
	
	
	public boolean deleteClub(String clubID){
		String sql = "update club set DeletedFlag='Y',updated_TS='"+Time()+"' where ClubID='"+ clubID +"'";
		return super.executeUpdate(sql);
	}

}

/**
 *               ii.                                         ;9ABH,          
 *              SA391,                                    .r9GG35&G          
 *              &#ii13Gh;                               i3X31i;:,rB1         
 *              iMs,:,i5895,                         .5G91:,:;:s1:8A         
 *               33::::,,;5G5,                     ,58Si,,:::,sHX;iH1        
 *                Sr.,:;rs13BBX35hh11511h5Shhh5S3GAXS:.,,::,,1AG3i,GG        
 *                .G51S511sr;;iiiishS8G89Shsrrsh59S;.,,,,,..5A85Si,h8        
 *               :SB9s:,............................,,,.,,,SASh53h,1G.       
 *            .r18S;..,,,,,,,,,,,,,,,,,,,,,,,,,,,,,....,,.1H315199,rX,       
 *          ;S89s,..,,,,,,,,,,,,,,,,,,,,,,,....,,.......,,,;r1ShS8,;Xi       
 *        i55s:.........,,,,,,,,,,,,,,,,.,,,......,.....,,....r9&5.:X1       
 *       59;.....,.     .,,,,,,,,,,,...        .............,..:1;.:&s       
 *      s8,..;53S5S3s.   .,,,,,,,.,..      i15S5h1:.........,,,..,,:99       
 *      93.:39s:rSGB@A;  ..,,,,.....    .SG3hhh9G&BGi..,,,,,,,,,,,,.,83      
 *      G5.G8  9#@@@@@X. .,,,,,,.....  iA9,.S&B###@@Mr...,,,,,,,,..,.;Xh     
 *      Gs.X8 S@@@@@@@B:..,,,,,,,,,,. rA1 ,A@@@@@@@@@H:........,,,,,,.iX:    
 *     ;9. ,8A#@@@@@@#5,.,,,,,,,,,... 9A. 8@@@@@@@@@@M;    ....,,,,,,,,S8    
 *     X3    iS8XAHH8s.,,,,,,,,,,...,..58hH@@@@@@@@@Hs       ...,,,,,,,:Gs   
 *    r8,        ,,,...,,,,,,,,,,.....  ,h8XABMMHX3r.          .,,,,,,,.rX:  
 *   :9, .    .:,..,:;;;::,.,,,,,..          .,,.               ..,,,,,,.59  
 *  .Si      ,:.i8HBMMMMMB&5,....                    .            .,,,,,.sMd 
 *  SS       :: h@@@@@@@@@@#; .                     ...  .         ..,,,,iM5 
 *  91  .    ;:.,1&@@@@@@MXs.                            .          .,,:,:&S 
 *  hS ....  .:;,,,i3MMS1;..,..... .  .     ...                     ..,:,.99 
 *  ,8; ..... .,:,..,8Ms:;,,,...                                     .,::.83 
 *   s&: ....  .sS553B@@HX3s;,.    .,;13h.                            .:::&1 
 *    SXr  .  ...;s3G99XA&X88Shss11155hi.                             ,;:h&, 
 *     iH8:  . ..   ,;iiii;,::,,,,,.                                 .;irHA  
 *      ,8X5;   .     .......                                       ,;iihS8Gi
 *         1831,                                                 .,;irrrrrs&@
 *           ;5A8r.                                            .:;iiiiirrss1H
 *             :X@H3s.......                                .,:;iii;iiiiirsrh
 *              r#h:;,...,,.. .,,:;;;;;:::,...              .:;;;;;;iiiirrss1
 *             ,M8 ..,....,.....,,::::::,,...         .     .,;;;iiiiiirss11h
 *             8B;.,,,,,,,.,.....          .           ..   .:;;;;iirrsss111h
 *            i@5,:::,,,,,,,,.... .                   . .:::;;;;;irrrss111111
 *            9Bi,:,,,,......                        ..r91;;;;;iirrsss1ss1111
 */