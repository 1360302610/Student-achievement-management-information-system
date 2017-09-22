package com.scm;
import javax.swing.*;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.event.*;
public class Home2 extends JFrame {
	private JMenu Menu_base;
	private JMenu Menu_height;
	private JDesktopPane desktop;
	private JMenuItem exit;
	private JMenuBar jMenuBar1;
	private JMenuItem order;
	private JMenuItem seacher;
	private JMenuItem updatePwd;
	private UserDao userDao = new UserDao();
	private String sno;
	//�޲ι��캯��
	public Home2(String sno) {
		initComponents();
		this.sno=sno;
		//�������
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	//������
	public static void main(String args[]) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				new Home2("").setVisible(true);
//			}
//		});
	}
	//��ʼ�����
	private void initComponents() {
		desktop = new JDesktopPane(); //����
		jMenuBar1 = new JMenuBar();   //�˵���
		Menu_base = new JMenu();     //�������ܲ˵�
		updatePwd = new JMenuItem();  //�޸Ĳ˵���
		exit = new JMenuItem();       //�˳�ϵͳ�˵���
		Menu_height = new JMenu();   //�߼����ܲ˵�
		seacher = new JMenuItem();   //ģ����ѯ
		order = new JMenuItem();    //����
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ѧ���ɼ�������Ϣϵͳ");
		
		Menu_base.setText("��������");
		Menu_base.setFont(new Font("����", 0, 14));
		updatePwd.setFont(new Font("����", 0, 14));
		updatePwd.setIcon(new ImageIcon("images/user_edit.png"));
		updatePwd.setText("�޸�����");
		Menu_base.add(updatePwd);
		exit.setFont(new Font("����", 0, 14));
		exit.setIcon(new ImageIcon("images/logout.png"));
		exit.setText("�˳�ϵͳ");

		Menu_base.add(exit);
		jMenuBar1.add(Menu_base);
		Menu_height.setText("�߼�����");
		Menu_height.setFont(new Font("����", 0, 14));
		

		seacher.setFont(new Font("����", 0, 14));
		seacher.setIcon(new ImageIcon("images/find.png")); 
		seacher.setText("ģ����ѯ");
		Menu_height.add(seacher);
		order.setFont(new Font("����", 0, 14));
		order.setIcon(new ImageIcon("images/sort.png"));
		order.setText("����");
		Menu_height.add(order);
		jMenuBar1.add(Menu_height);
		setJMenuBar(jMenuBar1);
		
		//����
		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(
				desktop, GroupLayout.DEFAULT_SIZE, 644,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(
				desktop, GroupLayout.DEFAULT_SIZE, 338,
				Short.MAX_VALUE));
		
			
		
		//�޸������¼�����
		updatePwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updatePwdActionPerformed(evt);
			}
		});
		
		//�˳�ϵͳ�¼�����
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});
		//ģ����ѯ�¼�����
		seacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				seacherActionPerformed(evt);
			}
		});
		//�����¼�����
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				orderActionPerformed(evt);
			}
		});		
		pack();
	}

	//�޸�����
	protected void updatePwdActionPerformed(ActionEvent evt) {
		//����������
		String pwd=JOptionPane.showInputDialog("������������");
		if(pwd==null){
			return;
		}
		if(pwd.trim().equals("")){
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			return;
		}
		String repwd=JOptionPane.showInputDialog("������ȷ������");
		if(repwd==null){
			return;
		}
		if(!pwd.equals(repwd)){
			JOptionPane.showMessageDialog(null, "�������벻һ��");
			return;
		}
		//����ҵ���޸�����
		boolean b=userDao.editPwd(sno,repwd);
		if(b){
			JOptionPane.showMessageDialog(null, "�����޸ĳɹ�,�����µ�¼");
			this.dispose();
			new Login().setVisible(true);
			return;
		}else{
			JOptionPane.showMessageDialog(null, "�����޸�ʧ��");
			return;
		}
		
	}
	//����˵����¼�
	private void orderActionPerformed(ActionEvent evt) {
		GeneralSort gs=new GeneralSort();
		gs.setVisible(true);
		this.desktop.add(gs);//�����������ӵ�������
	}
	//ģ�������˵����¼�
	private void seacherActionPerformed(ActionEvent evt) {
		Search search = new Search();
		search.setVisible(true);
		this.desktop.add(search);//������������ӵ�������
	}


	//�˳�ϵͳ�˵����¼�
	private void exitActionPerformed(ActionEvent evt) {
		int a = JOptionPane.showConfirmDialog(null, "ȷ���˳�ϵͳ?", "��ʾ",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (a == 0) {
			this.dispose();
		}
	}
}