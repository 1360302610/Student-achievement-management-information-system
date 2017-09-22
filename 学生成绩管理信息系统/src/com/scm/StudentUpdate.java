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
	
	//�вι��캯��
	public StudentUpdate(Student stu) {
		initComponents(stu);
		//�������
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 300, dimension.height / 2 - 300);
	}
	//��ʼ�����
	private void initComponents(Student stu) {
		label_sno = new JLabel();
		snoField = new JTextField(); //ѧ�������
		label_name = new JLabel();
		nameField = new JTextField();  //���������
		label_classes = new JLabel();
		classesField = new JTextField(); //�༶�����
		btn_update = new JButton();   //�޸İ�ť
		btn_canle = new JButton();    //ȡ����ť
		//һ��ʼ��ʾ����
		snoField.setText(stu.getSno());
		nameField.setText(stu.getName());
		classesField.setText(stu.getClasses());
		setClosable(true);  //�ɹر�
		setIconifiable(true);  //��С��
		setTitle("ѧ����Ϣ�޸Ľ���");
		label_sno.setFont(new Font("����", 0, 14));
		label_sno.setText("ѧ��");
		snoField.setEditable(false);
		label_name.setFont(new Font("����", 0, 14));
		label_name.setText("����");
		label_classes.setFont(new Font("����", 0, 14));
		label_classes.setText("�༶");
		btn_update.setFont(new Font("����", 0, 12));
		btn_update.setIcon(new ImageIcon("images/update.png")); 
		btn_update.setText("�޸�");

		btn_canle.setFont(new Font("����", 0, 12));
		btn_canle.setIcon(new ImageIcon("images/canle.png")); 
		btn_canle.setText("ȡ��");
		
		//����
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
		
		//�޸��¼�����
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_updateActionPerformed(evt);
			}
		});
		//ȡ���¼�����
		btn_canle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_canleActionPerformed(evt);
			}
		});
		pack();
	}
	//ȡ����ť�¼�
	private void btn_canleActionPerformed(ActionEvent evt) {
		this.dispose();
	}
	//�޸İ�ť�¼�
	private void btn_updateActionPerformed(ActionEvent evt) {
		String name = nameField.getText();
		String classes = classesField.getText();
		String sno = snoField.getText();
		if (StringUtils.isBlank(name)) {
			JOptionPane.showMessageDialog(null, "��������Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (StringUtils.isBlank(classes)) {
			JOptionPane.showMessageDialog(null, "�༶����Ϊ��", "��ʾ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		boolean b = userDao.updateStudent(sno, name, classes);
		if (b) {
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "�޸�ʧ��", "��ʾ",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}