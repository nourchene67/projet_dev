import java.sql.*;
class MysqlCon{
    public static void main(String args[]){
        try{
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://172.20.54.101:3306/medicationsDB","nourchene","abcd12");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from medications");
            while(rs.next())

                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3).getClass().getName());
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}  