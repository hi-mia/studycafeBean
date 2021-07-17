package beanstudycafe_main_card;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;
import beanstudycafe_main_sit.SitList;

public class Payment extends JFrame implements ActionListener {

	private JLabel paymentwindow, checkSitNum, checkTime, checkPhoneNum, checkPayment;
	private JLabel sitNum, time, phoneNum, paymentL;
	private JButton payment, cancle;
	private Date date;
	private SimpleDateFormat hour;
	private SimpleDateFormat min;
	private double inTime;
	private double outTime;
	private int hour_ = 10; // 바꿔야함
	private int time_;
	private String selectNum, selectHour, tel;
	private int selectTime, selectPay;
	private CafeDTO cafeDTO;
	private SitList sitList;

	public Payment(String selectNum, String selectHour, int selectPay, int selectTime, String tel, CafeDTO cafeDTO, SitList sitList) { // 메인에서 cafeDTO값 받아오기
		this.selectNum = selectNum;
		this.selectHour = selectHour;
		this.selectPay = selectPay;
		this.selectTime = selectTime;
		this.tel = tel;
		this.cafeDTO = cafeDTO;
		this.sitList = sitList;

		setLayout(null);

		Font font = new Font("LH B", Font.BOLD, 15);
		Font font2 = new Font("LH B", Font.BOLD, 30);

		paymentwindow = new JLabel("결제창");
		paymentwindow.setBounds(130, 2, 200, 40);
		paymentwindow.setFont(font2);
		paymentwindow.setHorizontalAlignment(JLabel.CENTER);
		paymentwindow.setForeground(Color.white);

		checkSitNum = new JLabel("선택한 좌석 :");
		checkSitNum.setBounds(20, 50, 180, 30);
		checkSitNum.setFont(font2);
		checkSitNum.setForeground(Color.white);

		checkTime = new JLabel("선택한 시간 :");
		checkTime.setBounds(20, 90, 180, 30);
		checkTime.setFont(font2);
		checkTime.setForeground(Color.white);

		checkPhoneNum = new JLabel("핸드폰 번호 :");
		checkPhoneNum.setBounds(20, 130, 180, 30);
		checkPhoneNum.setFont(font2);
		checkPhoneNum.setForeground(Color.white);

		checkPayment = new JLabel("결제 금액 :");
		checkPayment.setBounds(160, 190, 200, 60);
		checkPayment.setFont(font2);
		checkPayment.setForeground(Color.white);
		// ------------------------------------------------

		sitNum = new JLabel();// sit
		sitNum.setText(selectNum);
		sitNum.setBounds(300, 50, 180, 30);
		sitNum.setFont(font);
		sitNum.setForeground(Color.white);

		time = new JLabel();// 선택한 시간 time
		time.setText(selectHour);
		time.setBounds(300, 90, 180, 30);
		time.setFont(font);
		time.setForeground(Color.white);

		phoneNum = new JLabel();// 로그인 한 사람 tel
		phoneNum.setText(tel);
		phoneNum.setBounds(300, 130, 180, 30);
		phoneNum.setFont(font);
		phoneNum.setForeground(Color.white);

		paymentL = new JLabel(selectPay + "원");
		paymentL.setBounds(320, 190, 200, 60);
		paymentL.setFont(font2);
		paymentL.setForeground(Color.white);

		payment = new JButton("결제 진행");// payment
		payment.setBounds(30, 280, 200, 60);
		payment.setFont(font2);
		payment.setBackground(Color.black);
		payment.setForeground(Color.white);
		payment.setFocusable(false);

		cancle = new JButton("결제 취소");
		cancle.setBounds(260, 280, 200, 60);
		cancle.setFont(font2);
		cancle.setBackground(Color.black);
		cancle.setForeground(Color.white);
		cancle.setFocusable(false);

		Container container = this.getContentPane();

		container.add(paymentwindow);
		container.add(checkSitNum);
		container.add(checkTime);
		container.add(checkPhoneNum);
		container.add(checkPayment);

		container.add(payment);
		container.add(cancle);

		container.add(sitNum);
		container.add(time);
		container.add(phoneNum);
		container.add(paymentL);

		payment.addActionListener(this);
		cancle.addActionListener(this);

		// 현재시간 받아와서
		// date 시간 메소드 시/분 , ".", parse double ,변경 후 intime 세팅

		container.setBackground(new Color(51, 51, 51));
		setBounds(550, 250, 500, 400);
		setBackground(Color.white);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == payment) {
			CafeDAO cafeDAO = CafeDAO.getInstance();
			SRLDAO srlDAO = SRLDAO.getInstance();
			//insertPayment();			
			SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
			String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
			double inTime = Double.parseDouble(currentTimeTest);

			if (sitList.getSelectSit() != 0 && sitList.getSelectRoom() == 0) {
				cafeDTO.setIntime(inTime);
				cafeDTO.setSit(sitList.getSelectSit());
				cafeDTO.setTime(cafeDTO.getTime() + selectTime);
				cafeDTO.setTotStime(cafeDTO.getTotStime() + selectTime);
				cafeDTO.setPayment(cafeDTO.getPayment() + selectPay);

				cafeDAO.updateInSR(cafeDTO);

				SRLDTO srlDTO = srlDAO.getList();
				srlDTO.getSitCheck()[sitList.getSelectSit() - 1] = true;

				srlDAO.setSit(srlDTO);

				sitList.reset();

			} else if (sitList.getSelectRoom() != 0 && sitList.getSelectSit() == 0) {
				cafeDTO.setIntime(inTime);
				cafeDTO.setRoom(sitList.getSelectRoom());
				cafeDTO.setTime(cafeDTO.getTime() + selectTime);
				cafeDTO.setTotRtime(cafeDTO.getTotRtime() + selectTime);
				cafeDTO.setPayment(cafeDTO.getPayment() + selectPay);

				cafeDAO.updateInSR(cafeDTO);

				SRLDTO srlDTO = srlDAO.getList();
				srlDTO.getRoomCheck()[sitList.getSelectRoom() - 1] = true;

				srlDAO.setSit(srlDTO);

				sitList.reset();

			}

			
			JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.");
			
		}else if(e.getSource() == cancle) {
			JOptionPane.showMessageDialog(this, "결제가 취소되었습니다.");
			
		}
		setVisible(false);
	}

	public void insertPayment() {
		// 요청시간 String
//		int clientChooseTime = 120;//(cafeDTO.getTime()+"");

//		//현재시간 Date
//		Date currentTime = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm");
//		
//		//요청시간을 Date로 parsing 후 time가져오기
//		Date reqDate = dateFormat.parse(clientChooseTime);
//		long reqDateTime = reqDate.getTime();

//		//현재시간을 요청시간의 형태로 format 후 time 가져오기
//		//currentTime = dateFormat.parse(dateFormat.format(currentTime));
//		long curDateTime = currentTime.getTime();
//		
//		//분으로 표현
//		//long minute = (curDateTime + reqDateTime) / 60000;
//		long minute = (curDateTime + clientChooseTime) / 60000;
//		System.out.println(minute+"분 차이");

//		SimpleDateFormat sdf = new SimpleDateFormat("hh.mm");// 00.00 시 분
//		String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
//		inTime = Double.parseDouble(currentTimeTest);
//		System.out.println("c" + currentTimeTest);
//		System.out.println(inTime);

	}

//	public static void main(String[] args) {
//		new Payment("좌석 3", "3시간", "13000원", "01043214321");
//	}
}
