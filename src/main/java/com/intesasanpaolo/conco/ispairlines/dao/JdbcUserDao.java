package com.intesasanpaolo.conco.ispairlines.dao;

import com.intesasanpaolo.conco.ispairlines.model.User;
import com.intesasanpaolo.conco.ispairlines.util.AeSimpleSHA1;
//import com.intesasanpaolo.conco.ispairlines.util.DbUtil;
import com.intesasanpaolo.conco.ispairlines.util.SimpleLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Random;

public class JdbcUserDao implements UserDAO {
	
	private Connection connection;
	private final DataSource dataSource;

	public JdbcUserDao() {
		//connection = DbUtil.getConnection();
		dataSource = lookupDataSource();
		initializeSchemaIfNeeded();
	}
	
	private void initializeSchemaIfNeeded() {
		try {
			Connection connection = getConnection();
			try {
				if (!isSchemaInitialized(connection)) {
					connection.setAutoCommit(true);
					Statement statement = connection.createStatement();
					try {
						// statement.executeUpdate("CREATE TABLE todo_entries (id bigint, summary VARCHAR(255), description TEXT)");
						statement.executeUpdate("CREATE TABLE `airlineusers` (`userid` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,`username` VARCHAR(10) NOT NULL COLLATE 'latin1_bin',`password` VARCHAR(45) NOT NULL COLLATE 'latin1_bin',`firstname` VARCHAR(45) NOT NULL COLLATE 'latin1_bin',`isadmin` TINYINT(4) NOT NULL,`lastname` VARCHAR(45) NOT NULL COLLATE 'latin1_bin',`dob` DATE NULL DEFAULT NULL,`email` VARCHAR(100) NOT NULL COLLATE 'latin1_bin',PRIMARY KEY (`userid`)");

					} finally {
						statement.close();
					}
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("could not initialize database schema", e);
		}
	}

	private DataSource lookupDataSource() {
		try {
			Context initialContext = new InitialContext();
			try {
				return (DataSource) initialContext.lookup(System.getenv("DB_JNDI"));
			} catch (NameNotFoundException e) {
				//Context envContext = (Context) initialContext.lookup("java:comp/env");  // Tomcat places datasources inside java:comp/env
				Context envContext = (Context) initialContext.lookup("java:jboss/env"); 

				return (DataSource) envContext.lookup(System.getenv("DB_JNDI"));
			}
		} catch (NamingException e) {
			throw new DataAccessException("Could not look up datasource", e);
		}
	}

	private boolean isSchemaInitialized(Connection connection) throws SQLException {
		ResultSet rset = connection.getMetaData().getTables(null, null, "airlineusers", null);
		try {
			return rset.next();
		} finally {
			rset.close();
		}
	}



	private DataSource getDataSource() {
		return dataSource;
	} 


    @Override
    public void save(User user) {
    	String hash="";
    	try {
            Connection connection = getConnection();
            try {
                connection.setAutoCommit(true);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO airlineusers (`username`, `password`, `firstname`, `isadmin`, `lastname`, `dob`, `email`) VALUES (?, ?, ?, ?, ?, ?, ?)");
                try {
                    //statement.setLong(1, getNextId());
        			statement.setString(1, user.getUserName());
        			try{
        				hash=AeSimpleSHA1.SHA1(user.getPassword());
        			}
        			catch(Exception e){
        				hash=user.getPassword();
        			}
        			statement.setString(2, hash);
        			//statement.setString(2, user.getPassword());
        			statement.setString(3, user.getFirstName());
        			statement.setString(4, user.getLastName());
        			statement.setDate(5, new java.sql.Date(user.getDob().getTime()));
        			statement.setString(6, user.getEmail());
        			statement.setBoolean(7, user.getIsAdmin());
        			statement.executeUpdate();
        			statement.close();
                    
                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user " + user, e);
        }
    }

	

    @Override
    public List<User> list() {
        try {
            Connection connection = getConnection();
            try {
                Statement statement = connection.createStatement();
                try {
                    ResultSet rset = statement.executeQuery("select *  from ispairlinedb.airlineusers");
                    try {
                        List<User> list = new ArrayList<User>();
                        while (rset.next()) {
                        	
            				User user = new User();
                            //Long id = rset.getLong(1);  se aggiungo id
            				user.setUserid(rset.getInt("userid"));
            				user.setUserName(rset.getString("username"));
            				user.setPassword(rset.getString("password"));
            				user.setFirstName(rset.getString("firstname"));
            				user.setLastName(rset.getString("lastname"));
            				user.setDob(rset.getDate("dob"));
            				user.setEmail(rset.getString("email"));
            				user.setIsAdmin(rset.getBoolean("isadmin"));                            
                            list.add(user);
                        }
                        return list;
                    } finally {
                        rset.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not list entries", e);
        }
    }


    private long getNextId() {
        return new Random().nextLong();
    }

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
	
	
	
    //OLD METHOD
	
	public void addUser(User user) throws SQLException {
		try {
			String hash="";
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers](username,password,firstname,lastname,dob,email,isAdmin) values (?, ?, ?, ?, ?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserName());
			try{
				hash=AeSimpleSHA1.SHA1(user.getPassword());
			}
			catch(Exception e){
				hash=user.getPassword();
			}
			preparedStatement.setString(2, hash);
			//preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setDate(5, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.setBoolean(7, user.getIsAdmin());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Errore in fase di add: " + e.toString());
		}
	}

	public void deleteUser(int userId) throws SQLException{
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers] where userid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error in delete - " + this.toString() + " - " + e.toString());
		}
	}

	public void updateUser(User user) throws SQLException{
		try {
			String hash="";
			PreparedStatement preparedStatement = connection
					.prepareStatement("update [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers] set username=?, password=?, firstname=?, lastname=?, dob=?, email=?, isAdmin=?" +
							"where userid=?");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserName());
			try{
				hash=AeSimpleSHA1.SHA1(user.getPassword());
			}
			catch(Exception e){
				hash=user.getPassword();
			}
			preparedStatement.setString(2, hash);			
			//preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setDate(5, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.setBoolean(7, user.getIsAdmin());
			preparedStatement.setInt(8, user.getUserid());
			preparedStatement.executeUpdate();
			SimpleLog.toFileINFO("JbdcUserDao updateUser - query: " + preparedStatement.toString());
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error in update - " + this.toString() + " - " + e.toString());
		}
	}

	public void changePassword(User user) throws SQLException{
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers] set password=?" +
							"where userid=?");
			// Parameters start with 1
			String hash="";
			try{
				hash=AeSimpleSHA1.SHA1(user.getPassword());
			}
			catch(Exception e){
				hash=user.getPassword();
			}
			preparedStatement.setString(1, hash);	
			//preparedStatement.setString(1, user.getPassword());
			preparedStatement.setInt(2, user.getUserid());
			preparedStatement.executeUpdate();
			SimpleLog.toFileINFO("JbdcUserDao changePassword - query: " + preparedStatement.toString());
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error in changePassword - " + this.toString() + " - " + e.toString());
		}
	}

	public List<User> getAllUsers(){
		List<User> users = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers]");
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getBoolean("isadmin"));
				users.add(user);
			}
			statement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public User getUserById(int userId){
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers] where userid=?");
			preparedStatement.setInt(1, userId);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getBoolean("isadmin"));
			}
			preparedStatement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User getUserByUserName(String userName){
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from [Request_Center_Ext].[dbo].[CloudPortalUtilsUsers] where userName=?");
			preparedStatement.setString(1, userName);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getBoolean("isadmin"));
			}
			preparedStatement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public void CloseConnection(){
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
