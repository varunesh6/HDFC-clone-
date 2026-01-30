package DAO;

import java.sql.*;
public class UserDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/bank_app";
	private static final String USER = "root";
	private static final String PASSWORD = "2023Ucs1216*";

	public double checkBalance(long accountNumber) {

		String getUserSql = "SELECT balance FROM users WHERE accountNumber=?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			        PreparedStatement ps = conn.prepareStatement(getUserSql)) {

			ps.setLong(1, accountNumber);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble("balance");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	public boolean withdraw(long accountNumber, double amount) {

		String getUserSql = "SELECT userId, balance FROM users WHERE accountNumber=?";
		String insertTxnSql = "INSERT INTO transactions (userId, type, amount) VALUES (?, 'WITHDRAW', ?)";
		String updateBalanceSql = "UPDATE users SET balance = balance - ? WHERE userId=?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			conn.setAutoCommit(false);

			PreparedStatement ps1 = conn.prepareStatement(getUserSql);
			ps1.setLong(1, accountNumber);
			ResultSet rs = ps1.executeQuery();

			if (!rs.next()) {
				return false;
			}

			int userId = rs.getInt("userId");
			double balance = rs.getDouble("balance");

			if (balance < amount) {
				return false;
			}


			PreparedStatement ps2 = conn.prepareStatement(insertTxnSql);
			ps2.setInt(1, userId);
			ps2.setDouble(2, amount);
			ps2.executeUpdate();

			PreparedStatement ps3 = conn.prepareStatement(updateBalanceSql);
			ps3.setDouble(1, amount);
			ps3.setInt(2, userId);
			ps3.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	public boolean deposit(long accountNumber, double amount) {

		String getUserSql = "SELECT userId FROM users WHERE accountNumber=?";
		String insertTxnSql = "INSERT INTO transactions (userId, type, amount) VALUES (?, 'DEPOSIT', ?)";
		String updateBalanceSql = "UPDATE users SET balance = balance + ? WHERE userId=?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			conn.setAutoCommit(false);

			PreparedStatement ps1 = conn.prepareStatement(getUserSql);
			ps1.setLong(1, accountNumber);
			ResultSet rs = ps1.executeQuery();

			if (!rs.next()) {
				return false;
			}

			int userId = rs.getInt("userId");

			PreparedStatement ps2 = conn.prepareStatement(insertTxnSql);
			ps2.setInt(1, userId);
			ps2.setDouble(2, amount);
			ps2.executeUpdate();

			PreparedStatement ps3 = conn.prepareStatement(updateBalanceSql);
			ps3.setDouble(1, amount);
			ps3.setInt(2, userId);
			ps3.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	// ================= REGISTER USER =================
	public boolean saveUser(UserDetails user) {

		String insertSql = "INSERT INTO users " +
		                   "(username, mobileNumber, adharNumber, panCard, userAddress, gender, password) " +
		                   "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			        PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, user.getUserName());
			ps.setLong(2, user.getMobileNumber());
			ps.setString(3, user.getAdharNumber());
			ps.setString(4, user.getPanCard());
			ps.setString(5, user.getUserAddress());
			ps.setString(6, user.getGender());
			ps.setString(7, user.getPassword());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int userId = rs.getInt(1);
				long accountNumber = 1000000000L + userId;

				user.setUserId(userId);
				user.setAccountNumber(accountNumber);

				String updateSql = "UPDATE users SET accountNumber=? WHERE userId=?";
				try (PreparedStatement ps2 = conn.prepareStatement(updateSql)) {
					ps2.setLong(1, accountNumber);
					ps2.setInt(2, userId);
					ps2.executeUpdate();
				}

				System.out.println(" Account Number: " + accountNumber);
			}
			return true;

		} catch (SQLException e) {
			System.out.println(" Error saving user: " + e.getMessage());
			return false;
		}
	}

	// ================= LOGIN / AUTH =================
	public UserDetails getUserByMobileAndPassword(long mobile, String password) {

		String sql = "SELECT * FROM users WHERE mobileNumber = ? AND password = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			        PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, mobile);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				UserDetails user = new UserDetails();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("username"));
				user.setMobileNumber(rs.getLong("mobileNumber"));
				user.setAccountNumber(rs.getLong("accountNumber"));
				user.setPassword(rs.getString("password"));
				return user;
			}

		} catch (SQLException e) {
			System.out.println(" Login error: " + e.getMessage());
		}
		return null;
	}

	// ================= DELETE USER =================
	public boolean deleteUser(int userId) {

		String sql = "DELETE FROM users WHERE userId=?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			        PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println(" Delete error: " + e.getMessage());
			return false;
		}
	}



}