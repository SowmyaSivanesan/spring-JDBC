package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response rsp = new Response();

	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String username = "root";
	String password = "Sowmiya0209";

	public Response createUser(SignUpModel values) {

		String uuid = UUID.randomUUID().toString();
		values.setsNo(uuid);
		values.setCreatedBy(uuid);
		values.setUpdatedBy(uuid);
		values.setIsActive(1);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		values.setCreatedDate(date);
		values.setUpdatedDate(date);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				String emailValidation = "^(.+)@gmail.com$";
				Pattern emailpattern = Pattern.compile(emailValidation);
				Matcher emailMatch = emailpattern.matcher(values.getEmail());

				if (emailMatch.matches() == true) {

					String insertQuery = "INSERT INTO kgm.userdetails(s_no,first_name,last_name,email,dob,gender,phone_number,password,created_by,updated_by,created_date,updated_date,is_active)"
							+ "VALUES('" + values.getsNo() + "','" + values.getFirtsName() + "','"
							+ values.getLastName() + "'," + "'" + values.getEmail() + "','" + values.getDob() + "','"
							+ values.getGender() + "'," + "" + values.getPhoneNumber() + ",'" + values.getPassword()
							+ "'," + "'" + values.getCreatedBy() + "','" + values.getUpdatedBy() + "','"
							+ values.getCreatedDate() + "','" + values.getUpdatedDate() + "','" + values.getIsActive()
							+ "');";

					st.executeUpdate(insertQuery);

					rsp.setData("User Created Successfully!");
					rsp.setRespondCode(200);
					rsp.setRespondMsg("success");

				} else {

					rsp.setData("Doesn't create user- Invalid email");
					rsp.setRespondCode(500);
					rsp.setRespondMsg("failed");
				}

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Create User!");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

	@SuppressWarnings("unchecked")
	public Response getUser() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from userdetails;";

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();
					ResultSet rst = st.executeQuery(selectQuery);) {

				JSONArray jsonArray = new JSONArray();

				while (rst.next()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("sNo", rst.getString("s_no"));
					jsonObject.put("firstName", rst.getString("first_name"));
					jsonObject.put("lastname", rst.getString("last_name"));
					jsonObject.put("email", rst.getString("email"));
					jsonObject.put("dob", rst.getDate("dob"));
					jsonObject.put("gender", rst.getObject("gender"));
					jsonObject.put("phoneNumber", rst.getLong("phone_number"));
					jsonObject.put("password", rst.getString("password"));
					jsonObject.put("createdBy", rst.getString("created_by"));
					jsonObject.put("updatedBy", rst.getString("updated_by"));
					jsonObject.put("createdDate", rst.getDate("created_date"));
					jsonObject.put("updatedDate", rst.getDate("updated_date"));

					jsonArray.add(jsonObject);

				}

				rsp.setData("User Fetch Successfully!");
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");
				rsp.setjData(jsonArray);

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");

			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");

		}

		return rsp;
	}

	public Response updateUser(String email, String s_no) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				String updateQuery = "Update userdetails set email ='" + email + "' where s_no='" + s_no + "'";

				st.executeUpdate(updateQuery);

				rsp.setData("User Updated Successfully!");
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Update User!");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

	@SuppressWarnings("unchecked")
	public Response getOneAPI(String s_no) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from userdetails where s_no ='" + s_no + "'";

			JSONObject jasoJsonObject = new JSONObject();
			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement stm = conn.createStatement();
					ResultSet rst = stm.executeQuery(selectQuery)) {

				while (rst.next()) {

					jasoJsonObject.put("sNo", rst.getString("s_no"));
					jasoJsonObject.put("firstName", rst.getString("first_name"));
					jasoJsonObject.put("lastName", rst.getString("last_name"));
					jasoJsonObject.put("email", rst.getString("email"));
					jasoJsonObject.put("dob", rst.getDate("dob"));
					jasoJsonObject.put("gender", rst.getString("gender"));
					jasoJsonObject.put("phoneNumber", rst.getLong("phone_number"));
					jasoJsonObject.put("password", rst.getString("password"));
					jasoJsonObject.put("createdBy", rst.getString("created_by"));
					jasoJsonObject.put("updatedBy", rst.getString("updated_by"));
					jasoJsonObject.put("createdDate", rst.getString("updated_date"));
					jasoJsonObject.put("updatedDate", rst.getString("updated_date"));

				}
				rsp.setData("User updated(one) Successfully!");
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");
				rsp.setjData(jasoJsonObject);

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot GetOne User!");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

	public Response deleteUser(String s_no) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				String deleteQuery = "delete from  userdetails where s_no ='" + s_no + "'";

				st.executeUpdate(deleteQuery);

				rsp.setData("User Deleted Successfully!");
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot delete User!");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

	public Response scam(String s_no) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, password);
					Statement st = conn.createStatement();) {

				String scamQuery = "Update userdetails set is_active=0 where s_no='" + s_no + "'";
				st.executeUpdate(scamQuery);

				rsp.setData("User has been soft deleted");
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");

			} catch (Exception e) {
				e.printStackTrace();
				rsp.setData("User is not soft deleted");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");

			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

	public Response login(String email, String pswrd) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from userdetails where email ='" + email + "' and password ='" + pswrd
					+ "'";

			try (Connection conn = DriverManager.getConnection(url, username, password);
					PreparedStatement preparest = conn.prepareStatement(selectQuery);
					ResultSet rs = preparest.executeQuery();) {
				
				
				
				
				String result;
				
				if(rs.next()) {
					result = "Existing User";
				}else {
					result = "No such user found";
				}

				rsp.setData(result);
				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");

			} catch (Exception e) {
				e.printStackTrace();
				
				rsp.setData("User failed to login");
				rsp.setRespondCode(500);
				rsp.setRespondMsg("error");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			
			rsp.setData("Driver Class Error!");
			rsp.setRespondCode(500);
			rsp.setRespondMsg("error");
		}

		return rsp;
	}

}
