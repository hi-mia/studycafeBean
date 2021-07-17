package beanstudycafe_main_card;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
//패널highlight, setBorder( BorderFactory.createBevelBorder ( BevelBorder.RAISED, Color.blue, Color.red ) );

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;
import beanstudycafe_main_sit.SitList;

public class Week extends JPanel implements ActionListener {
	private JLabel weekTicket;
	private JLabel title;
	private JButton twoWeek, fourWeek, sixWeek, eightWeek, previewButton;
	private List<JButton> weekbuttonList;
	private CafeDTO cafeDTO;
	private CardLayout card;
	private JPanel menuP;
	private SitList sitList;

	private int roomTwoWeek;
	private int roomFourWeek;
	private int roomSixWeek;
	private int roomEightWeek;

	public Week(CafeDTO cafeDTO, CardLayout card, JPanel menuP, SitList sitList) {
		this.cafeDTO = cafeDTO;
		this.card = card;
		this.menuP = menuP;
		this.sitList = sitList;

		setLayout(null);
		Font font = new Font("LH B", Font.BOLD, 30);
		Font font2 = new Font("LH B", Font.BOLD, 12);
		Font font3 = new Font("LH B", Font.BOLD, 40);

		weekbuttonList = new ArrayList<JButton>();

		title = new JLabel("회원 정기권");
		title.setBounds(165, 25, 250, 40);
		title.setFont(font3);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(Color.white);

		weekTicket = new JLabel("1인석");
		weekTicket.setBounds(10, 100, 100, 80);
		weekTicket.setFont(font);
		weekTicket.setHorizontalAlignment(JLabel.CENTER);
		weekTicket.setForeground(new Color(78, 85, 92));

		twoWeek = new JButton("50시간");
		twoWeek.setBounds(100, 85, 180, 80);
		twoWeek.setFont(font);
		twoWeek.setBackground(Color.DARK_GRAY);
		twoWeek.setForeground(Color.white);

		fourWeek = new JButton("100시간");
		fourWeek.setBounds(300, 85, 180, 80);
		fourWeek.setFont(font);
		fourWeek.setBackground(Color.DARK_GRAY);
		fourWeek.setForeground(Color.white);

		sixWeek = new JButton("150시간");
		sixWeek.setBounds(100, 185, 180, 80);
		sixWeek.setFont(font);
		sixWeek.setBackground(Color.DARK_GRAY);
		sixWeek.setForeground(Color.white);

		eightWeek = new JButton("200시간");
		eightWeek.setBounds(300, 185, 180, 80);
		eightWeek.setFont(font);
		eightWeek.setBackground(Color.DARK_GRAY);
		eightWeek.setForeground(Color.white);

		previewButton = new JButton("이전으로");
		previewButton.setBounds(490, 225, 65, 40);
		previewButton.setFont(font2);
		previewButton.setBackground(Color.DARK_GRAY);
		previewButton.setForeground(Color.white);
		previewButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.blue, Color.red));

		weekbuttonList.add(twoWeek);
		weekbuttonList.add(fourWeek);
		weekbuttonList.add(sixWeek);
		weekbuttonList.add(eightWeek);
		weekbuttonList.add(previewButton);

		for (JButton weekJbtn : weekbuttonList) {
			weekJbtn.setOpaque(true);// 불투명하게
			weekJbtn.setBorderPainted(false);// 버튼 겉선 제거
			weekJbtn.setFocusPainted(false);// 버튼 누르면 글자에 테두리 생기는데 그거 제거
		}

		// add(weekTicket);
		add(twoWeek);
		add(fourWeek);
		add(sixWeek);
		add(eightWeek);
		add(previewButton);
		add(title);

		setBounds(500, 200, 590, 340);
		setBackground(new Color(51, 51, 51));
		setVisible(true);

//      this.addWindowListener(new WindowAdapter() {
//         @Override
//         public void windowClosing(WindowEvent e) {
//            setVisible(false);
//         }
//      });

		event();

	}

	public void event() {

		previewButton.addActionListener(this);
		twoWeek.addActionListener(this);
		fourWeek.addActionListener(this);
		sixWeek.addActionListener(this);
		eightWeek.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ~주 버튼 클릭시.
		// sit = 0;
		CafeDAO cafeDAO = CafeDAO.getInstance();
		SRLDAO srlDAO = SRLDAO.getInstance();

		String selectNum = null;
		selectNum = "좌석 " + sitList.getSelectSit();

		String selectWeek = e.getActionCommand();

		int selectPay = 0, selectTime = 0;
		if (e.getSource() == twoWeek) {
			selectTime = 50;
			selectPay = 200000;
		} else if (e.getSource() == fourWeek) {
			selectTime = 100;
			selectPay = 350000;
		} else if (e.getSource() == sixWeek) {
			selectTime = 150;
			selectPay = 500000;
		} else if (e.getSource() == eightWeek) {
			selectTime = 200;
			selectPay = 600000;
		}

		String tel = cafeDTO.getTel();
		// sit = 0;

		if (e.getSource() == twoWeek || e.getSource() == fourWeek || e.getSource() == sixWeek
				|| e.getSource() == eightWeek) { // 버튼 클릭시

			int result = JOptionPane.showConfirmDialog(this, "결제하시겠습니까?", "comfirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				Payment payment = new Payment(selectNum, selectWeek, selectPay, selectTime, tel, cafeDTO, sitList); // 결제창으로ㄱㄱ

			} else {
				JOptionPane.showMessageDialog(this, "다시 선택해주세요.");
			}

		}

		// 이전으로
		else if (e.getSource() == previewButton) {
			card.show(menuP, "DW");
		}

	}

}