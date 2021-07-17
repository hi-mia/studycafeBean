package beanstudycafe_main_card;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_main_sit.SitList;

//패널.... 메인p에 붙여넣기. 

public class DayWeek extends JPanel implements ActionListener {
//1일권 정기권 

   private JLabel info;
   private JButton dayButton, weekButton, choiceButton, returnButton, extendTime;
   private CafeDTO cafeDTO;
   private CardLayout card;
   private JPanel menuP;
   private SitList sitList;
   private int hour = 1;// double형 totStime,totRtime에 붙여주기.
   private int time = 1;// 총이용시간
   private int extendCost = 5000;
   private int sw;

   public DayWeek(CafeDTO cafeDTO, CardLayout card, JPanel menuP, SitList sitList) {
      this.cafeDTO = cafeDTO;
      this.card = card;
      this.menuP = menuP;
      this.sitList = sitList;

      setLayout(null);

      Font font = new Font("LH B", Font.BOLD, 35);
      Font font2 = new Font("LH B", Font.BOLD, 25);
      Font font3 = new Font("LH B", Font.BOLD, 15);

      info = new JLabel("원하는 버튼을 눌러주세요.");
      info.setBounds(70, 12, 400, 50);
      info.setFont(font2);
      info.setForeground(Color.white);

      dayButton = new JButton("1일권");
      dayButton.setBounds(70, 80, 210, 120);
      dayButton.setFont(font);
      dayButton.setBackground(Color.DARK_GRAY);
      dayButton.setForeground(Color.white);

      weekButton = new JButton("정기권");
      weekButton.setBounds(300, 80, 200, 120);
      weekButton.setFont(font);
      weekButton.setBackground(Color.DARK_GRAY);
      weekButton.setForeground(Color.white);

      returnButton = new JButton("이전으로");
      returnButton.setBounds(410, 20, 90, 45);
      returnButton.setFont(font3);
      returnButton.setBackground(Color.DARK_GRAY);
      returnButton.setForeground(Color.white);

      extendTime = new JButton("1시간 연장");
      extendTime.setHorizontalAlignment(JButton.CENTER);
      extendTime.setBounds(70, 210, 430, 70);
      extendTime.setFont(font);
      extendTime.setBackground(Color.DARK_GRAY);
      extendTime.setForeground(Color.white);

      add(info);
      add(dayButton);
      add(weekButton);
      add(returnButton);
      add(extendTime);

      dayButton.setOpaque(true);// 불투명하게
      dayButton.setBorderPainted(false);// 버튼 겉선 제거
      dayButton.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거

      weekButton.setOpaque(true);// 불투명하게
      weekButton.setBorderPainted(false);// 버튼 겉선 제거
      weekButton.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거

      returnButton.setOpaque(true);// 불투명하게
      returnButton.setBorderPainted(false);// 버튼 겉선 제거
      returnButton.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거

      extendTime.setOpaque(true);// 불투명하게
      extendTime.setBorderPainted(false);// 버튼 겉선 제거
      extendTime.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거

      setBackground(new Color(51, 51, 51));
      setBounds(200, 200, 590, 340);
      setVisible(true);

      dayButton.addActionListener(this);
      weekButton.addActionListener(this);
      returnButton.addActionListener(this);
      extendTime.addActionListener(this);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // 1일 정기권 데이위크 이 사람이 좌석이 없을 떄

      // 1일권 회원+비회원.

      if (e.getSource() == dayButton) {
         // 1인좌석, 룸X 구매가능.
         if (cafeDTO.getSit() == 0 && cafeDTO.getRoom() == 0) {

            int result = JOptionPane.showConfirmDialog(this, "1일권이 맞습니까?", "comfirm", JOptionPane.YES_NO_OPTION,
                  
                  JOptionPane.QUESTION_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
               card.show(menuP, "Day");

            } else
               JOptionPane.showMessageDialog(this, "다시 선택해주세요.");
            setVisible(false);
            // 좌석이 있고, 잔여 시간이 있을 떄
         }else {
            JOptionPane.showMessageDialog(this, "이미 좌석이 존재합니다.");

              }
         
//         if (cafeDTO.getTotRtime() != 0 || cafeDTO.getTotStime() != 0) {
//            JOptionPane.showMessageDialog(this, "이미 사용 중 입니다.");
//                 card.show(menuP, "DW");
//         }


      }
      // 정기권
      else if (e.getSource() == weekButton) { // 정기권은 회원가입한 사람. 1인좌석만 가능.

         if (cafeDTO.getCode() == 1) {

            if (sitList.getSelectSit() != 0 && sitList.getSelectRoom() == 0) { // 좌석이 선택되었을 때 .

               int result = JOptionPane.showConfirmDialog(this, "정기권이 맞습니까?", "comfirm", JOptionPane.YES_NO_OPTION,

                     JOptionPane.QUESTION_MESSAGE);

               if (result == JOptionPane.YES_OPTION) {
                  card.show(menuP, "Week");
                  
               } else
                  JOptionPane.showMessageDialog(this, "다시 선택해주세요.");

            } else if (sitList.getSelectRoom() != 0) {
               JOptionPane.showMessageDialog(this, "정기권은 1인 좌석만 선택 가능합니다.");
            }
         } // if
         else {
            JOptionPane.showMessageDialog(this, "정기권은 회원만 이용 가능합니다.");

         }
      }
      // 이전으로
      else if (e.getSource() == returnButton) {
         sitList.reset();
         this.setVisible(false);
      }
      // 시간연장
      else if (e.getSource() == extendTime) {
         extendSitRoomTime();
      }

   }// action

   // 회원 - 1일 , 정기권 데이위크 이 사람이 좌석이 없을 떄 . 시간연장은 있을 떄 결제 후 setV

   // 시간연장
   private void extendSitRoomTime() {

      // 이용시간 남아있을 때만 연장가능

      if (cafeDTO.getTotRtime() != 0 || cafeDTO.getTotStime() != 0) {

         // 1인실이니? ( yes? no? result 값에 따라 1인석, 룸 분리 )
         int result = JOptionPane.showConfirmDialog

         (this, "1시간 연장하시겠습니까?", "좌석 종류 확인창", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if (cafeDTO.getSit() != 0 && cafeDTO.getRoom() == 0) {//좌석이 존재한다면
            // yes == 1인좌석
            if (result == JOptionPane.YES_OPTION) {
               // 200시간있을떄 연장누르면 1시간으로 스레드가 시간이 바뀜.
               extendSitTime();// db 로 . DAO메소드는 extendSTime
            }
            // no == 룸좌석
            else if (result == JOptionPane.NO_OPTION) {
            	card.show(menuP, "DW");
               //setVisible(false);
            }
         } else if (cafeDTO.getRoom() != 0 && cafeDTO.getSit() == 0) { //룸이 존재한다면
            if (result == JOptionPane.YES_OPTION) {
               // 200시간있을떄 연장누르면 1시간으로 스레드가 시간이 바뀜.
               extendRoomTime();// db 로 . DAO메소드는 extendSTime
            }
            // no == 룸좌석
            else if (result == JOptionPane.NO_OPTION) {
            	card.show(menuP, "DW");
               //setVisible(false);
            }
         } else if (cafeDTO.getSit() == 0 || cafeDTO.getRoom() == 0) {
            JOptionPane.showMessageDialog(this, "좌석 결제 후 연장 가능합니다.");
            return;
         }
      }
   }// extendTime()

   private void extendSitTime() { // 시간연장시바뀌는건 시간만.시간만.

      CafeDAO cafeDAO = CafeDAO.getInstance();

      cafeDTO.setTime(cafeDTO.getTime() + time);
      cafeDTO.setTotStime(cafeDTO.getTotStime() + hour);
      cafeDTO.setTotRtime(0);
      cafeDTO.setPayment(cafeDTO.getPayment() + extendCost);

      int su = cafeDAO.updateInSR(cafeDTO);

      if (su == 0) {// 0==실패
         JOptionPane.showMessageDialog(this, "1인석 1시간 연장에 실패했습니다.");
      } else {
         JOptionPane.showMessageDialog(this, "1인석 1시간 연장에 성공하였습니다.");
         sitList.reset();
      }
   } // extendRoomTime()

   private void extendRoomTime() { // 시간연장시바뀌는건 시간만.시간만.

      CafeDAO cafeDAO = CafeDAO.getInstance();

      cafeDTO.setTotStime(0);
      cafeDTO.setTotRtime(cafeDTO.getTotRtime() + hour);
      cafeDTO.setTime(cafeDTO.getTime() + time);
      cafeDTO.setPayment(cafeDTO.getPayment() + extendCost);

      int su = cafeDAO.updateInSR(cafeDTO);

      if (su == 0) {// 0==실패
         JOptionPane.showMessageDialog(this, "룸 1시간 연장에 실패했습니다.");
      } else {
         JOptionPane.showMessageDialog(this, "룸 1시간 연장에 성공하였습니다.");
         sitList.reset();
      }
   }
}