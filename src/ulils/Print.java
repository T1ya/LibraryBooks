package ulils;


public class Print {
    public static final String RESET = "\u001B[0m";  // Сброс цвета
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static void Error(String message) {
        System.out.println(RED +  "Error: " + message + RESET);
    }

    public static void Success(String message) {
        System.out.println(GREEN +"Success: " + message + RESET);
    }

}
