import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Profil_Courant {
    public void tri() {
        Comparator<Profil> compa_create_date = Comparator.comparing((Profil p) -> p.date_de_creation, Comparator.reverseOrder())
                                      .thenComparing(p -> p.nom);



    }
}