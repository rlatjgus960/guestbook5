package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private DataSource dataSource; //내부적으로 가지고있어서 알아서 연결해줌

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private void getConnection() {
		try {
			
			conn = dataSource.getConnection();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 등록
	public int guestbookInsert(GuestbookVo guestbookVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, SYSDATE) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// 삭제
	public int guestbookDelete(GuestbookVo guestbookVo) {

		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestbookVo.getNo());
			pstmt.setString(2, guestbookVo.getPassword());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

		return count;
	}

	// 리스트
	public List<GuestbookVo> getGuestbookList() {
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         to_char(reg_date, 'YYYY-MM-DD HH24:MI:SS') regDate ";
			query += " from guestbook";
			query += " order by reg_date ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("regDate");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);

				guestbookList.add(guestbookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return guestbookList;

	}

}
