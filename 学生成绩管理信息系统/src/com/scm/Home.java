package com.scm;
import javax.swing.*;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.event.*;
public class Home extends JFrame {
	private JMenu Menu_base;
	private JMenu Menu_height;
	private JMenuItem addStudent;
	private JMenuItem deleteStudent;
	private JDesktopPane desktop;
	private JMenuItem exit;
	private JMenuBar jMenuBar1;
	private JMenuItem addCouse;
	private JMenuItem order;
	private JMenuItem seacher;
	private JMenuItem updateStudent;
	private UserDao userDao = new UserDao();
	//�޲ι��캯��
	public Home() {
		initComponents();
		//�������
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	//������
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Home().setVisible(true);
			}
		});
	}
	//��ʼ�����
	private void initComponents() {
		desktop = new JDesktopPane(); //����
		jMenuBar1 = new JMenuBar();   //�˵���
		Menu_base = new JMenu();     //�������ܲ˵�
		addStudent = new JMenuItem();  //��Ӳ˵���
		updateStudent = new JMenuItem();  //�޸Ĳ˵���
		deleteStudent = new JMenuItem();  //ɾ���˵���
		exit = new JMenuItem();       //�˳�ϵͳ�˵���
		Menu_height = new JMenu();   //�߼����ܲ˵�
		
		addCouse=new JMenuItem();//��ӿγ� 
		seacher = new JMenuItem();   //ģ����ѯ
		order = new JMenuItem();    //����
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ѧ���ɼ�������Ϣϵͳ");
		
		Menu_base.setText("��������");
		Menu_base.setFont(new Font("����", 0, 14));
		addStudent.setFont(new Font("����", 0, 14));
		addStudent.setIcon(new ImageIcon("images/user_add.png"));
		addStudent.setText("���");
		
		Menu_base.add(addStudent);
		updateStudent.setFont(new Font("����", 0, 14));
		updateStudent.setIcon(new ImageIcon("images/user_edit.png"));
		updateStudent.setText("�޸�");

		Menu_base.add(updateStudent);
		deleteStudent.setFont(new Font("����", 0, 14));
		deleteStudent.setIcon(new ImageIcon("images/user_delete.png"));
		deleteStudent.setText("ɾ��");

		Menu_base.add(deleteStudent);
		exit.setFont(new Font("����", 0, 14));
		exit.setIcon(new ImageIcon("images/logout.png"));
		exit.setText("�˳�ϵͳ");

		Menu_base.add(exit);
		jMenuBar1.add(Menu_base);
		Menu_height.setText("�߼�����");
		Menu_height.setFont(new Font("����", 0, 14));
		
		addCouse.setFont(new Font("����",0,14));
		addCouse.setIcon(new ImageIcon("images/add.png"));
		addCouse.setText("��ӿγ�");
		Menu_height.add(addCouse);

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
		
		
		//��ӿγ��¼�����
		addCouse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addCouseActionPerformed(e);
			}

			
			
		});
		
		//���ѧ���¼�����
		addStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addStudentActionPerformed(evt);
			}
		});
		//�޸�ѧ���¼�����
		updateStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateStudentActionPerformed(evt);
			}
		});
		//ɾ��ѧ���¼�����
		deleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteStudentActionPerformed(evt);
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
	
	//��ӿγ��¼�
	protected void addCouseActionPerformed(ActionEvent e) {
		String input=JOptionPane.showInputDialog("��������Ҫ��ӵĿγ�����");
		if(input==null){
			return;
		}
		if("".equals(input)){
			JOptionPane.showMessageDialog(null, "������Ŀγ����Ʋ���Ϊ��");
			return;
		}
		//����ҵ���߼������γ����Ʋ��뵽���ݿ��� 
		UserDao userDao=new UserDao();
		//�ȴ����ݿ��в�ѯ�Ƿ���ڸÿγ̣�������ڣ���������Ӹÿγ�
		boolean b=userDao.isCourseByCname(input);
		if(b){
			JOptionPane.showMessageDialog(null, "�ÿγ��Ѿ����ڣ����ʧ��");
			return;
		}
		//���������
		userDao.addCourse(input);
		System.out.println(input);
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
	//ɾ��ѧ���˵����¼�
	private void deleteStudentActionPerformed(ActionEvent evt) {
		StudentDelete sd = new StudentDelete();
		sd.setVisible(true);
		this.desktop.add(sd);//��ɾ��������ӵ�������
	}
	//�޸�ѧ����Ϣ
	private void updateStudentActionPerformed(ActionEvent evt) {
		String input = JOptionPane.showInputDialog(null, "Ҫ�޸�ѧ����ѧ��:", "������",
				JOptionPane.INFORMATION_MESSAGE);
		//û������""  ȡ����null 
		System.out.println(input);
		if ("".equals(input)) {
			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ��");
			return;
		}
		if (input == null) {
			return;
		}
		Student s = userDao.findStudent(input);
		if (s == null) {
			JOptionPane.showMessageDialog(null, "ѧ����Ч");
			return;
		}
		StudentUpdate su = new StudentUpdate(s);
		su.setVisible(true);
		this.desktop.add(su);//���޸Ľ�����ӵ�������
	}
	//���ѧ����Ϣ�¼�
	private void addStudentActionPerformed(ActionEvent evt) {
		StudentAdd sa = new StudentAdd();
		sa.setVisible(true);
		this.desktop.add(sa);
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