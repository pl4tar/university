import voids.*;
import org.apache.log4j.Logger;

public class Main {

    public static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Start app");
        UI r = new UI();
        r.setVisible(true);
        r.setLocationRelativeTo(null);
        log.info("Finish app");
    }

}