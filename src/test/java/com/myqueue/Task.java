package myqueue;

/**
 * @author chenbin
 * @ClassName Task
 * @Description TODO
 * @date 2019/11/24 9:30
 * @Vsersion
 */
public class Task implements Comparable<Task> {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Task task) {
        return this.id > task.getId() ? 1 : (this.id<task.getId() ? -1 : 0);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
