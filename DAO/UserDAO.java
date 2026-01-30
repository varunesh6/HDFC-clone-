package DAO;

import java.sql.*;
public class UserDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/bank_app";
	private static final String USER = "root";
	private static final String PASSWORD = "2023Ucs1216*";


	public boolean transfer(long fromAccNo, long toAccNo, double amount) {

		String getUserSql = "SELECT userId, balance FROM users WHERE accountNumber=?";
		String debitSql   = "UPDATE users SET balance = balance - ? WHERE userId=?";
		String creditSql  = "UPDATE users SET balance = balance + ? WHERE userId=?";
		String txnSql     = "INSERT INTO transactions (userId, type, amount) VALUES (?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			conn.setAutoCommit(false);

			// ===== FROM ACCOUNT =====
			PreparedStatement psFrom = conn.prepareStatement(getUserSql);
			psFrom.setLong(1, fromAccNo);
			ResultSet rsFrom = psFrom.executeQuery();

			if (!rsFrom.next()) return false;

			int fromUserId = rsFrom.getInt("userId");
			double fromBalance = rsFrom.getDouble("balance");

			if (fromBalance < amount) return false;

			// ===== TO ACCOUNT =====
			PreparedStatement psTo = conn.prepareStatement(getUserSql);
			psTo.setLong(1, toAccNo);
			ResultSet rsTo = psTo.executeQuery();

			if (!rsTo.next()) return false;

			int toUserId = rsTo.getInt("userId");

			// ===== DEBIT =====
			PreparedStatement psDebit = conn.prepareStatement(debitSql);
			psDebit.setDouble(1, amount);
			psDebit.setInt(2, fromUserId);
			psDebit.executeUpdate();

			// ===== CREDIT =====
			PreparedStatement psCredit = conn.prepareStatement(creditSql);
			psCredit.setDouble(1, amount);
			psCredit.setInt(2, toUserId);
			psCredit.executeUpdate();

			// ===== TRANSACTIONS =====
			PreparedStatement psTxn1 = conn.prepareStatement(txnSql);
			psTxn1.setInt(1, fromUserId);
			psTxn1.setString(2, "TRANSFER_OUT");
			psTxn1.setDouble(3, amount);
			psTxn1.executeUpdate();

			PreparedStatement psTxn2 = conn.prepareStatement(txnSql);
			psTxn2.setInt(1, toUserId);
			psTxn2.setString(2, "TRANSFER_IN");
			psTxn2.setDouble(3, amount);
			psTxn2.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public void transactionHistory(long accountNumber) {

		String getUserSql = "SELECT userId FROM users WHERE accountNumber=?";
		String txnSql = "SELECT type, amount, created_at FROM transactions WHERE userId=? ORDER BY created_at DESC";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

			// 1. Get userId
			PreparedStatement ps1 = conn.prepareStatement(getUserSql);
			ps1.setLong(1, accountNumber);
			ResultSet rs1 = ps1.executeQuery();

			if (!rs1.next()) {
				System.out.println("Account not found");
				return;
			}

			int userId = rs1.getInt("userId");

			// 2. Get transactions
			PreparedStatement ps2 = conn.prepareStatement(txnSql);
			ps2.setInt(1, userId);
			ResultSet rs2 = ps2.executeQuery();

			System.out.println("\n----- Transaction History -----");
			System.out.printf("%-15s %-10s %-20s%n", "Type", "Amount", "Date");

			boolean hasData = false;
			while (rs2.next()) {
				hasData = true;
				System.out.printf(
				    "%-15s %-10.2f %-20s%n",
				    rs2.getString("type"),
				    rs2.getDouble("amount"),
				    rs2.getTimestamp("created_at")
				);
			}

			if (!hasData) {
				System.out.println("No transactions found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


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