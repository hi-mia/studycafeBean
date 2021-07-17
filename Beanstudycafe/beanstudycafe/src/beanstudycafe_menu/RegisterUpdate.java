package beanstudycafe_menu;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;

public class RegisterUpdate extends JFrame implements ActionListener {

   private JLabel nameL, phoneL,phoneL2,phoneL3,pwdL;
   private JTextField nameT, tel2T, tel3T;
   private JComboBox phoneComboBox;
   private JPasswordField pwdT;
   private JButton loginBtn, resetBtn;
   private Font font = new Font("LH B", Font.BOLD, 15);
   private CafeDTO cafeDTO;
   
   public RegisterUpdate(CafeDTO cafeDTO) {
      this.cafeDTO = cafeDTO;
//---------------------------------------------------------------
      setLayout(null);
      
      nameL = new JLabel("이    름");
      nameL.setBounds(40,30,100,40);
      nameL.setFont(font);
      nameL.setForeground(Color.WHITE);
      
      nameT = new JTextField();
      nameT.setBounds(120, 40, 210, 25);
      
      phoneL =new JLabel("전화번호");
      phoneL.setBounds(40,70,100,40);
      phoneL.setFont(font);
      phoneL.setForeground(Color.WHITE);
      String[]number = {"010","02","031"};
      phoneComboBox =new JComboBox<String>(number);
      phoneComboBox.setBounds(120, 80, 60, 25);
      
      phoneL2 = new JLabel("-");
      phoneL2.setBounds(185, 80, 50, 25);
      phoneL2.setFont(font);
      phoneL2.setForeground(Color.WHITE);
      tel2T = new JTextField();
      tel2T.setBounds(200, 80, 50, 25);
     
      
      phoneL3 = new JLabel("-");      
      phoneL3.setBounds(260, 80, 50, 25);
      phoneL3.setFont(font);
      phoneL3.setForeground(Color.WHITE);
      tel3T = new JTextField();
      tel3T.setBounds(280, 80, 50, 25);
      
      pwdL = new JLabel("비밀번호");
      pwdL.setBounds(40,110,100,40);
      pwdL.setFont(font);
      pwdL.setForeground(Color.WHITE);
      pwdT = new JPasswordField();
      pwdT.setBounds(120, 120, 210, 25);
      
      loginBtn = new JButton("확인");
      loginBtn.setBounds(70, 170, 90, 50);
      loginBtn.setFont(font);
      loginBtn.setBackground(new Color(51, 51, 51));
      loginBtn.setForeground(Color.WHITE);
      resetBtn = new JButton("다시 작성");
      resetBtn.setBounds(210, 170, 110, 50);
      resetBtn.setFont(font);
      resetBtn.setBackground(new Color(51, 51, 51));
      resetBtn.setForeground(Color.WHITE);
   
      //컨테이너
      Container c = this.getContentPane();
      
      c.setBackground(new Color(51, 51, 51));
      
      c.add(nameL);
      c.add(nameT);
      c.add(phoneComboBox);
      c.add(phoneL);
      c.add(phoneL2);
      c.add(tel2T);
      c.add(phoneL3);
      c.add(tel3T);
      c.add(pwdL);
      c.add(pwdT);
      c.add(loginBtn);
      c.add(resetBtn);
      
      setTitle("로그인 수정창");
      setFont(new Font("돋움체",Font.BOLD,16));
      setVisible(true);
      setBounds(300,200,400,300);
      setResizable(false);
      
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            setVisible(false);
         }  
        });
      
//--------------------------------------------------------------------------------------------------전화번호 숫자만 입력하게   
      
      tel2T.addKeyListener(new KeyAdapter() {//숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });
      

      tel3T.addKeyListener(new KeyAdapter() {//숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });
      
      event();
      
   }//RegisterUpdate()
   
   public void event() {
      loginBtn.addActionListener(this);
      resetBtn.addActionListener(this);
      
   }//event()
   
   
   @Override
   public void actionPerformed(ActionEvent e) {

      if (e.getSource() == loginBtn) {// 확인 버튼 누르면
    	  
    	// 빈칸검사
          if (nameT.getText().length() == 0 || tel2T.getText().length() == 0 || tel3T.getText().length() == 0
                || pwdT.getText().length() == 0) {
             JOptionPane.showMessageDialog(this, "빈칸을 채워주세요.");
             return;
             
          } else {// 채워져있다면
             if (tel2T.getText().length() > 4 || tel3T.getText().length() > 4) {// 전화번호 4자리까지만 허용
                JOptionPane.showMessageDialog(this, "핸드폰번호 4자리 이하로 치시오", "오류", JOptionPane.ERROR_MESSAGE);
                tel2T.setText("");
                tel3T.setText("");
                return;
                
             } else if (nameT.getText().length() > 4) {
                JOptionPane.showMessageDialog(this, "이름을 5자리 미만으로 입력하세요.");
                nameT.setText("");
                return;
             }
          }
          
    	  
         int result = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정 확인창", JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);

         if (result == JOptionPane.YES_OPTION) {// yes버튼을 누르면
        	 String tel = cafeDTO.getTel(); //원본 전화번호 - updateCafe에 tel을 넣어줌
             updateCafe(tel);
             setVisible(false);

         } else if (result == JOptionPane.NO_OPTION) {// no버튼을 누르면 비워주기
            nameT.setText("");
            phoneComboBox.setSelectedItem("010");
            tel2T.setText("");
            tel3T.setText("");
            pwdT.setText("");
         }
      } else if (e.getSource() == resetBtn) { // 다시 작성 버튼을 누르면 다비워주기
         nameT.setText("");
         phoneComboBox.setSelectedItem("010");
         tel2T.setText("");
         tel3T.setText("");
         pwdT.setText("");
      }

   }// actionPerformed(ActionEvent e)


   private void updateCafe(String tel) {
      
      //데이터를 가지고 와라
      String name = nameT.getText();
      String updateTel = (String)phoneComboBox.getSelectedItem()+
                        tel2T.getText()+
                        tel3T.getText();
      String password = pwdT.getText();
      
      CafeDAO cafeDAO = CafeDAO.getInstance();
      
      CafeDTO cafeDTO = new CafeDTO();
      cafeDTO.setName(name);
      cafeDTO.setTel(updateTel);
      cafeDTO.setPassword(password);
      
      int su = cafeDAO.updateCafe(cafeDTO, tel); //수정된 tel임
      
      if(su == 1) { //줄이 수정됨
    	  JOptionPane.showMessageDialog(this, "수정완료했습니다.");
    	  setVisible(false);
      }else JOptionPane.showMessageDialog(this, "수정실패했습니다.");
	      nameT.setText("");
	      phoneComboBox.setSelectedItem("010");
	      tel2T.setText("");
	      tel3T.setText("");
	      pwdT.setText("");
      
   }

//public static void main(String[] args) {
//         new RegisterUpdate().event();
//      }//main

   
}//class