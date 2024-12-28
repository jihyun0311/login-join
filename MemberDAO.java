package com.myspring.test.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Connection getConnection() throws Exception{
		String jdbcURL = "jdbc:mysql://localhost:3306/이름?serverTimezone=UTC&useSSL=false";
		String dbId = "root";
		String dbPw = "rootroot";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcURL, dbId, dbPw);
		
		return conn;
	}
	
	public ArrayList<Member> getMemberList(){
		ArrayList<Member> memberList = new ArrayList<Member>();
		try {
			conn = getConnection();
			
			String sql = "select * from member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setMember_number(rs.getInt(1));
				member.setMember_id(rs.getString(2));
				member.setMember_pw(rs.getString(3));
				member.setMember_name(rs.getString(4));		
				member.setMember_email(rs.getString(5));
				memberList.add(member);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {try {conn.close();} catch (Exception e) {}}
			if(pstmt != null) {try {pstmt.close();} catch (Exception e) {}}
			if(rs != null) {try {rs.close();} catch (Exception e) {}}
		}
		return memberList;
	}
	
	
	
	
	
	
}
