package com.scm;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
public class StudentAdd extends JInternalFrame {
	private JButton btn_add;
	private JButton btn_reset;
	private JTextField classesField;
	private JTextField coreField;
	
	private JComboBox courseCom;
//	private JTextField courseCom;
	
	private JLabel label_classes;
	private JLabel label_cname;
	private JLabel label_core;
	private JLabel label_sname;
	private JLabel label_sno;
	private JTextField snameField;
	private JTextField snoField; 
	private ResultSet rs=null;
	private Connection conn=null;
    //�޲ι��캯��
	public StudentAdd() {
		try {
			initComponents();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//��ȡ������Ļ��С����������ʾ
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 300);
	}
	//��ʼ�����
	private void initComponents() throws SQLException {
		label_sno = new JLabel();
		snoField = new JTextField();  //ѧ�������
		label_sname = new JLabel(); 
		snameField = new JTextField(); // ���������
		label_classes = new JLabel();
		classesField = new JTextField(); //�༶�����
		label_cname = new JLabel();
	//	courseCom = new JTextField(); //�γ������
		courseCom=new JComboBox();//�γ�ѡ���
		label_core = new JLabel();
		coreField = new JTextField(); //���������
		btn_add = new JButton();    //��Ӱ�ť
		btn_reset = new JButton();   //���ð�ť
		
		setClosable(true);
		setIconifiable(true);
		setTitle("���ѧ����Ϣ");
		label_sno.setFont(new Font("����", 0, 14));
		label_sno.setText("ѧ��");
		label_sname.setFont(new Font("����", 0, 14));
		label_sname.setText("����");
		label_classes.setFont(new Font("����", 0, 14));
		label_classes.setText("�༶");
		label_cname.setFont(new Font("����", 0, 14));
		label_cname.setText("�γ�����");
		label_core.setFont(new Font("����", 0, 14));
		label_core.setText(" ����");
		
		btn_add.setFont(new Font("����", 0, 14));
		btn_add.setIcon(new ImageIcon("images/add.png")); 
		btn_add.setText("���");
		btn_reset.setFont(new Font("����", 0, 14));
		btn_reset.setIcon(new ImageIcon("images/reset.png")); // NOI18N
		btn_reset.setText("����");
		
		//��̬���ɿγ��б�
		UserDao userDao=new UserDao();
		conn=DBUtils.getConnection();
		rs=userDao.findAllCourse(conn);
		Vector v=new Vector();
		while(rs.next()){
			v.add(rs.getString("cname"));
		}
		DBUtils.closeAll(rs, null, conn);
		DefaultComboBoxModel model=new DefaultComboBoxModel(v);
		courseCom.setModel(model);
		courseCom.setFont(new Font("����",0,12));
		
		//����
		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(55, 55, 55)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		label_classes)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						label_sname)
																				.addComponent(
																						label_sno))
																.addGap(7, 7, 7)))
								.addGap(29, 29, 29)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		btn_add)
																.addGap(100,
																		100,
																		100)
																.addComponent(
																		btn_reset))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						snoField,
																						GroupLayout.PREFERRED_SIZE,
																						85,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						classesField,
																						GroupLayout.Alignment.TRAILING,
																						GroupLayout.DEFAULT_SIZE,
																						87,
																						Short.MAX_VALUE)
																				.addComponent(
																						snameField,
																						GroupLayout.Alignment.TRAILING))
																.addGap(46, 46,
																		46)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						label_cname)
																				.addComponent(
																						label_core))
																.addGap(30, 30,
																		30)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						coreField)
																				.addComponent(
																						courseCom,
																						GroupLayout.PREFERRED_SIZE,
																						94,
																						GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(63, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(35, 35,
																		35)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						snoField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						label_sno))
																.addGap(32, 32,
																		32)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_sname)
																				.addComponent(
																						snameField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(36, 36,
																		36)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_classes)
																				.addComponent(
																						classesField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(53, 53,
																		53))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap(
																		94,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_cname)
																				.addComponent(
																						courseCom,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(32, 32,
																		32)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_core)
																				.addComponent(
																						coreField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(53, 53,
																		53)))
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														btn_add,
														GroupLayout.PREFERRED_SIZE,
														25,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btn_reset,
														GroupLayout.PREFERRED_SIZE,
														25,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(65, Short.MAX_VALUE)));
		//��Ӱ�ť�¼�����
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_addActionPerformed(evt);
			}
		});		
		//���ð�ť�¼�����
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_resetActionPerformed(evt);
			}
		});		
		pack();
	}
	//��Ӱ�ť�¼�
	private void btn_addActionPerformed(ActionEvent evt) {
		String sno=this.snoField.getText();
		String name=this.snameField.getText();
		String classes=this.classesField.getText();
		String course=(String) this.courseCom.getSelectedItem();
		String core=this.coreField.getText();
		if(StringUtils.isBlank(sno)){
			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(StringUtils.isBlank(name)){
			JOptionPane.showMessageDialog(null, "��������Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(StringUtils.isBlank(classes)){
			JOptionPane.showMessageDialog(null, "�༶����Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
	
		Student stu=new Student(sno, name, classes, course, core);
		UserDao userDao=new UserDao();
		int result=userDao.addStudent(stu);
		if(result ==0){
			JOptionPane.showMessageDialog(null, "���ѧ��ʧ��");
		}else{
			JOptionPane.showMessageDialog(null, "��ӳɹ�");
			this.reset();
		}
	}
	//���ð�ť�¼�
	private void btn_resetActionPerformed(ActionEvent evt) {
		this.reset();
	}
	//��������
	private void reset(){
		this.snoField.setText("");
		this.snameField.setText("");
		this.classesField.setText("");
		//this.courseCom.setText("");
		this.coreField.setText("");
	}
}