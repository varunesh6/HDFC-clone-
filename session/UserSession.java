package session;

import DAO.UserDetails;

public class UserSession {

    private static UserDetails currentUser;

    private UserSession() {}

    public static void login(UserDetails user) {
        currentUser = user;
    }

    public static UserDetails getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}
