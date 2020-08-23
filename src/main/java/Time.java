public class Time {
    public int minute = 300;
    public long start = System.currentTimeMillis();
    public int currentTime;

    public int getCurrentTime() {
//        System.out.println(start);
        currentTime = (int) ((System.currentTimeMillis() - start) / minute);
//        if (currentTime >= 24*60) {
//            start = System.currentTimeMillis();
//            currentTime = (int) ((System.currentTimeMillis() - start) / minute);
//        }

        return currentTime;
    }
}
