package beanstudycafe_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beanstudycafe_dto.SRLDTO;

public class SRLDAO {
	private static SRLDAO instance;

	// 오라클드라이버명@내호스트:1521:xe
	private String url = "jdbc:oracle:thin:@192.168.0.230:1521:xe";
	private String username = "c##java";
	private String password = "bit";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public SRLDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}// SRLDAO

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// getConnection

	// 타입명
	public static SRLDAO getInstance() {
		if (instance == null)
			instance = new SRLDAO();

		return instance;
	}// getInstance

	public SRLDTO getList() {
		SRLDTO srlDTO = new SRLDTO();
		getConnection();
		String sql = "select * from beancafeSRL";

		try {
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < 33; i++) {
					if (rs.getInt((i + 1)) == 0) { // true == 입실 중, false = 빈 좌석
						if (i < 20) {
							srlDTO.getSitCheck()[i] = false;
						} else if (i < 23) {
							srlDTO.getRoomCheck()[i - 20] = false;
						} else if (i < 33) {
							srlDTO.getLockerCheck()[i - 23] = false;
						}
					} else if (rs.getInt((i + 1)) == 1) {
						if (i < 20) {
							srlDTO.getSitCheck()[i] = true;
						} else if (i < 23) {
							srlDTO.getRoomCheck()[i - 20] = true;
						} else if (i < 33) {
							srlDTO.getLockerCheck()[i - 23] = true;
						}
					}
				} // for

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// System.out.println(srlDTO);

		return srlDTO;
	}// getList

	public int setSit(SRLDTO srlDTO) {
		int su = 0;
		getConnection();
		String sql = "update beancafeSRL set sit1 = ?, sit2 = ?, sit3 = ?, sit4 = ?, sit5 = ?, sit6 = ?, sit7 = ?, sit8 = ?, sit9 = ?, sit10 = ?,"
				+ "sit11 = ?, sit12 = ?, sit13 = ?, sit14 = ?, sit15 = ?,sit16 = ?, sit17 = ?, sit18 = ?, sit19 = ?, sit20 = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < 20; i++) {

				if (srlDTO.getSitCheck()[i]) {
					pstmt.setInt(i + 1, 1);
				} else {
					pstmt.setInt(i + 1, 0);
				}

			} // for

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// System.out.println(srlDTO);

		return su;
	}// setSit

	public int setRoom(SRLDTO srlDTO) {
		int su = 0;
		getConnection();
		String sql = "update beancafeSRL set room1 = ?, room2 = ?, room3 = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < 3; i++) {
				if (srlDTO.getRoomCheck()[i]) {
					pstmt.setInt(i + 1, 1);
				} else {
					pstmt.setInt(i + 1, 0);
				}

			} // for

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// System.out.println(srlDTO);

		return su;
	}// setSRL

	public int setLocker(SRLDTO srlDTO) {
		int su = 0;
		getConnection();
		String sql = "update beancafeSRL set locker1 = ?, locker2 = ?, locker3 = ?, locker4 = ?, locker5 = ?, locker6 = ?, locker7 = ?, locker8 = ?, locker9 = ?, locker10 = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < 10; i++) {
				if (srlDTO.getLockerCheck()[i]) {
					pstmt.setInt(i + 1, 1);
				} else {
					pstmt.setInt(i + 1, 0);
				}

			} // for

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// System.out.println(srlDTO);

		return su;
	}// setLocker
}
