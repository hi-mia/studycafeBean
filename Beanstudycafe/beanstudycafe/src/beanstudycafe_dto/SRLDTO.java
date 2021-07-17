package beanstudycafe_dto;

import java.util.List;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;

public class SRLDTO {
   private boolean[] sitCheck = new boolean[20];
   private boolean[] roomCheck = new boolean[3];
   private boolean[] lockerCheck = new boolean[10];
   
   public SRLDTO() {
      
      CafeDAO cafeDAO = CafeDAO.getInstance();
      SRLDAO srlDAO = SRLDAO.getInstance();

      List<CafeDTO> list = cafeDAO.getCafeList();

      for (int i = 0; i < list.size(); i++) {
         if (list.get(i).getSit() != 0) {
            sitCheck[list.get(i).getSit() - 1] = true;
         }
         if (list.get(i).getRoom() != 0) {
            roomCheck[list.get(i).getRoom() - 1] = true;
         }
         if (list.get(i).getLocker() != 0) {
            lockerCheck[list.get(i).getLocker() - 1] = true;
         }
      }

      srlDAO.setSit(this);
      srlDAO.setRoom(this);
      srlDAO.setLocker(this);
   }

   
   
   public boolean[] getSitCheck() {
      return sitCheck;
   }
   public void setSitCheck(boolean[] sitCheck) {
      this.sitCheck = sitCheck;
   }
   public boolean[] getRoomCheck() {
      return roomCheck;
   }
   public void setRoomCheck(boolean[] roomCheck) {
      this.roomCheck = roomCheck;
   }
   public boolean[] getLockerCheck() {
      return lockerCheck;
   }
   public void setLockerCheck(boolean[] lockerCheck) {
      this.lockerCheck = lockerCheck;
   }
   
   @Override
   public String toString() {
      return sitCheck[0] + " " + sitCheck[1] + " " + sitCheck[2] + " " + sitCheck[3] + " " + sitCheck[4] + " " + sitCheck[5] + " " + 
            sitCheck[6] + " " + sitCheck[7] + " " + sitCheck[8] + " " + sitCheck[9] + " " + sitCheck[10] + " " + sitCheck[11] + " " + 
            sitCheck[12] + " " + sitCheck[13] + " " + sitCheck[14] + " " + sitCheck[15] + " " + sitCheck[16] + " " + sitCheck[17] + " " + 
            sitCheck[18] + " " + sitCheck[19] + " " + roomCheck[0] + " " + roomCheck[1] + " " + roomCheck[2] + " " + 
            lockerCheck[0] + " " + lockerCheck[1] + " " + lockerCheck[2] + " " + lockerCheck[3] + " " + lockerCheck[4] + " " + lockerCheck[5] + " " + 
            lockerCheck[6] + " " + lockerCheck[7] + " " + lockerCheck[8] + " " + lockerCheck[9];
   }
}