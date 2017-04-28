package ycp.cs320.teamProject.DBpersist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;
import ycp.cs320.teamProject.DBpersist.ReadCSV;
import ycp.cs320.teamProject.model.*;

public class InitialData {

	//user db will be for authentication and hold a primary key for users and admins
	public static List<User> getUsers() throws IOException {
		//read the users file
		List<User> userList = new ArrayList<User>();
		ReadCSV readUser = new ReadCSV("Users.csv");

		try {
			//set this id, and auto generate them: they are not unique to the table anymore
			Integer userId = 1;
			while (true) {
				List<String> tuple = readUser.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				User user = new User();

				// read User ID from CSV file, but don't use it
				// auto-generate User ID, instead
				user.setUserID(userId++);				
				user.setUsername(i.next());
				user.setPassword(i.next());
				user.setEmailAddress(i.next());
				user.setAdmin(i.next());
				user.setFirstName(i.next());
				user.setLastName(i.next());
				userList.add(user);
			}

			System.out.println("UserList loaded from CSV file");	
			return userList;
		} finally {
			readUser.close();
		}
	}

	//Sop db will be for authentication and hold primary key for users and admins 
	public static List<SOP> getSOPs() throws IOException {
		System.out.println("in getSOPs");
		//read the SOPs file
		List<SOP> sopList = new ArrayList<SOP>();
		ReadCSV readSOP = new ReadCSV("SOPs.csv");

		try {
			//set this id, and auto generate them: they are not unique to the table anymore
			Integer sopId = 1;
			while(true) {
				List<String> tuple = readSOP.next();
				if(tuple == null) {
					break;
				}

				System.out.println("taking the data from ReadCSV and adding it");

				Iterator<String> i = tuple.iterator();
				SOP sop = new SOP();

				sop.setSopIdNumber(sopId++);
				sop.setSopName(i.next());
				sop.setSopPurpose(i.next());
				sop.setPriority(i.next());
				sop.setRevision(i.next());

				sopList.add(sop);

			}
			System.out.println("SOPList loaded from the CSV file");
			return sopList;
		}
		finally {
			readSOP.close();
		}
	}
	//Position db will be for authentication and hold primary key for positions
	public static List<Position> getPositions() throws IOException {

		//read the Position file
		List<Position> positionList = new ArrayList<Position>();
		ReadCSV readPosition = new ReadCSV("Positions.csv");
		System.out.println("about to loop position csv");
		try {
			//set this id, and auto generate them: they are not unique to the table anymore
			Integer positionId = 1;
			while(true) {
				List<String> tuple = readPosition.next();
				if(tuple == null) {
					System.out.println("breaking for empty");
					break;

				}
				System.out.println("Looping Position");
				Iterator<String> i = tuple.iterator();
				Position position = new Position();

				position.setPositionIDS(positionId++);
				position.setPositionIDU(position.getPositionIDS());
				position.setPositionName(i.next());
				position.setPositionDuty(i.next());

				positionList.add(position);


			}
			System.out.println("position List loaded from the CSV file");
			return positionList;
		}
		finally {
			readPosition.close();
		}

	}
	
	//load the Position to Sop Junction table from the CSV file 
	public static List<PositionSOP> getPositionsSOPsList() throws IOException {

		List<PositionSOP> positionToSOPList = new ArrayList<PositionSOP>();
		ReadCSV readPositionSOP = new ReadCSV("Position-SOP.csv");
		System.out.println("Reading CSV Position SOP file");

		try {
			while(true) {
				List<String> tuple = readPositionSOP.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				PositionSOP ps = new PositionSOP();
				ps.setPositionID(Integer.parseInt(i.next()));
				ps.setSopID(Integer.parseInt(i.next()));
				positionToSOPList.add(ps);
				
			}
			
			System.out.println("Position to sop junction loaded from CSV file");
			return positionToSOPList;
		} 
		
		finally {
			readPositionSOP.close();
		}


		
	}
	
	//load User to position table information from CSV file 
	public static List<UserPosition> getUserPositionList() throws IOException {
		List<UserPosition> userPositionList = new ArrayList<UserPosition>();
		ReadCSV readUserPositions = new ReadCSV("User-Position.csv");
		
		try {
			while(true) {
				List<String> tuple = readUserPositions.next();
				if(tuple == null) {
					break;
				}
				
				Iterator<String> i = tuple.iterator();
				UserPosition up = new UserPosition();
				up.setUserID(Integer.parseInt(i.next()));
				up.setPositionID(Integer.parseInt(i.next()));
				userPositionList.add(up);
			}
			System.out.println("User to position junction loaded from CSV file");
			return userPositionList;
		}
		finally {
			readUserPositions.close();
		}
		
	}


}
