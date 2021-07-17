package beanstudycafe_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beanstudycafe_dto.CafeDTO;

public class CafeDAO {
   private static CafeDAO instance;

   // 오라클드라이버명@내호스트:1521:xe
   private String url = "jdbc:oracle:thin:@localhost:1521:xe";
   private String username = "c##java";
   private String password = "bit";

   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;

   public CafeDAO() {

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

   }// CafeDAO

   public void getConnection() {
      try {
         conn = DriverManager.getConnection(url, username, password);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }// getConnection

   // 타입명
   public static CafeDAO getInstance() {
      if (instance == null)
         instance = new CafeDAO();

      return instance;
   }// getInstance

   public int getSeq() {
      int seq = 0;
      getConnection();
      String sql = "select seq_beancafe.nextval from dual";

      try {
         pstmt = conn.prepareStatement(sql);

         rs = pstmt.executeQuery();

         rs.next();

         seq = rs.getInt(1);

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return seq;
   }// getSeq

   public List<CafeDTO> getCafeList() {
      List<CafeDTO> list = new ArrayList<CafeDTO>();
      getConnection();
      String sql = "select * from beancafe order by seq";

      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.setSeq(rs.getInt("seq"));
            cafeDTO.setCode(rs.getInt("code"));
            cafeDTO.setName(rs.getString("name"));
            cafeDTO.setTel(rs.getString("tel"));
            cafeDTO.setMail(rs.getString("mail"));
            cafeDTO.setPassword(rs.getString("password"));
            cafeDTO.setTime(rs.getInt("time"));
            cafeDTO.setTotStime(rs.getDouble("totstime"));
            cafeDTO.setTotRtime(rs.getDouble("totrtime"));
            cafeDTO.setPayment(rs.getInt("payment"));
            cafeDTO.setSit(rs.getInt("sit"));
            cafeDTO.setRoom(rs.getInt("room"));
            cafeDTO.setLocker(rs.getInt("locker"));
            cafeDTO.setIntime(rs.getDouble("intime"));
            cafeDTO.setIntime(rs.getDouble("outtime"));

            list.add(cafeDTO);
         } // while
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   }// getCafeList DB전체 가져오기

   public int insertCafe(CafeDTO cafeDTO) { // 회원가입
      int su = 0;
      getConnection();
//            Calendar  cal = new GregorianCalendar();
//            Timestamp ts = new Timestamp(cal.getTimeInMillis());
      String sql = "insert into beancafe(seq, code, name, tel, mail, password, totstime, totrtime, payment, sit, room, locker) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cafeDTO.getSeq());
         pstmt.setInt(2, cafeDTO.getCode());
         pstmt.setString(3, cafeDTO.getName());
         pstmt.setString(4, cafeDTO.getTel());
         pstmt.setString(5, cafeDTO.getMail());
         pstmt.setString(6, cafeDTO.getPassword());
         pstmt.setDouble(7, cafeDTO.getTotStime());
         pstmt.setDouble(8, cafeDTO.getTotRtime());
         pstmt.setInt(9, cafeDTO.getPayment());
         pstmt.setInt(10, cafeDTO.getSit());
         pstmt.setInt(11, cafeDTO.getRoom());
         pstmt.setInt(12, cafeDTO.getLocker());

         su = pstmt.executeUpdate();

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return su;
   }// insertCafe

   public int updateCafe(CafeDTO cafeDTO, String tel) {
      int su = 0;
      getConnection();
      String sql = "update beancafe set name = ?, tel = ?, password = ? where tel = ?"; // 못바꾸는거 : seq, intime / tel로
                                                                     // 찾아서 바꾸는데 tel도 수정 가능하게해도
                                                                     // 되나요?

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, cafeDTO.getName());
         pstmt.setString(2, cafeDTO.getTel());
         pstmt.setString(3, cafeDTO.getPassword());
         pstmt.setString(4, tel);

         su = pstmt.executeUpdate();

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return su;
   }// updateCafe

//   public void deleteCafe(int seq) {
//      getConnection();
//      String sql = "delete beancafe where seq = ?";
//      
//      try {
//         pstmt = conn.prepareStatement(sql);
//         pstmt.setInt(1, seq);
//         
//         pstmt.executeUpdate();
//      } catch (SQLException e) {
//         e.printStackTrace();
//      } finally {
//         try {
//            if(pstmt != null)pstmt.close();
//            if(conn != null)conn.close();
//         } catch (SQLException e) {
//            e.printStackTrace();
//         }
//      }
//   }//deleteCafe()

   public List<CafeDTO> selectPhone(String phone) {
      List<CafeDTO> list = new ArrayList<>();
      getConnection();
      String sql = "select * from beancafe where tel like ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + phone + "%");

         rs = pstmt.executeQuery();

         while (rs.next()) {
            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.setSeq(rs.getInt("seq"));
            cafeDTO.setCode(rs.getInt("code"));
            cafeDTO.setName(rs.getString("name"));
            cafeDTO.setTel(rs.getString("tel"));
            cafeDTO.setMail(rs.getString("mail"));
            cafeDTO.setPassword(rs.getString("password"));
            cafeDTO.setTime(rs.getInt("time"));
            cafeDTO.setTotStime(rs.getDouble("totstime"));
            cafeDTO.setTotRtime(rs.getDouble("totrtime"));
            cafeDTO.setPayment(rs.getInt("payment"));
            cafeDTO.setSit(rs.getInt("sit"));
            cafeDTO.setRoom(rs.getInt("room"));
            cafeDTO.setLocker(rs.getInt("locker"));
            cafeDTO.setIntime(rs.getDouble("intime"));
            cafeDTO.setIntime(rs.getDouble("outtime"));

            list.add(cafeDTO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   }// selectPhone - 특정 번호가 들어간 리스트 가져오기

   public List<CafeDTO> selectName(String name) {
      List<CafeDTO> list = new ArrayList<>();
      getConnection();
      String sql = "select * from beancafe where name like ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + name + "%");

         rs = pstmt.executeQuery();

         while (rs.next()) {
            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.setSeq(rs.getInt("seq"));
            cafeDTO.setCode(rs.getInt("code"));
            cafeDTO.setName(rs.getString("name"));
            cafeDTO.setTel(rs.getString("tel"));
            cafeDTO.setMail(rs.getString("mail"));
            cafeDTO.setPassword(rs.getString("password"));
            cafeDTO.setTime(rs.getInt("time"));
            cafeDTO.setTotStime(rs.getDouble("totstime"));
            cafeDTO.setTotRtime(rs.getDouble("totrtime"));
            cafeDTO.setPayment(rs.getInt("payment"));
            cafeDTO.setSit(rs.getInt("sit"));
            cafeDTO.setRoom(rs.getInt("room"));
            cafeDTO.setLocker(rs.getInt("locker"));
            cafeDTO.setIntime(rs.getDouble("intime"));
            cafeDTO.setIntime(rs.getDouble("outtime"));

            list.add(cafeDTO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   }// selectName - 특정 이름이 들어간 리스트 가져오기

   public List<CafeDTO> selectMember() {
      List<CafeDTO> list = new ArrayList<>();
      getConnection();
      String sql = "select * from beancafe where code like ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, 1);

         rs = pstmt.executeQuery();

         while (rs.next()) {
            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.setSeq(rs.getInt("seq"));
            cafeDTO.setCode(rs.getInt("code"));
            cafeDTO.setName(rs.getString("name"));
            cafeDTO.setTel(rs.getString("tel"));
            cafeDTO.setMail(rs.getString("mail"));
            cafeDTO.setPassword(rs.getString("password"));
            cafeDTO.setTime(rs.getInt("time"));
            cafeDTO.setTotStime(rs.getDouble("totstime"));
            cafeDTO.setTotRtime(rs.getDouble("totrtime"));
            cafeDTO.setPayment(rs.getInt("payment"));
            cafeDTO.setSit(rs.getInt("sit"));
            cafeDTO.setRoom(rs.getInt("room"));
            cafeDTO.setLocker(rs.getInt("locker"));
            cafeDTO.setIntime(rs.getDouble("intime"));
            cafeDTO.setIntime(rs.getDouble("outtime"));

            list.add(cafeDTO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   }// selectMember - 회원 리스트 가져오기

   public List<CafeDTO> selectGuest() {
      List<CafeDTO> list = new ArrayList<>();
      getConnection();
      String sql = "select * from beancafe where code like ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, 2);

         rs = pstmt.executeQuery();

         while (rs.next()) {
            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.setSeq(rs.getInt("seq"));
            cafeDTO.setCode(rs.getInt("code"));
            cafeDTO.setName(rs.getString("name"));
            cafeDTO.setTel(rs.getString("tel"));
            cafeDTO.setMail(rs.getString("mail"));
            cafeDTO.setPassword(rs.getString("password"));
            cafeDTO.setTime(rs.getInt("time"));
            cafeDTO.setTotStime(rs.getDouble("totstime"));
            cafeDTO.setTotRtime(rs.getDouble("totrtime"));
            cafeDTO.setPayment(rs.getInt("payment"));
            cafeDTO.setSit(rs.getInt("sit"));
            cafeDTO.setRoom(rs.getInt("room"));
            cafeDTO.setLocker(rs.getInt("locker"));
            cafeDTO.setIntime(rs.getDouble("intime"));
            cafeDTO.setIntime(rs.getDouble("outtime"));

            list.add(cafeDTO);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   }// selectGuest - 게스트 리스트 가져오기

   public CafeDTO selectTel(String tel) {
      CafeDTO cafeDTO = new CafeDTO();
      getConnection();

      String sql = "select * from beancafe where tel = ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, tel);

         rs = pstmt.executeQuery();

         if (!rs.isBeforeFirst()) {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
            return null;
         }

         rs.next();
         cafeDTO.setSeq(rs.getInt("seq"));
         cafeDTO.setCode(rs.getInt("code"));
         cafeDTO.setName(rs.getString("name"));
         cafeDTO.setTel(rs.getString("tel"));
         cafeDTO.setMail(rs.getString("mail"));
         cafeDTO.setPassword(rs.getString("password"));
         cafeDTO.setTime(rs.getInt("time"));
         cafeDTO.setTotStime(rs.getDouble("totstime"));
         cafeDTO.setTotRtime(rs.getDouble("totrtime"));
         cafeDTO.setPayment(rs.getInt("payment"));
         cafeDTO.setSit(rs.getInt("sit"));
         cafeDTO.setRoom(rs.getInt("room"));
         cafeDTO.setLocker(rs.getInt("locker"));
         cafeDTO.setIntime(rs.getDouble("intime"));
         cafeDTO.setIntime(rs.getDouble("outtime"));

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return cafeDTO;
   }// selectTel
   
   public int updateInSR(CafeDTO cafeDTO) {
      int su = 0;
      getConnection();
      String sql = "update beancafe set intime = ?, sit = ?, room = ?, time = ?, totStime = ?, totRtime = ?, payment = ? where tel = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setDouble(1, cafeDTO.getIntime());
         pstmt.setInt(2, cafeDTO.getSit());
         pstmt.setInt(3, cafeDTO.getRoom());
         pstmt.setInt(4, cafeDTO.getTime());
         pstmt.setDouble(5, cafeDTO.getTotStime());
         pstmt.setDouble(6, cafeDTO.getTotRtime());
         pstmt.setInt(7, cafeDTO.getPayment());
         pstmt.setString(8, cafeDTO.getTel());
         
         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(pstmt != null)pstmt.close();
            if(conn != null)pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return su;
   }
   
   public int updateOutSR(CafeDTO cafeDTO) {
      int su = 0;
      getConnection();
      String sql = "update beancafe set outtime = ?, sit = ?, room = ?, locker = ?, totStime = ?, totRtime = ? where tel = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setDouble(1, cafeDTO.getOuttime());
         pstmt.setInt(2, cafeDTO.getSit());
         pstmt.setInt(3, cafeDTO.getRoom());
         pstmt.setInt(4, cafeDTO.getLocker());
         pstmt.setDouble(5, cafeDTO.getTotStime());
         pstmt.setDouble(6, cafeDTO.getTotRtime());
         pstmt.setString(7, cafeDTO.getTel());
         
         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(pstmt != null)pstmt.close();
            if(conn != null)pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return su;
   }
   
   public int updateSR(CafeDTO cafeDTO) {
      int su = 0;
      getConnection();
      String sql = "update beancafe set sit = ?, room = ? where tel = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cafeDTO.getSit());
         pstmt.setInt(2, cafeDTO.getRoom());
         pstmt.setString(3, cafeDTO.getTel());
         
         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
            try {
               if(pstmt != null)pstmt.close();
               if(conn != null)conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
      }
   
      return su;
   }
   
   public int updateLocker(CafeDTO cafeDTO) {
      int su = 0;
      getConnection();
      String sql = "update beancafe set locker = ? where tel = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cafeDTO.getLocker());
         pstmt.setString(2, cafeDTO.getTel());
         
         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
            try {
               if(pstmt != null)pstmt.close();
               if(conn != null)conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
      }
   
      return su;
   }
   
   public String getTimeMember() {
      String time = "";
      getConnection();
      String sql = "select sum(time) from beancafe where code = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, 1);
         
         rs = pstmt.executeQuery();
         
         rs.next();
         
         time = rs.getInt(1) + "시간";
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null)rs.close();
            if(pstmt != null)pstmt.close();
            if(conn != null)conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return time;
   }// getTimeMember
   
   public String getTimeGuest() {
      String time = "";
      getConnection();
      String sql = "select sum(time) from beancafe where code = ?";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, 2);
         
         rs = pstmt.executeQuery();
         
         rs.next();
         
         time = rs.getInt(1) + "시간";
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null)rs.close();
            if(pstmt != null)pstmt.close();
            if(conn != null)conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return time;
   }// getTimeMember
   
   public String getTimeAll() {
      String time = "";
      getConnection();
      String sql = "select sum(time) from beancafe";
      
      try {
         pstmt = conn.prepareStatement(sql);
         
         rs = pstmt.executeQuery();
         
         rs.next();
         
         time = rs.getInt(1) + "시간";
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null)rs.close();
            if(pstmt != null)pstmt.close();
            if(conn != null)conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return time;
   }// getTimeMember
   
   public String getPayMember() {
         String time = "";
         getConnection();
         String sql = "select sum(payment) from beancafe where code = ?";
         
         try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            
            rs = pstmt.executeQuery();
            
            rs.next();
            
            time = rs.getInt(1) + "원";
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(rs != null)rs.close();
               if(pstmt != null)pstmt.close();
               if(conn != null)conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         
         return time;
      }// getPayMember

      public String getPayGuest() {
         String time = "";
         getConnection();
         String sql = "select sum(payment) from beancafe where code = ?";
         
         try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 2);
            
            rs = pstmt.executeQuery();
            
            rs.next();
            
            time = rs.getInt(1) + "원";
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(rs != null)rs.close();
               if(pstmt != null)pstmt.close();
               if(conn != null)conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         
         return time;
      }// getPayGuest

      public String getPayAll() {
         String time = "";
         getConnection();
         String sql = "select sum(payment) from beancafe";
         
         try {
            pstmt = conn.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            
            rs.next();
            
            time = rs.getInt(1) + "원";
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(rs != null)rs.close();
               if(pstmt != null)pstmt.close();
               if(conn != null)conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         
         return time;
      }// getPayAll
   

}