package sample;
public class StaticVariables {
    private static String[] items;
    private static String window;
    private static int whatToDo = 0;
    private static int id;

    public static String getWindow() {
        return window;
    }

    public static void setWindow(String window) {
        StaticVariables.window = window;
    }

    public static String[] getItems() {
        return items;
    }

    public static void setItems(String[] items) {
        setWhatToDo(1);
        setId(Integer.parseInt(items[0]));
        StaticVariables.items = items;
    }

    public static int getWhatToDo() {
        return whatToDo;
    }

    public static void setWhatToDo(int whatToDo) {
        StaticVariables.whatToDo = whatToDo;
        if (whatToDo == 0) {
            items = null;
            id = 0;
        }
    }

    public static int getId() {
        return id;
    }

    protected static void setId(int id) {
        StaticVariables.id = id;
    }
}
