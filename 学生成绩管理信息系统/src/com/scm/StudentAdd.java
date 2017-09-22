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
    //无参构造函数
	public StudentAdd() {
		try {
			initComponents();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//获取电脑屏幕大小，并居中显示
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 300);
	}
	//初始化组件
	private void initComponents() throws SQLException {
		label_sno = new JLabel();
		snoField = new JTextField();  //学号输入框
		label_sname = new JLabel(); 
		snameField = new JTextField(); // 姓名输入框
		label_classes = new JLabel();
		classesField = new JTextField(); //班级输入框
		label_cname = new JLabel();
	//	courseCom = new JTextField(); //课程输入框
		courseCom=new JComboBox();//课程选择框
		label_core = new JLabel();
		coreField = new JTextField(); //分数输入框
		btn_add = new JButton();    //添加按钮
		btn_reset = new JButton();   //重置按钮
		
		setClosable(true);
		setIconifiable(true);
		setTitle("添加学生信息");
		label_sno.setFont(new Font("仿宋", 0, 14));
		label_sno.setText("学号");
		label_sname.setFont(new Font("仿宋", 0, 14));
		label_sname.setText("姓名");
		label_classes.setFont(new Font("仿宋", 0, 14));
		label_classes.setText("班级");
		label_cname.setFont(new Font("仿宋", 0, 14));
		label_cname.setText("课程名称");
		label_core.setFont(new Font("仿宋", 0, 14));
		label_core.setText(" 分数");
		
		btn_add.setFont(new Font("楷体", 0, 14));
		btn_add.setIcon(new ImageIcon("images/add.png")); 
		btn_add.setText("添加");
		btn_reset.setFont(new Font("楷体", 0, 14));
		btn_reset.setIcon(new ImageIcon("images/reset.png")); // NOI18N
		btn_reset.setText("重置");
		
		//动态生成课程列表
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
		courseCom.setFont(new Font("楷体",0,12));
		
		//布局
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
		//添加按钮事件处理
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_addActionPerformed(evt);
			}
		});		
		//重置按钮事件处理
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_resetActionPerformed(evt);
			}
		});		
		pack();
	}
	//添加按钮事件
	private void btn_addActionPerformed(ActionEvent evt) {
		String sno=this.snoField.getText();
		String name=this.snameField.getText();
		String classes=this.classesField.getText();
		String course=(String) this.courseCom.getSelectedItem();
		String core=this.coreField.getText();
		if(StringUtils.isBlank(sno)){
			JOptionPane.showMessageDialog(null, "学号不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(StringUtils.isBlank(name)){
			JOptionPane.showMessageDialog(null, "姓名不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(StringUtils.isBlank(classes)){
			JOptionPane.showMessageDialog(null, "班级不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
	
		Student stu=new Student(sno, name, classes, course, core);
		UserDao userDao=new UserDao();
		int result=userDao.addStudent(stu);
		if(result ==0){
			JOptionPane.showMessageDialog(null, "添加学生失败");
		}else{
			JOptionPane.showMessageDialog(null, "添加成功");
			this.reset();
		}
	}
	//重置按钮事件
	private void btn_resetActionPerformed(ActionEvent evt) {
		this.reset();
	}
	//清空输入框
	private void reset(){
		this.snoField.setText("");
		this.snameField.setText("");
		this.classesField.setText("");
		//this.courseCom.setText("");
		this.coreField.setText("");
	}
}