import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xhplogvievg_ on 11.02.2015.
 */
public class WorkWhisDB {
    String serverName = "";
    String port = "";
    String dateBaseName = "";
    String userName = "";
    String password = "";
    Statement stmt = null;

    public WorkWhisDB(String serverName, String port, String dateBaseName, String userName, String password) {
        this.serverName = serverName;
        this.port = port;
        this.dateBaseName = dateBaseName;
        this.userName = userName;
        this.password = password;
    }
    public void testConnect() {
        try {
            Class.forName(getClassName());
            Connection conn = DriverManager.getConnection(getUrl(), userName, password);
            System.out.println("Connect is successfully");
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

//    public void createTable(String tableName, String error, String dateOfError) {
//        Connection c = null;zl
//        Statement stmt = null;
//        try {
//            String url = getUrl();
//            Class.forName(getClassName());
//            Connection conn = DriverManager.getConnection(url, userName, password);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE " + tableName +
//                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    error + " TEXT  UNIQUE  NOT NULL, " +
//                    dateOfError + "            DATE NOT NULL)";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//        }
//        System.out.println("Table created successfully");
//    }

    public void insertDate(String tableName, String error, String whereErrorFind, String dateOfError, String type) {
        try {
            Class.forName(getClassName());
            Connection c = DriverManager.getConnection(getUrl(), userName, password);

            stmt = c.createStatement();
            String sql = "INSERT INTO " + tableName + " (" + "Message" + " ," +
                                                            "SourceName" + " ," +
                                                            "TimeWritten" + " ," +
                                                            "Type" + ") " +
                                                "VALUES ('" + error + "' , " +
                                                        "'" + whereErrorFind + "'" + ", " +
                                                        "'" + dateOfError + "'" + ", "+
                                                        " '" + type + "'"+ ");";
            stmt.executeUpdate(sql);
            System.out.println("Insert date");
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Records created successfully");
    }

    public void selectDate(String errorNameTable) {
        try {
            Class.forName(getClassName());
            Connection c = DriverManager.getConnection(getUrl(), userName, password);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + errorNameTable +";" );
            System.out.println("______________________________");
            while ( rs.next() ) {
                int id = rs.getInt("Category");
                String  error = rs.getString("Message");
                String  sourceName = rs.getString("SourceName");
                Date date = rs.getDate("TimeWritten");
                String type = rs.getString("Type");
                System.out.println( "ID = " + id );
                System.out.println( "MESSAGE = " + error );
                System.out.println( "SourceName = " + sourceName);
                System.out.println( "TimeWritten = " + date);
                System.out.println( "Type = " + type);
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Date selected");
    }

    public ArrayList<String> selectDateWhere(String tableName, String whereString, String date) {
        ArrayList<String> strings = new ArrayList<String>();
        try {
            Class.forName(getClassName());
            Connection c = DriverManager.getConnection(getUrl(), userName, password);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName +" WHERE Message LIKE '" + whereString + "%';" );
            while ( rs.next() ) {
//                int id = rs.getInt("Category");
                String  error = rs.getString("Message");
//                System.out.println( "ID = " + id );
                System.out.println( "ERROR = " + error );
                System.out.println();
                strings.add(error);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return strings;
    }

//    public ArrayList<String> selectDateWhere1(String tableName, String whereString, String date) {
//        Statement stmt = null;
//        ArrayList<String> strings = new ArrayList<String>();
//        try {
//            String url = getUrl();
//            Class.forName(getClassName());
//            Connection c = DriverManager.getConnection(url, userName, password);
//
//            stmt = c.createStatement();
////            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName +" WHERE Message LIKE '" + whereString + "';" );
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName +" WHERE Message " +
//                    "LIKE '05.02.2015 16:47:52 org.apache.catalina.loader.WebappClassLoader clearReferencesThreads SEVERE: The web application [/new] appears to have started a thread named [Resource Housekeeper] but has failed to stop it. This is very likely to create a memory leak.';" );
//            while ( rs.next() ) {
//                int id = rs.getInt("id");
//                String  error = rs.getString("Message");
//                System.out.println( "ID = " + id );
//                System.out.println( "ERROR = " + error );
//                System.out.println();
//                strings.add(error);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");
//        return strings;
//    }

    public void deleteDate(String tableName) {
        try {
            Class.forName(getClassName());
            Connection c = DriverManager.getConnection(getUrl(), userName, password);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from " + tableName + ";";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + tableName + ";" );
            while ( rs.next() ) {
                int id = rs.getInt("Category");
                String  error = rs.getString("Message");
                System.out.println( "ID = " + id );
                System.out.println( "ERROR = " + error );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private String getUrl() {
        return "jdbc:sqlserver://" + serverName + ":" + port + ";databaseName=" + dateBaseName +";integratedSecurity=false";
    }

    private String getClassName() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
}
//    public void updateDate() {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:myErrorDB.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            String sql = "UPDATE ERRORSTACK set ERROR = 'sdfdsfdsf' where ID=1;";
//            stmt.executeUpdate(sql);
//            c.commit();
//
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM ERRORSTACK;" );
//            while ( rs.next() ) {
//                int id = rs.getInt("id");
//                String  error = rs.getString("error");
//                System.out.println( "ID = " + id );
//                System.out.println( "ERROR = " + error );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");
//    }