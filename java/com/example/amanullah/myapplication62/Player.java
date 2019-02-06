package com.example.amanullah.myapplication63;

public class Player {
    private String name;

    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getPrice(){
        return this.price;
    }

    private Integer price;
    private Integer roll;
    private String role;
    private String hall;
    private String id;
    private Integer score;

//"{\"ballsFaced\":0,\"battingMatches\":0,\"bowlingMatches\":0,\"fifties\":0,\"fours\":0," +
//        "\"hall\":\"Bangabandhu Sheikh Mujibur Rahman Hall\",\"hatricks\":0,\"hundreds\":0,\"id\":\"-LXtj-c0XelgbmxojPt1\"," +
//        "\"maidens\":0,\"name\":\"Nahin Kumar\",\"oversDone\":0," +
//        "\"price\":8000,\"role\":\"Bowler\",\"roll\":1507018,\"runs\":0,\"runsConceded\":0,\"score\":0,\"sixes\":0,\"wicketsTaken\":0}"

    /*Statistics*/
    private Integer battingMatches;
    private Integer fifties,hundreds;
    private Integer fours,sixes;
    private Integer runs;
    private Integer ballsFaced;

    private Integer bowlingMatches;
    private Integer oversDone;
    private Integer runsConceded;
    private Integer wicketsTaken;
    private Integer maidens;
    private Integer hatricks;
    private Integer threeWicketHauls;

    private Integer catchesTaken;


    public Player(Integer roll, String name, String hall, String role, Integer price ) {
        this.setName(name);
        this.setRoll(roll);
        this.setHall(hall);
        this.setRole(role);
        this.setPrice(price);

        this.id = "";
        this.setScore(0);

        this.setBattingMatches(0);
        this.setFifties(0);
        this.setHundreds(0);
        this.setFours(0);
        this.setSixes(0);
        this.setRuns(0);
        this.setBallsFaced(0);

        this.setBowlingMatches( 0);
        this.setOversDone(0);
        this.setRunsConceded(0);
        this.setWicketsTaken(0);
        this.setMaidens(0);
        this.setHatricks(0);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getBattingMatches() {
        return battingMatches;
    }

    public void setBattingMatches(Integer battingMatches) {
        this.battingMatches = battingMatches;
    }

    public Integer getFifties() {
        return fifties;
    }

    public void setFifties(Integer fifties) {
        this.fifties = fifties;
    }

    public Integer getHundreds() {
        return hundreds;
    }

    public void setHundreds(Integer hundreds) {
        this.hundreds = hundreds;
    }

    public Integer getFours() {
        return fours;
    }

    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(Integer ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public Integer getBowlingMatches() {
        return bowlingMatches;
    }

    public void setBowlingMatches(Integer bowlingMatches) {
        this.bowlingMatches = bowlingMatches;
    }

    public Integer getOversDone() {
        return oversDone;
    }

    public void setOversDone(Integer oversDone) {
        this.oversDone = oversDone;
    }

    public Integer getRunsConceded() {
        return runsConceded;
    }

    public void setRunsConceded(Integer runsConceded) {
        this.runsConceded = runsConceded;
    }

    public Integer getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(Integer wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }
    public Integer getMaidens() {
        return maidens;
    }
    public void setMaidens(Integer maidens) {
        this.maidens = maidens;
    }
    public Integer getHatricks() {
        return hatricks;
    }
    public void setHatricks(Integer hattricks) {
        this.hatricks = hattricks;
    }
    public Integer getThreeWicketHauls() {
        return threeWicketHauls;
    }
    public void setThreeWicketHauls(Integer threeWicketHauls) {
        this.threeWicketHauls = threeWicketHauls;
    }

    public Float getStrikeRate(){
        return (float)(((float)((int)(runs))/((float)((int)(ballsFaced))))*100.0);
    }

    public Float getEconomyRate(){
        return (float)(runsConceded)/(float)(oversDone);
    }

    public Float getBowlingAverage(){
        return (float)(runs)/(float)(wicketsTaken);
    }


}

