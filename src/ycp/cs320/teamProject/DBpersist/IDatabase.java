package ycp.cs320.teamProject.DBpersist;

import java.util.List;


import ycp.cs320.teamProject.model.*;

public interface IDatabase {
	//*******************
	// User Related methods implemented in Derby Database
	//*******************
	List<User> getAccountInfo(String name);
	List<User> matchUsernameWithPassword(String name);	
	List<User> addUserToDatabase(String name, String pswd, String email, String type, String first, String last);
	List<User> DeleteUserFromDatabase(String name, String pswd);
	List<User> changePassword(String name, String pswd, String newPassword);
	List<User> findAllUsers();
	List<User> findUserByLastName(String lastname);
	//rework 'delete users from database' to archive 'usersInDatabase'



	//*******************
	//SOP related methods implemented in derby database
	//*******************

	List<SOP> FindSOPByID(int sopID); 
	List<SOP> addSOP(final int sopID, final String sopName, final String authorID, final String priority, final String revision);
	List<SOP> changePriority(final int sopID, final String priority, final String newPriority);
	List<SOP> reviseSOP(final int sopID, final String version, final String newVersion);
	List<SOP> findSOPByName(String sopName);
	
	//archive sop
	

	//*******************
	//Position related methods 
	//*******************
	List<Position> getPositionByID(int positionId);
	List<Position> getPositionInfo(String position);
	List<Position> addPositionToDatabase(String name, String duty); 
	List<Position> findPositionByName(String positionName);
	
	//*******************
	//Training History related methods implemented in derby database
	//*******************

	//going to have a search in this section
	//implement sorts

	//******************
	//Utility Based methods
	//******************


}
