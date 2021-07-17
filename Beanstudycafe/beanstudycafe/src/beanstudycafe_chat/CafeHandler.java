package beanstudycafe_chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

      //클라이언트와 직접 교류하기 위해 handler 쓰는거니까..........................!
public class CafeHandler extends Thread {
   private Socket socket;
   private List<CafeHandler> list;
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   
   public CafeHandler(Socket socket,List<CafeHandler> list) throws IOException {
      this.socket = socket;
      this.list = list;
      
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());
      
   }//CafeHandler(Socket socket,List<CafeHandler> list)

   @Override
   public void run() {
      String phone = null;
      // 받는 ChatDTO
      ChatDTO chatDTO = null; // 클라이언트의 번호(닉넴)을 저장

      while (true) {
         // 클라이언트에게서 dto 갖고오기
         try {
        	 
            chatDTO = (ChatDTO) ois.readObject();

            if (chatDTO.getCommand() == Info.JOIN) {
               phone = chatDTO.getPhone(); // 번호(닉넴저장)

               // 나를 포함한 모든 클라이언트 한테 입장메세지 보내주기.
               ChatDTO chatSendDTO = new ChatDTO();// 보내는 chatDTO
               // 위에 저장된 번호(닉넴저장)을 chatSendDTO는 '번호(닉넴)님이 입장했습니다' 라고 보내주기
               chatSendDTO.setCommand(Info.SEND);
               chatSendDTO.setMessage(phone + "님이 입장하였습니다");
               chatSendDTO.setTo(phone);
               broadcast(chatSendDTO);
            }else if (chatDTO.getCommand()==Info.EXIT) {
               ChatDTO chatSendDTO = new ChatDTO();
               
               chatDTO.setCommand(Info.EXIT);
               chatDTO.setTo(phone);
               oos.writeObject(chatDTO);
               oos.flush();
               ois.close();
               oos.close();
               socket.close();
               
               list.remove(this);
               
               chatSendDTO.setCommand(Info.SEND);
               chatSendDTO.setMessage(phone+ "님이 퇴장하였습니다.");
               chatSendDTO.setTo("관리자");
               broadcast(chatSendDTO);
               break;
            }else if(chatDTO.getCommand()==Info.SEND) {
               ChatDTO chatSendDTO = new ChatDTO();
               chatSendDTO.setCommand(Info.SEND);
               chatSendDTO.setMessage("["+phone+"]"+chatDTO.getMessage());
               chatSendDTO.setTo(chatDTO.getTo());
               broadcast(chatSendDTO);
            }
            
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }

      } // while
   }// run()


   private void broadcast(ChatDTO chatSendDTO) {
      for(CafeHandler handler : list) {
         
         try {
            //지금메세지 보내주기
            handler.oos.writeObject(chatSendDTO);
            handler.oos.flush();
            
         } catch (IOException e) {
            e.printStackTrace();
         }
      }//for
      
   }// broadcast(ChatDTO chatSendDTO)
   
}//class CafeHandler