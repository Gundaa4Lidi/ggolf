package com.galaxy.ggolf.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.StaffDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.tools.CipherUtil;


public class StaffManager {
	public GenericCache<String, Staff> staffCache;
	public StaffDAO staffDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public StaffManager(StaffDAO staffDAO) {
		this.staffCache = new GenericCache<String, Staff>();
		this.staffDAO = staffDAO;
	}

	public void saveStaff(Staff staff) throws GalaxyLabException {
		if(staff.getUID()!=null){
			updateStaff(staff);
		}else{
			createStaff(staff);
		}		
	}
	
	private void createStaff(Staff staff) throws GalaxyLabException{
		Staff existingStaff =  getStaff(staff.getStaffID());
		if(existingStaff!=null){
			throw new GalaxyLabException("Staff exist");
		}
		if(!staffDAO.createStaff(staff)) {
			throw new GalaxyLabException("Error in create staff");
		}
		Staff staff1 = staffDAO.getStaffByStaffID(staff.getStaffID());
		staffCache.put(staff1.getStaffID(), staff1);
	}
	
	private void updateStaff(Staff staff) throws GalaxyLabException{
		CipherUtil cipher = new CipherUtil();
		if(!staff.getPassword().equals(staffDAO.getadminStaffByStaffID(staff.getStaffID()).getPassword())){
			staff.setPassword(cipher.generatePassword(staff.getPassword()));
		}
		if (!staffDAO.updateStaff(staff)) {
			throw new GalaxyLabException("Error in update staff");
		}
		staffCache.put(staff.getStaffID(), staff);
	}
	
	public boolean updateStaffPassword(String staffId,String newpassword){
		if(staffDAO.updateStaffPassword(staffId, newpassword)){
			return true;
		}else{
			return false;
		}
	}

	public Staff getStaff(String staffId) {
		if (staffCache.get(staffId) != null) {
			logger.debug("cache hit");
			return staffCache.get(staffId);
		} else {
			logger.debug("cache missed, looking up from DB");
			Staff staff = staffDAO.getStaffByStaffID(staffId);
			if (staff != null) {
				this.staffCache.put(staff.getStaffID(), staff);
			}
			return staff;
		}
	}
	
	public Staff logonStaff(String StaffID){
		Staff staff = staffDAO.getadminStaffByStaffID(StaffID);
		if(staff!=null){
		return staff;
		}else{
			return null;
		}
		
	}
	
	public void saveHead(Staff staff)throws Exception{
		if(!this.staffDAO.saveHead(staff)){
			throw new GalaxyLabException("Error in update staffHead");
		}else{
			Staff staff1 = this.staffDAO.getStaffByStaffID(staff.getStaffID());
			if (staff1 != null) {
				this.staffCache.put(staff1.getStaffID(), staff1);
			}
		}
	}
	
	public Collection<Staff> getAll(){
		Collection<Staff> staffs = staffDAO.getAllStaff();
		return staffs;
	}
	
	public boolean deleteStaff(String staffId) throws Exception{
		if(staffDAO.deletStaff(staffId)){
			staffCache.remove(staffId);
			return true;
		}else{
			return false;
		}
		
	}
}
