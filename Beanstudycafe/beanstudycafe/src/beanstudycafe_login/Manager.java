package beanstudycafe_login;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import beanstudycafe_chat.CafeManager;
import beanstudycafe_chat.ChatDTO;
import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;

public class Manager extends JFrame implements ActionListener {
   private Font fontKor, fontEng;
   private JButton memberBtn, payMentBtn;
   private JLabel titleKor, titleEng;
   private JLabel memberL, BmemberL, SmemberL;  //매출 목록라벨
   private JLabel memberTime, BmemberTime, SmemberTime;  //매출 시간라벨
   private JLabel memberPayment, BmemberPayment, SmemberPayment;  //매출 시간라벨
   private DefaultTableModel model;
   private JTable memberTab;
   private CardLayout cardLayout;
   private JPanel cardP;
   private JComboBox<String> title;
   private JTextField search;
   private JButton searchBtn;
   private CafeManager cafeManager;

   public Manager(CafeDTO cafeDTO) {
      CafeDAO cafeDAO = CafeDAO.getInstance();
      cafeManager = new CafeManager();
      
      setLayout(null);
      fontKor = new Font("LH B", Font.BOLD, 24);
      fontEng = new Font("LH B", Font.BOLD, 28);

      memberBtn = new JButton("회원 관리");
      payMentBtn = new JButton("매출 관리");

      titleKor = new JLabel("스터디 카페");
      titleEng = new JLabel("StudyCafe Bean");
      
      memberL = new JLabel("       회원");
      memberL.setFont(new Font("LH B", Font.BOLD, 22));
      memberL.setForeground(Color.WHITE);
      BmemberL = new JLabel("       비회원");
      BmemberL.setFont(new Font("LH B", Font.BOLD, 22));
      BmemberL.setForeground(Color.WHITE);
      
      SmemberL = new JLabel("       전체");
      SmemberL.setFont(new Font("LH B", Font.BOLD, 22));
      SmemberL.setForeground(Color.WHITE);
      
      //코드가 1이면 회원 코드가 2면 비회원
      memberTime = new JLabel("회원 이용시간 : "+cafeDAO.getTimeMember());
      memberTime.setFont(new Font("LH B", Font.BOLD, 14));
      memberTime.setForeground(Color.WHITE);
      BmemberTime = new JLabel("비회원 이용시간 : "+cafeDAO.getTimeGuest());
      BmemberTime.setFont(new Font("LH B", Font.BOLD, 14));
      BmemberTime.setForeground(Color.WHITE);
      SmemberTime = new JLabel("전체 이용시간 : "+cafeDAO.getTimeAll());
      SmemberTime.setFont(new Font("LH B", Font.BOLD, 14));
      SmemberTime.setForeground(Color.WHITE);
      
      memberPayment = new JLabel("  회원 결제 : "+cafeDAO.getPayMember());
      memberPayment.setFont(new Font("LH B", Font.BOLD, 14));
      memberPayment.setForeground(Color.WHITE);
      BmemberPayment = new JLabel("  비회원 결제 : "+cafeDAO.getPayGuest());
      BmemberPayment.setFont(new Font("LH B", Font.BOLD, 14));
      BmemberPayment.setForeground(Color.WHITE);
      SmemberPayment = new JLabel("  전체 결제 : "+cafeDAO.getPayAll());
      SmemberPayment.setFont(new Font("LH B", Font.BOLD, 14));
      SmemberPayment.setForeground(Color.WHITE);

      Vector<String> v = new Vector<String>();
      v.add("회원상태"); // 회원, 비회원
      v.add("이름");
      v.add("핸드폰번호");
      v.add("email");
      v.add("좌석 번호");
      v.add("룸 번호");
      v.add("사물함번호");
      v.add("총 이용시간");
      v.add("잔여시간");
      v.add("총 결제금액");

      model = new DefaultTableModel(v, 0);
      memberTab = new JTable(model);
      JScrollPane scroll = new JScrollPane(memberTab);
      scroll.setBounds(15, 135, 600, 460);

      cardLayout = new CardLayout();

      String[] titleArr = { "번호", "이름", "회원", "비회원" };

      title = new JComboBox<String>(titleArr);

      search = new JTextField();

      searchBtn = new JButton("검색");

      cardP = new JPanel();
      JPanel memberListP = new JPanel(new BorderLayout());
      JPanel paymentListP = new JPanel(new GridLayout(3,3));
      paymentListP.setBackground(new Color(51, 51, 51));
      
      paymentListP.add(memberL);
      paymentListP.add(memberTime);
      paymentListP.add(memberPayment);
      paymentListP.add(BmemberL);
      paymentListP.add(BmemberTime);
      paymentListP.add(BmemberPayment);
      paymentListP.add(SmemberL);
      paymentListP.add(SmemberTime);
      paymentListP.add(SmemberPayment);

      cardP.setLayout(cardLayout);

      memberListP.add(scroll);

      cardP.add(paymentListP, "매출");
      cardP.add(memberListP, "회원");

      cardLayout.show(cardP, "회원");
      

      cardP.setBounds(10, 135, 770, 420);

      titleKor.setFont(fontKor);
      titleKor.setBackground(new Color(51, 51, 51));
      titleKor.setForeground(Color.WHITE);
      titleKor.setBounds(260, 10, 400, 50);

      titleEng.setFont(fontEng);
      titleEng.setBackground(new Color(51, 51, 51));
      titleEng.setForeground(Color.WHITE);
      titleEng.setBounds(280, 30, 400, 50);

      memberBtn.setFont(fontKor);
      memberBtn.setBackground(new Color(51, 51, 51));
      memberBtn.setForeground(Color.WHITE);
      memberBtn.setBounds(15, 100, 150, 30);
      memberBtn.setFocusable(false);

      payMentBtn.setFont(fontKor);
      payMentBtn.setBackground(new Color(51, 51, 51));
      payMentBtn.setForeground(Color.WHITE);
      payMentBtn.setBounds(620, 100, 150, 30);
      payMentBtn.setFocusable(false);

      title.setFont(new Font("LH B", Font.BOLD, 14));
      title.setBounds(180, 100, 70, 25);
      title.setFocusable(false);

      search.setFont(new Font("LH B", Font.BOLD, 14));
      search.setBounds(250, 100, 270, 30);

      searchBtn.setFont(new Font("LH B", Font.BOLD, 14));
      searchBtn.setBackground(new Color(51, 51, 51));
      searchBtn.setForeground(Color.WHITE);
      searchBtn.setBounds(520, 100, 60, 30);

      Container con = getContentPane();

      con.setBackground(new Color(51, 51, 51));

      con.add(titleKor);
      con.add(titleEng);
      con.add(memberBtn);
      con.add(payMentBtn);
      con.add(title);
      con.add(search);
      con.add(searchBtn);
      con.add(cardP);
      setBounds(400, 30, 800, 600);
      setVisible(true);
//      setDefaultCloseOperation(EXIT_ON_CLOSE);

      memberBtn.addActionListener(this);
      payMentBtn.addActionListener(this);
      searchBtn.addActionListener(this);
      title.addActionListener(this);

      List<CafeDTO> list = cafeDAO.getCafeList();

      for (CafeDTO dto : list) {
         Vector<Object> vector = new Vector<Object>();
         if (dto.getCode() == 1) {
            String str = "회원";
            vector.add(str); // 회원, 비회원
            vector.add(dto.getName());
            vector.add(dto.getTel());
            vector.add(dto.getMail());
            vector.add(dto.getSit());
            vector.add(dto.getRoom());
            vector.add(dto.getLocker());
            vector.add(dto.getTime());
            vector.add(dto.getTotStime());
            vector.add(dto.getPayment());

            model.addRow(vector);
         } else if (dto.getCode() == 2) {
            String str = "비회원";
            vector.add(str); // 회원, 비회원
            vector.add(dto.getName());
            vector.add(dto.getTel());
            vector.add(dto.getMail());
            vector.add(dto.getSit());
            vector.add(dto.getRoom());
            vector.add(dto.getLocker());
            vector.add(dto.getTime());
            vector.add(dto.getTotStime());
            vector.add(dto.getPayment());

            model.addRow(vector);
         }
      } // for

       addWindowListener(new WindowAdapter() {
             
             @Override
            public void windowClosing(WindowEvent e) {//창을 종료할때
             cafeManager.end();
                setVisible(false);
            }
          });
      
      
      
   }// 생성자
   

   @Override
   public void actionPerformed(ActionEvent e) {
      CafeDAO cafeDAO = CafeDAO.getInstance();
      List<CafeDTO> list;
      if (e.getSource() == memberBtn) {
         search.setVisible(true);
         title.setVisible(true);
         searchBtn.setVisible(true);
         
         search.setEditable(true);
         list = cafeDAO.getCafeList();

         model.setRowCount(0);

         for (CafeDTO dto : list) {
            Vector<Object> v = new Vector<Object>();
            if (dto.getCode() == 1) {
               String str = "회원";
               v.add(str); // 회원, 비회원
               v.add(dto.getName());
               v.add(dto.getTel());
               v.add(dto.getMail());
               v.add(dto.getSit());
               v.add(dto.getRoom());
               v.add(dto.getLocker());
               v.add(dto.getTime());
               v.add(dto.getTotStime());
               v.add(dto.getPayment());

               model.addRow(v);
            } else if (dto.getCode() == 2) {
               String str = "비회원";
               v.add(str); // 회원, 비회원
               v.add(dto.getName());
               v.add(dto.getTel());
               v.add(dto.getMail());
               v.add(dto.getSit());
               v.add(dto.getRoom());
               v.add(dto.getLocker());
               v.add(dto.getTime());
               v.add(dto.getTotStime());
               v.add(dto.getPayment());

               model.addRow(v);
            }
         } // for

         cardLayout.show(cardP, "회원");

      } else if (e.getSource() == payMentBtn) {//매출버튼 누르면~
         search.setVisible(false);
         title.setVisible(false);
         searchBtn.setVisible(false);
         
         cardLayout.show(cardP, "매출");
         //여길 회원,비회원,총회원 각 매출과 총매출 넣어주기.

      } else if (e.getSource() == searchBtn) {
         if (title.getSelectedIndex() == 0) { // 핸드폰 번호 검색
            list = cafeDAO.selectPhone(search.getText());

            model.setRowCount(0);

            for (CafeDTO dto : list) {
               Vector<Object> v = new Vector<Object>();
               if (dto.getCode() == 1) {
                  String str = "회원";
                  v.add(str); // 회원, 비회원
                  v.add(dto.getName());
                  v.add(dto.getTel());
                  v.add(dto.getMail());
                  v.add(dto.getSit());
                  v.add(dto.getRoom());
                  v.add(dto.getLocker());
                  v.add(dto.getTime());
                  v.add(dto.getTotStime());
                  v.add(dto.getPayment());

                  model.addRow(v);
               } else if (dto.getCode() == 2) {
                  String str = "비회원";
                  v.add(str); // 회원, 비회원
                  v.add(dto.getName());
                  v.add(dto.getTel());
                  v.add(dto.getMail());
                  v.add(dto.getSit());
                  v.add(dto.getRoom());
                  v.add(dto.getLocker());
                  v.add(dto.getTime());
                  v.add(dto.getTotStime());
                  v.add(dto.getPayment());

                  model.addRow(v);
               }
            } // for
            search.setText("");
         } else if (title.getSelectedIndex() == 1) { // 이름 검색
            list = cafeDAO.selectName(search.getText());

            model.setRowCount(0);

            for (CafeDTO dto : list) {
               Vector<Object> v = new Vector<Object>();
               if (dto.getCode() == 1) {
                  String str = "회원";
                  v.add(str); // 회원, 비회원
                  v.add(dto.getName());
                  v.add(dto.getTel());
                  v.add(dto.getMail());
                  v.add(dto.getSit());
                  v.add(dto.getRoom());
                  v.add(dto.getLocker());
                  v.add(dto.getTime());
                  v.add(dto.getTotStime());
                  v.add(dto.getPayment());

                  model.addRow(v);
               } else if (dto.getCode() == 2) {
                  String str = "비회원";
                  v.add(str); // 회원, 비회원
                  v.add(dto.getName());
                  v.add(dto.getTel());
                  v.add(dto.getMail());
                  v.add(dto.getSit());
                  v.add(dto.getRoom());
                  v.add(dto.getLocker());
                  v.add(dto.getTime());
                  v.add(dto.getTotStime());
                  v.add(dto.getPayment());

                  model.addRow(v);
               }
            } // for
            search.setText("");
         }
      } else if (e.getSource() == title) {
         if (title.getSelectedIndex() == 2) { // 회원 중 검색
            search.setText("");
            search.setEditable(false);
            list = cafeDAO.selectMember();

            model.setRowCount(0);

            for (CafeDTO dto : list) {
               Vector<Object> v = new Vector<Object>();

               String str = "회원";
               v.add(str); // 회원, 비회원
               v.add(dto.getName());
               v.add(dto.getTel());
               v.add(dto.getMail());
               v.add(dto.getSit());
               v.add(dto.getRoom());
               v.add(dto.getLocker());
               v.add(dto.getTime());
               v.add(dto.getTotStime());
               v.add(dto.getPayment());

               model.addRow(v);
            } // for
         } else if (title.getSelectedIndex() == 3) { // 비회원 중 검색
            search.setText("");
            search.setEditable(false);
            list = cafeDAO.selectGuest();

            model.setRowCount(0);

            for (CafeDTO dto : list) {
               Vector<Object> v = new Vector<Object>();

               String str = "비회원";
               v.add(str); // 회원, 비회원
               v.add(dto.getName());
               v.add(dto.getTel());
               v.add(dto.getMail());
               v.add(dto.getSit());
               v.add(dto.getRoom());
               v.add(dto.getLocker());
               v.add(dto.getTime());
               v.add(dto.getTotStime());
               v.add(dto.getPayment());

               model.addRow(v);
            } // for
         } else if (title.getSelectedIndex() == 0) {
            search.setEditable(true);
            search.setText("");
         } else if (title.getSelectedIndex() == 1) {
            search.setEditable(true);
            search.setText("");
         }
      }
   }

//   public static void main(String[] args) {
//      new Manager();
//   }
}