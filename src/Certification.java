public class Certification {
    private int id;
    private String name;
    private String date;
    private String memo;
    private boolean favorite;
    private String user_id;

    public Certification(int id, String name,  String date, String memo, boolean favorite, String user_id) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.memo = memo;
        this.favorite = favorite;
        this.user_id = user_id;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getMemo() { return memo; }
    public boolean isFavorite() { return favorite; }
    public String getUser_id() { return user_id; }

}
