package com.scm;
import java.sql.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
public class GeneralSort extends JInternalFrame {
	private JComboBox JComBox_Classes;
	private JComboBox JComBox_Course;
	private JComboBox JComBox_order;
	private JTextField badField;
	private JButton btn_action;
	private JTextField goodField;
	private JScrollPane jScrollPane1;
	private JLabel label_bad;
	private JLabel label_good;
	private JLabel label_max;
	private JLabel label_min;
	private JTextField maxField;
	private JTextField minField;
	private JRadioButton radio_asc;
	private JRadioButton radio_desc;
	private JTable table;
	private ButtonGroup  bg;
	
	private UserDao userDao=new UserDao();
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	//�޲ι��캯��
	public GeneralSort() {
		try {
			initComponents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//�������
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 340);
	}
	//��ʼ������
	private void initComponents() throws Exception {
		JComBox_Classes = new JComboBox(); //�༶��ѡ��
		JComBox_Course = new JComboBox();  //�γ̸�ѡ��
		radio_asc = new JRadioButton();  //��ѡ��ť ����
		radio_desc = new JRadioButton();  //��ѡ��ť ����
		JComBox_order = new JComboBox();   //����ѡ��
		btn_action = new JButton();    //��ʼ��ť
		jScrollPane1 = new JScrollPane();   //������
		table = new JTable();          //���
		label_max = new JLabel();   
		label_min = new JLabel();
		label_good = new JLabel();
		label_bad = new JLabel();
		maxField = new JTextField();   //��߷������
		minField = new JTextField();  //��ͷ������
		goodField = new JTextField();  //������
		badField = new JTextField();    //��������
		setClosable(true);         //���ô���ɹر�
		setIconifiable(true);      //���ô������С��
		setTitle("�������");
		//��̬���ɰ༶�����б�
		conn=DBUtils.getConnection();
		ps=conn.prepareStatement("select distinct classes from student");
		rs=ps.executeQuery();
		Vector classesV=new Vector();
		classesV.add("--�༶--");
		while(rs.next()){
			classesV.add(rs.getString("classes"));
		}
		DBUtils.closeAll(rs, ps, conn);
		DefaultComboBoxModel classes_model=new DefaultComboBoxModel(classesV);
		JComBox_Classes.setModel(classes_model);
		JComBox_Classes.setFont(new Font("����", 0, 12));

		//��̬���ɿγ����������б�
		conn=DBUtils.getConnection();
		ps=conn.prepareStatement("select distinct cname from course");
		rs=ps.executeQuery();
		Vector courseV=new Vector();
		courseV.add("--�γ�--");
		while(rs.next()){
			courseV.add(rs.getString("cname"));
		}
		DBUtils.closeAll(rs, ps, conn);
		DefaultComboBoxModel course_model=new DefaultComboBoxModel(courseV);
		JComBox_Course.setModel(course_model);
		JComBox_Course.setFont(new Font("����", 0, 12));

		//���� ������ѡ��
		radio_asc.setFont(new Font("����", 0, 14));
		radio_asc.setText("����");
		radio_desc.setFont(new Font("����", 0, 14));
		radio_desc.setText("����");
        bg=new ButtonGroup();
        bg.add(radio_asc);
        bg.add(radio_desc);
        radio_asc.setSelected(true); //Ĭ��ѡ������
		JComBox_order.setFont(new Font("����", 0, 12));
		JComBox_order.setModel(new DefaultComboBoxModel(
				new String[] { "ѧ��", "����" }));
		btn_action.setFont(new Font("����", 0, 12));
		btn_action.setText("��ʼ");

		table.setFont(new Font("����", 0, 14));
		table.setModel(new DefaultTableModel(new Object[][] {
		}, new String[] { "ѧ��", "����", "�༶", "�γ�����", "����" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false,
					false };
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane1.setViewportView(table);
		label_max.setFont(new Font("����", 0, 12));
		label_max.setText(" ��߷�");
		label_min.setFont(new Font("����", 0, 12));
		label_min.setText(" ��ͷ�");
		label_good.setFont(new Font("����", 0, 12));
		label_good.setText(" ������");
		label_bad.setFont(new Font("����", 0, 12));
		label_bad.setText("��������");
		maxField.setEditable(false);  //���ò��ɱ༭
		minField.setEditable(false);
		goodField.setEditable(false);
		badField.setEditable(false);
		//����
		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(178, 178, 178)
								.addComponent(JComBox_Course,
										GroupLayout.PREFERRED_SIZE,
										92,
										GroupLayout.PREFERRED_SIZE)
								.addGap(33, 33, 33)
								.addComponent(radio_asc)
								.addGap(18, 18, 18)
								.addComponent(radio_desc)
								.addGap(27, 27, 27)
								.addComponent(JComBox_order,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(208, Short.MAX_VALUE))
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap(
																		571,
																		Short.MAX_VALUE)
																.addComponent(
																		btn_action))
												.addGroup(
														GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addGap(51, 51,
																		51)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						JComBox_Classes,
																						GroupLayout.PREFERRED_SIZE,
																						94,
																						GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jScrollPane1,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addGap(18,
																										18,
																										18)
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		label_bad)
																																.addGap(18,
																																		18,
																																		18)
																																.addComponent(
																																		badField,
																																		GroupLayout.PREFERRED_SIZE,
																																		41,
																																		GroupLayout.PREFERRED_SIZE))
																												.addGroup(
																														layout.createParallelGroup(
																																GroupLayout.Alignment.TRAILING,
																																false)
																																.addGroup(
																																		layout.createSequentialGroup()
																																				.addComponent(
																																						label_good)
																																				.addPreferredGap(
																																						LayoutStyle.ComponentPlacement.RELATED,
																																						GroupLayout.DEFAULT_SIZE,
																																						Short.MAX_VALUE)
																																				.addComponent(
																																						goodField,
																																						GroupLayout.PREFERRED_SIZE,
																																						41,
																																						GroupLayout.PREFERRED_SIZE))
																																.addGroup(
																																		layout.createSequentialGroup()
																																				.addComponent(
																																						label_min)
																																				.addPreferredGap(
																																						LayoutStyle.ComponentPlacement.RELATED,
																																						GroupLayout.DEFAULT_SIZE,
																																						Short.MAX_VALUE)
																																				.addComponent(
																																						minField,
																																						GroupLayout.PREFERRED_SIZE,
																																						41,
																																						GroupLayout.PREFERRED_SIZE))
																																.addGroup(
																																		GroupLayout.Alignment.LEADING,
																																		layout.createSequentialGroup()
																																				.addComponent(
																																						label_max,
																																						GroupLayout.PREFERRED_SIZE,
																																						48,
																																						GroupLayout.PREFERRED_SIZE)
																																				.addGap(18,
																																						18,
																																						18)
																																				.addComponent(
																																						maxField,
																																						GroupLayout.PREFERRED_SIZE,
																																						41,
																																						GroupLayout.PREFERRED_SIZE))))))))
								.addGap(84, 84, 84)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(48, 48,
																		48)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						radio_desc)
																				.addComponent(
																						JComBox_order,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						radio_asc)
																				.addComponent(
																						JComBox_Course,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						JComBox_Classes,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(50, 50,
																		50)
																.addComponent(
																		jScrollPane1,
																		GroupLayout.PREFERRED_SIZE,
																		408,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(75, 75,
																		75)
																.addComponent(
																		btn_action)
																.addGap(77, 77,
																		77)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_max)
																				.addComponent(
																						maxField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(27, 27,
																		27)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_min)
																				.addComponent(
																						minField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(21, 21,
																		21)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						label_good)
																				.addComponent(
																						goodField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(31, 31,
																		31)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						label_bad)
																				.addComponent(
																						badField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(40, Short.MAX_VALUE)));
		//��ʼ��ť�¼�����
		btn_action.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_actionActionPerformed(evt);
			}
		});
		pack();
	}
	//��ʼ��ť�¼�
	private void btn_actionActionPerformed(ActionEvent evt) {
		String classes = (String)this.JComBox_Classes.getSelectedItem();
		String cname=(String)this.JComBox_Course.getSelectedItem();
		//��ȡ��ѡ�еĵ�ѡ��ť
		int flag=0;
		if(radio_asc.isSelected()){
			flag=1;
		}else{
			flag=-1;
		}
		String order=(String)this.JComBox_order.getSelectedItem();
		//�����
		fillTable(classes,cname,flag,order);
	}
	private void fillTable(String classes, String cname, int flag, String order) {
		//��ȡģ��
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		//���ñ����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		Connection conn=null;
		ResultSet rs=null;
		model.setRowCount(0);//�ӵ�һ�п�ʼ�������
		conn=DBUtils.getConnection();
		try {
			List<ResultSet> resultSets = userDao.StudentOrder(conn,classes,cname,flag,order);
			 rs=resultSets.get(0);
			while(rs.next()){
				Vector v=new Vector();
				v.add(rs.getString("sno"));
				v.add(rs.getString("sname"));
				v.add(rs.getString("classes"));
				v.add(rs.getString("cname"));
				v.add(rs.getString("core"));
				model.addRow(v);
			}
			String max="";
			String min="";
			 rs=resultSets.get(1);
			while(rs.next()){
				max=rs.getString("max");
				min=rs.getString("min");
			}
			int total = 0;
			int good = 0;
			int bad = 0;
			 rs=resultSets.get(2);
			while(rs.next()){
				total=rs.getInt("total");
			}
			rs=resultSets.get(3);
			while(rs.next()){
				good=rs.getInt("good");
			}
			rs=resultSets.get(4);
			while(rs.next()){
				bad=rs.getInt("bad");
			}
			maxField.setText(max);
			minField.setText(min);
			NumberFormat numberFormat= NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(2);//��ȷ��С������λ
			if(total!=0){
				String g=numberFormat.format(good*1.0/total*100);
				goodField.setText(g+"%");
				String b=numberFormat.format(bad*1.0/total*100);
				badField.setText(b+"%");
			}else{
				goodField.setText("");
				badField.setText("");
			}
			DBUtils.closeAll(rs, null, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		DBUtils.closeAll(rs, ps, conn);
		}
	}
}