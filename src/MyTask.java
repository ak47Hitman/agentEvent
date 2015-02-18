import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by xhplogvievg_ on 05.02.2015.
 */
public class MyTask extends TimerTask {
    private String findError = "";
    private String inputFile = "";
    private String outPutFile = "";
    private String serverName = "";
    private String port = "";
    private String username = "";
    private String password = "";
    private String fromEmail = "";
    private String toEmail = "";
    private String smtp = "";
    private String smtpPort = "";
    private String errorNameTable = "";
    private String dateBaseName = "";

    public MyTask(String inputFile, String findError, String outPutFile,
                  String serverName, String port, String dateBaseName,
                  String username, String password,
                  String fromEmail, String toEmail, String smtp, String smtpPort,
                  String errorNameTable) {
        this.findError = findError;
        this.inputFile = inputFile;
        this.outPutFile = outPutFile;
        this.serverName = serverName;
        this.port = port;
        this.dateBaseName = dateBaseName;
        this.username = username;
        this.password = password;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.smtp = smtp;
        this.smtpPort = smtpPort;
        this.errorNameTable = errorNameTable;
    }
    @Override
    public void run() {
        try {
            this.createTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cancel();
    }

    //Main class to create Task. Take info from DB and and insert new rows into db.
    //Алгоритм следующий:
    //1. Считать данные с лог файлов системы .log
    //2. Найти ошибки ERROR
    //3. Проверить - существуют ли такие ошибки в базе
    //4. Если существуют, то не производить их вставку в БД
    //5. Если не существуют ошибки, то произвести вставку в БД
    //Т.к. записи строки ошибки содержат время - то эти ошибки буду в любом случае уникальные. Тоесть ошибка одна и таже в разное время - это разные ошибки
    public void createTask() throws IOException {
        ArrayList<String> nextArray;
        ArrayList<String> errorsFromDB;
        ArrayList<String> errorsFromDBToTextFile = new ArrayList<String>();
        ArrayList<String> errorArrayInList = new ArrayList<String>() ;
        TextFile textFile = new TextFile();
        String type = "Error";
        WorkWhisDB myDB = new WorkWhisDB(serverName, port, dateBaseName, username, password);

        String file = textFile.read(inputFile);
        nextArray = textFile.findWordInMyText(file, findError);
        myDB.testConnect();
//        myDB.deleteDate(errorNameTable);
        //Проверяем на наличие в БД уже имеющейся ошибки
        // если ошибка существует, то ничего не делаем,
        // если не существует то создаем новую ошибку в БД
        System.out.println("________________" + nextArray.toString() + "________________________________");
        for (String myError : nextArray) {
            errorsFromDB = myDB.selectDateWhere(errorNameTable, myError.replace("[", "[[]"), myError.substring(0, 19));
            if (errorsFromDB.size() == 0) {
                myDB.insertDate(errorNameTable, myError, inputFile, myError.substring(0,19), type);
                errorArrayInList.add(myError);
                errorsFromDBToTextFile.add(myError);
            } else if (errorsFromDB.size() > 1) {
                System.out.println("Two many rows in DB/Its bad error!!!!!");
                System.out.println("Size: " + errorsFromDB.size());
            } else {
                System.out.println("This error is have in the DB: " + myError);
            }
            errorsFromDB.clear();
        }

        if(errorArrayInList.size() != 0) {
            ArrayList<String> st = new ArrayList<String>();
            String line = System.lineSeparator();
            for(String word : errorArrayInList) {
                st.add(word + line);
            }
//            Sender sender = new Sender(smtp, smtpPort);
//            sender.send(username, password, st, fromEmail, toEmail);
            textFile.write(outPutFile, String.valueOf(st));
            st.clear();
        }
//        myDB.deleteDate(errorNameTable);
    }
}
