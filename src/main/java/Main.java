public class Main {
    public static void main(String[] args) {
        Time time = new Time();
//        Thread some = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println(time.getCurrentTime());
//                }
//            }
//        });
//        Time time=new Time();
//        some.start();

        Const cons=new Const();
        cons.setPow();
        System.out.println(cons.pow(2,"Consumer1"));
    }
}
