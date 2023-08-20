import commons.FileNames;
import controllers.TaskController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;


public class TaskManagerMain {
    public static Logger logger = LogManager.getLogger(TaskManagerMain.class); /*нужен для того, чтобы после добавления логирования
    все логи сохранялись в этот объект*/

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        Scanner scanner = new Scanner(System.in);
        TaskController taskController = new TaskController();

        while (true) {
            System.out.println("Выберите действие:\n1.Создать задачу\n2.Обновить задачу\n3.Найти задачу\n4.Удалить задачу\n5.Остановить программу");
            int choice = scanner.nextInt();
            if (choice == 1) {
                JSONObject jsonObject = taskController.choiceTask();
                taskController.addInDB(jsonObject);
            } else if (choice == 3) {
                JSONArray jsonArray = taskController.getInDBAll();
                taskController.getInDB(jsonArray);
            } else if (choice == 2) {
                taskController.updateTask();
            } else if (choice == 4) {
                taskController.deleteTask();
            } else if (choice == 5) {
                break;
            }
        }
    }
}

/*
ПОльзователь вводит число
Если 1 , то :
1.компьютер спрашивает название файла json
1.1. Заходим в интерфейс криейт таск и в нём создаём метод чёйс таск
1.2. Овверайдим созданный метод в классе ТаскКонтроллер
1.3. Релизовываем метод ТаскКонтроллер который запрашивает название файла о возвращает джсон

Дз1907:
Чтобы открыть нужный джсон нужно указать папку где будут лежать все джсоны, создать эту папку в ресурсес.
Создать папку коммонс в джава и в нём создать класс файлНеймс внутри, которого должен будет лежать атрибут паблик статик файнл,
который константа, хранящий в себе путь к папке ресурсес.
Открыть файл используя данный созданный атрибут.(проверить сиаутом содержимого файла)



2.далее надо распарсить джсон
дз2007
доделать вместо ссылки переменную в таскконтроллер
для перехода в мэйне ловлю(это значит надо сохранить) этот объект класса джсон и кладу его вметод криейт, который будет
из этого джсона создавать объект класса таск и возвращать его.

3.сохранить данные в бд
 */