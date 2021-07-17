package beanstudycafe_login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegisterResult extends JFrame implements ActionListener {

	private JLabel mailCheck;
	private JButton finalCheck;
	private JTextField mailCheck_F;
	private String code;
	private int sw;

	public int getSW() {
		return sw;
	}

	public RegisterResult(String code) {
		this.code = code;

		setLayout(null);

		mailCheck = new JLabel("인증번호를 입력하세요.");
		mailCheck.setBounds(70, 15, 300, 50);
		mailCheck.setFont(new Font("LH B", Font.BOLD, 15));
		mailCheck.setBackground(Color.BLACK);
		mailCheck.setForeground(Color.WHITE);

		mailCheck_F = new JTextField();
		mailCheck_F.setBounds(45, 60, 200, 35);

		finalCheck = new JButton("인증");
		finalCheck.setBounds(90, 120, 100, 30);
		finalCheck.setFont(new Font("LH B", Font.BOLD, 15));
		finalCheck.setBackground(Color.BLACK);
		finalCheck.setForeground(Color.WHITE);

		Container container = this.getContentPane();
		container.setBackground(new Color(51, 51, 51));
		container.add(mailCheck);
		container.add(mailCheck_F);
		container.add(finalCheck);

		finalCheck.addActionListener(this);

		setTitle("이메일 인증");

		setBounds(850, 300, 300, 200);
		setVisible(true);
		setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == finalCheck) {
			if (mailCheck_F.getText().equals(code)) {// 입력한값과 난수발생 코드가 같다면,
				sw = 1;
				JOptionPane.showMessageDialog(this, "메일인증 완료했습니다.");
				setVisible(false);
				return;
			} else
				sw = 0;
			JOptionPane.showMessageDialog(this, "번호를 다시 입력하세요.");

		}
		mailCheck_F.setText("");

	}
}