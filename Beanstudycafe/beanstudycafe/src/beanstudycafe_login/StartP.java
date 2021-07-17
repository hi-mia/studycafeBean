package beanstudycafe_login;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import beanstudycafe_dao.CafeDAO;
import beanstudycafe_dao.SRLDAO;
import beanstudycafe_dto.CafeDTO;
import beanstudycafe_dto.SRLDTO;

public class StartP extends JFrame {
	private ImageIcon image;
	private Font fontKor, fontEng;
	private JLabel titleKor, titleEng;
	private JLabel imageL;

	public StartP() {
		image = new ImageIcon("image/look.jpg");

		Image img = image.getImage();
		Image changeImg = img.getScaledInstance(600, 800, Image.SCALE_SMOOTH);
//      //ImageIcon changeIcon = new ImageIcon(changeImg);
//      //imageL = new JLabel(new ImageIcon(changeImg));
//      //imageL.setBounds(0, 0, 600, 800);

		JPanel background = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(image.getImage(), 0, 0, 600, 800, 0, 0, image.getIconWidth(), image.getIconHeight(),
						StartP.this);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		fontKor = new Font("한컴 쿨재즈 B", Font.ITALIC, 50);
		fontEng = new Font("Cooper Blk BT", Font.ITALIC, 50);

		titleKor = new JLabel("스터디 카페 ");
		titleEng = new JLabel("StudyCafe Bean");

		titleKor.setFont(fontKor);
		titleEng.setFont(fontEng);

		titleKor.setForeground(Color.WHITE);
		titleEng.setForeground(Color.WHITE);

		titleKor.setBounds(240, 600, 400, 150);
		titleEng.setBounds(260, 620, 400, 100);

		JPanel centerP = new JPanel();

		centerP.setOpaque(false);
		JPanel titleP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleP.setOpaque(false);
		titleP.add(titleKor);
		// titleP.add(titleEng);

		centerP.setBounds(50, 520, 500, 200);

		// centerP.add(imageL);
		centerP.add(titleP);
		centerP.add(titleEng);
		background.add(centerP);

		add(background);

		setBounds(500, 0, 600, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		background.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				background.setVisible(false);
				add(new Login(StartP.this));
			}
		});

		SRLDTO srlDTO = new SRLDTO();
		
		CafeDAO cafeDAO = CafeDAO.getInstance();
		SRLDAO srlDAO = SRLDAO.getInstance();

		List<CafeDTO> list = cafeDAO.getCafeList();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSit() != 0) {
				srlDTO.getSitCheck()[list.get(i).getSit() - 1] = true;
			}
			if (list.get(i).getRoom() != 0) {
				srlDTO.getRoomCheck()[list.get(i).getRoom() - 1] = true;
			}
			if (list.get(i).getLocker() != 0) {
				srlDTO.getLockerCheck()[list.get(i).getLocker() - 1] = true;
			}
		}

		srlDAO.setSit(srlDTO);
		srlDAO.setRoom(srlDTO);
		srlDAO.setLocker(srlDTO);
	}

	public static void main(String[] args) {
		new StartP();
	}
}