package pl.damian.beautyglow.dao;


import pl.damian.beautyglow.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
