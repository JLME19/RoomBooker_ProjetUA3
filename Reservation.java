public class Reservation {

    private String nomClient;
    private String date;
    private int nombrePersonnes;
    private String type;

    public Reservation(String nomClient, String date, int nombrePersonnes, String type) {
        this.nomClient = nomClient;
        this.date = date;
        this.nombrePersonnes = nombrePersonnes;
        this.type = type;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getDate() {
        return date;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Réservation {" +
                "Client='" + nomClient + '\'' +
                ", Date='" + date + '\'' +
                ", Nombre de personnes=" + nombrePersonnes +
                ", Type='" + type + '\'' +
                '}';
    }
}
