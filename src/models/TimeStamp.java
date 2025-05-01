package models;

public class TimeStamp {
    
    private long lastStamp;

    public TimeStamp() {
        lastStamp = System.currentTimeMillis();
    }

    public float getStamp() {
        long current = System.currentTimeMillis();
        float elapsedSeconds = (current - lastStamp) / 1000.0F;
        lastStamp = current;
        return elapsedSeconds;
    }
}
