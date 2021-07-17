package beanstudycafe_menu;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import beanstudycafe_chat.CafeClient;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_login.StartP;
import beanstudycafe_main_card.Day;
import beanstudycafe_main_card.DayWeek;
import beanstudycafe_main_card.ExitRoom;
import beanstudycafe_main_card.Locker;
import beanstudycafe_main_card.Week;
import beanstudycafe_main_sit.SitList;

public class MainP extends JFrame implements ActionListener { // 메인 배경색 스페이스 그레이로 바꾸기
   private CafeDTO cafeDTO;
   private JLabel titleL, enterL, sitL1, sitL2;
   private JButton questionBtn, registerUpdateBtn, enterBtn, exitBtn, sitChangeBtn, lockerBtn, logoutBtn;
   private CardLayout card;
   private JPanel sitP, menuP, enterP, exitP, sitChangeP, lockerP;
   private SitList sitList;
   private CafeClient chat;
   private Locker locker;

   public MainP(CafeDTO cafeDTO) {
      this.cafeDTO = cafeDTO;
      setLayout(null);

      Font title = new Font("LH B", Font.BOLD, 30);
      Font topBtn = new Font("LH B", Font.BOLD, 15);
      Font bottomBtn = new Font("LH B", Font.BOLD, 20);

      // 위
      titleL = new JLabel("BeanStudyCafe");
      titleL.setForeground(Color.WHITE);
      titleL.setBounds(165, 20, 300, 40);
      titleL.setFont(title);

      enterL = new JLabel(""); // 좌석을 먼저 선택해주세요
      enterL.setForeground(Color.WHITE);
//    enterL.setBounds(200, 450, 300, 300); // 글자크기 20일 때
      enterL.setBounds(155, 450, 300, 300);
      enterL.setFont(title);

      sitL1 = new JLabel(""); // 현재 좌석 보여주기
      sitL1.setForeground(Color.WHITE);
      sitL1.setBounds(220, 475, 300, 100); // 글자크기 20일 때
      sitL1.setFont(bottomBtn);

      sitL2 = new JLabel(""); // sitL2: 변경할 좌석을 선택해주세요
      sitL2.setForeground(Color.WHITE);
      sitL2.setBounds(115, 470, 500, 300); // 글자크기 20일 때
      sitL2.setFont(title);

      questionBtn = new JButton("문의하기");
      questionBtn.setBounds(40, 70, 100, 40);
      questionBtn.setBackground(new Color(51, 51, 51));
      questionBtn.setForeground(Color.WHITE);
      questionBtn.setFont(topBtn);
      questionBtn.setFocusable(false);

      registerUpdateBtn = new JButton("정보수정");
      registerUpdateBtn.setBounds(240, 70, 100, 40);
      registerUpdateBtn.setBackground(new Color(51, 51, 51));
      registerUpdateBtn.setForeground(Color.WHITE);
      registerUpdateBtn.setFont(topBtn);
      registerUpdateBtn.setFocusable(false);

      // 아래
      enterBtn = new JButton("입실");
      enterBtn.setFont(bottomBtn);
      enterBtn.setBackground(new Color(51, 51, 51));
      enterBtn.setForeground(Color.WHITE);
      enterBtn.setBounds(70, 430, 110, 70);
      enterBtn.setFocusable(false);

      exitBtn = new JButton("퇴실");
      exitBtn.setFont(bottomBtn);
      exitBtn.setBackground(new Color(51, 51, 51));
      exitBtn.setForeground(Color.WHITE);
      exitBtn.setBounds(180, 430, 110, 70);
      exitBtn.setFocusable(false);

      sitChangeBtn = new JButton("좌석이동");
      sitChangeBtn.setFont(bottomBtn);
      sitChangeBtn.setBackground(new Color(51, 51, 51));
      sitChangeBtn.setForeground(Color.WHITE);
      sitChangeBtn.setBounds(290, 430, 110, 70);
      sitChangeBtn.setFocusable(false);

      lockerBtn = new JButton("사물함");
      lockerBtn.setFont(bottomBtn);
      lockerBtn.setBackground(new Color(51, 51, 51));
      lockerBtn.setForeground(Color.WHITE);
      lockerBtn.setBounds(400, 430, 110, 70);
      lockerBtn.setFocusable(false);

      logoutBtn = new JButton("로그아웃");
      logoutBtn.setBounds(445, 70, 100, 40);
      logoutBtn.setBackground(new Color(51, 51, 51));
      logoutBtn.setForeground(Color.WHITE);
      logoutBtn.setFont(topBtn);
      logoutBtn.setFocusable(false);
      
      // 카드 레이아웃-------------
      card = new CardLayout();
      menuP = new JPanel(); // 입퇴실 큰 패널 - (10,500, 560, 270)
      menuP.setLayout(card);
      menuP.setBounds(10, 500, 565, 282);
      menuP.setBackground(new Color(51, 51, 51));

      sitList = new SitList(cafeDTO, card, menuP, enterL, sitL1, sitL2);
      
      sitP = new JPanel(new BorderLayout()); // 좌석 패널
      sitP.add(sitList);
      // sitP.setBackground(Color.BLACK);
      sitP.setBounds(10, 122, 590, 300);

      enterP = new JPanel(); // 입실패널 - DayWeek -> Day & Week 선택 / locker은 따로
      enterP.setBounds(10, 500, 565, 282);
      enterP.setBackground(new Color(51, 51, 51));

      exitP = new JPanel(); // 퇴실패널
      exitP.setBounds(10, 500, 565, 282);

      sitChangeP = new JPanel(); // 좌석이동패널
      sitChangeP.setBounds(10, 500, 565, 282);
//      sitChangeP.add(sitL1);
//      sitChangeP.add(sitL2);

      lockerP = new JPanel(); // 사물함 패널
      lockerP.setBounds(10, 500, 565, 282);
      // lockerP.setBackground(new Color(51, 51, 51));

      DayWeek dayWeek = new DayWeek(cafeDTO, card, menuP, sitList);
      Day day = new Day(cafeDTO, card, menuP, sitList);
      Week week = new Week(cafeDTO, card, menuP, sitList);
      locker = new Locker(card, menuP, cafeDTO,sitList);
      
      menuP.add(enterP, "EnP");
      menuP.add(dayWeek, "DW");
      menuP.add(day, "Day");
      menuP.add(week, "Week");
      menuP.add(locker, "LP");

      menuP.add(exitP, "ExP");

      menuP.add(sitChangeP, "SP");

      // menuP.add(lockerP,"LP");s

      // add
      Container c = this.getContentPane();
      c.setBackground(new Color(51, 51, 51));
      c.add(titleL);
      c.add(enterL);
      c.add(questionBtn);
      
      if(cafeDTO.getCode() == 1) {
    	  c.add(registerUpdateBtn);    	  
      }

      c.add(logoutBtn);
      c.add(sitChangeP);
      c.add(sitL1);
      c.add(sitL2);

      c.add(sitP);
      c.add(menuP);

      c.add(enterBtn);
      c.add(exitBtn);
      c.add(sitChangeBtn);
      c.add(lockerBtn);

      setTitle("메인 메뉴");
      setResizable(false);
      setVisible(true);
      setBounds(500, 0, 600, 830);

      setDefaultCloseOperation(EXIT_ON_CLOSE);

      event();
   }

   public void event() { // 이벤트
      questionBtn.addActionListener(this);
      registerUpdateBtn.addActionListener(this);
      enterBtn.addActionListener(this);
      exitBtn.addActionListener(this);
      sitChangeBtn.addActionListener(this);
      lockerBtn.addActionListener(this);
      logoutBtn.addActionListener(this);
   }// event()

//----------------
   @Override
   public void actionPerformed(ActionEvent e) {// 퇴실버튼 누른 후 글자가 겹친다
      menuP.setVisible(true);
      enterL.setText("");
      sitL1.setText("");
      sitL2.setText("");
      if (e.getSource() == questionBtn) {// 채팅버튼
        chat = new CafeClient(cafeDTO);

      }
      if (e.getSource() == registerUpdateBtn) {// 회원정보수정
    	  
         new RegisterUpdate(cafeDTO);

      }
      if (e.getSource() == logoutBtn) { // 로그아웃버튼
         setVisible(false);
         if(chat!=null) {
            chat.setVisible(false);
         }
         new StartP();

      }
      if (e.getSource() == enterBtn) {// 입실버튼

         if (cafeDTO.getSit() == 0) {// 1인석이 없고
            if (cafeDTO.getRoom() == 0) { // 룸도 없으면
               menuP.setVisible(false);
               enterL.setText("좌석을 선택해주세요");
            } else {
               card.show(menuP, "DW");
            }
         } else {// 1인석이 있다면
            card.show(menuP, "DW");
         }
         // 디비에 좌석 넣는거 해야함 - 버튼을 누르면 그 좌석번호가 디비에 저장되고 입실메뉴 활성화
         // 지금은 로그인전에 이미 좌석번호를 정해놓고 확인함
      }

      else if (e.getSource() == exitBtn) { // 퇴실버튼
         menuP.setVisible(false);
         if (cafeDTO.getSit() == 0) {// 1인석 좌석이 없다면
            if (cafeDTO.getRoom() == 0) { // 룸도 좌석이 없어
               enterL.setText("좌석을 선택해주세요");
            } else { // 룸이 좌석이 존재해
               String roomNumber = "룸 " + cafeDTO.getRoom();
               new ExitRoom(cafeDTO, roomNumber, sitList, locker, card, menuP);
            }

         } else {// 1인석 좌석이 존재하고
            if (cafeDTO.getRoom() == 0) {
               String sitNumber = "좌석 " + cafeDTO.getSit();
               new ExitRoom(cafeDTO, sitNumber, sitList, locker, card, menuP);
            }

         }

      }

      else if (e.getSource() == sitChangeBtn) {// 좌석이동버튼
//         menuP.setVisible(false);
         menuP.setVisible(false);
         if (cafeDTO.getSit() == 0) {// 1인석좌석이 없고
            if (cafeDTO.getRoom() == 0) {// 룸도 없다면
               enterL.setText("좌석을 선택해주세요");
            } else {
               sitL1.setText("현재 룸: " + cafeDTO.getRoom() + "번"); // 1번만 보임 다시 누르면 안됨
               sitL2.setText("변경할 자리를 선택해주세요");
            }
         } else {// 1인석이 존재하고
            if (cafeDTO.getRoom() == 0) { // 룸은 없을때
               sitL1.setText("현재 좌석: " + cafeDTO.getSit() + "번"); // 1번만 보임 다시 누르면 안됨
               sitL2.setText("변경할 자리를 선택해주세요");
            }
         } // else

      }

      else if (e.getSource() == lockerBtn) { // 사물함버튼
//         card.show(menuP, "SP");

         if (cafeDTO.getCode() != 1) {
            menuP.setVisible(false);
            enterL.setText("회원만 이용가능합니다");
            return;
         } else {
            card.show(menuP, "LP");
         }

      }
   }

//---------메인
//   public static void main(String[] args) {
//      new MainP(new CafeDTO());
//   }
}