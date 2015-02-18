//132.171.52.79 25

public class Main {

    public static void main(String[] args) {
        String userName = "ak47Hitman";
        String password = "aka47Hitman1";
        String serverName = "localhost";
        String port = "1433";
        String dateBaseName = "MYDB1";
        String inputFile = "c://1/log/catalina.2015-02-05.log";
        String outPutFile = "textSend.txt";
        String findError = "error:";
        String toEmail = "";
        String fromEmail = "";
        String smtp = "";
        String smtpPort = "";
        String errorNameTable = "[dbo].[SYSTEM_EVEN]";
        //Timer timer = new Timer();
        MyTask task = new MyTask(inputFile, findError, outPutFile,
                serverName, port, dateBaseName, userName, password,
                fromEmail, toEmail, smtp, smtpPort,
                errorNameTable);
        task.run();
    }
}

//System.out.println("Enter input file: ");
//        String inputFile = args[0];
//        System.out.println("Input file: " + inputFile);
//        System.out.println("Enter output file: ");
//        String outPutFile = args[1];
//        System.out.println("Output file: " + outPutFile);
//
//        System.out.println("Enter serverName host: ");
//        String serverName = args[2];
//        System.out.println("host: " + serverName);
//        System.out.println("Enter port: ");
//        String port = args[3];
//        System.out.println("port: " + port);
//        String dateBaseName = args[4];
//        System.out.println("From dateBaseName: " + dateBaseName);
//
//        System.out.println("Enter username: ");
//        String userName = args[5];
//        System.out.println("Username: " + userName);
//        System.out.println("Enter password: ");
//        String password = args[6];
//        System.out.println("Enter dateBaseName: ");
//        String toEmail = "";
//        String findError = "error:";
//        String errorNameTable = "[dbo].[SYSTEM_EVEN]";
//        String errorCol = "ERROR";
//        String dateOfError = "DATEERROR";
//        String smtp = "";
//        String smtpPort = "";
//        String fromEmail = "";
//        String outPutFileInfo = "textSend.txt";