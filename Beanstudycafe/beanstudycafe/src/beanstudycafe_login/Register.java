package beanstudycafe_login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;

public class Register extends JFrame implements ActionListener {
   private JComboBox phoneComboBox, emailComboBox;
   private JTextField nameT, tel2T, tel3T, mailT;
   private JPasswordField passwordF_1, passwordF_2;
   private JButton signup, regisAgain, mailCheck, passwordCheck, cancle, idCheck;
   private JLabel signUpInfo, nameL, phoneL, mailL, passwordL_1, passwordL_2, dash1, dash2, info;
   private RegisterResult registerResult;
   private ImageIcon image;
//name/phone/password/mail/
   private int mailBC;
   private int sw;
   private boolean idCheckB;
   private boolean pwCheck;

   public Register() {

      setLayout(null);

      signUpInfo = new JLabel("회 원 가 입");
      signUpInfo.setBounds(100, 0, 250, 100);
      signUpInfo.setFont(new Font("LH B", Font.BOLD, 40));

      nameL = new JLabel("이 름");
      nameL.setBounds(42, 100, 50, 30);
      nameL.setFont(new Font("LH B", Font.BOLD, 15));
      nameT = new JTextField();
      nameT.setBounds(110, 100, 200, 30);

      phoneL = new JLabel("아이디");
      phoneL.setBounds(40, 155, 50, 30);
      phoneL.setFont(new Font("LH B", Font.BOLD, 15));

      info = new JLabel("*아이디는 핸드폰 번호 입니다.");
      info.setBounds(40, 195, 200, 10);
      info.setFont(new Font("LH B", Font.BOLD, 10));

      String[] number = { "010", "02", "031" };
      phoneComboBox = new JComboBox<String>(number);
      phoneComboBox.setBounds(110, 155, 50, 30);

      tel2T = new JTextField();
      tel2T.setBounds(175, 155, 60, 30);

      tel3T = new JTextField();
      tel3T.setBounds(250, 155, 60, 30);

      String[] mailUrl = { "@gmail.com", "@naver.com", "@hanmail.net", "@kakao.com", "@yahoo.com" };
      emailComboBox = new JComboBox<String>(mailUrl);
      emailComboBox.setBounds(210, 220, 100, 30);

      dash1 = new JLabel("-");
      dash1.setBounds(165, 157, 20, 30);
      dash1.setFont(new Font("LH B", Font.BOLD, 10));

      dash2 = new JLabel("-");
      dash2.setBounds(240, 157, 20, 30);
      dash2.setFont(new Font("LH B", Font.BOLD, 10));

      mailL = new JLabel("이메일");
      mailL.setBounds(40, 220, 50, 30);
      mailL.setFont(new Font("LH B", Font.BOLD, 15));

      mailT = new JTextField();
      mailT.setBounds(110, 220, 90, 30);

      passwordL_1 = new JLabel("비밀번호");
      passwordL_1.setBounds(40, 275, 100, 30);
      passwordL_1.setFont(new Font("LH B", Font.BOLD, 15));
      passwordF_1 = new JPasswordField();
      passwordF_1.setBounds(110, 275, 200, 30);

      JLabel pwAgain = new JLabel("재입력");
      pwAgain.setBounds(40, 305, 100, 30);
      pwAgain.setFont(new Font("LH B", Font.BOLD, 15));

      passwordL_2 = new JLabel("");
      passwordL_2.setBounds(25, 350, 50, 30);
      passwordL_2.setFont(new Font("LH B", Font.BOLD, 15));
      passwordF_2 = new JPasswordField();
      passwordF_2.setBounds(110, 310, 200, 30);

      signup = new JButton("등록");
      signup.setBounds(50, 370, 90, 40);
      signup.setFont(new Font("LH B", Font.BOLD, 12));

      regisAgain = new JButton("다시등록");
      regisAgain.setBounds(150, 370, 90, 40);
      regisAgain.setFont(new Font("LH B", Font.BOLD, 12));

      cancle = new JButton("이전으로");
      cancle.setBounds(250, 370, 90, 40);
      cancle.setFont(new Font("LH B", Font.BOLD, 12));

      mailCheck = new JButton("인증");
      mailCheck.setBounds(315, 220, 60, 30);
      mailCheck.setFont(new Font("LH B", Font.BOLD, 12));

      passwordCheck = new JButton("확인");
      passwordCheck.setBounds(315, 310, 60, 30);
      passwordCheck.setFont(new Font("LH B", Font.BOLD, 12));

      idCheck = new JButton("확인");
      idCheck.setBounds(315, 155, 60, 30);
      idCheck.setFont(new Font("LH B", Font.BOLD, 12));

//      passwordCheck = new JButton("비밀번호 중복체크");
//      passwordCheck.setBounds(310,280,50,65);
//      passwordCheck.setFont(new Font("맑은고딕", Font.BOLD, 8));

      Container container = this.getContentPane();
      container.add(signUpInfo);
      container.add(nameL);
      container.add(phoneL);
      container.add(mailL);
      container.add(passwordL_1);
      container.add(passwordL_2);

      container.add(phoneComboBox);
      container.add(nameT);
      container.add(tel2T);
      container.add(tel3T);
      container.add(mailT);
      container.add(emailComboBox);

      container.add(passwordF_1);
      container.add(passwordF_2);

      container.add(signup);
      container.add(regisAgain);
      container.add(cancle);
      container.add(mailCheck);
      container.add(dash1);
      container.add(dash2);
      container.add(info);
      container.add(pwAgain);
      container.add(passwordCheck);
      container.add(idCheck);

      signup.setBackground(new Color(51, 51, 51));
      signup.setForeground(Color.WHITE);
      regisAgain.setBackground(new Color(51, 51, 51));
      regisAgain.setForeground(Color.WHITE);
      mailCheck.setBackground(new Color(51, 51, 51));
      mailCheck.setForeground(Color.WHITE);
      passwordCheck.setBackground(new Color(51, 51, 51));
      passwordCheck.setForeground(Color.WHITE);
      cancle.setBackground(new Color(51, 51, 51));
      cancle.setForeground(Color.WHITE);
      idCheck.setBackground(new Color(51, 51, 51));
      idCheck.setForeground(Color.WHITE);

      signUpInfo.setForeground(Color.WHITE);
      nameL.setForeground(Color.WHITE);
      phoneL.setForeground(Color.WHITE);
      mailL.setForeground(Color.WHITE);
      passwordL_1.setForeground(Color.WHITE);
      passwordL_2.setForeground(Color.WHITE);
      dash1.setForeground(Color.WHITE);
      dash2.setForeground(Color.WHITE);
      info.setForeground(Color.WHITE);
      pwAgain.setForeground(Color.WHITE);
      container.setBackground(new Color(51, 51, 51));

      setTitle("회원가입");
      setBounds(800, 200, 410, 500);
      setResizable(false);
      setVisible(true);
      setLocationRelativeTo(null);

      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            setVisible(false);
         }
      });
      tel2T.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });

      tel3T.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });

      setLocationRelativeTo(null);
      signup.addActionListener(this);
      regisAgain.addActionListener(this);
      mailCheck.addActionListener(this);
      cancle.addActionListener(this);
      passwordCheck.addActionListener(this);
      idCheck.addActionListener(this);
   }

   public void remove() {
      nameT.setText("");
      phoneComboBox.setSelectedItem("010");
      emailComboBox.setSelectedItem("@gmail.com");
      // gmail로만 ?
      tel2T.setText("");
      tel3T.setText("");
      mailT.setText("");
      passwordF_1.setText("");
      passwordF_2.setText("");
   }

   @Override
   public void actionPerformed(ActionEvent e) {

      if (e.getSource() == signup) {// 등록
         int sw = signupInput();

         if (sw == 0) {
            remove();
            return;
         }

         if (mailBC == 1) {
            if (registerResult.getSW() == 0) {
               JOptionPane.showMessageDialog(this, "메일 인증을 완료 바랍니다.");
               remove();
               return;
            } else if (registerResult.getSW() == 1) {
               if (pwCheck && idCheckB) {
                  int result = JOptionPane.showConfirmDialog(this, "등록하시겠습니까?", "회원가입", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                  if (result == JOptionPane.YES_OPTION) {
                     insertData();
                     setVisible(false);
                  }
               } else {
                  JOptionPane.showMessageDialog(this, "비밀번호를 확인 바랍니다."); //아이디와
                  passwordF_1.setText("");
                  passwordF_2.setText("");
               }
            }
         } 
         else {
            JOptionPane.showMessageDialog(this, "메일 인증을 해주세요.");
         }

      } else if (e.getSource() == regisAgain) {// 초기화
         remove();

      } else if (e.getSource() == mailCheck) {
         mailBC = 1;
         // 인증번호를 전송하였씁니다.
         SendMail sendMail = new SendMail((mailT.getText() + (String) emailComboBox.getSelectedItem()));
         emailCheck(sendMail);

      } else if (e.getSource() == passwordCheck) {
         
         if (passwordF_1.getText().length() != 0 && passwordF_2.getText().length() != 0) {
            if (passwordF_1.getText().equals(passwordF_2.getText())) {
               JOptionPane.showMessageDialog(this, "비밀번호 확인 완료 했습니다.");
               pwCheck = true;
            } else {
               JOptionPane.showMessageDialog(this, "비밀번호 확인 바랍니다.");
               pwCheck = false;
            }
         } else if (passwordF_1.getText().length() == 0 || passwordF_2.getText().length() == 0)
            JOptionPane.showMessageDialog(this, "다시 입력하세요.");
      } else if (e.getSource() == cancle) {
         setVisible(false);
      } else if (e.getSource() == idCheck) {
         CafeDAO cafeDAO = CafeDAO.getInstance();
         CafeDTO cafeDTO = cafeDAO.selectTel((phoneComboBox.getSelectedItem() + tel2T.getText() + tel3T.getText()));
         
         if (tel2T.getText().length() == 4 && tel3T.getText().length() == 4) {
            
            if (cafeDTO == null) {
               JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
               idCheckB = true;
               
            } else {
               JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
               idCheckB = false;
            }

         } else {
            JOptionPane.showMessageDialog(this, "핸드폰 번호를 4자리로 입력하세요.");
            tel2T.setText("");
            tel3T.setText("");
            
         }

      }//
   }

   private void emailCheck(SendMail sendMail) {
      registerResult = new RegisterResult(sendMail.getCode());
   }

   private int signupInput() {

      String pwd1 = passwordF_1.getText();
      String pwd2 = passwordF_2.getText();
      String inputMessage = null;
      int sw = 0;

      if (nameT.getText().length() == 0 || tel2T.getText().length() == 0 || tel3T.getText().length() == 0
            || mailT.getText().length() == 0 || passwordF_1.getText().length() == 0
            || passwordF_2.getText().length() == 0) {
         JOptionPane.showMessageDialog(this, "빈칸을 채워주세요.");
         return sw;
      }

      else {
         if (nameT.getText().length() > 4) { // setColumns 로 5자리 지정?
            JOptionPane.showMessageDialog(this, "이름을 5자리 미만으로 입력하세요.");
            nameT.setText("");
            return sw;

         }
         if (tel2T.getText().length() != 4) {
            JOptionPane.showMessageDialog(this, "핸드폰번호를 4자리로 입력하세요.");
            tel2T.setText("");
            tel3T.setText("");
            return sw;

         }
         if (tel3T.getText().length() != 4) {
            JOptionPane.showMessageDialog(this, "핸드폰번호를 4자리로 입력하세요.");
            tel2T.setText("");
            tel3T.setText("");
            return sw;

         }
         if (mailT.getText().length() > 29) {
            JOptionPane.showMessageDialog(this, "이메일을 30자리 미만으로 입력하세요.");
            mailT.setText("");
            return sw;

         }
      }
      sw = 1;
      return sw;

   }

   private void insertData() {// db

      // code name tel mail password totTime payment sit room locker
      String combo = (String) phoneComboBox.getSelectedItem();

      String name = nameT.getText();
      String tel = (combo + tel2T.getText() + tel3T.getText());
      String mail = (mailT.getText() + (String) emailComboBox.getSelectedItem());
      String password = passwordF_1.getText();

      CafeDAO cafeDAO = CafeDAO.getInstance();

      int seq = cafeDAO.getSeq();

      CafeDTO cafeDTO = new CafeDTO();

      cafeDTO.setSeq(seq);
      cafeDTO.setCode(1);
      cafeDTO.setName(name);
      cafeDTO.setTel(tel);
      cafeDTO.setMail(mail);
      cafeDTO.setPassword(password);
      cafeDTO.setTotStime(0);
      cafeDTO.setTotRtime(0);
      cafeDTO.setPayment(0);
      cafeDTO.setSit(0);
      cafeDTO.setRoom(0);
      cafeDTO.setLocker(0);

      int su = cafeDAO.insertCafe(cafeDTO);

      if (su == 0) {
         JOptionPane.showMessageDialog(this, "서버 접속에 실패하였습니다.");
      } else {
         JOptionPane.showMessageDialog(this, "회원가입에 성공하셨습니다.");
      }
   }

   // int seq = _____.getSeq(); seq받아오기.
   public String getMail() {
      return (mailT.getText() + (String) emailComboBox.getSelectedItem());
   }

//   public static void main(String[] args) {
//      new Register();
//   }
}