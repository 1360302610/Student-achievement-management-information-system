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
	//������
		public static void main(String args[]) {
			new Login().setVisible(true);
		 }
	//�޲ι��캯��
	public Login() {
		initComponents();
		this.setLocationRelativeTo(null);//����
	}
	//��ʼ�����
	private void initComponents() {
		label_top = new JLabel();
		label_username = new JLabel();
		label_pwd = new JLabel();
		
		username = new JTextField();//�û��������
		password = new JPasswordField();//���������
		btn_login = new JButton();//��¼��ť
		btn_reset = new JButton();//ȡ����ť
		
		//���ô���ر�ϵͳ�˳�
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("��¼����");
		label_top.setFont(new Font("����", 0, 18));
		label_top.setIcon(new ImageIcon("images/title.png"));
		label_top.setText("ѧ���ɼ�������Ϣϵͳ");
		label_username.setFont(new Font("����", 0, 14));
		label_username.setIcon(new ImageIcon("images/user.png")); 
		label_username.setText("�û���");
		label_pwd.setFont(new Font("����", 0, 14));
		label_pwd.setIcon(new ImageIcon("images/key.png")); 
		label_pwd.setText("�� �� ");
		btn_login.setFont(new Font("����", 0, 14));
		btn_login.setText("��¼");
		btn_reset.setFont(new Font("����", 0, 14));
		btn_reset.setText("ȡ��");
		
		//��¼��ť�¼�����
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_loginActionPerformed(evt);
			}
		});
		//ȡ����ť�¼�����
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_resetActionPerformed(evt);
			}
		});
		
		//����
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
	
	//ȡ����ť�¼�����
	private void btn_resetActionPerformed(ActionEvent evt) {
		username.setText("");
		password.setText("");
	}
	
	//��¼��ť�¼�����
	private void btn_loginActionPerformed(ActionEvent evt) {
		String sno = username.getText();
		String pwd = String.valueOf(password.getPassword());
		if (StringUtils.isBlank(sno)) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (StringUtils.isBlank(pwd)) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		UserDao userDao = new UserDao();
		//�ж��û����
		if("admin".equals(StringUtils.trim(sno))){
			//����Ա
			boolean b= userDao.isStudent(sno,pwd);
			if(b){
				//��¼�ɹ�
				this.dispose();
				new Home().setVisible(true);
				return;
			}else{
				JOptionPane.showMessageDialog(null, "����Ա�˻����������");
				return;
			}
		}else{
			//��ͨѧ��
			boolean b= userDao.isStudent(sno,pwd);
			if(b){
				//��¼�ɹ�
				new Home2(sno).setVisible(true);
				this.dispose();
				return;
			}else{
				JOptionPane.showMessageDialog(null, "�û������������");
				return;
			}
			
		}
	}
}