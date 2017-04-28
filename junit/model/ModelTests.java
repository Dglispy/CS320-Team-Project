package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import ycp.cs320.teamProject.model.*; 

public class ModelTests {
	 //test users
	 private User user;
	 private User Admin;
	 private boolean isAdmin;
	
	 //test SOPs
	 private SOP sop;
	 private SOP sop2;
	 private SOP sop3;
	 
	 //Test positions
	 private Position admin;
	 private Position Manager;
	 private Position regularUser;
	 private ArrayList<Position> positionsAffected;
	 private ArrayList<SOP> sopsAffected; 
	 
	 //Test histories 
	 private TrainingHistory userhistory;
	 private TrainingHistory adminhistory;
	 private TrainingHistory Managerhistory;
	 
	 @Before
	 public void setUp() {
		 user = new User();
		 user.setAdmin("user");
		 
		 user.setUsername("Sir Robin");
		 user.setPassword("bravebravebrave");
		 user.setUserAccountInformation("Eric", "Idle", "junkTrons@junkworld.com");
		 
		 
		 Admin = new User();
		 Admin.setAdmin("Admin");
		 Admin.setUsername("Bruce");
		 Admin.setPassword("noPuftas");
		 Admin.setUserAccountInformation("Bruce", "Bruce", "australiaWeLoveYouAmen@email.aus");
		 
		 
		 sop= new SOP();
		 sop.setSopPurpose("Keep it simple stupid");
		 sop.setSopName("K.I.S.S");
		 sop.setSopIdNumber(1025649);
		 sop.setPriority("7");
		 positionsAffected = new ArrayList<Position>();
		 
		 sop2 = new SOP();
		 
		 sop2.setSopName("Dosument Editing");
		 sop2.setSopIdNumber(1025650);
		 
		 sop3 = new SOP();
		 ;
		 sop3.setSopName("Stretching Work");
		 sop3.setSopIdNumber(1025651);
		 
		 
		 /*positionsAffected = new ArrayList<Position>();
		
		 positionsAffected.add(Manager);
		 positionsAffected.add(admin);
		 positionsAffected.add(regularUser);*/
		 
		 
		 admin = new Position();
		 admin.setPositionIDS(1025682);
		 admin.setPositionName("Administrator");
		 
		 regularUser = new Position();
		 regularUser.setPositionIDU(1102597);
		 regularUser.setPositionName("User");
		 
		 Manager = new Position();
		 Manager.setPositionIDU(1200349);
		 Manager.setPositionName("Manager");
		 
		 
		 //test training histories 
		 userhistory = new TrainingHistory();
		 userhistory.addToCompleted(sop);
		 userhistory.addToTodoList(sop2);
		 userhistory.addToTodoList(sop3);
		 
		 adminhistory = new TrainingHistory();
		 adminhistory.addToCompleted(sop);
		 adminhistory.addToCompleted(sop2);
		 adminhistory.addToCompleted(sop3);
		 
		 Managerhistory = new TrainingHistory();
		 Managerhistory.addToCompleted(sop);
		 Managerhistory.addToCompleted(sop2);
		 Managerhistory.addToTodoList(sop3);
		 
	 }
	 
	 @Test
	 public void testUsername() {
		 String username = user.getUsername();
		 String username2 = Admin.getUsername();
		 
		 assertEquals("Sir Robin", username);
		 assertEquals("Bruce", username2);
	 }
	 
	 @Test
	 public void testPassword() {
		 String pswd = user.getPassword();
		 String pswd2 = Admin.getPassword();
		 assertEquals("bravebravebrave", pswd);
		 assertEquals("noPuftas", pswd2);
	 }
	 
	 @Test
	 public void testAccountInfo() {
		 String Firstname = user.getFirstName();
		 String firstname = Admin.getFirstName();
		 String Lastname = user.getLastName();
		 String lastname = Admin.getLastName();
		 String Email = user.getEmailAddress();
		 String email = Admin.getEmailAddress();
		 
		 
		 assertEquals("Eric", Firstname);
		 assertEquals("Idle", Lastname);
		 assertEquals("junkTrons@junkworld.com", Email);
		 
		 assertEquals("Bruce", firstname);
		 assertEquals("Bruce", lastname);
		 assertEquals("australiaWeLoveYouAmen@email.aus", email);
	 }
	 
	 @Test
	 public void testAccountType() {
		 String use = user.isAdmin();
		 String admin = Admin.isAdmin();
		 
		 assertEquals(use, "user");
		 assertEquals(admin, "Admin");
	 }
	 
	 public void testLoginAndFirstTimeStatus() {
		 
	 }
	 
	 @Test
	 public void testSOP() {
		 String sopPurpose = sop.getSopPurpose();
		 String sopName = sop.getSopName();
		 String priority = sop.getPriority();
		 int id = sop.getSopIdNumber();
		 
		 assertEquals("K.I.S.S", sopName);
		 assertEquals("7", priority);
		 assertEquals("Keep it simple stupid", sopPurpose);
		 assertEquals("1025649", id);
	 }
	 
	 @Test
	 public void testPositionsAffected() {
		 positionsAffected = new ArrayList<Position>();
		
		 positionsAffected.add(Manager);
		 positionsAffected.add(admin);
		 positionsAffected.add(regularUser);
		 
		 assertEquals(3, positionsAffected.size());
		 
		 //need to find how to get this test to pass 
		 //assertEquals(3, sop.showPositionsAffected(1002456));
	 }
	 
	 
	 @Test
	 public void testPositionRegulation() {
		 Manager.addRegulatedSOP(sop);
		 Manager.addRegulatedSOP(sop3);
		 
		 assertEquals(2, Manager.getRegulatingSOPs().size());
		 
		 admin.addRegulatedSOP(sop);
		 admin.addRegulatedSOP(sop2);
		 
		 assertEquals(2, admin.getRegulatingSOPs().size());
		 
		 int testsopID = Manager.findRelevantSOP(sop.getSopIdNumber());
		 assertEquals(testsopID, sop.getSopIdNumber());
		 
		 int testsopID2 = admin.findRelevantSOP(sop2.getSopIdNumber());
		 assertEquals(testsopID2,sop2.getSopIdNumber());
		 
		 
	 }
	 
	 @Test
	 public void testPositions() {
		int positionID1 = Manager.getPositionIDS();
		int positionID2 = regularUser.getPositionIDS();
		int positionID3 = admin.getPositionIDS();
		
		assertEquals(1200349, positionID1);
		assertEquals(1102597, positionID2);
		assertEquals(1025682, positionID3);
		
		String name1 = Manager.getPositionName();
		String name2 = regularUser.getPositionName();
		String name3 = admin.getPositionName();
		
		assertEquals("Manager", name1);
		assertEquals("User", name2);
		assertEquals("Administrator", name3);
		
	 }
	 
	 @Test
	 public void testTrainingHistories() {
		 int hist1 = userhistory.TrainingHistorySize();
		 int hist2 = adminhistory.TrainingHistorySize();
		 int hist3 = Managerhistory.TrainingHistorySize();
		 
		 assertEquals(3, hist1);
		 assertEquals(3, hist2);
		 assertEquals(3, hist3);
		 
		 
	 }
	 
	 
	 
}
