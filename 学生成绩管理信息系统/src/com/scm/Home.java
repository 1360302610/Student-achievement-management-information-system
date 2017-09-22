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
	//无参构造函数
	public Home() {
		initComponents();
		//设置最大化
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	//主函数
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Home().setVisible(true);
			}
		});
	}
	//初始化组件
	private void initComponents() {
		desktop = new JDesktopPane(); //桌子
		jMenuBar1 = new JMenuBar();   //菜单栏
		Menu_base = new JMenu();     //基本功能菜单
		addStudent = new JMenuItem();  //添加菜单项
		updateStudent = new JMenuItem();  //修改菜单项
		deleteStudent = new JMenuItem();  //删除菜单项
		exit = new JMenuItem();       //退出系统菜单项
		Menu_height = new JMenu();   //高级功能菜单
		
		addCouse=new JMenuItem();//添加课程 
		seacher = new JMenuItem();   //模糊查询
		order = new JMenuItem();    //排序
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("学生成绩管理信息系统");
		
		Menu_base.setText("基本功能");
		Menu_base.setFont(new Font("楷体", 0, 14));
		addStudent.setFont(new Font("楷体", 0, 14));
		addStudent.setIcon(new ImageIcon("images/user_add.png"));
		addStudent.setText("添加");
		
		Menu_base.add(addStudent);
		updateStudent.setFont(new Font("楷体", 0, 14));
		updateStudent.setIcon(new ImageIcon("images/user_edit.png"));
		updateStudent.setText("修改");

		Menu_base.add(updateStudent);
		deleteStudent.setFont(new Font("楷体", 0, 14));
		deleteStudent.setIcon(new ImageIcon("images/user_delete.png"));
		deleteStudent.setText("删除");

		Menu_base.add(deleteStudent);
		exit.setFont(new Font("楷体", 0, 14));
		exit.setIcon(new ImageIcon("images/logout.png"));
		exit.setText("退出系统");

		Menu_base.add(exit);
		jMenuBar1.add(Menu_base);
		Menu_height.setText("高级功能");
		Menu_height.setFont(new Font("楷体", 0, 14));
		
		addCouse.setFont(new Font("楷体",0,14));
		addCouse.setIcon(new ImageIcon("images/add.png"));
		addCouse.setText("添加课程");
		Menu_height.add(addCouse);

		seacher.setFont(new Font("楷体", 0, 14));
		seacher.setIcon(new ImageIcon("images/find.png")); 
		seacher.setText("模糊查询");
		Menu_height.add(seacher);
		
		order.setFont(new Font("楷体", 0, 14));
		order.setIcon(new ImageIcon("images/sort.png"));
		order.setText("排序");
		Menu_height.add(order);
		jMenuBar1.add(Menu_height);
		setJMenuBar(jMenuBar1);
		
		//布局
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
		
		
		//添加课程事件处理
		addCouse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addCouseActionPerformed(e);
			}

			
			
		});
		
		//添加学生事件处理
		addStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addStudentActionPerformed(evt);
			}
		});
		//修改学生事件处理
		updateStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateStudentActionPerformed(evt);
			}
		});
		//删除学生事件处理
		deleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteStudentActionPerformed(evt);
			}
		});
		//退出系统事件处理
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});
		//模糊查询事件处理
		seacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				seacherActionPerformed(evt);
			}
		});
		//排序事件处理
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				orderActionPerformed(evt);
			}
		});		
		pack();
	}
	
	//添加课程事件
	protected void addCouseActionPerformed(ActionEvent e) {
		String input=JOptionPane.showInputDialog("请输入你要添加的课程名称");
		if(input==null){
			return;
		}
		if("".equals(input)){
			JOptionPane.showMessageDialog(null, "你输入的课程名称不能为空");
			return;
		}
		//处理业务逻辑，将课程名称插入到数据库中 
		UserDao userDao=new UserDao();
		//先从数据库中查询是否存在该课程，如果存在，不能再添加该课程
		boolean b=userDao.isCourseByCname(input);
		if(b){
			JOptionPane.showMessageDialog(null, "该课程已经存在，添加失败");
			return;
		}
		//可以添加了
		userDao.addCourse(input);
		System.out.println(input);
	}
	//排序菜单项事件
	private void orderActionPerformed(ActionEvent evt) {
		GeneralSort gs=new GeneralSort();
		gs.setVisible(true);
		this.desktop.add(gs);//将排序界面添加到主界面
	}
	//模糊搜索菜单项事件
	private void seacherActionPerformed(ActionEvent evt) {
		Search search = new Search();
		search.setVisible(true);
		this.desktop.add(search);//将搜索界面添加到主界面
	}
	//删除学生菜单项事件
	private void deleteStudentActionPerformed(ActionEvent evt) {
		StudentDelete sd = new StudentDelete();
		sd.setVisible(true);
		this.desktop.add(sd);//将删除界面添加到主界面
	}
	//修改学生信息
	private void updateStudentActionPerformed(ActionEvent evt) {
		String input = JOptionPane.showInputDialog(null, "要修改学生的学号:", "请输入",
				JOptionPane.INFORMATION_MESSAGE);
		//没输入是""  取消是null 
		System.out.println(input);
		if ("".equals(input)) {
			JOptionPane.showMessageDialog(null, "学号不能为空");
			return;
		}
		if (input == null) {
			return;
		}
		Student s = userDao.findStudent(input);
		if (s == null) {
			JOptionPane.showMessageDialog(null, "学号无效");
			return;
		}
		StudentUpdate su = new StudentUpdate(s);
		su.setVisible(true);
		this.desktop.add(su);//将修改界面添加到主界面
	}
	//添加学生信息事件
	private void addStudentActionPerformed(ActionEvent evt) {
		StudentAdd sa = new StudentAdd();
		sa.setVisible(true);
		this.desktop.add(sa);
	}
	//退出系统菜单项事件
	private void exitActionPerformed(ActionEvent evt) {
		int a = JOptionPane.showConfirmDialog(null, "确认退出系统?", "提示",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (a == 0) {
			this.dispose();
		}
	}

	
}