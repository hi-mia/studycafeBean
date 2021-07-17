package beanstudycafe_login;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import beanstudycafe_chat.CafeServer;
import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_menu.MainP;

public class Login extends JPanel implements ActionListener {

   private JLabel idL, passwordL, telL, idDash1, idDash2, dash1, dash2, mailL;
   private JTextField idTel2T, idTel3T, tel2T, tel3T, mailT;
   private JPasswordField passwordT;
   private JRadioButton member, guest;
   private JButton loginBtn, registerBtn, mailBtn, gLoginBtn;
   private CardLayout card;
   private JPanel loginP, memberP, guestP;
   private JComboBox idComboBox, phoneComboBox, emailComboBox;
   private ImageIcon image;
   private StartP startP;
   private RegisterResult registerResult;
   private int mailBC; //메일버튼체크
   private boolean check;

   public Login(StartP startP) {
      
      this.startP = startP;
      image = new ImageIcon("image/room00.jpg");

      Image img = image.getImage();
      Image changeImg = img.getScaledInstance(600, 800, Image.SCALE_SMOOTH);
//      //ImageIcon changeIcon = new ImageIcon(changeImg);
//      //imageL = new JLabel(new ImageIcon(changeImg));
//      //imageL.setBounds(0, 0, 600, 800);

      JPanel background = new JPanel(null) {
         @Override
         protected void paintComponent(Graphics g) {
            g.drawImage(image.getImage(), 0, 0, 600, 800, 0, 0, image.getIconWidth(), image.getIconHeight(),
                  Login.this);
            setOpaque(false);
            super.paintComponent(g);
         }
      };

      setLayout(new BorderLayout());

      Font font = new Font("LH B", Font.BOLD, 20);
      Font font2 = new Font("LH B", Font.BOLD, 13);

      // --------회원
      idL = new JLabel("아  이  디");
      idL.setFont(font);
      idL.setBackground(Color.BLACK);
      idL.setForeground(Color.WHITE);
      String[] number1 = { "010", "02", "031" };
      idComboBox = new JComboBox<String>(number1);
      idComboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));
      idDash1 = new JLabel("-");
      idTel2T = new JTextField("");
      idDash2 = new JLabel("-");
      idTel3T = new JTextField(""); // ID부분 추가할거 생성함

      passwordL = new JLabel("비 밀 번 호");
      passwordL.setFont(font);
      passwordL.setBackground(Color.BLACK);
      passwordL.setForeground(Color.WHITE);

      passwordT = new JPasswordField("");

      member = new JRadioButton("회  원", true);
      member.setFont(font);
      member.setBounds(163, 370, 100, 40);
      member.setBackground(Color.BLACK);
      member.setForeground(Color.WHITE);

      guest = new JRadioButton("비회원");
      guest.setFont(font);
      guest.setBounds(348, 370, 100, 40);
      guest.setBackground(Color.BLACK);
      guest.setForeground(Color.WHITE);

      ButtonGroup btng = new ButtonGroup();
      btng.add(member);
      btng.add(guest);

      // ------비회원
      telL = new JLabel("아이디(핸드폰)"); // 비회원용 아이디
      telL.setFont(font);
      telL.setForeground(Color.WHITE);

      dash1 = new JLabel("-");
      dash2 = new JLabel("-");

      String[] number2 = { "010", "02", "031" };
      phoneComboBox = new JComboBox<String>(number2);
      phoneComboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));

      tel2T = new JTextField();
      tel3T = new JTextField();

      mailL = new JLabel("이    메    일");
      mailL.setFont(font);
      mailL.setForeground(Color.WHITE);

      mailT = new JTextField();
      String[] mailUrl = { "@gmail.com", "@naver.com", "@hanmail.net", "@kakao.com", "@yahoo.com" };
      emailComboBox = new JComboBox<String>(mailUrl); // 이메일 생성함

      // -------버튼
      loginBtn = new JButton("로그인");
      loginBtn.setFont(font);
      loginBtn.setBackground(Color.BLACK);
      loginBtn.setForeground(Color.WHITE);

      registerBtn = new JButton("회원가입");
      registerBtn.setFont(font);
      registerBtn.setBackground(Color.BLACK);
      registerBtn.setForeground(Color.WHITE);

      mailBtn = new JButton("인증");
      mailBtn.setFont(font2);
      mailBtn.setBackground(Color.BLACK);
      mailBtn.setForeground(Color.WHITE);

      gLoginBtn = new JButton("비회원 로그인");
      gLoginBtn.setFont(font);
      gLoginBtn.setBackground(Color.BLACK);
      gLoginBtn.setForeground(Color.WHITE);

      // 카드 레이아웃
      card = new CardLayout();
      loginP = new JPanel();
      loginP.setLayout(card);
      loginP.setBounds(50, 430, 450, 250);

      // 셋널 + 각각 좌표 정해주기
      // ------------회원 패널
      memberP = new JPanel();
      memberP.setLayout(null);
      memberP.add(idL);
      idL.setBounds(42, 5, 120, 30);
      memberP.add(idComboBox);
      idComboBox.setBounds(152, 6, 80, 30);
      memberP.add(idDash1);
      idDash1.setBounds(237, 6, 60, 30);
      idDash1.setForeground(Color.WHITE);
      memberP.add(idTel2T);// 위치정하기
      idTel2T.setBounds(246, 6, 80, 30);
      memberP.add(idDash2);
      idDash2.setBounds(331, 6, 60, 30);
      idDash2.setForeground(Color.WHITE);
      memberP.add(idTel3T);
      idTel3T.setBounds(340, 6, 80, 30);

      memberP.add(passwordL);
      passwordL.setBounds(42, 60, 120, 30);
      memberP.add(passwordT);
      passwordT.setBounds(152, 62, 270, 30);
      memberP.add(loginBtn);
      loginBtn.setBounds(42, 125, 140, 50);
      memberP.add(registerBtn);
      registerBtn.setBounds(280, 125, 140, 50);

      loginP.add(memberP, "MP");
      card.show(loginP, "MP");

      // ------------------------비회원 패널
      guestP = new JPanel();
      guestP.setLayout(null);
      guestP.add(telL);
      telL.setBounds(14, 5, 200, 30);
      guestP.add(phoneComboBox);
      phoneComboBox.setBounds(152, 6, 80, 30);
      guestP.add(tel2T);
      tel2T.setBounds(250, 6, 80, 30);
      guestP.add(dash1);
      dash1.setBounds(240, 6, 60, 30);
      dash1.setForeground(Color.WHITE);
      guestP.add(dash2);
      dash2.setBounds(338, 6, 60, 30);
      dash2.setForeground(Color.WHITE);
      guestP.add(tel3T);
      tel3T.setBounds(350, 6, 80, 30);

      guestP.add(mailL); // 메일
      mailL.setBounds(14, 60, 200, 30);
      guestP.add(mailT);
      mailT.setBounds(152, 60, 100, 30);
      guestP.add(emailComboBox);
      emailComboBox.setBounds(257, 60, 105, 30);
      // 이메일 콤보박스 크기: 100, 30
      guestP.add(mailBtn);
      mailBtn.setBounds(370, 60, 60, 30);

      guestP.add(gLoginBtn);
      gLoginBtn.setBounds(232, 125, 200, 50);

      loginP.add(guestP, "GP");

      guestP.setBounds(50, 400, 450, 650);

      loginP.setBackground(Color.BLACK);
      memberP.setBackground(Color.BLACK);
      guestP.setBackground(Color.BLACK);

      add(background);
      background.add(member);
      background.add(guest);
      background.add(loginP);

//-----------------
      idTel2T.addKeyListener(new KeyAdapter() {// 숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });

      idTel3T.addKeyListener(new KeyAdapter() {// 숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });

      tel2T.addKeyListener(new KeyAdapter() {// 숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });

      tel3T.addKeyListener(new KeyAdapter() {// 숫자만 입력
         public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
               getToolkit().beep();
               e.consume();
            }
         }
      });
      event();
   }

   public void event() { // 이벤트
      member.addActionListener(this);
      guest.addActionListener(this);
      loginBtn.addActionListener(this);
      registerBtn.addActionListener(this);
      gLoginBtn.addActionListener(this);
      mailBtn.addActionListener(this);
   }// event()

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == loginBtn) {

         // 빈칸검사
         if ((idTel2T.getText().length() == 0) || (idTel3T.getText().length() == 0)) {
            JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
            return;
         }
         if (passwordT.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.");
            return;
         }
         // 채워져있다면
         if (idTel2T.getText().length() > 4 || idTel3T.getText().length() > 4) {// 전화번호 4자리만 허용
            JOptionPane.showMessageDialog(this, "핸드폰번호 4자리를 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);
            idTel2T.setText("");
            idTel3T.setText("");
            return;
         }

         String tel = idComboBox.getSelectedItem() + idTel2T.getText() + idTel3T.getText();
         String password = passwordT.getText();
         
         CafeDTO cafeDTO = login(tel, password);//로그인창에서 입력한 아이디와 비밀번호
            
            if(cafeDTO == null) {
               JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 확인해주세요.");
               idTel2T.setText("");
               idTel3T.setText("");
               passwordT.setText("");
               return;
            }
            
            if(cafeDTO.getCode() == 0) {//코드가 0 이면 관리자
               new Manager(cafeDTO);
               idComboBox.setSelectedIndex(0);
               idTel2T.setText("");
               idTel3T.setText("");
               passwordT.setText("");
               
               return;
            }
         
         if (check) {
            JOptionPane.showMessageDialog(this, "로그인 성공했습니다.");
            new MainP(cafeDTO);
            startP.setVisible(false);
         } else
            JOptionPane.showMessageDialog(this, "로그인 실패했습니다.");

      } else if (e.getSource() == gLoginBtn) {
         // 빈칸검사
         if (tel2T.getText().length() == 0 || tel3T.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "전화번호를 입력하세요.");
            return;
         }
         if (mailT.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "이메일을 입력하세요.");
            return;
            // 문제가 빈칸만 다 채우면 '인증'버튼 안 누르고 비회원 로그인 눌려도 로그인 성공함 -> 강제로 버튼누르게
         } // 채워져있다면
         if (tel2T.getText().length() > 4 || tel3T.getText().length() > 4) {// 전화번호 4자리만 허용
            JOptionPane.showMessageDialog(this, "핸드폰번호 4자리를 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);
            tel2T.setText("");
            tel3T.setText("");
            return;
         }
         if (mailBC == 1) {
            if (registerResult.getSW() == 1) {// 메일인증을 받은거
               CafeDTO cafeDTO = insertData();

               if (cafeDTO == null) {
                  mailT.setText("");
                  tel2T.setText("");
                  tel3T.setText("");
                  emailComboBox.setSelectedIndex(0);
                  return;
               } else {
                  JOptionPane.showMessageDialog(this, "비회원 로그인 성공했습니다.");
                  startP.setVisible(false);
                  new MainP(cafeDTO);
               }
            } else {
               JOptionPane.showMessageDialog(this, "메일 인증을 해주세요.");

            }
         } else {
            JOptionPane.showMessageDialog(this, "메일 인증을 해주세요.");

         }

      } else if (e.getSource() == mailBtn) {
         mailBC=1;
         if (mailT.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "이메일을 입력하세요.");

         } else {
            SendMail sendMail = new SendMail(mailT.getText() + (String) emailComboBox.getSelectedItem());
            emailCheck(sendMail);
         }

      } else if (e.getSource() == registerBtn) {
         new Register();
      } else if (e.getSource() == member) {
         card.show(loginP, "MP");
      } else if (e.getSource() == guest) {
         card.show(loginP, "GP");
      }
   }// actionPerformed

   private CafeDTO login(String tel, String password) {
      CafeDAO cafaDAO = CafeDAO.getInstance();

      CafeDTO cafeDTO = cafaDAO.selectTel(tel);
      if (cafeDTO == null) {// 찾는 아이디가 없을경우
         check = false;
         return cafeDTO;
      }
      if (cafeDTO.getPassword().equals(password)) { // 입력한 비밀번호가 맞다면
         check = true;
         return cafeDTO;

      } else {
         check = false;
         return cafeDTO;
      }
   }

   private CafeDTO insertData() {//

      String combo = (String) phoneComboBox.getSelectedItem();

      String tel = (combo + tel2T.getText() + tel3T.getText());
      String mail = (mailT.getText() + (String) emailComboBox.getSelectedItem());

      CafeDAO cafeDAO = CafeDAO.getInstance();

      int seq = cafeDAO.getSeq();

      CafeDTO cafeDTO = new CafeDTO();

      cafeDTO.setSeq(seq);
      cafeDTO.setCode(2); //게스트값
      cafeDTO.setTel(tel);
      cafeDTO.setMail(mail);
      cafeDTO.setTotStime(0);
      cafeDTO.setTotRtime(0);
      cafeDTO.setPayment(0);
      cafeDTO.setSit(0);
      cafeDTO.setRoom(0);
      cafeDTO.setLocker(0);

      int su = cafeDAO.insertCafe(cafeDTO);
      if(su==0) {
         JOptionPane.showMessageDialog(this, "서버 접속에 실패하였습니다.");
         cafeDTO = null;
      }
      return cafeDTO;
   }

   private void emailCheck(SendMail sendMail) {
      registerResult = new RegisterResult(sendMail.getCode());
   }

   public String getMail() {
      System.out.println(emailComboBox.getSelectedItem());
      return (mailT.getText() + (String) emailComboBox.getSelectedItem());
   }
   // 비회원은 db에 입력도 해야함

}