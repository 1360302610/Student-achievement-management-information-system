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
	//无参构造函数
	public Home2(String sno) {
		initComponents();
		this.sno=sno;
		//设置最大化
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	//主函数
	public static void main(String args[]) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				new Home2("").setVisible(true);
//			}
//		});
	}
	//初始化组件
	private void initComponents() {
		desktop = new JDesktopPane(); //桌子
		jMenuBar1 = new JMenuBar();   //菜单栏
		Menu_base = new JMenu();     //基本功能菜单
		updatePwd = new JMenuItem();  //修改菜单项
		exit = new JMenuItem();       //退出系统菜单项
		Menu_height = new JMenu();   //高级功能菜单
		seacher = new JMenuItem();   //模糊查询
		order = new JMenuItem();    //排序
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("学生成绩管理信息系统");
		
		Menu_base.setText("基本功能");
		Menu_base.setFont(new Font("楷体", 0, 14));
		updatePwd.setFont(new Font("楷体", 0, 14));
		updatePwd.setIcon(new ImageIcon("images/user_edit.png"));
		updatePwd.setText("修改密码");
		Menu_base.add(updatePwd);
		exit.setFont(new Font("楷体", 0, 14));
		exit.setIcon(new ImageIcon("images/logout.png"));
		exit.setText("退出系统");

		Menu_base.add(exit);
		jMenuBar1.add(Menu_base);
		Menu_height.setText("高级功能");
		Menu_height.setFont(new Font("楷体", 0, 14));
		

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
		
			
		
		//修改密码事件处理
		updatePwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updatePwdActionPerformed(evt);
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

	//修改密码
	protected void updatePwdActionPerformed(ActionEvent evt) {
		//输入新密码
		String pwd=JOptionPane.showInputDialog("请输入新密码");
		if(pwd==null){
			return;
		}
		if(pwd.trim().equals("")){
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}
		String repwd=JOptionPane.showInputDialog("请输入确认密码");
		if(repwd==null){
			return;
		}
		if(!pwd.equals(repwd)){
			JOptionPane.showMessageDialog(null, "两次密码不一致");
			return;
		}
		//处理业务，修改密码
		boolean b=userDao.editPwd(sno,repwd);
		if(b){
			JOptionPane.showMessageDialog(null, "密码修改成功,请重新登录");
			this.dispose();
			new Login().setVisible(true);
			return;
		}else{
			JOptionPane.showMessageDialog(null, "密码修改失败");
			return;
		}
		
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


	//退出系统菜单项事件
	private void exitActionPerformed(ActionEvent evt) {
		int a = JOptionPane.showConfirmDialog(null, "确认退出系统?", "提示",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (a == 0) {
			this.dispose();
		}
	}
}