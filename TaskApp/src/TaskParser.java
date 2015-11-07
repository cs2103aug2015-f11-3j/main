package src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskParser {
    
    private static Logger logger = Logger.getLogger("Warning");
    private static final String PARSE_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";
    
    public TaskParser() {
        
    }
    
    public static ArrayList<Tasks> parseArrList(ArrayList<String> arrList) {
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
        
        for(int i=0; i<arrList.size(); i++) {
            ArrayList<Date> dates = new ArrayList<Date>();
            String event = createEvent(arrList.get(i));
            toDateList(dates ,arrList.get(i));
            tasks.add(new Tasks(event, dates));
        }
        return tasks;
    }
    
    private static String createEvent(String string) {
        String[] tokens = string.split("\\[");
        return tokens[0].trim();
    }

    private static void toDateList(ArrayList<Date> dates, String string) {
        String[] tokens = string.split("\\[");
        String str =tokens[1].substring(0, tokens[1].length()-1);
        String[] rawDate = str.split(",");
        SimpleDateFormat sdf = new SimpleDateFormat(PARSE_PATTERN);
        
        for(int i=0; i<rawDate.length; i++) {
            try {
                Date date = sdf.parse(rawDate[i].trim());
                dates.add(date);
            } catch (ParseException e) {
                logger.log(Level.INFO, "Acceptable Condition: empty date.");
            }   
        }
    }
    
}
