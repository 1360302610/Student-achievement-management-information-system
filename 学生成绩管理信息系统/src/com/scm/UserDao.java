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
	 * 多条件查询
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
	 * 添加学生信息
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
			//取消事务自动回滚功能
			conn.setAutoCommit(false);
			
			//从数据库中查出该课程的编号
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
			
			if(findStudent(stu.getSno())==null){ //学号不存在
				ps2=conn.prepareStatement("insert into student values (?,?,?)");
				ps2.setString(1,stu.getSno());
				ps2.setString(2,stu.getName());
				ps2.setString(3,stu.getClasses());
				flag=true;
			}
			//学号不存在才插入student表
			if(flag){
				int a=ps2.executeUpdate();
				//将学号和默认密码插入到user表中
				User user=new User(stu.getSno(),"123456");
				this.addUser(user);
			}
			int c=ps.executeUpdate();
			//提交事务
			conn.commit();
			return c;
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); //如果添加失败就回滚
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
	 * 修改学生信息
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
	 * 根据学号删除学生
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
	 * 通过姓名查询学生是否存在
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
	 * 根据学生姓名删除学生
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
	 * 排序
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
		if(!classes.equals("--班级--")){
			sb.append(" and s.classes ='"+classes+"'");
		}
		if(!cname.equals("--课程--")){
			sb.append(" and c.cname ='"+cname+"'");
		}
		if(order.equals("学号")){
			sb.append(" order by s.sno");
		}
		if(order.equals("分数")){
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

		 //查询最高分和最低分
		 String max_min="select max(CORE) max,min(CORE) min from ("+sql+") as q where CORE is not null";
		 System.out.println(max_min);
		 PreparedStatement ps2=conn.prepareStatement(max_min);
		 ResultSet rs2=ps2.executeQuery();
		 list.add(rs2);
		 
		 //查询总人数
		 String total="select count(SNO) total from ("+sql+") as q";
		 System.out.println(total);
		 PreparedStatement ps3=conn.prepareStatement(total);
		 ResultSet rs3=ps3.executeQuery();
		 list.add(rs3);
		 
		 //查出优秀的人数
		 String good="select count(SNO) good from ("+sql+") as q where CORE>=90";
		 System.out.println(good);
		 PreparedStatement ps4=conn.prepareStatement(good);
		 ResultSet rs4=ps4.executeQuery();
		 list.add(rs4);
		 
		 //查询不及格人数
		 String bad="select count(SNO) bad from ("+sql+") as q where CORE<60";
		 System.out.println(bad);
		 PreparedStatement ps5=conn.prepareStatement(bad);
		 ResultSet rs5=ps5.executeQuery();
		 list.add(rs5);
		 
		return list;
		
	}
	//添加课程

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

	//通过课程名称查询是否存在该课程，如果存在返回true,否则返回false
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

	//查询出所有的课程名称
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


	//学生登录
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
	
	//根据学号查询学生是否存在
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
	
	//添加user
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

	//修改密码

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
