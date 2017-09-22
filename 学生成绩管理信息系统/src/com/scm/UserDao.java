package com.scm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class UserDao {
	/**
	 * ��������ѯ
	 * @param stu
	 * @return
	 */
	public ResultSet findStudentByMany(Connection conn,Student stu){
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			StringBuffer sb=new StringBuffer("SELECT s.sno ,s.sname,s.classes,c.cname,g.core FROM student s LEFT JOIN grade g ON g.sno=s.sno LEFT JOIN course c ON g.cno=c.cno");
			if(StringUtils.isNotBlank(stu.getSno())){
				sb.append(" and s.sno like '%"+stu.getSno()+"%'");
			}
			if(StringUtils.isNotBlank(stu.getName())){
				sb.append(" and s.sname like '%"+stu.getName()+"%'");
			}
			if(StringUtils.isNotBlank(stu.getClasses())){
				sb.append(" and s.classes like '%"+stu.getClasses()+"%'");
			}
			if(StringUtils.isNotBlank(stu.getCname())){
				sb.append(" and c.cname like '%"+stu.getCname()+"%'");
			}
			String sql=sb.toString().replaceFirst("and","where");
			
			ps=conn.prepareStatement(sql);
		    rs = ps.executeQuery();
		    return rs;
		}catch(Exception e){
			e.printStackTrace();
			return rs;
		}
	}


	/**
	 * ���ѧ����Ϣ
	 * @param stu
	 * @return
	 * @throws SQLException 
	 */
	public int addStudent(Student stu){
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		ResultSet rs=null;
		boolean flag=false;
		try {
			conn=DBUtils.getConnection();
			//ȡ�������Զ��ع�����
			conn.setAutoCommit(false);
			
			//�����ݿ��в���ÿγ̵ı��
			ps=conn.prepareStatement("select cno from course where cname=?");
			ps.setString(1,stu.getCname());
			rs=ps.executeQuery();
			String cno="";
			while(rs.next()){
				cno=rs.getString("cno");
			}
			ps=conn.prepareStatement("insert into grade values (?,?,?)");
			ps.setString(1,cno);
			ps.setString(2,stu.getSno());
			ps.setString(3,stu.getCore());
			
			if(findStudent(stu.getSno())==null){ //ѧ�Ų�����
				ps2=conn.prepareStatement("insert into student values (?,?,?)");
				ps2.setString(1,stu.getSno());
				ps2.setString(2,stu.getName());
				ps2.setString(3,stu.getClasses());
				flag=true;
			}
			//ѧ�Ų����ڲŲ���student��
			if(flag){
				int a=ps2.executeUpdate();
				//��ѧ�ź�Ĭ��������뵽user����
				User user=new User(stu.getSno(),"123456");
				this.addUser(user);
			}
			int c=ps.executeUpdate();
			//�ύ����
			conn.commit();
			return c;
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); //������ʧ�ܾͻع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBUtils.closeAll(rs, ps, conn);
			if(flag){
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	/**
	 * �޸�ѧ����Ϣ
	 * @param sno
	 * @param name
	 * @param classes
	 * @return
	 */
	public boolean updateStudent(String sno, String name, String classes) {
		Connection conn=null;
		PreparedStatement  ps=null;
		try {
			 conn=DBUtils.getConnection();
			ps=conn.prepareStatement("update student set sname=? , classes=? where sno =?");
			ps.setString(1,name);
			ps.setString(2,classes);
			ps.setString(3,sno);
			int a = ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
	}
	/**
	 * ����ѧ��ɾ��ѧ��
	 * @param sno
	 * @return
	 */
	public boolean deleteBySno(String sno) {
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("delete from student where sno=?");
			ps.setString(1,sno);
			int a = ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
	}
	/**
	 * ͨ��������ѯѧ���Ƿ����
	 * @param name
	 * @return
	 */
	public boolean findStudentBySname(String name) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("select * from student where sname=?");
			ps.setString(1,name);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeAll(rs, ps, conn);
		}
	}
	/**
	 * ����ѧ������ɾ��ѧ��
	 * @param name
	 * @return
	 */
	public boolean deleteBySname(String name) {
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("delete from student where sname=?");
			ps.setString(1,name);
			int a = ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
	}

	/**
	 * ����
	 * @param classes
	 * @param cname
	 * @param flag
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	public List<ResultSet> StudentOrder(Connection conn,String classes, String cname, int flag,
			String order) throws Exception {

		StringBuffer sb=new StringBuffer("SELECT s.sno SNO ,s.sname SNAME,s.classes CLASSES,c.cname CNAME,g.core CORE FROM student s LEFT JOIN grade g ON g.sno=s.sno LEFT JOIN course c ON g.cno=c.cno");
		sb.append(" and 1=1");
		if(!classes.equals("--�༶--")){
			sb.append(" and s.classes ='"+classes+"'");
		}
		if(!cname.equals("--�γ�--")){
			sb.append(" and c.cname ='"+cname+"'");
		}
		if(order.equals("ѧ��")){
			sb.append(" order by s.sno");
		}
		if(order.equals("����")){
			sb.append(" order by g.core");
		}
		if(flag ==1){
			sb.append(" asc");
		}else{
			sb.append(" desc");
		}
		String sql=sb.toString().replaceFirst("and","where");
		System.out.println(sql);
		 PreparedStatement ps1=conn.prepareStatement(sql);
		 ResultSet rs1=ps1.executeQuery();
		 List<ResultSet> list=new ArrayList<ResultSet>();
		 list.add(rs1);

		 //��ѯ��߷ֺ���ͷ�
		 String max_min="select max(CORE) max,min(CORE) min from ("+sql+") as q where CORE is not null";
		 System.out.println(max_min);
		 PreparedStatement ps2=conn.prepareStatement(max_min);
		 ResultSet rs2=ps2.executeQuery();
		 list.add(rs2);
		 
		 //��ѯ������
		 String total="select count(SNO) total from ("+sql+") as q";
		 System.out.println(total);
		 PreparedStatement ps3=conn.prepareStatement(total);
		 ResultSet rs3=ps3.executeQuery();
		 list.add(rs3);
		 
		 //������������
		 String good="select count(SNO) good from ("+sql+") as q where CORE>=90";
		 System.out.println(good);
		 PreparedStatement ps4=conn.prepareStatement(good);
		 ResultSet rs4=ps4.executeQuery();
		 list.add(rs4);
		 
		 //��ѯ����������
		 String bad="select count(SNO) bad from ("+sql+") as q where CORE<60";
		 System.out.println(bad);
		 PreparedStatement ps5=conn.prepareStatement(bad);
		 ResultSet rs5=ps5.executeQuery();
		 list.add(rs5);
		 
		return list;
		
	}
	//��ӿγ�

	public void addCourse(String cname) {
		Connection conn=null;
		PreparedStatement ps=null;
		
		try{
			Long cno=System.currentTimeMillis();
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("insert into course values (?,?)");
			ps.setString(1,cno.toString());
			ps.setString(2,cname);
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
		
	}

	//ͨ���γ����Ʋ�ѯ�Ƿ���ڸÿγ̣�������ڷ���true,���򷵻�false
	public boolean isCourseByCname(String cname) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("select * from course where cname=?");
			ps.setString(1,cname);
			rs=ps.executeQuery();
			while(rs.next()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(rs, ps, conn);
		}
		return false;
	}

	//��ѯ�����еĿγ�����
	public ResultSet findAllCourse(Connection conn) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement("select cname from course");
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}


	//ѧ����¼
	public boolean isStudent(String sno, String pwd) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("select * from user where username=? and password=?");
			ps.setString(1,sno);
			ps.setString(2,pwd);
			rs=ps.executeQuery();
			
			while(rs.next()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(rs, ps, conn);
		}
		return false;
	}
	
	//����ѧ�Ų�ѯѧ���Ƿ����
	public Student findStudent(String sno){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Student stu=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("select * from student where sno=?");
			ps.setString(1,sno);
			
			rs=ps.executeQuery();
			while(rs.next()){
				stu=new Student();
				stu.setSno(sno);
				stu.setName(rs.getString("sname"));
				stu.setClasses(rs.getString("classes"));
				return stu;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(rs, ps, conn);
		}
		return stu;
	}
	
	//���user
	public boolean addUser(User user){
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("insert into user values (?,?)");
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getPassword());
			ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	//�޸�����

	public boolean editPwd(String sno, String repwd) {
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DBUtils.getConnection();
			ps=conn.prepareStatement("update user set password=? where username=?");
			ps.setString(1,repwd);
			ps.setString(2,sno);
			ps.executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}


	public ResultSet findAllClasse(Connection conn) {
		ResultSet rs=null;
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT DISTINCT classes FROM student");
			rs=ps.executeQuery();
			return  rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
}
