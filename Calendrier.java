import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; 
        int day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println("Today is: " + year + "-" + month + "-" + day);
    }
}
