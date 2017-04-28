package ycp.cs320.teamProject.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ycp.cs320.teamProject.DBpersist.*;
import ycp.cs320.teamProject.model.*;


public class Projectcontroller {
	private IDatabase db;
	private TrainingHistory t; 
	
	public Projectcontroller() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	//User Methods and db methods 
	public ArrayList<User> matchUserNameWithPassword(String username) {
		
		List<User> userList = db.matchUsernameWithPassword(username);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for(User user : userList) {
			users.add(user);
		}
		
		return users;
	}
	
	//allow a user, or an admin to access a users account information 
	public ArrayList<User> getAccountInformation(String username){
		List<User> userList = db.getAccountInfo(username);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for(User user : userList) {
			users.add(user);
		}
		
		return users;
	}
	
	
	
	//Changed the arguments to newpassword / oldpassword to signify the changing
	//of password rather than username and added the same you had below with a new name~devin
	
	//Allowing a user to change their password 
	public void changeUserPassword(String Username, String oldpassword, String newpassword)  {
		 //throw new UnsupportedOperationException();//useless exception is useless
		 db.changePassword(Username, oldpassword, newpassword);
	}
	//Allowing a user to change their username 
	public void changeUsername(String Username, String newUsername, String password)  {
		 throw new UnsupportedOperationException();//useless exception is useless
	}
	
	//adding a user to the database 
	public void addUserToDatabase(String userName, String passWord, String email, String type, String firstName, String lastname) {
		db.addUserToDatabase(userName, passWord, email, type, firstName, lastname);
	}
	
	//need to work out how to change this to archive user, not delete 
	public void DeleteUserFromDatabase(String userName, String password){
		db.DeleteUserFromDatabase(userName, password);
	}
	
	//need a method for pulling an SOP out of the DB based on the SOP number
	public void pullSOPfromDB(int sopNumber) {
		
	}
	
	//add an SOP to the DB 
	public boolean addSOPtoDB(String sopName, int sopID, String sopPurpose, String priority, String revision) {
		
		
		List<SOP> sopid = db.addSOP(sopID, sopName, sopPurpose, priority, revision);
		
		if(sopid.size() > 0) {
			System.out.println("New SOP (ID: " + sopid + "entered into the database");
			return true;
		}
		else {
			System.out.println("Failed to add the new sop (ID: " + sopid + "to the DB");
			return false;
		}
	}
	
	//revise an SOP in the DB
	public void reversionSOP(int sopID, String version, String newVersion) {
		db.reviseSOP(sopID, version, newVersion);
	}
	
	//change an SOP's priority in the DB
	public void changeSOPpriority(int sopID, String priority, String newPriority){
		db.changePriority(sopID, priority, newPriority);
	}
	
	public static boolean authenticate(User u, String pswd)
	{
		boolean real = false;
		if(u.getPassword().equals(pswd)){

			real = true;
		}
		
		return real;
	}
	
	//positions methods
	public void addPositionToDatabase(String name, String duty) {
		db.addPositionToDatabase(name, duty);
	}
	
	
	//utility methods 
	/*public Queue<SOP> pushToPriorityQueue(SOP sopID){
		Queue<SOP> priorityQueue = null;
		if(sopID.getPriority() >= 7) {
			priorityQueue = p.pushToQueue(sopID);
		}
		return priorityQueue;
	}*/
}
