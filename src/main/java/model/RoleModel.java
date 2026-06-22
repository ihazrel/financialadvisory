package model;

public class RoleModel {
	private Integer roleId;
	private String roleName;
	private String roleDescription;

	
	public RoleModel() {}

	public RoleModel(Integer roleId, String roleName, String roleDescription) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}

	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return roleDescription;
	}
	
	public void setDescription(String description) {
		this.roleDescription = description;
	}
}
