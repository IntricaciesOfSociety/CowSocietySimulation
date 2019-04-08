package cowParts.cowThoughts;

import org.jetbrains.annotations.Contract;

import java.util.Random;

/**
 * Holds all the cognitive values for each cow. Also contains the getters and setters for each value.
 */
public class Cognition {

    private Random random = new Random();

    //Emotions: 0 is low 100 is high
    private int anger = random.nextInt(101);
    private int anticipation = random.nextInt(101);
    private int disgust = random.nextInt(101);
    private int fear = random.nextInt(101);
    private int happiness = random.nextInt(101);
    private int surprise = random.nextInt(101);
    private int trust = random.nextInt(101);

    //Finances 0 is low 100 is high
    private int income = 0;
    private int bills = 0;
    private int taxes = 0;
    private int savings = random.nextInt(101);
    private int debt = random.nextInt(101);

    //Social 0 is low 100 is high
    private int boredom = random.nextInt(101);
    private int companionship = random.nextInt(101);

    //Physical 0 is low 100 is high
    private int hunger = random.nextInt(101);
    private int thirst = random.nextInt(101);
    private int age = random.nextInt(101) * 360;
    private int physicalHealth = random.nextInt(101);
    private int sleepiness = 100;

    //Mental 0 is low 100 is high
    private int faith = random.nextInt(101);
    private int mentalHealth = random.nextInt(101);

    //Academic 0 is low 100 is high
    private int intelligence = random.nextInt(101);

    private int fitness = random.nextInt(900);

    /**
     * Tests the given modification delta against a given cognitive element to see if a limit would be met. If a limit
     * is met, sets the cognitive element to the closest limit.
     * @param cog The cognitive element to check against
     * @param delta The modification delta to change the cognitive element by
     * @return The properly limited new cognitive element value
     */
    @Contract(pure = true)
    private int testLimit(int cog, int delta) {
        if (cog + delta >= 0 && cog + delta <= 100)
            return cog + delta;
        else if (cog + delta < 0)
            return 0;
        else
            return 100;
    }

    public int getAnger() {
        return anger;
    }

    public void setAnger(int anger) {
        this.anger = testLimit(this.anger, anger);
    }

    public int getAnticipation() {
        return anticipation;
    }

    public void setAnticipation(int anticipation) {
        this.anticipation = testLimit(this.anticipation, anticipation);
    }

    public int getDisgust() {
        return disgust;
    }

    public void setDisgust(int disgust) {
        this.disgust = testLimit(this.disgust, disgust);
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = testLimit(this.fear, fear);
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = testLimit(this.happiness, happiness);
    }

    public int getSurprise() {
        return surprise;
    }

    public void setSurprise(int surprise) {
        this.surprise = testLimit(this.surprise, surprise);
    }

    public int getTrust() {
        return trust;
    }

    public void setTrust(int trust) {
        this.trust = testLimit(this.trust, trust);
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = testLimit(this.income, income);
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = testLimit(this.bills, bills);
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = testLimit(this.taxes, taxes);
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = testLimit(this.savings, savings);
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = testLimit(this.debt, debt);
    }

    public int getBoredom() {
        return boredom;
    }

    public void setBoredom(int boredom) {
        this.boredom = testLimit(this.boredom, boredom);
    }

    public int getCompanionship() {
        return companionship;
    }

    public void setCompanionship(int companionship) {
        this.companionship = testLimit(this.companionship, companionship);
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = testLimit(this.hunger, hunger);
    }

    public int getThirst() {
        return this.thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = testLimit(this.thirst, thirst);
    }

    public int getAge() {
        return (age / 360);
    }

    public void setAge(int age) {
        this.age += age * 360;
    }

    public int getPhysicalHealth() {
        return physicalHealth;
    }

    public void setPhysicalHealth(int physicalHealth) {
        this.physicalHealth = testLimit(this.physicalHealth, physicalHealth);
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = testLimit(this.faith, faith);
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(int mentalHealth) {
        this.mentalHealth = testLimit(this.mentalHealth, mentalHealth);
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = testLimit(this.intelligence, intelligence);
    }

    public int getSleepiness() {
        return sleepiness;
    }

    public void setSleepiness(int sleepiness) {
        this.sleepiness = testLimit(this.sleepiness, sleepiness);
    }

    /**
     * @return The sum of the emotions as a string over 200, as a string.
     */
    public String getEmotionAggregate() {
        return ((happiness - fear) - anger) + "/100";
    }

    /**
     * @return The sum of finances as a string over 200, as a string.
     */
    public String getFinanceAggregate() {
        return ((income + savings) - debt) + "/200";
    }

    /**
     * @return The sum of socials as a string over 100, as a string.
     */
    public String getSocialAggregate() {
        return (companionship - boredom) + "/100";
    }

    /**
     * @return The sum of physicals as a string over 300, as a string.
     */
    public String getPhysicalAggregate() {
        return (hunger + thirst + physicalHealth) + "/300";
    }

    /**
     * @return The sum of mentals as a string over 200, as a string.
     */
    public String getMentalAggregate() {
        return (mentalHealth + intelligence) + "/200";
    }

    /**
     * @return The sum of the emotions as a string over 100, as a string.
     */
    public String getAcademicAggregate() {
        return intelligence + "/100";
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int newFitness) {
        fitness = newFitness;
    }

    public void clear() {
        anger = 0;
        anticipation = 0;
        disgust = 0;
        fear = 0;
        happiness = 0;
        surprise = 0;
        trust = 0;

        income = 0;
        bills = 0;
        taxes = 0;
        savings = 0;
        debt = 0;

        boredom = 0;
        companionship = 0;

        hunger = 0;
        thirst = 0;
        age = 360;
        physicalHealth = 0;
        sleepiness = 0;

        faith = 0;
        mentalHealth = 0;

        intelligence = 0;

        fitness = 0;
    }
}