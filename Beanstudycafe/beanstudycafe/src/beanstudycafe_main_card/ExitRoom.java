package beanstudycafe_main_card;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;
import beanstudycafe_main_sit.SitList;

public class ExitRoom extends JFrame implements ActionListener {

   private JLabel sitNumber, sitNumber2;
   private JButton yesBtn, noBtn;
   private CafeDTO cafeDTO;
   private String number;
   private SitList sitList;
   private Locker locker;
   private CardLayout card;
   private JPanel menuP;

   public ExitRoom(CafeDTO cafeDTO, String Number, SitList sitList, Locker locker, CardLayout card, JPanel menuP) {//
      this.number = Number;
      this.cafeDTO = cafeDTO;
      this.sitList = sitList;
      this.locker = locker;
      this.card = card;
      this.menuP = menuP;

      setLayout(null);

      Font font = new Font("LH B", Font.BOLD, 30);
      Font fontB = new Font("LH B", Font.BOLD, 40);

      sitNumber = new JLabel(Number + "번 퇴실");
      sitNumber.setBounds(130, 50, 400, 50);
      sitNumber.setForeground(Color.WHITE);
      sitNumber.setFont(fontB);

      sitNumber2 = new JLabel("하시겠습니까?");
      sitNumber2.setBounds(130, 150, 400, 50);
      sitNumber2.setForeground(Color.WHITE);
      sitNumber2.setFont(fontB);

      yesBtn = new JButton("확인");
      yesBtn.setBounds(100, 250, 100, 70);
      yesBtn.setFont(font);
      yesBtn.setBackground(Color.DARK_GRAY);
      yesBtn.setForeground(Color.white);

      noBtn = new JButton();
      noBtn = new JButton("취소");
      noBtn.setBounds(300, 250, 100, 70);
      noBtn.setFont(font);
      noBtn.setBackground(Color.DARK_GRAY);
      noBtn.setForeground(Color.white);

      Container container = this.getContentPane();

      container.add(sitNumber);
      container.add(sitNumber2);
      container.add(yesBtn);
      container.add(noBtn);

      yesBtn.addActionListener(this);
      noBtn.addActionListener(this);

      yesBtn.setOpaque(true);
      yesBtn.setBorderPainted(false);
      yesBtn.setFocusPainted(false);

      noBtn.setOpaque(true);
      noBtn.setBorderPainted(false);
      noBtn.setFocusPainted(false);

      setTitle("퇴실창");
      container.setBackground(new Color(51, 51, 51));
      setBounds(550, 250, 500, 400);
      setVisible(true);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == yesBtn) {// 확인 버튼을 누르면
         updateDTO();
         JOptionPane.showMessageDialog(this, "퇴실하셨습니다.");
         locker.reset();

         card.show(menuP, "EnP");
         // 사물함도 정기권을 이용하는 사람이면 비S워주자
         // 퇴실되면 모든창이 꺼진다 //그니까 로그아웃된다

      }
      if (e.getSource() == noBtn) {// 취소 버튼을 누르면
         this.setVisible(false);
      }

   }

   private void updateDTO() {
//      디티오만 바뀌게
//      마지막에 전체업데이트만 하면 쭉바뀌게
      CafeDAO cafeDAO = CafeDAO.getInstance();
      SRLDAO srlDAO = SRLDAO.getInstance();

      if (number.equals("룸 " + cafeDTO.getRoom())) {
         double intime = cafeDTO.getIntime();
         SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
         String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
         double outTime = Double.parseDouble(currentTimeTest);

         double time = outTime - intime;

         SRLDTO srlDTO = srlDAO.getList();
         srlDTO.getRoomCheck()[cafeDTO.getRoom() - 1] = false;
      
         if (cafeDTO.getLocker() != 0) {
            
            srlDTO.getLockerCheck()[cafeDTO.getLocker() - 1] = false;
            srlDAO.setRoom(srlDTO);
            srlDAO.setLocker(srlDTO);
         }

         cafeDTO.setRoom(0);
         cafeDTO.setOuttime(outTime);
         cafeDTO.setTotRtime(0);
         cafeDTO.setLocker(0);

         cafeDAO.updateOutSR(cafeDTO);

         sitList.reset();
         setVisible(false);
         // 아웃타임
      } else {
         double intime = cafeDTO.getIntime();
         SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
         String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
         double outTime = Double.parseDouble(currentTimeTest);
         double time = outTime - intime;

         SRLDTO srlDTO = srlDAO.getList();
         srlDTO.getSitCheck()[cafeDTO.getSit() - 1] = false;

         // 락커가 있을 떄만 동작
         if (cafeDTO.getLocker() != 0) {
            srlDTO.getLockerCheck()[cafeDTO.getLocker() - 1] = false;
            srlDAO.setSit(srlDTO);
            srlDAO.setLocker(srlDTO);
         }
         // 아무것도없는 계쩡으로 로그인 정기권 50시간, 락커가 없는 상탱서 퇴실

         cafeDTO.setSit(0);
         cafeDTO.setOuttime(outTime);
         
         if (cafeDTO.getCode() == 1) {
            cafeDTO.setTotStime(cafeDTO.getTotStime() - time);
         
         double min = cafeDTO.getTotStime() - time;
         double temp = min%1;
         
         if(temp>=0.6) {
            temp = temp-0.4;
         }
         double totS = (int)min+temp;
         cafeDTO.setTotStime(totS);
         
         
         } else {
            cafeDTO.setTotStime(0);
         }
         
         cafeDTO.setLocker(0);
         cafeDAO.updateOutSR(cafeDTO);

         sitList.reset();
         // 아웃타임
         setVisible(false);
      }

   }

}