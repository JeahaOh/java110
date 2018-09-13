package bitcamp.java110.cms.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bitcamp.java110.cms.annotation.Component;
import bitcamp.java110.cms.dao.DaoException;
import bitcamp.java110.cms.dao.ManagerDao;
import bitcamp.java110.cms.domain.Manager;

@Component
public class ManagerJdbcDao implements ManagerDao{
    
    public int insert(Manager manager) {
        Connection con = null;
        Statement stmt = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            con = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/studydb", "study", "1111");
            
            //  매니져 정보를 입력할 때 p1_mamb 테이블 과 p1_mgr 테이블에
            //  매니져 정보를 분산 입력 해야 한다.
            //  두 테이블에 모두 입력 성공 할 때 입력을 완료하도록
            //  두 insert를 한 작업(transaction)으로 묶는다.
            //  => SQL을 서버로 보낸 후 클라이언트가 최종 완료 신호를 
            //  보내기 전까지는 처리를 보류하도록 한다.
            con.setAutoCommit(false);
            //  실무는 기본 false. 이거 default값은 true임.
            
            stmt = con.createStatement();
            String sql = ("insert into p1_memb(name,email,pwd,tel,cdt)"
                    +" values('" + manager.getName()
                    + "','" + manager.getEmail()
                    + "',password('" + manager.getPassword()
                    + "'),'" +manager.getTel()
                    + "',now())");
//            System.out.println(sql);
            //  sql 만들고 체크 꼭 해봐라.
            
            //  p1_memb 테이블에 회원 기본 정보를 입력 한 후
            //  자동으로 생성된 회원 번호를 리턴 받는다.
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            
            //  insert를 실행한 후 리턴 받은 자동 증가 pk값을 꺼내기.
            ResultSet autogeneratedKeys = stmt.getGeneratedKeys();
            autogeneratedKeys.next();
            int memberNo = autogeneratedKeys.getInt(1);
            autogeneratedKeys.close();
            
            //  ResultSet 개객기. 이 새끼는 index가 1부터 시작함.
                    //  ResultSet 이 새끼 작동하는 방법 잘 기억해 둬라.
            //  회원 번호로 매니져 테이블에 직위 정보를 입력한다.
            
            String sql2 = "insert into p1_mgr(mrno,posi)"
                    + " values(" + memberNo
                    + ",'" + manager.getPosition()
                    +"')";
            stmt.executeUpdate(sql2);
            
            
            //  두 insert가 모두 성공했을 때만 서버에 완료 신호를 보낸다.
            con.commit();
            
            //  Transaction 관리.
            //  여러개의 작업을 한 단위로 묶어서 하는것.
            return 1;
        }   catch(Exception e) {
            throw new DaoException(e);
        }   finally {
            try {stmt.close();} catch (Exception e) {    }
            try {con.close();} catch (Exception e) {    }
        }
    }
    
    public List<Manager> findAll() {
        
        ArrayList<Manager> list = new ArrayList<>();
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
        //  java.sql.Driver를 구현체로 로딩한다.
        //  해당 클래스의 객체를 만들어 Driver에 등록한다.
//            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            //  둘다 같은것이나 아랫것이 낫다.
            Class.forName("org.mariadb.jdbc.Driver");
            
            //  드라이버 매니져에게 java.sql.Connection 객체를 요구한다.
            //  DriverManager는 등록된 Driver 들 중에서 요구 사항에
            //  맞는 드라이버를 찾아 connect()를 호출한다.
            //  그리고 connect 메서드의 리턴값을 그대로 리턴해준다.
            con = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/studydb", "study", "1111");
            
            //  query문을 작성할 객체를 준비한다.
            stmt = con.createStatement();
            
            //  select query를 짠다.
            /*
             select 
                 m.name,
                 m.email,
                 mr.posi
             from p1_mgr mr
                 inner join p1_memb m
                 on mr.mrno = m.mno;
                 
             --------------------------
                 
             select 
                 m.name,
                 m.email,
                 mr.posi
             from p1_mgr mr
                 right outer join p1_memb m
                 on mr.mrno = m.mno;
             */
            rs = stmt.executeQuery(
                    " select " + 
                    " m.mno, " +
                    " m.name, " + 
                    " m.email, " + 
                    " mr.posi " + 
                    " from p1_mgr mr " + 
                    " inner join p1_memb m " + 
                    " on mr.mrno = m.mno ");
            //  서버에 생성된 질의 결과를 한 개씩 가져 온다.
            while(rs.next()){
                Manager mgr = new Manager();
                mgr.setNo(rs.getInt("mno"));
                mgr.setEmail(rs.getString("email"));
                mgr.setName(rs.getString("name"));
                mgr.setPosition(rs.getString("posi"));
                
                list.add(mgr);
            }
        }   catch(Exception e) {
            throw new DaoException(e);
        }   finally {
            try {rs.close();} catch (Exception e) {    }
            try {stmt.close();} catch (Exception e) {    }
            try {con.close();} catch (Exception e) {    }
        }
        return list;
    }
    
    public Manager findByEmail(String email) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            con = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/studydb", "study", "1111");
            
            stmt = con.createStatement();
            
            rs = stmt.executeQuery(
                    " select " + 
                    " m.mno, " +
                    " m.name, " + 
                    " m.email, " + 
                    " m.tel, " + 
                    " mr.posi " + 
                    " from p1_mgr mr " + 
                    " inner join p1_memb m " + 
                    " on mr.mrno = m.mno " +
                    " where m.email = '" + email + "'");
            
            if(rs.next()) {
                Manager mgr = new Manager();
                mgr.setNo(rs.getInt("mno"));
                mgr.setEmail(rs.getString("email"));
                mgr.setName(rs.getString("name"));
                mgr.setTel(rs.getString("tel"));
                mgr.setPosition(rs.getString("posi"));
                
                return mgr;
            }
            return null;
        }   catch(Exception e) {
            throw new DaoException(e);
        }   finally {
            try {rs.close();} catch (Exception e) {    }
            try {stmt.close();} catch (Exception e) {    }
            try {con.close();} catch (Exception e) {    }
        }
    }
    
    public Manager findByNo(int no) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            con = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/studydb", "study", "1111");
            
            stmt = con.createStatement();
            
            rs = stmt.executeQuery(
                    " select" + 
                    " m.mno," +
                    " m.name," + 
                    " m.email," + 
                    " m.tel," + 
                    " mr.posi" + 
                    " from p1_mgr mr " + 
                    " inner join p1_memb m " + 
                    " on mr.mrno = m.mno " +
                    " where m.mno =" + no);
            
            if(rs.next()) {
                Manager mgr = new Manager();
                mgr.setNo(rs.getInt("mno"));
                mgr.setEmail(rs.getString("email"));
                mgr.setName(rs.getString("name"));
                mgr.setTel(rs.getString("tel"));
                mgr.setPosition(rs.getString("posi"));
                
                return mgr;
            }
            return null;
        }   catch(Exception e) {
            throw new DaoException(e);
        }   finally {
            try {rs.close();} catch (Exception e) {    }
            try {stmt.close();} catch (Exception e) {    }
            try {con.close();} catch (Exception e) {    }
        }
    }
    
    public int delete(String email) { return 0; }
    
    public int deleteByNo(int no) {
        Connection con = null;
        Statement stmt = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection
            ("jdbc:mariadb://localhost:3306/studydb", "study", "1111");
            
            con.setAutoCommit(false);
            stmt = con.createStatement();
            
            String sql1 = ("delete from p1_mgr where mrno=" + no);
            int count = stmt.executeUpdate(sql1);
            
            if(count == 0)
                return 0;
            
            String sql2 = ("delete from p1_memb where mno=" + no);
            count = stmt.executeUpdate(sql2);
            con.commit();
            return 1;
        }   catch(Exception e) {
            throw new DaoException(e);
        }   finally {
            try {stmt.close();} catch (Exception e) {    }
            try {con.close();} catch (Exception e) {    }
        }
    }
}
/*
    회원 정보 입력할때 posi에 50자가 넘으면 Error 뜨지만 posi정보를 남겨두고 나머지는 들어감.
    Transaction을 사용해야 할 때가 옴.
    
    
    insert 두번하고 ResultSet으로 autogeneratedKey, transaction 배움.
*/