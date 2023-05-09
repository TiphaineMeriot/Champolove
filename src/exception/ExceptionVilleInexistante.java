package exception;

public class ExceptionVilleInexistante extends Exception{
    public ExceptionVilleInexistante(){
         super("La ville est inexistante,non renseignée (une ville doit faire entre 3 et 200 caractères) ou n'a pas l'orthographe adéquate");
    }
}
