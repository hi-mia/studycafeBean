package beanstudycafe_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CafeServer {

   private ServerSocket serverSocket;
   private List<CafeHandler> list;
//   private int no = 0;

   public CafeServer() {

      try {
         // 소켓 생성
         serverSocket = new ServerSocket(9500);
         System.out.println("서버 준비 완료!!!!");

         // 리스트 생성
         list = new ArrayList<CafeHandler>();

         while (true) {

            // 낚아채서 소켓 만들기
            Socket socket = serverSocket.accept();

            // chatHandler 생성해서 소켓과 list 보내기
            CafeHandler cafeHandler = new CafeHandler(socket, list);// 스레드 생성
            // 스레드 시작- 스레드 실행(run())
            cafeHandler.start();

            // 리스트에 담기
            list.add(cafeHandler);// 각각의 대화를 처리하기 위해 리스트에 담기
//            no++;
//            
//            if( no % 2 ==0) {
//               list.get(no-2).start();
//               list.get(no-1).start();
//            }
         } // while
      } catch (IOException e) {
         e.printStackTrace();
      }

   }// CafeServer()

   public static void main(String[] args) {
      new CafeServer();
   }

}