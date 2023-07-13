package hospital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hospital.utils.ConnectionHelper;
import hospital.vo.patientRecordVO;

public class HospitalDao {
	
	public List<patientRecordVO> searchOK(String search_str, boolean check, String find) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(search_str);
		try {
			conn = ConnectionHelper.getConnection();
			String sql = "";
			if(check && find.equals("검색")) {
				sql = "select p.address, p.note, p.num, p.name, p.birth, p.sex, p.phone, r.r_num, r.r_date from patient p join record r on p.num = r.r_p_num where phone LIKE ?";
			}else if(!check && find.equals("검색")) {
				sql = "select p.address, p.note, p.num, p.name, p.birth, p.sex, p.phone, r.r_num, r.r_date from patient p join record r on p.num = r.r_p_num where name LIKE ?";
			}else {
				sql = "select p.address, p.note, p.num, p.name, p.birth, p.sex, p.phone, r.r_num, r.r_date from patient p join record r on p.num = r.r_p_num where r_date LIKE ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search_str+"%");

			ResultSet rs = pstmt.executeQuery();
			
			patientRecordVO vo = null;
			List<patientRecordVO> list = new ArrayList<>();
			while(rs.next()) {
				vo = new patientRecordVO();
				vo.setNote(rs.getString("note"));
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setBirth(rs.getString("birth"));
				vo.setSex(rs.getString("sex"));
				vo.setPhone(rs.getString("phone"));
				vo.setR_num(rs.getInt("r_num"));
				vo.setR_date(rs.getString("r_date"));
				vo.setAddress(rs.getString("address"));
				
				list.add(vo);
				System.out.println(vo);
			}
			
			ConnectionHelper.close(rs);
			ConnectionHelper.close(pstmt);
			ConnectionHelper.close(conn);
			return list;
				
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return null;
	}
	
	public List<patientRecordVO> infoSearch(String p_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select p.name, p.num, p.birth, p.phone, p.address, p.sex, p.note, w_symptom, r.r_num, r.r_date, r.r_opinion, ");
			sql.append("r.r_d_code, r.r_e_code, pr.p_num, pr.p_m_code, pr.p_use, pay.pay_num, pay.pay_amount, pay.pay_pay, tr.tr_num, tr.tr_t_code");
			sql.append("from patient p join wait w on p.num = w.w_p_num join record r on p.num = r.r_p_num join prescription pr on r.r_num = pr.p_r_num ");
			sql.append("join payment pay on r.r_num = pay.pay_r_num join tr_mapping tr on r.r_num = tr.tr_r_num");
			sql.append("where num = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, p_num);

			ResultSet rs = pstmt.executeQuery();
			
			patientRecordVO vo = null;
			List<patientRecordVO> list = new ArrayList<>();
			while(rs.next()) {
				vo = new patientRecordVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setBirth(rs.getString("birth"));
				vo.setPhone(rs.getString("phone"));
				vo.setAddress(rs.getString("address"));
				vo.setSex(rs.getString("sex"));
				vo.setNote(rs.getString("note"));
/*
				vo.setR_date(rs.getString("r_date"));
				vo.setR_ban(rs.getString("r_ban"));
				vo.setR_d_code(rs.getString("r_d_code"));
				vo.setSymptom(rs.getString("symptom"));
				vo.setP_amount(rs.getInt("p_amount"));
				vo.setP_pay(rs.getInt("p_pay"));
				vo.setP_r_num(rs.getInt("p_r_num"));
				*/
				list.add(vo);
				System.out.println(vo);
				
			}
			
			ConnectionHelper.close(rs);
			ConnectionHelper.close(pstmt);
			ConnectionHelper.close(conn);
			return list;
				
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public void insertHistory() {
		
		
		
	}
	
	
	
	
	
	
}
