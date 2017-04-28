package ycp.cs320.teamProject.DBpersist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import sqldemo.DButil;
import ycp.cs320.teamProject.model.Position;
import ycp.cs320.teamProject.model.PositionSOP;
import ycp.cs320.teamProject.model.SOP;
import ycp.cs320.teamProject.model.User;
import ycp.cs320.teamProject.model.UserPosition;


public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 100;

	//Get user account Information
	@Override
	public List<User> getAccountInfo(final String name) {

		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				
				try{
					stmt = conn.prepareStatement(
							//" select * from users, user_positions " +
								//	" where users.user_id = user_positions.user_id " +
									//" and user_userName = ? "
									" select * from users " +
									" where user_userName = ? "
							);
					
					stmt.setString(1, name);
					resultSet = stmt.executeQuery();

					//if anything is found, return it in a list format
					List<User> result = new ArrayList<User>();
					Boolean found = false;
					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the Users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//Match user with password, for the purposes of logging in and authentication
	@Override
	public List<User> matchUsernameWithPassword(final String name) {

		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				
				try {
					stmt = conn.prepareStatement(
							" select * from users " +
									//until we get the forgein key issue cleared up this is out due to not working
									//I want to get the servlets working for the milestone
									//" select * from users " +
									//" where users.user_id = user_positions.user_id " +
								    " where user_userName = ? "
							);
					
					stmt.setString(1, name);
					List<User> result = new ArrayList<User>();
					resultSet = stmt.executeQuery();

					//if anything is found, return it in a list format
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the Users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//Add a new user to the system 
	@Override
	public List<User> addUserToDatabase(final String name, final String pswd, final String email, final String type, final String first,
			final String last) {
		return executeTransaction(new Transaction<List<User>>() {
			int user_Id = -1;
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				
				//to save employee number
				
				
				//Don't need to edit this method to work with the junction
				try {
					System.out.println("prepareStatement addUser");
					stmt = conn.prepareStatement(
							" insert into users(user_userName, user_passWord, user_email, user_accountType, user_firstName, user_lastName) " +
									" values(?, ?, ?, ?, ?, ?) "
							);
					stmt.setString(1, name);
					stmt.setString(2, pswd);
					stmt.setString(3, email);
					stmt.setString(4, type);
					stmt.setString(5, first);
					stmt.setString(6, last);
					System.out.println("execure addUser");
					stmt.executeUpdate();

					stmt2 = conn.prepareStatement(
							" select * " +
									" from users " +
									" where user_userName = ?"
							);
					stmt2.setString(1, name);

					resultSet = stmt2.executeQuery();

					//if anything is found, return it in a list format
					Boolean found = false;
					List<User> result = new ArrayList<User>();

					/*//This doesn't seem to be working using example from lab 6 by D.Hake					
					while (resultSet.next()) {
						found = true;
						User u = new User();
						loadUser(u, resultSet, 1);
						result.add(u);
					}
*/
					if (resultSet.next())
					{
				 user_Id = resultSet.getInt(1);
						System.out.println("New User <" + name + "> ID: " + user_Id);						
					}
					else	// really should throw an exception here 
					{
						System.out.println("New user <" + name + "> not found in Users table (ID: " + user_Id);
					}
					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the users table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}

	//find all users
	//this is going to be an Admin only function
	@Override
	public List<User> findAllUsers() {
		return executeTransaction(new Transaction<List<User>>() {

			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				
				try {
					stmt = conn.prepareStatement(
							//until we get the forgein key issue cleared up this is out due to not working
							//I want to get the servlets working for the milestone
							" select * from users, user_positions " +
							" where users.user_id = user_positions.user_id "+
							" order by lastName asc, firstName asc "
							);
					
					List<User> result = new ArrayList<User>();

					resultSet = stmt.executeQuery();

					Boolean found = false;
					while(resultSet.next()) {
						found = true;

						User user = new User();
						loadUser(user, resultSet, 1);

						result.add(user);
					}

					if(!found) {
						System.out.println("No users were found in the database");
					}

					return result;	

				}finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}


			}

		});

	}
	
	@Override
	public List<User> findUserByLastName(String lastname) {
		return executeTransaction(new Transaction<List<User>>() {

			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				
				try {
					stmt = conn.prepareStatement(
							//until we get the forgein key issue cleared up this is out due to not working
							//I want to get the servlets working for the milestone
							" select users.*, user_positions.* " +
							" from users, user_positions, " +
							" where users.user_id = user_positions.user_id" +
							" where users.lastname = ? "
							);

					stmt.setString(1, lastname);
					List<User> result = new ArrayList<User>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()) {
						User user = new User();

						loadUser(user, resultSet, 1);

						result.add(user);
					}
					return result;
				}
				finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}


			}

		});
	}

	//this one may be tricky to work out 
	@Override
	public List<User> DeleteUserFromDatabase(final String name, final String pswd) {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null; 
				PreparedStatement stmt3 = null;
				
				
				ResultSet resultSet = null;

				try {

				
					stmt = conn.prepareStatement(
							" select users.* " +
									" from users, user_positions " +
									" where users.user_id = user_positions.user_id " +
									" and users_userName = ? " +
									" and users_passWord = ? "
							);
					stmt.setString(1, name);
					stmt.setString(2, pswd);
					resultSet = stmt.executeQuery();

				
					//Using the remove book by title as a guide here
					List<User> Users = new ArrayList<User>();

					

					while (resultSet.next()) {
						User u = new User();
						loadUser(u, resultSet, 1);
						Users.add(u);
					}

					// check if the title was found
					if (Users.size() == 0) {
						System.out.println("<" + name + "> was not found: users list is empty");
					}

					stmt2 = conn.prepareStatement(
							" delete from user_positions " +
									"where user_id = ? "
							);
					stmt2.setInt(1, Users.get(0).getUserID());
					stmt2.executeUpdate();
					
					System.out.println("Deleting the user from the junction table");
					
					stmt3 = conn.prepareStatement(
							" delete from users " +
									" where users.userName = ? " +
									" and users.passWord = ? "
							);
					
					stmt3.setString(1, name);
					stmt3.setString(2, pswd);
					stmt3.executeUpdate();
					
					
					
					return Users;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}

	@Override
	public List<User> changePassword(final String name, final String pswd, final String newPassword) {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				System.out.println(name);
				System.out.println(pswd);
				System.out.println(newPassword);
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				ResultSet resultSet2 = null;

				try {

					System.out.println("about to change PW");
					stmt = conn.prepareStatement(
							" update users " +
									" set user_passWord = ? " +
									" where user_userName = ? "+ 
									" and user_passWord = ? "
							);

					stmt.setString(1, newPassword);
					stmt.setString(2, name);
					stmt.setString(3, pswd);
					stmt.executeUpdate();
					System.out.printf("Querry Completed: Update user's password");

					// return all users and see that the one entered was deleted

					stmt2 = conn.prepareStatement(
							" select * from users " 	+
									" where user_userName = ? " +
									" and user_password = ? "
							);
					//ensure new userName is in database
					stmt2.setString(1, name);
					stmt2.setString(2, newPassword);

					resultSet2 = stmt2.executeQuery();


					//if anything is found, return it in a list format
					List<User> result = new ArrayList<User>();
					Boolean found = false;

					while (resultSet2.next()) {
						found = true;

						User u = new User();
						loadUser(u, resultSet2, 1);
						result.add(u);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not in users list");
					}

					return result;


				} finally {

					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}


	//pull out the SOP requested 
	@Override
	public List<SOP> FindSOPByID(final int sopID) {
		return executeTransaction(new Transaction<List<SOP>>() {
			@Override
			public List<SOP> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							" select * from SOPs, sop_positions " +
									" where sops.sop_id = sop_positions.sop_id " +
									" and sop_id = ? "	
							);
					stmt.setInt(1, sopID);
					resultSet = stmt.executeQuery();

					//if anything is found, return it in a list format

					Boolean found = false;
					List<SOP> result = new ArrayList<SOP>();

					while(resultSet.next()) {
						found = true;
						SOP s = new SOP();
						loadSOP(s, resultSet, 1);
						result.add(s);
					}

					//check if the SOP was found
					if(!found) {
						System.out.println("<" + sopID + "was not found in the database" );
					}

					return result;


				}

				finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(resultSet);
				}

			}
		});
	}

	//Add an SOP to the DB
	@Override
	public List<SOP> addSOP(final int sopID, final String sopName, final String sopPurpose, final String priority, final String revision) {
		return executeTransaction(new Transaction<List<SOP>>() {
			@Override 
			public List<SOP> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				
				ResultSet resultSet = null;
				ResultSet resultSet2 = null;
				try {

					stmt = conn.prepareStatement(
							" insert into SOPs(sop_id, sop_Name, sop_purpose, sop_priority, sop_revision) " +
									" values (?, ?, ?, ?, ?, ?) "

							);
					stmt.setInt(1, sopID);
					stmt.setString(2, sopName);
					stmt.setString(3, sopPurpose);
					stmt.setString(4, priority);
					stmt.setString(5, revision);

					stmt.executeUpdate();

					stmt2 = conn.prepareStatement(
							" select * from sops, sop_positions " +
									" where sops.sop_id = sop_positions.sop_id " +
									" and sop_id = ? " + 
									" and sopName = ? "
							);

					stmt2.setInt(1, sopID);
					stmt2.setString(2, sopName);
					
					
					resultSet = stmt2.executeQuery();
					
					stmt3 = conn.prepareStatement(
							" select positions.positoinIdS " +
									" from positions, position_sops " +
									" where positions.positionIdS = position_sops.positionId " +
									" and positions.sop_id = sops.sop_id " +
									" and sop_id = ? "
							);
					
					stmt3.setInt(1, sopID);
					resultSet2 = stmt3.executeQuery();
					
					stmt4 = conn.prepareStatement(
							" insert into position_sops (positionId, sop_id) " +
									" values(?, ?) "
							);
					
					stmt4.setInt(1, resultSet2.getInt(1));
					stmt4.setInt(2, sopID);
					
					stmt4.executeUpdate();
					
					//if anything is found, return it in a list format
					Boolean found = false;
					List<SOP> result = new ArrayList<SOP>();

					while(resultSet.next()) {
						found = true;
						SOP s = new SOP();
						loadSOP(s, resultSet, 1);
						result.add(s);
					}

					//check if the SOP was found
					if(!found) {
						System.out.println("<" + sopName + "was not found in the database" );
					}

					return result;
				}
				finally {
					DBUtil.closeQuietly(conn);
					DButil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
				}
			}
		});
	}

	//change the priority of an SOP in the DB
	@Override
	public List<SOP> changePriority(final int sopID, final String priority, final String newPriority){
		return executeTransaction(new Transaction<List<SOP>>() {
			@Override 
			public List<SOP> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				ResultSet resultSet = null;

				
				try {
					stmt = conn.prepareStatement(
							" update sops, position_sops " +
									" set priority = ? "+
									" where sops.sop_id = position_sops.sop_id " +
									" and sop_id = ? " +
									" and priority = ? "
							);

					stmt.setString(1, newPriority);
					stmt.setInt(2, sopID);
					stmt.setString(3, priority);

					stmt.executeUpdate();

					stmt2 = conn.prepareStatement(
							" select sops.priority " +
									" from sops, position_sops " +
									" where sops.sop_id = position_sops.sop_id " +
									" and sops.sop_id = ? "
							);
					stmt2.setInt(1, sopID);

					resultSet = stmt2.executeQuery();

					List<SOP> result = new ArrayList<SOP>();

					//if anything is found, return it in a list format
					Boolean found = false;
					while(resultSet.next()) {
						found = true;

						SOP s = new SOP();
						loadSOP(s, resultSet, 1);
						result.add(s);
					}

					if (!found) {
						System.out.println("<" + sopID + "> was not in the SOP list");
					}


					return result;
				}

				finally {
					DBUtil.closeQuietly(conn);
					DButil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(resultSet);
				}

			}

		});
	}

	//change the version number and 'edit' the SOP in the DB
	@Override 
	public List<SOP> reviseSOP(final int sopID, final String version, final String newVersion) {
		return executeTransaction(new Transaction<List<SOP>>() {
			@Override 
			public List<SOP> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				ResultSet resultSet = null;

				try {

					//update the SOPS version number in the database
					stmt = conn.prepareStatement(
							" update sops " +
									" set revision = ? " +
									" where sops.sop_id = position_sops.sop_id " +
									" and sop_id = ? " +
									" and revision = ? "
							);

					stmt.setString(1, newVersion);
					stmt.setInt(2, sopID);
					stmt.setString(3, version);
					stmt.executeUpdate();

					//pull out the edited SOP
					stmt2 = conn.prepareStatement(
							" select sops.* " +
									" from sops " +
									" where sops.sop_id = position_sops.sop_id " +
									" and sops.sop_id = ?"
							);

					stmt2.setInt(1, sopID);

					resultSet = stmt2.executeQuery();

					//if anything is found, return it in a list format
					List<SOP> result = new ArrayList<SOP>();

					Boolean found = false;
					while(resultSet.next()) {
						found = true;

						SOP s = new SOP();
						loadSOP(s, resultSet, 1);
						result.add(s);
					}

					if (!found) {
						System.out.println("<" + sopID + "> was not in the SOP list");
					}


					return result;
				}

				finally {
					DBUtil.closeQuietly(conn);
					DButil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(resultSet);
				}


			}

		});
	}
	//search for an sop based on its name 
	@Override
	public List<SOP> findSOPByName(String sopName) {
		return executeTransaction(new Transaction<List<SOP>>() {

			@Override
			public List<SOP> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				
				try {

					stmt = conn.prepareStatement(
							" select sops.* " +
									" from sops, position_sops " +
									" where sops.sop_id = position_sops.sop_id " +
									" and sops.sopName = ? "

							);
					stmt.setString(1, sopName);

					resultSet = stmt.executeQuery();

					List<SOP> result = new ArrayList<SOP>();

					boolean found = true;
					while(resultSet.next()) {
						found = true;
						SOP sop = new SOP();

						loadSOP(sop, resultSet, 1);

						result.add(sop);
					}

					if(!found) {
						System.out.println("No sops found with name: " + sopName);
					}

					return result;

				}finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}


			}

		});
	}

	// get a positions information 
	@Override
	public List<Position> getPositionInfo(final String position) {

		return executeTransaction(new Transaction<List<Position>>() {
			@Override
			public List<Position> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							" select * from Positions, pososition_sops, user_positions " +
									" where positions.positionIdS = position_sops.positionId" +
									" and positions.positionIdU = user_positions.positionId "+
									" and positionName = ? "
							);
					stmt.setString(1, position);
					resultSet = stmt.executeQuery();

					//if anything is found, return it in a list format
					List<Position> result = new ArrayList<Position>();
					Boolean found = false;
					while (resultSet.next()) {
						found = true;

						Position p = new Position();
						loadPosition(p, resultSet, 1);
						result.add(p);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + position + "> was not found in the Positions table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//Add a new position to the system 
	@Override
	public List<Position> addPositionToDatabase(final String name, final String duty) {
		return executeTransaction(new Transaction<List<Position>>() {
			@Override
			public List<Position> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				ResultSet resultSet = null;
				ResultSet resultSet1 = null;
				
				//TODO
				try {
					stmt = conn.prepareStatement(
							" insert into positions(positionName, positionDuty) " +
									" values(?, ?) "
							);
					stmt.setString(1, name);
					stmt.setString(2, duty);
					stmt.executeUpdate();
//
				/*
					//WE need to make it so that it goes back and gets the primary key and puts it back in
					//get positionIdS and set positionIdU = positionIdS
					stmt1 = conn.prepareStatement(
							"Select positionIdS from positions"+
					"where positionName = ? and "
											);
					stmt1.setString(1, name);
					resultSet1 = stmt1.executeQuery();
					*/
					stmt2 = conn.prepareStatement(
							//until we get the forgein key issue cleared up this is out due to not working
							//I want to get the servlets working for the milestone
							" select positions.positionIdS, position_sops.sop_id " +
							" from positions, sops, position_sops, user_positions " +
							" where positions.positionIdS = position_sops.positionId " +
							" and positions.positionIdU = user_positions.positionId " +
							" and positionName = ?"

							);
					stmt2.setString(1, name);
					
					resultSet = stmt2.executeQuery();
					// took this out for now, at least till the errors with cross referencing are cleared
					stmt3 = conn.prepareStatement(
							" insert into position_sops(positionId, sop_id) " +
									" values (?, ?) "
							);
					
					//stmt3.setInt(1, );
					//stmt3.setInt(2, );
					
					stmt3.setInt(1, resultSet.getInt(1));
					stmt3.setInt(2, resultSet.getInt(2));
					
					//if anything is found, return it in a list format
					Boolean found = false;
					List<Position> result = new ArrayList<Position>();
					while (resultSet.next()) {
						found = true;
						Position p = new Position();
						loadPosition(p, resultSet, 1);
						result.add(p);
					}

					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the positions table");
					}

					return result;


				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	@Override
	public List<Position> getPositionByID(int positionId) {
		return executeTransaction(new Transaction<List<Position>>() {

			@Override
			public List<Position> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							" select positions.* " +
									" from positions, position_sops, user_positions " +
									" where positions.positionIdS = position_sops.positionId " +
									" and positions.positionIdU = user_positions.positionId " +
									" and positions.postionIdU = ? "
							);
					stmt.setInt(1, positionId);

					List<Position> result = new ArrayList<Position>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()) {
						Position position = new Position();

						loadPosition(position, resultSet, 1);
						result.add(position);

					}
					return result;
				}


				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}
	
	@Override
	public List<Position> findPositionByName(String positionName) { 
		return executeTransaction(new Transaction<List<Position>>() {

			@Override
			public List<Position> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							" select positions * " +
									" from positions " +
									" where positions.positionIdS = position_sops.positionId " +
									" and positions.positionIdU = user_positions.positionId " +
									" and positions.positionName = ? "

							);

					stmt.setString(1, positionName);

					List<Position> result = new ArrayList<Position>();

					resultSet = stmt.executeQuery();
					boolean found = false;

					while(resultSet.next()) {
						found = true;

						Position p = new Position();
						loadPosition(p, resultSet, 1);
						result.add(p);
					}

					if (!found) {
						System.out.println("No positions were found with: " + positionName);
					}

					return result;

				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}
	
	//remove position method/archive position method
	//add sop to a position
	//add a user to a position 
	
	//those two should be the last 3 methods 
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}

	//these build the collections to return to the servlets, controlles
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setUserID(resultSet.getInt(index++));
		user.setUsername(resultSet.getString(index++));
		user.setPassword(resultSet.getString(index++));
		user.setEmailAddress(resultSet.getString(index++));
		user.setAdmin(resultSet.getString(index++));
		user.setFirstName(resultSet.getString(index++));
		user.setLastName(resultSet.getString(index++));
	}


	private void loadSOP(SOP sop, ResultSet resultSet, int index) throws SQLException {
		sop.setSopIdNumber(resultSet.getInt(index++));
		sop.setSopName(resultSet.getString(index++));
		sop.setSopPurpose(resultSet.getString(index++));
		sop.setPriority(resultSet.getString(index++));
		sop.setRevision(resultSet.getString(index++));

	}
	//load position
	private void loadPosition(Position position, ResultSet resultSet, int index) throws SQLException {
		position.setPositionIDS(resultSet.getInt(index++));
		position.setPositionIDU(resultSet.getInt(index++));
		position.setPositionName(resultSet.getString(index++));
		position.setPositionDuty(resultSet.getString(index++));	
	}

	//load position to sop junction
	public void loadPositionSOPs(PositionSOP ps, ResultSet resultSet, int index) throws SQLException {
		ps.setPositionID(resultSet.getInt(index++));
		ps.setSopID(resultSet.getInt(index++));
	}

	//load user to position junction
	public void loadUserPositions(UserPosition up, ResultSet resultSet, int index) throws SQLException{
		up.setUserID(resultSet.getInt(index++));
		up.setPositionID(resultSet.getInt(index++));
	}

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				try {

					System.out.println("prepare statement for users");
					//create the user table 
					stmt1 = conn.prepareStatement(
							" create table users ( " +
									"	user_id integer primary key " +
									"	generated always as identity (start with 1, increment by 1), " +									
									"	user_userName varchar(40),"     +
									"	user_passWord varchar(40), "     +
									"   user_email varchar(40), "        +
									"   user_accountType varchar(30), " +
									"   user_firstName varchar(50), "  +
									"   user_lastName varchar(50) "    +
									") "
							);	
					System.out.println("execute users");
					stmt1.executeUpdate();
					//Changed from success because that is the marker for the successful build of whole DB
					System.out.println("user table created");

					//this is where the program is breaking. 
					//create the sop table
					System.out.println("prepare SOP");
					stmt2 = conn.prepareStatement(
							" create table sops ( " +
									" sop_id integer primary key " +
									" generated always as identity (start with 100, increment by 2), " +
									" sop_name varchar(40), " +
									" sop_purpose varchar(100)," +
									" sop_priority varchar(2)," +
									" sop_revision varchar(5)" +
									") "
							);
					System.out.println("execute SOP");
					stmt2.executeUpdate();
					System.out.println("sop table created");

					System.out.println("prepare Position");
					//create table for the position class
					stmt3 = conn.prepareStatement(
							" create table positions ( "+
									"	 positionIdS integer primary key "+
									"	 generated always as identity (start with 1, increment by 1), "+
									"	 positionName varchar(40), "+
									"	 positionDuty varchar(250), "+
									"	 positionIdU integer "+
									//"	 generated always (start with 1, increment by 1) "+
									") "
							);
					System.out.println(" execute position ");
					stmt3.executeUpdate();
					System.out.println("position table created");


					//create the position and sop junction table 
					System.out.println("prepare position to sop table");
					stmt4 = conn.prepareStatement(
							" create table position_sops( " +
									"  positionId integer constraint positionIdS references positions, " + 
									"  sop_id integer constraint sop_id references sops " +
									") "
							);

					stmt4.executeUpdate();
					System.out.println("position to sop table created");

					
					//create the user and position junction table 
					System.out.println("prepare user to position table");
					stmt5 = conn.prepareStatement(
							" create table user_positions( " +
									"  user_id    integer constraint user_id references users, " + 
									"  positionId integer constraint positionIdU references positions " +
									") "
							);
					/*	rewrote because something wasn't working and I couldn't see it, hoping it was a weird typo
					stmt5 = conn.prepareStatement(
							" create table user_positions (" +
									"  user_id integer constraint user_id references users, " +
									"  positionId integer constraint positionId references positions " +
									") "
							);
							*/
					System.out.println("about to execute user to position table");
					stmt5.executeUpdate();
					System.out.println("user to position table created");

					return true;

				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);

				}
			}	

		});
	}

	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {

				List<User> userList;
				List<SOP> sopList;
				List<Position> positionList;
				List<PositionSOP> positionSOPList;
				List<UserPosition> UserPositionList;

				try {
					System.out.println("init userlist");
					userList = InitialData.getUsers();
					System.out.println("init SOPlist");
					sopList = InitialData.getSOPs();
					System.out.println("init PositionList");
					positionList = InitialData.getPositions();

					//will uncomment these when the methods are working 
					System.out.println("init Position To SOP List");
					positionSOPList = InitialData.getPositionsSOPsList();
					System.out.println("init User to Position List");
					UserPositionList = InitialData.getUserPositionList();

				}
				catch (IOException e){
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertUsers = null;
				PreparedStatement insertSOPs = null;
				PreparedStatement insertPositions = null;
				PreparedStatement insertPositionSOP = null;
				PreparedStatement insertUserPosition = null;

				try{
					//add the user csv file into the DB
					System.out.println("preparing users insert");
					insertUsers = conn.prepareStatement("insert into users (user_userName, user_passWord, user_email, user_accountType, user_firstName, user_lastname) "
							+ "		values (?, ?, ?, ?, ?, ?)");
					for (User u : userList) {
						insertUsers.setString(1, u.getUsername());
						insertUsers.setString(2, u.getPassword());
						insertUsers.setString(3, u.getEmailAddress());
						insertUsers.setString(4, u.isAdmin());
						insertUsers.setString(5, u.getFirstName());
						insertUsers.setString(6, u.getLastName());
						insertUsers.addBatch();
					}

					System.out.println("inserting users");
					insertUsers.executeBatch();
					System.out.println("Users table populated");

					System.out.println("preparing sop insert");
					insertSOPs = conn.prepareStatement("insert into sops (sop_name, sop_purpose, sop_priority, sop_revision ) "
							+ "		values (?, ?, ?, ?) " );

					for(SOP s : sopList) {
						insertSOPs.setString(1, s.getSopName());
						insertSOPs.setString(2, s.getSopPurpose());
						insertSOPs.setString(3, s.getPriority());
						insertSOPs.setString(4, s.getRevision());
						insertSOPs.addBatch();
					}
					System.out.println("inserting sops");
					insertSOPs.executeBatch();
					System.out.println("Sops table populated");

					System.out.println("prepareing to insert postions");
					//insert the position csv file into the DB
					insertPositions = conn.prepareStatement("insert into positions (positionName, positionDuty, positionIdU ) "
							+ "		values (?, ?, ?) " );

					for(Position p : positionList){
						insertPositions.setString(1, p.getPositionName());
						insertPositions.setString(2, p.getPositionDuty());
						insertPositions.setLong(3, p.getPositionIDU());
						insertPositions.addBatch();
					}
					System.out.println("inserting positions");
					System.out.println("inserting positions...");
					insertPositions.executeBatch();
					System.out.println("Positions table populated");

					//insert the position to sop file into the DB
					System.out.println("Preparing Position SOP junction");
					insertPositionSOP = conn.prepareStatement(" insert into position_sops (positionId, sop_id ) "
							+ " 	values (?, ?) " );

					for(PositionSOP posSop: positionSOPList) {
						insertPositionSOP.setInt(1, posSop.getPositionID());
						insertPositionSOP.setInt(2, posSop.getSopID());
						//this was causing errors I figured I'd get the servlets we have up and running first
						//insertPositionSOP.addBatch();
					}


					System.out.println("inserting positions to sops...");
					insertPositionSOP.executeBatch();
					System.out.println("Position SOPs junction table populated");

					//insert the user to position file into the DB
					System.out.println("Preparing USer to position junction");
					insertUserPosition = conn.prepareStatement(" insert into user_positions (user_id, positionId) "
							+ "		values (?, ?) " );

					for(UserPosition userPosition: UserPositionList) {
						insertUserPosition.setInt(1, userPosition.getUserID());
						insertUserPosition.setInt(2, userPosition.getPositionID());
						//this was causing errors I figured I'd get the servlets we have up and running first
						//insertUserPosition.addBatch();
					}

					System.out.println("inserting users to positions...");
					insertUserPosition.executeBatch();
					System.out.println("User Positions junction table populated");

					return true;

				}


				finally {
					DBUtil.closeQuietly(insertUsers);
					DBUtil.closeQuietly(insertSOPs);
					DBUtil.closeQuietly(insertPositions);
					DBUtil.closeQuietly(insertPositionSOP);
					DBUtil.closeQuietly(insertUserPosition);
				}
			}
		});
	}


	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();

		System.out.println("Users");
		System.out.println("SOPs");
		System.out.println("Positions");

		System.out.println("Loading initial data...");
		db.loadInitialData();

		System.out.println("Users");
		System.out.println("SOPs");
		System.out.println("Positions");


		System.out.println("Sucess!");
	}
}
