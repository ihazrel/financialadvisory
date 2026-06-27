package helper;

import model.UserModel;

public class RoleHelper {
	
	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_FINANCIAL_MANAGER = 2;
	public static final int ROLE_DEPARTMENT_MANAGER = 3;
	public static final int ROLE_STAFF = 4;
	
	public static String getRoleName(int roleId) {
		switch (roleId) {
			case ROLE_ADMIN:
				return "System Admin";
			case ROLE_FINANCIAL_MANAGER:
				return "Financial Manager";
			case ROLE_DEPARTMENT_MANAGER:
				return "Department Manager";
			case ROLE_STAFF:
				return "Staff";
			default:
				return "Unknown Role";
		}
	}
	
	public static boolean isAdmin(UserModel user) {
		return user != null && user.getRoleId() == ROLE_ADMIN;
	}

	public static boolean isFinancialManager(UserModel user) {
		return user != null && user.getRoleId() == ROLE_FINANCIAL_MANAGER;
	}
	
	public static boolean isDepartmentManager(UserModel user) {
		return user != null && (user.getRoleId() == ROLE_DEPARTMENT_MANAGER || user.getRoleId() == ROLE_FINANCIAL_MANAGER);
	}
	
	public static boolean isStaff(UserModel user) {
		return user != null && user.getRoleId() == ROLE_STAFF;
	}
}
