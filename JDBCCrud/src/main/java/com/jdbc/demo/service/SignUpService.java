package com.jdbc.demo.service;

import org.springframework.stereotype.Service;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@Service
public interface SignUpService {

	public Response createUser(SignUpModel values);
	
	public Response getUser();
	
	public Response updateUser(String email,String s_no);

	public Response getOneAPI(String s_no);
	
	public Response deleteUser(String s_no);
	
	public Response scam(String s_no);
	
	public Response login(String email, String pswrd);
	
}
