package com.scm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.StringUtils;
public class StudentUpdate extends JInternalFrame {
	private JButton btn_canle;
	private JButton btn_update;
	private JTextField classesField;
	private JLabel label_classes;
	private JLabel label_name;
	private JLabel label_sno;
	private JTextField nameField;
	private JTextField snoField;
	
	private UserDao userDao=new UserDao();
	private Student stu;
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public Student getStu() {
		return stu;
	}
	
	//有参构造函数
	public StudentUpdate(Student stu) {
		initComponents(stu);
		//界面居中
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 300);
	}
	//初始化组件
	private void initComponents(Student stu) {
		label_sno = new JLabel();
		snoField = new JTextField(); //学号输入框
		label_name = new JLabel();
		nameField = new JTextField();  //姓名输入框
		label_classes = new JLabel();
		classesField = new JTextField(); //班级输入框
		btn_update = new JButton();   //修改按钮
		btn_canle = new JButton();    //取消按钮
		//一开始显示数据
		snoField.setText(stu.getSno());
		nameField.setText(stu.getName());
		classesField.setText(stu.getClasses());
		setClosable(true);  //可关闭
		setIconifiable(true);  //最小化
		setTitle("学生信息修改界面");
		label_sno.setFont(new Font("楷体", 0, 14));
		label_sno.setText("学号");
		snoField.setEditable(false);
		label_name.setFont(new Font("楷体", 0, 14));
		label_name.setText("姓名");
		label_classes.setFont(new Font("楷体", 0, 14));
		label_classes.setText("班级");
		btn_update.setFont(new Font("楷体", 0, 12));
		btn_update.setIcon(new ImageIcon("images/update.png")); 
		btn_update.setText("修改");

		btn_canle.setFont(new Font("楷体", 0, 12));
		btn_canle.setIcon(new ImageIcon("images/canle.png")); 
		btn_canle.setText("取消");
		
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
												GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(105,
																		105,
																		105)
																.addComponent(
																		btn_update)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		btn_canle))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(115,
																		115,
																		115)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						label_classes)
																				.addComponent(
																						label_name)
																				.addComponent(
																						label_sno))
																.addGap(44, 44,
																		44)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						classesField)
																				.addComponent(
																						nameField,
																						GroupLayout.Alignment.LEADING)
																				.addComponent(
																						snoField,
																						GroupLayout.Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						113,
																						Short.MAX_VALUE))))
								.addContainerGap(94, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(40, 40, 40)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														label_sno,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														snoField,
														GroupLayout.DEFAULT_SIZE,
														23, Short.MAX_VALUE))
								.addGap(28, 28, 28)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														label_name,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(nameField))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										30, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING,
												false)
												.addComponent(
														label_classes,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(classesField))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										47, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(btn_update)
												.addComponent(btn_canle))
								.addGap(45, 45, 45)));
		
		//修改事件处理
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_updateActionPerformed(evt);
			}
		});
		//取消事件处理
		btn_canle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_canleActionPerformed(evt);
			}
		});
		pack();
	}
	//取消按钮事件
	private void btn_canleActionPerformed(ActionEvent evt) {
		this.dispose();
	}
	//修改按钮事件
	private void btn_updateActionPerformed(ActionEvent evt) {
		String name = nameField.getText();
		String classes = classesField.getText();
		String sno = snoField.getText();
		if (StringUtils.isBlank(name)) {
			JOptionPane.showMessageDialog(null, "姓名不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (StringUtils.isBlank(classes)) {
			JOptionPane.showMessageDialog(null, "班级不能为空", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		boolean b = userDao.updateStudent(sno, name, classes);
		if (b) {
			JOptionPane.showMessageDialog(null, "修改成功", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "修改失败", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}