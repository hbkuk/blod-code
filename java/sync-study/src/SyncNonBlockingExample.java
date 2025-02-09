public class SyncNonBlockingExample {
    public static void main(String[] args) {
        System.out.println("음식 주문");
        
        while(!isFoodReady()) { // 음식이 준비될 때까지 계속 확인(Polling)
            System.out.println("음식이 아직 안 나왔네. 1초 후 다시 확인");
            try {
                System.out.println("핸드폰 보는 중");
                Thread.sleep(1000);             // 1초 후 다시 확인
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("음식 받음!");
    }
    
    public static boolean isFoodReady() {
        return Math.random() > 0.8;
    }
}