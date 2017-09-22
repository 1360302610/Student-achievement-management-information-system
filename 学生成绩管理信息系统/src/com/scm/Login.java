package com.scm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.StringUtils;
public class Login extends JFrame {
	private JButton btn_login;
	private JButton btn_reset;
	private JLabel label_pwd;
	private JLabel label_top;
	private JLabel label_username;
	private JPasswordField password;
	private JTextField username;
	//主函数
		public static void main(String args[]) {
			new Login().setVisible(true);
		 }
	//无参构造函数
	public Login() {
		initComponents();
		this.setLocationRelativeTo(null);//居中
	}
	//初始化组件
	private void initComponents() {
		label_top = new JLabel();
		label_username = new JLabel();
		label_pwd = new JLabel();
		
		username = new JTextField();//用户名输入框
		password = new JPasswordField();//密码输入框
		btn_login = new JButton();//登录按钮
		btn_reset = new JButton();//取消按钮
		
		//设置窗体关闭系统退出
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("登录界面");
		label_top.setFont(new Font("仿宋", 0, 18));
		label_top.setIcon(new ImageIcon("images/title.png"));
		label_top.setText("学生成绩管理信息系统");
		label_username.setFont(new Font("楷体", 0, 14));
		label_username.setIcon(new ImageIcon("images/user.png")); 
		label_username.setText("用户名");
		label_pwd.setFont(new Font("楷体", 0, 14));
		label_pwd.setIcon(new ImageIcon("images/key.png")); 
		label_pwd.setText("密 码 ");
		btn_login.setFont(new Font("楷体", 0, 14));
		btn_login.setText("登录");
		btn_reset.setFont(new Font("楷体", 0, 14));
		btn_reset.setText("取消");
		
		//登录按钮事件处理
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_loginActionPerformed(evt);
			}
		});
		//取消按钮事件处理
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_resetActionPerformed(evt);
			}
		});
		
		//布局
		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(101, 101, 101)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(label_top)
												.addGroup(
														layout.createParallelGroup(
																GroupLayout.Alignment.TRAILING,
																false)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						btn_login)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						btn_reset))
																.addGroup(
																		GroupLayout.Alignment.LEADING,
																		layout.createSequentialGroup()
																				.addGroup(
																						layout.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																								.addComponent(
																										label_pwd)
																								.addComponent(
																										label_username))
																				.addGap(40,
																						40,
																						40)
																				.addGroup(
																						layout.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																								.addComponent(
																										username)
																								.addComponent(
																										password,
																										GroupLayout.DEFAULT_SIZE,
																										107,
																										Short.MAX_VALUE)))))
								.addGap(99, 99, 99)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(33, 33, 33)
								.addComponent(label_top,
										GroupLayout.PREFERRED_SIZE,
										54,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														username,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(label_username))
								.addGap(28, 28, 28)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(label_pwd)
												.addComponent(
														password,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(32, 32, 32)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(btn_reset)
												.addComponent(btn_login))
								.addContainerGap(66, Short.MAX_VALUE)));
		pack();
	}
	
	//取消按钮事件处理
	private void btn_resetActionPerformed(ActionEvent evt) {
		username.setText("");
		password.setText("");
	}
	
	//登录按钮事件处理
	private void btn_loginActionPerformed(ActionEvent evt) {
		String sno = username.getText();
		String pwd = String.valueOf(password.getPassword());
		if (StringUtils.isBlank(sno)) {
			JOptionPane.showMessageDialog(null, "用户名不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (StringUtils.isBlank(pwd)) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		UserDao userDao = new UserDao();
		//判断用户身份
		if("admin".equals(StringUtils.trim(sno))){
			//管理员
			boolean b= userDao.isStudent(sno,pwd);
			if(b){
				//登录成功
				this.dispose();
				new Home().setVisible(true);
				return;
			}else{
				JOptionPane.showMessageDialog(null, "管理员账户或密码错误");
				return;
			}
		}else{
			//普通学生
			boolean b= userDao.isStudent(sno,pwd);
			if(b){
				//登录成功
				new Home2(sno).setVisible(true);
				this.dispose();
				return;
			}else{
				JOptionPane.showMessageDialog(null, "用户名或密码错误");
				return;
			}
			
		}
	}
}