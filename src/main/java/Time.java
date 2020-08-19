public class Time {
    public int hour = 5000;
    public long start = System.currentTimeMillis();
    public int currentTime;

    public int getCurrentTime() {
//        System.out.println(start);
        currentTime = (int) ((System.currentTimeMillis() - start) / hour);
        if (currentTime >= 24) {
            start = System.currentTimeMillis();
            currentTime = (int) ((System.currentTimeMillis() - start) / hour);
        }

        return currentTime;
    }
}
