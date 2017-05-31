package com.galaxy.ggolf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.mapper.RowMapper;
import com.galaxy.ggolf.jdbc.CommonConfig;



public abstract class GenericDAO<T> {

	private final RowMapper<T> mapper;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public GenericDAO(RowMapper<T> mapper) {
		this.mapper = mapper;
	}

	protected ArrayList<T> convert(ResultSet resultSet) throws SQLException {
		ArrayList<T> result = new ArrayList<T>();
		while (resultSet.next()) {
			result.add(mapper.mapRow(resultSet));
		}
		return result;
	}

	protected Connection getConnection() throws Exception {
		Class.forName(CommonConfig.DB_CLASS).newInstance();
		Connection conn = DriverManager.getConnection(CommonConfig.DB_URL,
				CommonConfig.DB_ID, CommonConfig.DB_PWD);
		return conn;
	}

	/**
	 * Execute query and return result as an ArrayList
	 * */
	public ArrayList<T> executeQuery(String sql) {
		ResultSet resultSet = null;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			logger.debug("SQL is: {}", sql);
			resultSet = stmt.executeQuery(sql);
			return convert(resultSet);
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return null;
	}

	/**
	 * Execute query and return whether any row is updated
	 * */
	public boolean executeUpdate(String sql) {
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			logger.debug("SQL is: {}", sql);
			stmt.execute(sql);
			if (stmt.getUpdateCount() >= 1) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return false;
	}
	
	public boolean executeUpdate(String sql, String... params) {
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			logger.debug("SQL is: {}", sql);
			if (params != null && params.length > 0) {
				logger.debug("Param is: {}", listToArray(params));
				for (int i = 0; i < params.length; i++) {
					stmt.setString(i + 1, params[i]);
				}
			}
			stmt.execute();
			if (stmt.getUpdateCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return false;
	}

	/**
	 * Execute query and return whether any row is updated
	 * */
	public boolean executeUpdateWithStatement(String sql,
			ArrayList<String> params) {
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			logger.debug("SQL is: {}", sql);
			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, params.get(i));
			}
			stmt.execute();
			if (stmt.getUpdateCount() >= 1) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return false;
	}
	
	
	public boolean sqlUpdate(String sql, String... params) {
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			logger.debug("SQL is: {}", sql);
			if (params != null && params.length > 0) {
				logger.debug("Param is: {}", listToArray(params));
				for (int i = 0; i < params.length; i++) {
					stmt.setString(i + 1, params[i]);
				}
			}
			stmt.execute();
			if (stmt.getUpdateCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return false;
	}
	
	private String listToArray(String[] params) {
		String sb = "";
		for (String param : params) {
			sb = sb + param + ",";
		}
		return sb.substring(0, sb.lastIndexOf(","));
	}
	
	public String Time(){
		Date now = new Date();
		String dt = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		return dt;
	}
	
	public String Time1(){
		Date now = new Date();
		String dt = new String(new SimpleDateFormat("yyyy-MM-dd").format(now));
		return dt;
	}

	/**
	 * Return an integer which maps the number of rows
	 * */
	public int count(String sql) {
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			logger.debug("SQL is: {}", sql);
			ResultSet rest = stmt.executeQuery(sql);
			while (rest.next()) {
				return rest.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return 0;
	}
	
	public double avg(String sql) {
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			logger.debug("SQL is: {}", sql);
			ResultSet rest = stmt.executeQuery(sql);
			while (rest.next()) {
				return rest.getDouble(1);
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return 0;
	}

	/**
	 * getId
	 * 
	 * @param sql
	 * @return
	 */
	public int getId(String[] s) {
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			String sql = s[0];
			logger.debug("SQL is: {}", sql);
			stmt.execute(sql);
			String sql1 = s[1];
			ResultSet rest = stmt.executeQuery(sql1);
			while (rest.next()) {
				return rest.getInt(1);
			}

		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return 0;
	}
	
	public boolean batchInsert(Collection<String> sqls) {
		Connection conn = null;
		try {
			conn = getConnection();
			if(sqls.size()>0){
				Statement stmt = conn.createStatement();
				for (String sql:sqls) {
					stmt.addBatch(sql);
					logger.debug("SQL is: {}", sql);
				}
			stmt.executeBatch();
			return true;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error", e);
				}
		}
		return false;
	}
	
	
	public String getCustomID(String prefix,String main,String count){
		String CustomID = "";
		main = main.substring(0,main.length() - count.length());
		CustomID = prefix + main + count;
		return CustomID;
	}
	
	public String limit(String pageNum,String rows){
		String limit = "";
		int page = 1;
		if(pageNum!=null&&!pageNum.equals("")&&!pageNum.equalsIgnoreCase("null")){
			page = Integer.parseInt(pageNum);
		}
		if(rows!=null&&!rows.equals("")&&!rows.equalsIgnoreCase("null")){
			int row = Integer.parseInt(rows);
			limit = "limit "+(page-1)*row+","+row+" ";
		}
		return limit;
	}

}
