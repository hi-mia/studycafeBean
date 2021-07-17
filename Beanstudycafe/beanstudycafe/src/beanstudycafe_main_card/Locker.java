package beanstudycafe_main_card;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

class Lockerpayment extends JFrame implements ActionListener {

   private JLabel paymentwindow, checkSitNum, checkPhoneNum;
   private JLabel sitNum, phoneNum;
   private JButton payment, cancle;
   private int selectLocker;
   private CafeDTO cafeDTO;
   private Locker locker;
   private SitList sitList;

   public Lockerpayment(int selectLocker, CafeDTO cafeDTO, Locker locker ,SitList sitList) {
      this.selectLocker = selectLocker;
      this.cafeDTO = cafeDTO;
      this.locker = locker;
      this.sitList = sitList;
      
      setLayout(null);

      Font font = new Font("LH B", Font.BOLD, 15);
      Font font2 = new Font("LH B", Font.BOLD, 15);

      paymentwindow = new JLabel("사물함 신청");
      paymentwindow.setBounds(100, 2, 200, 40);
      paymentwindow.setFont(font2);
      paymentwindow.setHorizontalAlignment(JLabel.CENTER);
      paymentwindow.setForeground(Color.white);

      checkSitNum = new JLabel("선택한 사물함 :");
      checkSitNum.setBounds(30, 50, 100, 60);
      checkSitNum.setFont(font2);
      checkSitNum.setForeground(Color.white);

      checkPhoneNum = new JLabel("핸드폰 번호 :");
      checkPhoneNum.setBounds(30, 120, 100, 60);
      checkPhoneNum.setFont(font2);
      checkPhoneNum.setForeground(Color.white);

      // ------------------------------------------------
      CafeDTO cafedto = new CafeDTO();

      sitNum = new JLabel("사물함 " + selectLocker);// 수정해야할부분
      sitNum.setBounds(150, 50, 100, 60);
      sitNum.setFont(font);
      sitNum.setForeground(Color.white);

      phoneNum = new JLabel(cafeDTO.getTel());// 수정해야할부분
      phoneNum.setBounds(150, 120, 200, 60);
      phoneNum.setFont(font);
      phoneNum.setForeground(Color.white);

      payment = new JButton("신청 진행");// 수정해야할부분
      payment.setBounds(30, 190, 150, 40);
      payment.setFont(font2);
      payment.setBackground(Color.black);
      payment.setForeground(Color.white);

      cancle = new JButton("신청 취소");// 수정해야할부분
      cancle.setBounds(220, 190, 150, 40);
      cancle.setFont(font2);
      cancle.setBackground(Color.black);
      cancle.setForeground(Color.white);

      Container con = this.getContentPane();

      con.add(paymentwindow);
      con.add(checkSitNum);
      con.add(checkPhoneNum);
      con.add(payment);
      con.add(sitNum);
      con.add(phoneNum);
      con.add(cancle);

      payment.addActionListener(this);
      cancle.addActionListener(this);
      
      con.setBackground(new Color(51, 51, 51));
      setBounds(300, 100, 400, 320);
      setBackground(Color.white);
      setVisible(true);

      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            setVisible(false);
         }
      });
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == payment) {
         CafeDAO cafeDAO = CafeDAO.getInstance();
         SRLDAO srlDAO = SRLDAO.getInstance();
         SRLDTO srlDTO = srlDAO.getList();
         
         cafeDTO.setLocker(selectLocker);
         
         cafeDAO.updateLocker(cafeDTO);
         
         srlDTO.getLockerCheck()[selectLocker - 1] = true;
         
         srlDAO.setLocker(srlDTO);
         
         JOptionPane.showMessageDialog(this, "신청 완료되었습니다.");
         locker.reset();
         sitList.reset();
         setVisible(false);
      }else if(e.getSource() == cancle){
         JOptionPane.showMessageDialog(this, "신청 취소되었습니다.");
         locker.reset();
         sitList.reset();
         setVisible(false);
      }
   }
}

public class Locker extends JPanel implements ActionListener {

   // 사물함..;
   private JButton locker;
   private JLabel lockerTitle, chooseLocker;
   private JButton[] lockerBTN = new JButton[10];
   private JButton check = new JButton("선택");
   private SRLDTO srlDTO;
   private int emptyLocker;
   private int selectLocker;
   private CardLayout card;
   private JPanel menuP;
   private CafeDTO cafeDTO;
   private SitList sitList;

   public Locker(CardLayout card, JPanel menuP, CafeDTO cafeDTO,SitList sitList) {
      this.card = card;
      this.menuP = menuP;
      this.cafeDTO = cafeDTO;
      this.sitList = sitList;
      
      setLayout(null);

      Font font = new Font("LH B", Font.BOLD, 40);
      Font font2 = new Font("LH B", Font.BOLD, 15);

      lockerTitle = new JLabel("사물함");
      lockerTitle.setBounds(165, 25, 250, 40);
      lockerTitle.setFont(font);
      lockerTitle.setHorizontalAlignment(JLabel.CENTER);
      lockerTitle.setForeground(Color.white);

      check.setFont(font2);
      check.setHorizontalAlignment(JLabel.CENTER);
      check.setBackground(new Color(51, 51, 51));
      check.setForeground(Color.white);

      SRLDAO srlDAO = SRLDAO.getInstance();

      srlDTO = srlDAO.getList();

      for (int i = 0; i < lockerBTN.length; i++) {
         lockerBTN[i] = new JButton("사물함" + (i + 1));
         lockerBTN[i].setFont(new Font("LH B", Font.BOLD, 9));
         lockerBTN[i].setBackground(new Color(51, 51, 51));
         lockerBTN[i].setForeground(Color.WHITE);
         lockerBTN[i].setFocusable(false);
      }

      for (int i = 0; i < 10; i++) {
         if (srlDTO.getLockerCheck()[i]) {
            lockerBTN[i].setEnabled(false);
            lockerBTN[i].setBackground(Color.BLACK);
         } else {
            emptyLocker++;
            lockerBTN[i].setEnabled(true);
            lockerBTN[i].setBackground(new Color(51, 51, 51));
         }
      }

      lockerBTN[0].setBounds(20, 100, 100, 80);
      lockerBTN[1].setBounds(130, 100, 100, 80);
      lockerBTN[2].setBounds(240, 100, 100, 80);
      lockerBTN[3].setBounds(350, 100, 100, 80);
      lockerBTN[4].setBounds(460, 100, 100, 80);
      lockerBTN[5].setBounds(20, 190, 100, 80);
      lockerBTN[6].setBounds(130, 190, 100, 80);
      lockerBTN[7].setBounds(240, 190, 100, 80);
      lockerBTN[8].setBounds(350, 190, 100, 80);
      lockerBTN[9].setBounds(460, 190, 100, 80);
      check.setBounds(460, 30, 100, 50);

      // 마우스이벤트 클릭된 버튼을 선택시 = 해당 버튼 event
      add(check);
      add(lockerTitle);

      for (int i = 0; i < lockerBTN.length; i++) {
         add(lockerBTN[i]);
      } // for

      setBackground(new Color(51, 51, 51));
      setBounds(100, 0, 590, 330);
      setVisible(true);

      check.addActionListener(this);
      for (int i = 0; i < lockerBTN.length; i++) {
         lockerBTN[i].addActionListener(this);
      } // for

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      SRLDAO srlDAO = SRLDAO.getInstance();
      srlDTO = srlDAO.getList();
      
      CafeDAO cafeDAO = CafeDAO.getInstance();
      
   
      reset();
      
      if (e.getSource() != check) {
         for (int i = 0; i < lockerBTN.length; i++) {
            if (e.getSource() == lockerBTN[i]) {
               if(srlDTO.getLockerCheck()[i]) {
                  JOptionPane.showMessageDialog(this, "이미 사용 중인 사물함 입니다.");
                  reset();
               }else {
               selectLocker = i + 1;
               lockerBTN[i].setBackground(Color.WHITE);
               lockerBTN[i].setForeground(Color.BLACK);
               }
            }
         } // for
         
      } else if (e.getSource() == check) {
         if (cafeDTO.getLocker() == 0) {
            if (selectLocker != 0) {
               new Lockerpayment(selectLocker, cafeDTO, this, sitList);
               selectLocker = 0;
            } else {
               JOptionPane.showMessageDialog(this, "사물함을 먼저 선택해주세요.");
            }
         } else {
            JOptionPane.showMessageDialog(this, "1인 1사물함만 허용합니다.");
         }
      }
   }

   public void reset() {
      SRLDAO srlDAO = SRLDAO.getInstance();
      srlDTO = srlDAO.getList();

      for (int i = 0; i < 10; i++) {
         if (srlDTO.getLockerCheck()[i]) {
            lockerBTN[i].setEnabled(false);
            lockerBTN[i].setBackground(Color.BLACK);
            lockerBTN[i].setForeground(Color.WHITE);
         } else {
            emptyLocker++;
            lockerBTN[i].setEnabled(true);
            lockerBTN[i].setBackground(new Color(51, 51, 51));
            lockerBTN[i].setForeground(Color.WHITE);
         }
      }
   }

//   public static void main(String[] args) {
//
//   }

}