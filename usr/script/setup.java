import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class setup{
  
  /**
   * 控制台打印消息
   * @param msg 需要打印的消息
   */
   private static void log(String msg){
     System.out.println(msg);
   }
  
  /**
   * 获取时间戳
   * @return 时间戳字符串
   * @throws Exception 错误
   */
  private static String getTime(){
    
    // 纳秒
    // long timestamp = System.currentTimeMillis();
    // 微秒
    long timestamp =System.nanoTime();
    
    String timestr = String.valueOf(timestamp);
    return timestr;
  }
  
  /**
   * 创建线程池
   * @param size 线程池大小
   * @return 创建好的线程池
   */
  public static ExecutorService createExecutor(int size){
    ExecutorService executor = Executors.newFixedThreadPool(size);
    return executor;
  }
  
  
  /**
   * 入口函数
   */
  public static void main(String[] args){
    
    ExecutorService executor = createExecutor(5);
    for (int i=1; i<=10; i++){
      // 线程池需要运行的了类名
      String times = getTime();
      Runnable worker = new tasking("hello:"+ times);
      // 加入任务进线程池
      executor.execute(worker);
    }
    // 关闭线程池
    executor.shutdown();
    while (!executor.isTerminated()) {}
    
  }
  
}


class tasking implements Runnable {
  private String msg;
  
  private void log(String msg){
    System.out.println(msg);
  }
  public tasking(String msg){
    this.msg = msg;
  }
  
  @Override
  public void run(){
    try{
      Thread.sleep(3000);
    }catch(InterruptedException e){
      e.printStackTrace();
    }
    this.log(this.msg);
  }
}
  
