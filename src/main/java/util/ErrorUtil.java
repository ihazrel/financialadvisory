package util;

public class ErrorUtil {
    public static String format(String fileName, String functionName, String message) {
        return "[" + fileName + "#" + functionName + "] " + message;
    }

    public static void log(String fileName, String functionName, Exception e) {
        System.out.println(format(fileName, functionName, e.toString()));
    }
}
