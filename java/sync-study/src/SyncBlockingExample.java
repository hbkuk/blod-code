public class SyncBlockingExample {
    public static void main(String[] args) {
        System.out.println("음식 주문");
        
        String food = cookFood();     // Main Thread 대기
        
        System.out.println("음식 받음: " + food);
    }
    
    public static String cookFood() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return "치킨";
    }
}