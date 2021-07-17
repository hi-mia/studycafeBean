package beanstudycafe_main_card;

import java.awt.CardLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;
import beanstudycafe_main_sit.SitList;

public class Day extends JPanel implements ActionListener { // 1일권

   private JLabel onePersonSit, roomSit;
   private JLabel title;
   private JButton twohourBtn, sixhourBtn, eightHourBtn, previewBtn;
   private JButton roomTwohourBtn, roomSixhourBtn, roomEighthourBtn;

   private Payment payment;

   private List<JButton> dayButtonList;
   private JFrame frame = new JFrame();
   // private int click;//버튼 눌렀을 때 번호

   private int sitTwoHour;
   private int sitSixHour;
   private int sitEightHour;
   private int roomTwoHour;
   private int roomSixHour;
   private int roomEightHour;

   private CafeDTO cafeDTO;
   private CardLayout card;
   private JPanel menuP;
   private CafeDTO tempCafeDTO;
   private SitList sitList;

   private boolean check;

   // (20, 480, 560, 340);

   public Day(CafeDTO cafeDTO, CardLayout card, JPanel menuP, SitList sitList) {// dto , s, r,
      this.cafeDTO = cafeDTO;
      this.card = card;
      this.menuP = menuP;
      this.sitList = sitList;
      setLayout(null);

      Font font = new Font("LH B", Font.BOLD, 30);
      Font font2 = new Font("LH B", Font.BOLD, 15);
      Font font3 = new Font("LH B", Font.BOLD, 40);// 타이틀폰트

      dayButtonList = new ArrayList<JButton>();

      title = new JLabel("당일권");
      title.setBounds(165, 20, 250, 40);
      title.setFont(font3);
      title.setHorizontalAlignment(JLabel.CENTER);
      title.setForeground(Color.white);

      onePersonSit = new JLabel("1인석");
      onePersonSit.setBounds(20, 80, 100, 80);
      onePersonSit.setFont(font);
      onePersonSit.setHorizontalAlignment(JLabel.CENTER);
      onePersonSit.setForeground(Color.white);

      twohourBtn = new JButton("2시간");
      twohourBtn.setBounds(130, 80, 130, 80);
      twohourBtn.setFont(font);
      twohourBtn.setBackground(Color.DARK_GRAY);
      twohourBtn.setForeground(Color.white);

      sixhourBtn = new JButton("6시간");
      sixhourBtn.setBounds(270, 80, 130, 80);
      sixhourBtn.setFont(font);
      sixhourBtn.setBackground(Color.DARK_GRAY);
      sixhourBtn.setForeground(Color.white);

      eightHourBtn = new JButton("8시간");
      eightHourBtn.setBounds(410, 80, 130, 80);
      eightHourBtn.setFont(font);
      eightHourBtn.setBackground(Color.DARK_GRAY);
      eightHourBtn.setForeground(Color.white);

      // --------------------------------------------//

      roomSit = new JLabel("룸좌석");
      roomSit.setBounds(20, 170, 100, 80);
      roomSit.setFont(font);
      roomSit.setHorizontalAlignment(JLabel.CENTER);
      roomSit.setForeground(Color.white);

      roomTwohourBtn = new JButton("2시간");
      roomTwohourBtn.setBounds(130, 170, 130, 80);
      roomTwohourBtn.setFont(font);
      roomTwohourBtn.setBackground(Color.DARK_GRAY);
      roomTwohourBtn.setForeground(Color.white);

      roomSixhourBtn = new JButton("6시간");
      roomSixhourBtn.setBounds(270, 170, 130, 80);
      roomSixhourBtn.setFont(font);
      roomSixhourBtn.setBackground(Color.DARK_GRAY);
      roomSixhourBtn.setForeground(Color.white);

      roomEighthourBtn = new JButton("8시간");
      roomEighthourBtn.setBounds(410, 170, 130, 80);
      roomEighthourBtn.setFont(font);
      roomEighthourBtn.setBackground(Color.DARK_GRAY);
      roomEighthourBtn.setForeground(Color.white);

      previewBtn = new JButton("이전으로");
      previewBtn.setBounds(440, 30, 100, 40);
      previewBtn.setFont(font2);
      previewBtn.setBackground(Color.DARK_GRAY);
      previewBtn.setForeground(Color.white);

      // 글자색변경
      add(onePersonSit);
      add(twohourBtn);
      add(sixhourBtn);
      add(eightHourBtn);

      add(roomSit);
      add(roomTwohourBtn);
      add(roomSixhourBtn);
      add(roomEighthourBtn);
      add(previewBtn);
      add(title);

      dayButtonList.add(twohourBtn);
      dayButtonList.add(sixhourBtn);
      dayButtonList.add(eightHourBtn);
      dayButtonList.add(previewBtn);
      dayButtonList.add(roomTwohourBtn);
      dayButtonList.add(roomSixhourBtn);
      dayButtonList.add(roomEighthourBtn);

      for (JButton dayJbtn : dayButtonList) {
         dayJbtn.setOpaque(true);// 불투명하게
         dayJbtn.setBorderPainted(false);// 버튼 겉선 제거
         dayJbtn.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거
      }

      event();

      setBackground(new Color(51, 51, 51));
      setBounds(500, 200, 590, 340);
      setVisible(true);

   }

   public void event() {
      previewBtn.addActionListener(this);
      twohourBtn.addActionListener(this);
      sixhourBtn.addActionListener(this);
      eightHourBtn.addActionListener(this);
      roomTwohourBtn.addActionListener(this);
      roomSixhourBtn.addActionListener(this);
      roomEighthourBtn.addActionListener(this);

   }

   @Override
   public void actionPerformed(ActionEvent e) {

      CafeDAO cafeDAO = CafeDAO.getInstance();
      SRLDAO srlDAO = SRLDAO.getInstance();

      String selectNum = null;
      if (sitList.getSelectSit() != 0 && sitList.getSelectRoom() == 0) {
         selectNum = "좌석 " + sitList.getSelectSit();
      } else if (sitList.getSelectRoom() != 0 && sitList.getSelectSit() == 0) {
         selectNum = "룸 " + sitList.getSelectRoom();
      }

      String selectHour = e.getActionCommand();

      int selectPay = 0, selectTime = 0;
      if (e.getSource() == twohourBtn) {
         selectTime = 2;
         selectPay = 10000;
      } else if (e.getSource() == sixhourBtn) {
         selectTime = 6;
         selectPay = 28000;
      } else if (e.getSource() == eightHourBtn) {
         selectTime = 8;
         selectPay = 35000;
      } else if (e.getSource() == roomTwohourBtn) {
         selectTime = 2;
         selectPay = 25000;
      } else if (e.getSource() == roomSixhourBtn) {
         selectTime = 6;
         selectPay = 70000;
      } else if (e.getSource() == roomEighthourBtn) {
         selectTime = 8;
         selectPay = 90000;
      }

      String tel = cafeDTO.getTel();

      // (1인좌석선택안함 룸선택 || 1인좌석선택 룸좌석선택안함)

      if (sitList.getSelectSit() == 0 && sitList.getSelectRoom() != 0) {

         if (e.getSource() == roomTwohourBtn || e.getSource() == roomSixhourBtn
               || e.getSource() == roomEighthourBtn) {

            int result = JOptionPane.showConfirmDialog(this, "결제하시겠습니까?", "comfirm", JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
               new Payment(selectNum, selectHour, selectPay, selectTime, tel, cafeDTO, sitList);
            } else {
               JOptionPane.showMessageDialog(this, "다시 선택해주세요.");
            }
         }
      } 

      // (1인좌석선택, 룸선택안함 . 1인좌석선택안함 룸선택. 했을 떄. )
      if (sitList.getSelectSit() != 0 && sitList.getSelectRoom() == 0) {

         if (e.getSource() == twohourBtn || e.getSource() == sixhourBtn || e.getSource() == eightHourBtn) {

            int result = JOptionPane.showConfirmDialog(this, "결제하시겠습니까?", "comfirm", JOptionPane.YES_NO_OPTION,
                  JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
               new Payment(selectNum, selectHour, selectPay, selectTime, tel, cafeDTO, sitList);
            } else {
               JOptionPane.showMessageDialog(this, "다시 선택해주세요.");
            }
         } // if
      }

      // 이전으로
      if (e.getSource() == previewBtn) {
         card.show(menuP, "DW");
      }

   }
}