package com.scm;

public class Student {
	private String sno;//ѧ��
	private String name;//ѧ������
	private String classes;//ѧ���༶
	private String cname;//�γ�����
	private String core;//�ɼ�
	
	public Student(){}
	public Student(String sno, String name, String classes, String cname,
			String core) {
		super();
		this.sno = sno;
		this.name = name;
		this.classes = classes;
		this.cname = cname;
		this.core = core;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	
}
