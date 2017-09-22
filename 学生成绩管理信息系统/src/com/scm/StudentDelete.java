package com.scm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.apache.commons.lang.StringUtils;
public class StudentDelete extends JInternalFrame {
	private JComboBox JComBox_delete;
	private JButton btn_delete;
	private JTextField inputTxt;
	private UserDao userDao=new UserDao();
	//无参构造函数
	public StudentDelete() {
		initComponents();
		//界面居中
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 200, dimension.height / 2 - 200);
	}
	//初始化组件
	private void initComponents() {
		JComBox_delete = new JComboBox();  //复选框
		inputTxt = new JTextField();   //输入框
		btn_delete = new JButton();    //删除按钮
		setClosable(true);    //设置窗体可关闭
		setIconifiable(true);   //设置窗体可最小化
		setTitle("删除学生界面");
		JComBox_delete.setFont(new Font("仿宋", 0, 12));
		JComBox_delete.setModel(new DefaultComboBoxModel(
				new String[] { "-请选择-", "学号", "姓名" }));
		inputTxt.setFont(new Font("仿宋", 0, 12));
		btn_delete.setFont(new Font("楷体", 0, 12));
		btn_delete.setIcon(new ImageIcon("images/delete.png")); 
		btn_delete.setText("删除");
		
		//布局
		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(77, 77,
																		77)
																.addComponent(
																		JComBox_delete,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(37, 37,
																		37)
																.addComponent(
																		inputTxt,
																		GroupLayout.PREFERRED_SIZE,
																		74,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(126,
																		126,
																		126)
																.addComponent(
																		btn_delete)))
								.addContainerGap(79, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(61, 61, 61)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														JComBox_delete,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														inputTxt,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(48, 48, 48).addComponent(btn_delete)
								.addContainerGap(63, Short.MAX_VALUE)));
		//删除按钮事件处理
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_deleteActionPerformed(evt);
			}
		});
		pack();
	}
	//删除按钮事件
	private void btn_deleteActionPerformed(ActionEvent evt) {
		//获取复选框的模型
		ComboBoxModel model = this.JComBox_delete.getModel();
		Object selectedItem = model.getSelectedItem();
		if("-请选择-".equals(selectedItem)){
			return;
		}else{
			//判断用户选的是学号
			if("学号".equals(selectedItem)){
				String sno=this.inputTxt.getText();
				if(StringUtils.isBlank(sno)){
					JOptionPane.showMessageDialog(null, "请输入学号", "提示",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				//查询用户是否存在
				Student student = userDao.findStudent(sno);
				if(student!=null){
					//如果学号存在，则删除该学生
					boolean b=userDao.deleteBySno(sno);
					if(b){
						JOptionPane.showMessageDialog(null, "删除成功");
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				}else{
					JOptionPane.showMessageDialog(null, "该学生不存在");
					return;
				}
			}else{
				//用户选的是姓名
				String name=this.inputTxt.getText();
				if(StringUtils.isBlank(name)){
					JOptionPane.showMessageDialog(null, "请输入学生姓名", "提示",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				boolean hasStudent = userDao.findStudentBySname(name);
				if(hasStudent){
					//如果学号存在，则删除该学生
					boolean b=userDao.deleteBySname(name);
					if(b){
						JOptionPane.showMessageDialog(null, "删除成功");
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				}else{
					JOptionPane.showMessageDialog(null, "该学生不存在");
					return;
				}
			}
		}
	}
}