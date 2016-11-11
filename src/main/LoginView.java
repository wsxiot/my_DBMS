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
	private JLabel JLb1;//用户名
	private JLabel JLb2;//密码
	private JButton Ok_btn;//登录
	private JButton register_btn;//注册
	private JTextField Jtfld1;//用户名框
	private JPasswordField Jtfld2;//密码框
	private JFrame frame;//主窗体

	public LoginView() {
		Font s = new Font("楷体", Font.PLAIN, 12);
		UIManager.put("Component.font", s);//为控件指定字体
		UIManager.put("Label.font", s);
//		UIManager.put("ComboBox.font", s);
		UIManager.put("Button.font", s);
//		UIManager.put("Menu.font", s);
//		UIManager.put("MenuItem.font", s);

		frame = new JFrame("登录到DBMS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用户单击窗口的关闭按钮时程序执行的操作

		Container content = frame.getContentPane();
		content.setLayout(new GridLayout(3, 2, 20, 20));
		JLb1 = new JLabel("   用户名:");

		JLb2 = new JLabel("   密 码:");
		Jtfld1 = new JTextField();
		Jtfld2 = new JPasswordField();
		Ok_btn = new JButton("登录");
		register_btn = new JButton("注册");

		Ok_btn.addActionListener(new ActionHandler());
		register_btn.addActionListener(new ActionHandler());
		content.add(JLb1);
		content.add(Jtfld1);

		content.add(JLb2);
		content.add(Jtfld2);
		content.add(Ok_btn);
		content.add(register_btn);//有顺序要求

		frame.pack();//调整此窗口的大小，以适合其子组件的首选大小和布局
		frame.setLocationRelativeTo(null);//设置窗口相对于指定组件的位置。 如果组件当前未显示，或者为 null，则此窗口将置于屏幕的中央
		frame.setSize(300, 200);//指定大小
		frame.setVisible(true);//可见

	}

	class ActionHandler implements ActionListener {//添加监听事件
		public void actionPerformed(ActionEvent e) {
			String str1, str2;
			Object obj = e.getSource();
			str1 = Jtfld1.getText().trim();
			str2 = Jtfld2.getText().trim();
			if (obj.equals(Ok_btn)) {//登录
				//////////////////////////////////////////////// 用来控制输入不能为空
				if (str1.equals("")) {
					JOptionPane.showMessageDialog(frame, "用户名不能为空!");
					return;
				}
				if (str2.equals("")) {
					JOptionPane.showMessageDialog(frame, "密码不能为空!");
					return;
				}
//////////////////////////////////////////////////

				if (UserUtils.login(str1, str2).equals("ok")) {
					MainFrame mf = new MainFrame();
					mf.jlinfo.setText("用户名:" + str1);
					mf.setUsername(str1);
					System.out.println(str1);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "用户名或密码错误");
					return;
				}
			} else if (obj.equals(register_btn)) {// 注册
////////////////////////////////////////////////用来控制输入不能为空
				if (str1.equals("")) {
					JOptionPane.showMessageDialog(frame, "用户名不能为空!");
					return;
				}
				if (str2.equals("")) {
					JOptionPane.showMessageDialog(frame, "密码不能为空!");
					return;
				}
//////////////////////////////////////////////////
				if (UserUtils.register(str1, str2).equals("ok")) {
					MainFrame mf = new MainFrame();
					mf.jlinfo.setText("用户名:" + str1);
					mf.setUsername(str1);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "用户名重复");
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