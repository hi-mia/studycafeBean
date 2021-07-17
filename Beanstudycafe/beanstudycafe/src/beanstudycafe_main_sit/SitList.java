package beanstudycafe_main_sit;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;
import beanstudycafe_main_card.Locker;

// 340 560
public class SitList extends JPanel implements ActionListener, Runnable {
	private JButton[] sitArr = new JButton[20];
	private JButton[] roomArr = new JButton[3];
	private JButton nowSit, nowRoom, nowLocker, haveSitTime, haveRoomTime;
	private JPanel locker;
	private JLabel sa, mul, ham;
	private int emptySit, emptyRoom, emptyLocker;
	private String timeSit, timeRoom;
	private int hourS, minuteS, hourR, minuteR;
	private int second;
	private CafeDTO cafeDTO;
	private CardLayout card;
	private JPanel menuP;
	private SRLDTO srlDTO;
	private int selectSit, selectRoom;
	private JLabel enterL, sitL1, sitL2;

	public SitList(CafeDTO cafeDTO, CardLayout card, JPanel menuP, JLabel enterL, JLabel sitL1, JLabel sitL2) {
		this.cafeDTO = cafeDTO;
		this.card = card;
		this.menuP = menuP;
		this.enterL = enterL;
		this.sitL1 = sitL1;
		this.sitL2 = sitL2;

		setLayout(null);

		for (int i = 0; i < sitArr.length; i++) {
			sitArr[i] = new JButton("좌석" + (i + 1));
			sitArr[i].setFont(new Font("LH B", Font.BOLD, 9));
			sitArr[i].setBackground(new Color(51, 51, 51));
			sitArr[i].setForeground(Color.WHITE);
			sitArr[i].setFocusable(false);
		} // for

		for (int i = 0; i < roomArr.length; i++) {
			roomArr[i] = new JButton("룸" + (i + 1));
			roomArr[i].setFont(new Font("LH B", Font.BOLD, 9));
			roomArr[i].setBackground(new Color(51, 51, 51));
			roomArr[i].setForeground(Color.WHITE);
			roomArr[i].setFocusable(false);
		} // for

		JPanel northP = new JPanel(new BorderLayout());
		JPanel nowP = new JPanel(new GridLayout(1, 3));

		SRLDAO srlDAO = SRLDAO.getInstance();
		srlDTO = srlDAO.getList();

		for (int i = 0; i < srlDTO.getSitCheck().length; i++) {
			if (srlDTO.getSitCheck()[i]) {
				sitArr[i].setEnabled(false);
				sitArr[i].setBackground(Color.BLACK);
			} else {
				emptySit++;
				sitArr[i].setEnabled(true);
				sitArr[i].setBackground(new Color(51, 51, 51));

			}
		} // for sit

		for (int i = 0; i < srlDTO.getRoomCheck().length; i++) {
			if (srlDTO.getRoomCheck()[i]) {
				roomArr[i].setEnabled(false);
				roomArr[i].setBackground(Color.BLACK);
			} else {
				emptyRoom++;
				roomArr[i].setEnabled(true);
				roomArr[i].setBackground(new Color(51, 51, 51));

			}
		} // for room

		for (int i = 0; i < srlDTO.getLockerCheck().length; i++) {
			if (!srlDTO.getLockerCheck()[i]) {
				emptyLocker++;
			}
		} // for locker

		nowSit = new JButton("좌석 현황 : " + emptySit + " / 20");
		nowRoom = new JButton("룸 현황 : " + emptyRoom + " / 3");
		nowLocker = new JButton("사물함 현황 : " + emptyLocker + " / 10");

		JPanel timeP = new JPanel(new GridLayout(1, 2));

		timeSit = (int) cafeDTO.getTotStime() + "시간" + (int) ((cafeDTO.getTotStime() % 1) * 100) + "분";
		timeRoom = (int) cafeDTO.getTotRtime() + "시간" + (int) ((cafeDTO.getTotRtime() % 1) * 100) + "분";

		haveSitTime = new JButton("남은 좌석 시간 : " + timeSit);
		haveRoomTime = new JButton("남은 룸 시간 : " + timeRoom);

		nowSit.setEnabled(false);
		nowSit.setFont(new Font("LH B", Font.BOLD, 12));
		nowSit.setBackground(new Color(51, 51, 51));
		nowSit.setForeground(Color.WHITE);
		nowRoom.setEnabled(false);
		nowRoom.setFont(new Font("LH B", Font.BOLD, 12));
		nowRoom.setBackground(new Color(51, 51, 51));
		nowRoom.setForeground(Color.WHITE);
		nowLocker.setEnabled(false);
		nowLocker.setFont(new Font("LH B", Font.BOLD, 12));
		nowLocker.setBackground(new Color(51, 51, 51));
		nowLocker.setForeground(Color.WHITE);

		haveSitTime.setEnabled(false);
		haveSitTime.setFont(new Font("LH B", Font.BOLD, 9));
		haveSitTime.setBackground(new Color(51, 51, 51));
		haveSitTime.setForeground(Color.WHITE);
		haveRoomTime.setEnabled(false);
		haveRoomTime.setFont(new Font("LH B", Font.BOLD, 9));
		haveRoomTime.setBackground(new Color(51, 51, 51));
		haveRoomTime.setForeground(Color.WHITE);

		timeP.add(haveSitTime);
		timeP.add(haveRoomTime);

		nowP.add(nowSit);
		nowP.add(nowRoom);
		nowP.add(nowLocker);

		northP.add("South", timeP);
		northP.add("North", nowP);

		northP.setBounds(5, 5, 560, 45);

		locker = new JPanel(new GridLayout(5, 1));

		sa = new JLabel("사", JLabel.CENTER);
		mul = new JLabel("물", JLabel.CENTER);
		ham = new JLabel("함", JLabel.CENTER);

		// System.out.println(sdf.format(date));

		sitArr[0].setBounds(5, 60, 65, 40);
		sitArr[1].setBounds(75, 60, 65, 40);
		sitArr[2].setBounds(145, 60, 65, 40);
		sitArr[3].setBounds(215, 60, 65, 40);
		sitArr[4].setBounds(285, 60, 65, 40);
		sitArr[5].setBounds(355, 60, 65, 40);
		sitArr[6].setBounds(425, 60, 65, 40);

		sitArr[7].setBounds(5, 105, 65, 40);
		sitArr[8].setBounds(5, 150, 65, 40);
		sitArr[9].setBounds(5, 195, 65, 40);
		sitArr[10].setBounds(5, 240, 65, 40);

		sitArr[11].setBounds(75, 240, 65, 40);
		sitArr[12].setBounds(145, 240, 65, 40);
		sitArr[13].setBounds(215, 240, 65, 40);
		sitArr[14].setBounds(285, 240, 65, 40);
		sitArr[15].setBounds(355, 240, 65, 40);

		sitArr[16].setBounds(100, 120, 65, 40);
		sitArr[17].setBounds(180, 120, 65, 40);
		sitArr[18].setBounds(100, 180, 65, 40);
		sitArr[19].setBounds(180, 180, 65, 40);

		roomArr[0].setBounds(280, 120, 50, 100);
		roomArr[1].setBounds(360, 120, 50, 100);
		roomArr[2].setBounds(440, 120, 50, 100);

		locker.setBounds(500, 60, 50, 220);
		locker.setBackground(Color.DARK_GRAY);
		sa.setForeground(Color.WHITE);
		mul.setForeground(Color.WHITE);
		ham.setForeground(Color.WHITE);

		locker.add(new JLabel("", JLabel.CENTER));
		locker.add(sa);
		locker.add(mul);
		locker.add(ham);
		locker.add(new JLabel("", JLabel.CENTER));

		for (int i = 0; i < sitArr.length; i++) {
			add(sitArr[i]);
		} // for

		for (int i = 0; i < roomArr.length; i++) {
			add(roomArr[i]);
		} // for
		add(northP);
		add(locker);

		setBackground(new Color(51, 51, 51));

		setBounds(100, 0, 590, 330);
		setVisible(true);

		for (int i = 0; i < sitArr.length; i++) {
			sitArr[i].addActionListener(this);
		} // for
		for (int i = 0; i < roomArr.length; i++) {
			roomArr[i].addActionListener(this);
		} // for

		if (cafeDTO.getSit() != 0 || cafeDTO.getRoom() != 0) {
			hourS = (int) (cafeDTO.getTotStime() / 1);
			minuteS = (int) ((cafeDTO.getTotStime() % 1.0) * 100);

			hourR = (int) (cafeDTO.getTotRtime() / 1);
			minuteR = (int) ((cafeDTO.getTotRtime() % 1.0) * 100);

//         Thread t = new Thread(this);
//         t.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SRLDAO srlDAO = SRLDAO.getInstance();
		srlDTO = srlDAO.getList();

		CafeDAO cafeDAO = CafeDAO.getInstance();

		selectSit = 0;
		selectRoom = 0;
		reset();

		int sw = 0;

		for (int i = 0; i < sitArr.length; i++) {
			if (e.getSource() == sitArr[i]) {
				if (srlDTO.getSitCheck()[i]) {
					JOptionPane.showMessageDialog(this, "이미 이용 중인 좌석입니다.");
					reset();
				} else {
					sw = 1;
					selectSit = i + 1;
					sitArr[i].setBackground(Color.WHITE);
					sitArr[i].setForeground(Color.BLACK);
				}
			}
		} // for 빈자리 체크

		for (int i = 0; i < roomArr.length; i++) {
			if (e.getSource() == roomArr[i]) {
				if (srlDTO.getRoomCheck()[i]) {
					JOptionPane.showMessageDialog(this, "이미 이용 중인 룸입니다.");
					reset();
				} else {
					sw = 1;
					selectRoom = i + 1;
					roomArr[i].setBackground(Color.WHITE);
					roomArr[i].setForeground(Color.BLACK);
				}
			}
		}

		if (sw == 0) {
			reset();

		} else {
			if (cafeDTO.getSit() == 0) {
				if (cafeDTO.getRoom() == 0) {
					// 눌린 버튼으로 입실
					if (cafeDTO.getTotStime() != 0 && selectSit != 0) {
						int result = JOptionPane.showConfirmDialog(this, "좌석 : " + selectSit + "번에 입실하시겠습니까?",
								"Confirm", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							// insertPayment();
							SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
							String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
							double inTime = Double.parseDouble(currentTimeTest);

							cafeDTO.setIntime(inTime);
							cafeDTO.setSit(selectSit);
							cafeDTO.setTime(cafeDTO.getTime());
							cafeDTO.setTotStime(cafeDTO.getTotStime());
							cafeDTO.setPayment(cafeDTO.getPayment());

							cafeDAO.updateInSR(cafeDTO);

							SRLDTO srlDTO = srlDAO.getList();
							srlDTO.getSitCheck()[selectSit - 1] = true;

							srlDAO.setSit(srlDTO);

							reset();
						} else {
							JOptionPane.showMessageDialog(this, "입실 취소 하셨습니다.");
						}
					} else {

						card.show(menuP, "DW");
					}

				} else if (cafeDTO.getRoom() != 0) {
					// 눌린버튼으로 룸 이동
					if (selectSit != 0) {
						JOptionPane.showMessageDialog(this, "룸에서 좌석으로의 이동은 불가능합니다.");
					} else if (selectRoom != 0) {
						int result = JOptionPane.showConfirmDialog(this,
								"룸 " + cafeDTO.getRoom() + "번에서 " + "룸 " + selectRoom + "번으로 이동하시겠습니까?", "확인창",
								JOptionPane.YES_NO_OPTION);

						if (result == JOptionPane.YES_OPTION) {
							SRLDTO srlDTO = new SRLDTO();
							srlDTO.getRoomCheck()[cafeDTO.getRoom() - 1] = false;
							srlDTO.getRoomCheck()[selectRoom - 1] = true;
							cafeDTO.setRoom(selectRoom);

							cafeDAO.updateSR(cafeDTO);
							srlDAO.setRoom(srlDTO);
							reset();
						}
					}
				}
//            reset();
			} else if (cafeDTO.getSit() != 0) {
				if (selectRoom != 0) {
					JOptionPane.showMessageDialog(this, "좌석에서 룸으로의 이동은 불가능합니다.");
				} else if (selectSit != 0) {
					int result = JOptionPane.showConfirmDialog(this,
							"좌석 " + cafeDTO.getSit() + "번에서 " + "좌석 " + selectSit + "번으로 이동하시겠습니까?", "확인창",
							JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION) {
						SRLDTO srlDTO = new SRLDTO();
						srlDTO.getSitCheck()[cafeDTO.getSit() - 1] = false;
						srlDTO.getSitCheck()[selectSit - 1] = true;
						cafeDTO.setSit(selectSit);

						cafeDAO.updateSR(cafeDTO);
						srlDAO.setSit(srlDTO);
						reset();
					}
				}
			}
//         reset();
		}

		if (cafeDTO.getSit() != 0 || cafeDTO.getRoom() != 0) {
			hourS = (int) (cafeDTO.getTotStime() / 1);
			minuteS = (int) ((cafeDTO.getTotStime() % 1.0) * 100);

			hourR = (int) (cafeDTO.getTotRtime() / 1);
			minuteR = (int) ((cafeDTO.getTotRtime() % 1.0) * 100);

			Thread t = new Thread(this);
			t.start();
		}

	}

	public int getSelectSit() {
		return selectSit;
	}

	public int getSelectRoom() {
		return selectRoom;
	}

	public void reset() {
		SRLDAO srlDAO = SRLDAO.getInstance();
		srlDTO = srlDAO.getList();

		menuP.setVisible(true);
		enterL.setText("");
		sitL1.setText("");
		sitL2.setText("");

		emptySit = 0;
		emptyRoom = 0;
		emptyLocker = 0;

		for (int i = 0; i < srlDTO.getSitCheck().length; i++) {
			if (srlDTO.getSitCheck()[i]) {
				sitArr[i].setEnabled(false);
				sitArr[i].setBackground(Color.BLACK);
				sitArr[i].setForeground(Color.WHITE);
			} else {
				emptySit++;
				sitArr[i].setEnabled(true);
				sitArr[i].setBackground(new Color(51, 51, 51));
				sitArr[i].setForeground(Color.WHITE);

			}
		} // for sit

		for (int i = 0; i < srlDTO.getRoomCheck().length; i++) {
			if (srlDTO.getRoomCheck()[i]) {
				roomArr[i].setEnabled(false);
				roomArr[i].setBackground(Color.BLACK);
				roomArr[i].setForeground(Color.WHITE);
			} else {
				emptyRoom++;
				roomArr[i].setEnabled(true);
				roomArr[i].setBackground(new Color(51, 51, 51));
				roomArr[i].setForeground(Color.WHITE);

			}
		} // for room

		for (int i = 0; i < srlDTO.getLockerCheck().length; i++) {
			if (!srlDTO.getLockerCheck()[i]) {
				emptyLocker++;
			}
		} // for locker

		timeSit = (int) cafeDTO.getTotStime() + "시간" + (int) ((cafeDTO.getTotStime() % 1) * 100) + "분";
		timeRoom = (int) cafeDTO.getTotRtime() + "시간" + (int) ((cafeDTO.getTotRtime() % 1) * 100) + "분";

		haveSitTime.setText("남은 좌석 시간 : " + timeSit);
		haveRoomTime.setText("남은 룸 시간 : " + timeRoom);

		nowSit.setText("좌석 현황 : " + emptySit + " / 20");
		nowRoom.setText("룸 현황 : " + emptyRoom + " / 3");
		nowLocker.setText("사물함 현황 : " + emptyLocker + " / 10");

		if (cafeDTO.getSit() != 0 || cafeDTO.getRoom() != 0) {
			hourS = (int) (cafeDTO.getTotStime() / 1);
			minuteS = (int) ((cafeDTO.getTotStime() % 1.0) * 100);

			hourR = (int) (cafeDTO.getTotRtime() / 1);
			minuteR = (int) ((cafeDTO.getTotRtime() % 1.0) * 100);

//         Thread t = new Thread(this);
//         t.start();
		}
	}// reset

	@Override
	public void run() {
		while (true) {
			if (cafeDTO.getSit() != 0 || cafeDTO.getRoom() != 0) {
				second++;

				if (cafeDTO.getSit() != 0 && cafeDTO.getRoom() == 0) {
					if (second == 60) {
						second = 0;
						minuteS--;
					}

					if (minuteS == 0 && hourS != 0) {
						minuteS = 59;
						hourS--;
					}

					if (minuteS == 0 && hourS == 0) {
						CafeDAO cafeDAO = CafeDAO.getInstance();
						SRLDAO srlDAO = SRLDAO.getInstance();

						SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
						String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
						double outTime = Double.parseDouble(currentTimeTest);

						SRLDTO srlDTO = srlDAO.getList();
						srlDTO.getSitCheck()[cafeDTO.getSit() - 1] = false;
						if (cafeDTO.getLocker() != 0) {
							srlDTO.getLockerCheck()[cafeDTO.getLocker() - 1] = false;
						}

						srlDAO.setSit(srlDTO);
						srlDAO.setLocker(srlDTO);

						cafeDTO.setSit(0);
						cafeDTO.setOuttime(outTime);
						cafeDTO.setTotStime(0);
						cafeDTO.setLocker(0);

						cafeDAO.updateOutSR(cafeDTO);

						reset();
					}

					timeSit = hourS + "시간" + minuteS + "분";

					haveSitTime.setText("남은 좌석 시간 : " + timeSit);
				} else if (cafeDTO.getRoom() != 0 && cafeDTO.getSit() == 0) {
					if (second == 60) {
						second = 0;
						minuteR--;
					}

					if (minuteR == 0 && hourR != 0) {
						minuteR = 59;
						hourR--;
					}

					if (minuteR == 0 && hourR == 0) {
						CafeDAO cafeDAO = CafeDAO.getInstance();
						SRLDAO srlDAO = SRLDAO.getInstance();

						SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");// 00.00 시 분
						String currentTimeTest = sdf.format(new Date());// 현재시간 __.__ String로 받아옴.
						double outTime = Double.parseDouble(currentTimeTest);

						SRLDTO srlDTO = srlDAO.getList();
						srlDTO.getRoomCheck()[cafeDTO.getRoom() - 1] = false;
						if (cafeDTO.getLocker() != 0) {
							srlDTO.getLockerCheck()[cafeDTO.getLocker() - 1] = false;
						}
						srlDAO.setRoom(srlDTO);
						srlDAO.setLocker(srlDTO);

						cafeDTO.setRoom(0);
						cafeDTO.setOuttime(outTime);
						cafeDTO.setTotRtime(0);
						cafeDTO.setLocker(0);

						cafeDAO.updateOutSR(cafeDTO);

						reset();
					}

				}

				timeRoom = hourR + "시간" + minuteR + "분";

				haveRoomTime.setText("남은 룸 시간 : " + timeRoom);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (cafeDTO.getSit() == 0 && cafeDTO.getRoom() == 0) {
				break;
			}

			nowSit.setText("좌석 현황 : " + emptySit + " / 20");
			nowRoom.setText("룸 현황 : " + emptyRoom + " / 3");
			nowLocker.setText("사물함 현황 : " + emptyLocker + " / 10");
		} // while
	}

//   public static void main(String[] args) {
//      new SitList(new CafeDTO(), new CardLayout(), new JPanel());
//   }
}