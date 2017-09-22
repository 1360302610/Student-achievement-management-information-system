package com.scm;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
public class Search extends JInternalFrame {
	private JButton btn_search;
	private JTextField classesField;
	private JTextField cnameField;
	private JScrollPane jScrollPane1;
	private JLabel label_classes;
	private JLabel label_cname;
	private JLabel label_name;
	private JLabel label_sno;
	private JTextField nameField;
	private JTextField snoField;
	private JTable table_search;
	private UserDao userDao = new UserDao();
	//无参构造函数
	public Search() {
		initComponents();
		//界面居中
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 340);
		//初始界面显示出数据
		this.fillTable(new Student());
	}
	//填充表格数据
	private void fillTable(Student stu) {
		DefaultTableModel model = (DefaultTableModel)table_search.getModel();
		model.setRowCount(0);//每次从第一行开始填充数据，不然就会追加而不是重写
		//设置表格列宽
		this.table_search.getColumnModel().getColumn(3).setPreferredWidth(130);
		this.table_search.getColumnModel().getColumn(4).setPreferredWidth(30);
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			rs = userDao.findStudentByMany(conn, stu);
			while (rs.next()) {
				Vector v = new Vector();//线程安全
				v.add(rs.getString("sno"));
				v.add(rs.getString("sname"));
				v.add(rs.getString("classes"));
				v.add(rs.getString("cname"));
				v.add(rs.getString("core"));
				model.addRow(v);//添加进表格
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, null, conn);
		}
	}
	//初始化界面
	private void initComponents() {
		label_sno = new JLabel();
		snoField = new JTextField(); //学号输入框
		label_classes = new JLabel();
		classesField = new JTextField(); //班级输入框
		label_name = new JLabel();
		nameField = new JTextField(); //姓名输入框
		label_cname = new JLabel();
		cnameField = new JTextField(); //课程名称输入框
		btn_search = new JButton();
		jScrollPane1 = new JScrollPane();
		table_search = new JTable();  //表格
		setClosable(true);  //设置窗体可关闭
		setIconifiable(true);  //设置窗体可最小化
		setTitle("查询学生界面");
		label_sno.setFont(new Font("仿宋", 0, 14));
		label_sno.setText("学号");
		label_classes.setFont(new Font("仿宋", 0, 14));
		label_classes.setText("班级");
		label_name.setFont(new Font("仿宋", 0, 14));
		label_name.setText("学生姓名");
		label_cname.setFont(new Font("仿宋", 0, 14));
		label_cname.setText("课程名称");
		btn_search.setFont(new Font("仿宋", 0, 14));
		btn_search.setIcon(new ImageIcon("images/search.png")); // NOI18N
		btn_search.setText("查询");

		table_search.setFont(new Font("楷体", 0, 14));
		//表格模型
		table_search.setModel(new DefaultTableModel(
				new Object[][] {
				}, new String[] { "学号", "姓名", "班级", "课程名称", "分数" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false,
					false };
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		//将表格设置为滚动条模式
		jScrollPane1.setViewportView(table_search);
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
																.addGap(75, 75,
																		75)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						label_sno)
																				.addComponent(
																						label_classes))
																.addGap(31, 31,
																		31)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						classesField)
																				.addComponent(
																						snoField,
																						GroupLayout.DEFAULT_SIZE,
																						84,
																						Short.MAX_VALUE))
																.addGap(55, 55,
																		55)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						label_name)
																				.addComponent(
																						label_cname))
																.addGap(30, 30,
																		30)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						cnameField)
																				.addComponent(
																						nameField,
																						GroupLayout.DEFAULT_SIZE,
																						86,
																						Short.MAX_VALUE))
																.addGap(61, 61,
																		61)
																.addComponent(
																		btn_search))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(48, 48,
																		48)
																.addComponent(
																		jScrollPane1,
																		GroupLayout.PREFERRED_SIZE,
																		553,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(48, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(36, 36, 36)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(label_sno)
												.addComponent(label_name)
												.addComponent(
														nameField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														snoField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(29, 29, 29)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
												.addComponent(label_classes)
												.addGroup(
														layout.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
																.addComponent(
																		label_cname)
																.addComponent(
																		cnameField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		classesField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		btn_search)))
								.addGap(37, 37, 37)
								.addComponent(jScrollPane1,
										GroupLayout.PREFERRED_SIZE,
										369,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(51, Short.MAX_VALUE)));
		//查询按钮事件处理
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_searchActionPerformed(evt);
			}
		});
		pack();
	}
	//查询按钮事件
	private void btn_searchActionPerformed(ActionEvent evt) {
		String sno = this.snoField.getText();
		String name = this.nameField.getText();
		String classes = this.classesField.getText();
		String cname = this.cnameField.getText();
		Student stu = new Student(sno, name, classes, cname, null);
		//填充表格
		fillTable(stu);
	}
}