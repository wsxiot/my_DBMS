package main;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import utils.UserUtils;

public class LoginView// extends JFrame
{
	private JLabel JLb1;//�û���
	private JLabel JLb2;//����
	private JButton Ok_btn;//��¼
	private JButton register_btn;//ע��
	private JTextField Jtfld1;//�û�����
	private JPasswordField Jtfld2;//�����
	private JFrame frame;//������

	public LoginView() {
		Font s = new Font("����", Font.PLAIN, 12);
		UIManager.put("Component.font", s);//Ϊ�ؼ�ָ������
		UIManager.put("Label.font", s);
//		UIManager.put("ComboBox.font", s);
		UIManager.put("Button.font", s);
//		UIManager.put("Menu.font", s);
//		UIManager.put("MenuItem.font", s);

		frame = new JFrame("��¼��DBMS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�û��������ڵĹرհ�ťʱ����ִ�еĲ���

		Container content = frame.getContentPane();
		content.setLayout(new GridLayout(3, 2, 20, 20));
		JLb1 = new JLabel("   �û���:");

		JLb2 = new JLabel("   �� ��:");
		Jtfld1 = new JTextField();
		Jtfld2 = new JPasswordField();
		Ok_btn = new JButton("��¼");
		register_btn = new JButton("ע��");

		Ok_btn.addActionListener(new ActionHandler());
		register_btn.addActionListener(new ActionHandler());
		content.add(JLb1);
		content.add(Jtfld1);

		content.add(JLb2);
		content.add(Jtfld2);
		content.add(Ok_btn);
		content.add(register_btn);//��˳��Ҫ��

		frame.pack();//�����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		frame.setLocationRelativeTo(null);//���ô��������ָ�������λ�á� ��������ǰδ��ʾ������Ϊ null����˴��ڽ�������Ļ������
		frame.setSize(300, 200);//ָ����С
		frame.setVisible(true);//�ɼ�

	}

	class ActionHandler implements ActionListener {//��Ӽ����¼�
		public void actionPerformed(ActionEvent e) {
			String str1, str2;
			Object obj = e.getSource();
			str1 = Jtfld1.getText().trim();
			str2 = Jtfld2.getText().trim();
			if (obj.equals(Ok_btn)) {//��¼
				//////////////////////////////////////////////// �����������벻��Ϊ��
				if (str1.equals("")) {
					JOptionPane.showMessageDialog(frame, "�û�������Ϊ��!");
					return;
				}
				if (str2.equals("")) {
					JOptionPane.showMessageDialog(frame, "���벻��Ϊ��!");
					return;
				}
//////////////////////////////////////////////////

				if (UserUtils.login(str1, str2).equals("ok")) {
					MainFrame mf = new MainFrame();
					mf.jlinfo.setText("�û���:" + str1);
					mf.setUsername(str1);
					System.out.println(str1);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "�û������������");
					return;
				}
			} else if (obj.equals(register_btn)) {// ע��
////////////////////////////////////////////////�����������벻��Ϊ��
				if (str1.equals("")) {
					JOptionPane.showMessageDialog(frame, "�û�������Ϊ��!");
					return;
				}
				if (str2.equals("")) {
					JOptionPane.showMessageDialog(frame, "���벻��Ϊ��!");
					return;
				}
//////////////////////////////////////////////////
				if (UserUtils.register(str1, str2).equals("ok")) {
					MainFrame mf = new MainFrame();
					mf.jlinfo.setText("�û���:" + str1);
					mf.setUsername(str1);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "�û����ظ�");
					Jtfld1.setText("");
					Jtfld2.setText("");
					return;
				}

			}
		}

	}

//	public static void main(String[] args) {
//
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new LoginView();
//			}
//		});
//	}
}