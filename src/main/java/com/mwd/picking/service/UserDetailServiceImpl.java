package com.mwd.picking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mwd.picking.persitence.mybatis.UserRepository;
import com.mwd.picking.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
@Autowired
private UserRepository repository;

public UserDetails loadUserByUsername(String userId)
{
	User currentUser = (User) repository.loadUserByUsername(userId);
//	System.err.println("current user is " + currentUser);
//	System.err.println("current user getUserPasswd is " + currentUser.getPassword());
	UserDetails user = new org.springframework.security.core
	.userdetails.User(currentUser.getUserName(), currentUser.getPassword()
	, true, true, true, true,
	AuthorityUtils.createAuthorityList("USER"));
	//System.err.println("USER "+user);
	return user;
  }

public User loadUserInfo(String userId)
{
	System.err.println("loadUserInfo");
	User currentUser = (User) repository.loadUserByUsername(userId);
	System.err.println("loadUserInfo, "+currentUser.getStoreCode());
	return currentUser;
  }

}
