import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCTest {
	
	
	public JDBCTest(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startDB(){
		String dburl = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, "bookmaster", "book");
			
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			int menu;
			boolean sw = true;
			
			while(sw){
				System.out.println("=============");
				System.out.println("| 1. ��ȣ ��ȸ    |");
				System.out.println("| 2. ��ȣ �Է�    |");
				System.out.println("| 3. ����           |");
				System.out.println("=============");
				System.out.println("�޴��� �����ϼ��� : ");
				
				menu = Integer.parseInt(in.nextLine());
				
				switch(menu){
				case 1:
					pstmt = con.prepareStatement("SELECT * FROM book");
					rs = pstmt.executeQuery();
					
					System.out.println("�̸� \t ��ȭ��ȣ \t ����");
					
					while(rs.next()){
						System.out.print(rs.getString("name")+ "\t");
						System.out.print(rs.getString("phone_number")+"\t");
						System.out.print(rs.getInt("age"));
						System.out.println();
					}
					break;
					
				case 2:
					String name, phone_number;
					int age;
					System.out.println("�̸��� �Է��ϼ���");
					name = in.nextLine();
					
					System.out.println("��ȭ��ȣ�� �Է��ϼ���");
					phone_number = in.nextLine();
					
					System.out.println("���̸� �Է��ϼ���");
					age = Integer.parseInt(in.nextLine());
					
					pstmt = con.prepareStatement("INSERT INTO book VALUES(?,?,?)");
					pstmt.setString(1,  name);
					pstmt.setString(2,  phone_number);
					pstmt.setInt(3, age);
					pstmt.executeUpdate();
					break;
					
				case 3:
					sw = false;
					break;
				}
			
				
			}
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���Ÿ� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}

	
	public static void main(String[] args) throws SQLException{
		startDB();
	}
}
