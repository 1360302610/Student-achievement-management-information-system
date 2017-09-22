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
	//�޲ι��캯��
	public StudentDelete() {
		initComponents();
		//�������
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimension.width / 2 - 200, dimension.height / 2 - 200);
	}
	//��ʼ�����
	private void initComponents() {
		JComBox_delete = new JComboBox();  //��ѡ��
		inputTxt = new JTextField();   //�����
		btn_delete = new JButton();    //ɾ����ť
		setClosable(true);    //���ô���ɹر�
		setIconifiable(true);   //���ô������С��
		setTitle("ɾ��ѧ������");
		JComBox_delete.setFont(new Font("����", 0, 12));
		JComBox_delete.setModel(new DefaultComboBoxModel(
				new String[] { "-��ѡ��-", "ѧ��", "����" }));
		inputTxt.setFont(new Font("����", 0, 12));
		btn_delete.setFont(new Font("����", 0, 12));
		btn_delete.setIcon(new ImageIcon("images/delete.png")); 
		btn_delete.setText("ɾ��");
		
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
		//ɾ����ť�¼�����
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_deleteActionPerformed(evt);
			}
		});
		pack();
	}
	//ɾ����ť�¼�
	private void btn_deleteActionPerformed(ActionEvent evt) {
		//��ȡ��ѡ���ģ��
		ComboBoxModel model = this.JComBox_delete.getModel();
		Object selectedItem = model.getSelectedItem();
		if("-��ѡ��-".equals(selectedItem)){
			return;
		}else{
			//�ж��û�ѡ����ѧ��
			if("ѧ��".equals(selectedItem)){
				String sno=this.inputTxt.getText();
				if(StringUtils.isBlank(sno)){
					JOptionPane.showMessageDialog(null, "������ѧ��", "��ʾ",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				//��ѯ�û��Ƿ����
				Student student = userDao.findStudent(sno);
				if(student!=null){
					//���ѧ�Ŵ��ڣ���ɾ����ѧ��
					boolean b=userDao.deleteBySno(sno);
					if(b){
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
					}
				}else{
					JOptionPane.showMessageDialog(null, "��ѧ��������");
					return;
				}
			}else{
				//�û�ѡ��������
				String name=this.inputTxt.getText();
				if(StringUtils.isBlank(name)){
					JOptionPane.showMessageDialog(null, "������ѧ������", "��ʾ",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				boolean hasStudent = userDao.findStudentBySname(name);
				if(hasStudent){
					//���ѧ�Ŵ��ڣ���ɾ����ѧ��
					boolean b=userDao.deleteBySname(name);
					if(b){
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
					}
				}else{
					JOptionPane.showMessageDialog(null, "��ѧ��������");
					return;
				}
			}
		}
	}
}