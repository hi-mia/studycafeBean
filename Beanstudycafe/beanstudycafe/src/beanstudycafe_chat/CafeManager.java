package beanstudycafe_chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dto.CafeDTO;

public class CafeManager extends JFrame implements ActionListener,Runnable {
   private JTextArea output;
   private JTextField inputT;
   private JButton sendBtn;
   private Socket socket;
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private JList<CafeDTO> list;
   private DefaultListModel<CafeDTO> model;

    public CafeManager() {

    output = new JTextArea();
    output.setFont(new Font("LH B",Font.BOLD,20));
    JScrollPane scroll = new JScrollPane(output);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    output.setEditable(false);//입력못하게 원청봉쇄
    
    inputT = new JTextField();
    sendBtn = new JButton("보내기");
    
    JPanel southP = new JPanel();
    southP.setLayout(new BorderLayout());
    southP.add("Center", inputT);
    southP.add("East",sendBtn);
    
    model = new DefaultListModel<CafeDTO>();
    list = new JList<CafeDTO>(model);
    JScrollPane scroll2 = new JScrollPane(list);
    scroll2.setBackground(Color.WHITE);
    scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//  scroll2.setBounds(500, 500, 500, 500);
    JPanel imc = new JPanel(new BorderLayout());
    imc.add(scroll2);
    
    Container container = this.getContentPane();
    container.add("Center",scroll);
    container.add("South",southP);
    container.add("East",imc);
    
    setTitle("관리자 문의하기");
    setBounds(500,200,1000,500);
    setVisible(true);
   
    addWindowListener(new WindowAdapter() {
       
       @Override
      public void windowClosing(WindowEvent e) {//창을 종료할때
          ChatDTO chatDTO = new ChatDTO();
          chatDTO.setCommand(Info.EXIT); //나갈거얌!
          
          try {
            oos.writeObject(chatDTO);//server에 나갈거라고 보내주고 기다리기.
            oos.flush();
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
    });

   CafeDAO cafeDAO = CafeDAO.getInstance();
   List<CafeDTO> allList = cafeDAO.getCafeList();
   
   for( CafeDTO friendDTO : allList) {
      model.addElement(friendDTO);
   }
   service();
    }//CafeClient()
    
    public void service() {
       String serverIP="";
   try {
      serverIP = 
         Inet4Address.getLocalHost().getHostAddress();
   } catch (UnknownHostException e1) {//로컬호스트를 못가져오면
      e1.printStackTrace();
   }
       
      String phone = "관리자";
 
      try {
         socket = new Socket(serverIP, 9500); 
         
         //IO생성
         ois = new ObjectInputStream(socket.getInputStream());
         oos = new ObjectOutputStream(socket.getOutputStream());
         
         //폰번호(아이디)를 서버로 보내주기 --> 서버열리자마자 해줘야하는거
         ChatDTO chatDTO = new ChatDTO();
         chatDTO.setCommand(Info.JOIN);//입장
         chatDTO.setPhone(phone);//폰번호(아이디)보내주기
         oos.writeObject(chatDTO);//DTO를 보내주기
         oos.flush();//버퍼를 비우는거
      
      } catch (UnknownHostException e) {
         System.out.println("서버를 찾을 수 없습니다");
         e.printStackTrace();
         setVisible(false);
      } catch (IOException e) {
         System.out.println("서버와 연결이 안되었습니다");
         e.printStackTrace();
         setVisible(false);
      } 
      
      //스레드 생성
      Thread thread = new Thread(this);
      
      //스레드 시작 - 스레드 실행(run()을 찾아서 수행)
      thread.start();
      
      //이벤트발생
      inputT.addActionListener(this); //엔터누르면 go
      sendBtn.addActionListener(this); // 버튼누르면 go
      
    }//service()

    public void end() {
       ChatDTO chatDTO = new ChatDTO();
        chatDTO.setCommand(Info.EXIT); //나갈거얌!
        
        try {
          oos.writeObject(chatDTO);//server에 나갈거라고 보내주고 기다리기.
          oos.flush();
       } catch (IOException e1) {
          e1.printStackTrace();
       }
    }
    
    @Override
    public void run() { //server 에서 오는 메세지

       ChatDTO chatDTO = null;
       
       while(true) {
          
          try {
            chatDTO = (ChatDTO)ois.readObject(); //chatDTO가 받을라면 형변환
            
            if(chatDTO.getCommand()==Info.EXIT) {
               ois.close();
               oos.close();
               socket.close();
               
               setVisible(false);
               break;
            }else if(chatDTO.getCommand()==Info.SEND) {
               
               output.append(chatDTO.getMessage()+"\n");
               int pos = output.getText().length();
               output.setCaretPosition(pos);//글자 길이에 따라 자동으로 스크롤 조절
            }
         
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } //형변환

       }//while
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
       //메세지 보내주기
       String message = inputT.getText();
       
       ChatDTO chatDTO = new ChatDTO();
       if(message.equals("나갈래")) {
          chatDTO.setCommand(Info.EXIT);
          
       }
       else {
       chatDTO.setCommand(Info.SEND);
       chatDTO.setMessage(message); //메세지 보내주기
       chatDTO.setTo(list.getSelectedValue().getTel());
       chatDTO.setFrom("관리자");
       System.out.println(list.getSelectedValue().getTel());
       }
       try {
         oos.writeObject(chatDTO);
         oos.flush();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
       inputT.setText(""); //채팅창 입력부분 비워주기
    }//actionPerformed(ActionEvent e)
   
}