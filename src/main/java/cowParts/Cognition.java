package cowParts;

import java.util.Random;

public class Cognition {

    private Random random = new Random();

    //Emotions: 0 is low 100 is high
    private int anger = random.nextInt(100);
    private int anticipation = random.nextInt(100);
    private int disgust = random.nextInt(100);
    private int fear = random.nextInt(100);
    private int happiness = random.nextInt(100);
    private int surprise = random.nextInt(100);
    private int trust = random.nextInt(100);

    //Finances 0 is low 100 is high
    private int income = random.nextInt(100);
    private int bills = random.nextInt(100);
    private int food = random.nextInt(100);
    private int taxes = random.nextInt(100);
    private int savings = random.nextInt(100);
    private int debt = random.nextInt(100);

    //Social 0 is low 100 is high
    private int boredom = random.nextInt(100);
    private int companionship = random.nextInt(100);

    //Physical 0 is low 100 is high
    private int hunger = random.nextInt(100);
    private int thirst = random.nextInt(100);
    private int age = random.nextInt(100) * 360;
    private int physicalHealth = random.nextInt(100);
    private int sleepiness = random.nextInt(100);

    //Mental 0 is low 100 is high
    private int faith = random.nextInt(100);
    private int mentalHealth = random.nextInt(100);

    //Academic 0 is low 100 is high
    private int intelligence = random.nextInt(100);

    public int getAnger() {
        return anger;
    }

    public void setAnger(int anger) {
        this.anger = anger;
    }

    public int getAnticipation() {
        return anticipation;
    }

    public void setAnticipation(int anticipation) {
        this.anticipation = anticipation;
    }

    public int getDisgust() {
        return disgust;
    }

    public void setDisgust(int disgust) {
        this.disgust = disgust;
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getSurprise() {
        return surprise;
    }

    public void setSurprise(int surprise) {
        this.surprise = surprise;
    }

    public int getTrust() {
        return trust;
    }

    public void setTrust(int trust) {
        this.trust = trust;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getBoredom() {
        return boredom;
    }

    public void setBoredom(int boredom) {
        this.boredom = boredom;
    }

    public int getCompanionship() {
        return companionship;
    }

    public void setCompanionship(int companionship) {
        this.companionship = companionship;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getAge() {
        return (age / 360);
    }

    public void setAge(int age) {
        this.age = age * 360;
    }

    public int getPhysicalHealth() {
        return physicalHealth;
    }

    public void setPhysicalHealth(int physicalHealth) {
        this.physicalHealth = physicalHealth;
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(int mentalHealth) {
        this.mentalHealth = mentalHealth;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSleepiness() {
        return this.sleepiness;
    }

    public void setSleepiness(int sleepiness) {
        this.sleepiness = sleepiness;
    }

    /**
     * @return The sum of the emotions as a string over 700, as a string.
     */
    public String getEmotionAggregate() {
        return Integer.toString((anger + anticipation + disgust + fear + happiness + surprise + trust)) + "/700";
    }

    /**
     * @return The sum of finances as a string over 600, as a string.
     */
    public String getFinanceAggregate() {
        return Integer.toString((income + bills + food + taxes + savings + debt)) + "/600";
    }

    /**
     * @return The sum of socials as a string over 200, as a string.
     */
    public String getSocialAggregate() {
        return Integer.toString((boredom + companionship)) + "/200";
    }

    /**
     * @return The sum of physicals as a string over 300, as a string.
     */
    public String getPhysicalAggregate() {
        return Integer.toString((hunger + age + physicalHealth)) + "/300";
    }

    /**
     * @return The sum of mentals as a string over 200, as a string.
     */
    public String getMentalAggregate() {
        return Integer.toString((faith + mentalHealth)) + "/200";
    }

    /**
     * @return The sum of the emotions as a string over 100, as a string.
     */
    public String getAcademicAggregate() {
        return Integer.toString((intelligence)) + "/100";
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }
    public int getThirst() {
        return this.thirst;
    }
}
