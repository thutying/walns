

public class ServerMessageHandle implements Runnable{
    static String id;

    public ServerMessageHandle(String name){
        this.id = name;
    }

    private void log(String msg){
        System.out.println(msg);
    }


    public void run(){
        String ThreadId = this.id;
        this.log(ThreadId);
        while(true){
            
            try{
                Thread.sleep(3000);
                this.log(ThreadId);

            }catch(InterruptedException e){
                e.printStackTrace();

            }
            
        }
    }

}
