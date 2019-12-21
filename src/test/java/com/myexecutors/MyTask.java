package myexecutors;

/**
 * @author chenbin
 * @ClassName MyTask
 * @Description TODO
 * @date 2019/11/30 10:35
 * @Vsersion
 */
public class MyTask implements Runnable{

    private String id;
    private String name;

    public MyTask(String id,String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        try {
            System.out.println("run taskId = " + this.id);
            Thread.sleep(5*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.id;
    }
}
