package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import common.DBConnPool;
import dto.memberDTO;

public class MemberDAO extends DBConnPool{
	

	public void insertMember(memberDTO mdto) {
		
		String sql = "insert into member (id, password, private_num, kr_name, en_name, id_picture, phone_num, email, address, admin_key, age, college_year,gender)"
				+  "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1,mdto.getId());
			psmt.setString(2, mdto.getPassword());
			psmt.setString(3, mdto.getPrivate_num());
			psmt.setString(4, mdto.getKr_name());
			psmt.setString(5, mdto.getEn_name());
			psmt.setString(6, mdto.getId_picture());
			psmt.setString(7, mdto.getPhone_num());
			psmt.setString(8, mdto.getEmail());
			psmt.setString(9, mdto.getAddress());
			psmt.setString(10, mdto.getAdmin_key());
			psmt.setInt(11, mdto.getAge());
			psmt.setInt(12, mdto.getCollege_year());
			psmt.setString(13, mdto.getGender());
			System.out.println("member 등록 완료");
			psmt.executeUpdate();
			
			con.close();
			
		}catch(Exception e) {
			System.out.println("member 등록 중 오류 발생");
			e.printStackTrace();
		}
	}
	
	public ArrayList<memberDTO> getAllMember(){
	
		ArrayList<memberDTO> arr = new ArrayList<>();
		
		try {
			String sql = "select * from member";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				memberDTO mdto = new memberDTO();
				mdto.setId(rs.getString(1));
				mdto.setPassword(rs.getString(2));
				mdto.setPrivate_num(rs.getString(3));
				mdto.setKr_name(rs.getString(4));
				mdto.setEn_name(rs.getString(5));
				mdto.setId_picture(rs.getString(6));
				mdto.setPhone_num(rs.getString(7));
				mdto.setEmail(rs.getString(8));
				mdto.setAddress(rs.getString(9));
				mdto.setAdmin_key(rs.getString(10));
				mdto.setAge(rs.getInt(11));
				mdto.setCollege_year(rs.getInt(12));
				mdto.setGender(rs.getString(13));
				
				arr.add(mdto);
			}
			
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	public memberDTO getMember(String id, String pass) {
		
		memberDTO mdto = new memberDTO();
		
		try {
			String sql = "select * from member where id=? and password=?";
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, id);
			psmt.setString(2, pass);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				mdto.setId(rs.getString(1));
				mdto.setPassword(rs.getString(2));
				mdto.setPrivate_num(rs.getString(3));
				mdto.setKr_name(rs.getString(4));
				mdto.setEn_name(rs.getString(5));
				mdto.setId_picture(rs.getString(6));
				mdto.setPhone_num(rs.getString(7));
				mdto.setEmail(rs.getString(8));
				mdto.setAddress(rs.getString(9));
				mdto.setAdmin_key(rs.getString(10));
				mdto.setAge(rs.getInt(11));
				mdto.setCollege_year(rs.getInt(12));
				mdto.setGender(rs.getString(13));

			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mdto;
	}
	
	
	
}