

public class setup {
    
    private static void log(String msg){
        System.out.println(msg);
    }


    private static void ThreadObject(String id){
        String ThreadId = id;
        ServerMessageHandle object = new ServerMessageHandle(ThreadId);
        Thread thread = new Thread(object);
        //thread.setDaemon(true);
        thread.setName(ThreadId);
        thread.start();
        //log(id);
    }

    public static void main(String[] args){
        //log("starting");
        ThreadObject("first");
        ThreadObject("second");
    }
}
