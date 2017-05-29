package Utilities;

/**
 * Created by mnjru on 5/27/2017.
 */

public class Topic {
    //private variables
    private String topic;
    private String last_accessed;
    private int Count;

    public Topic(String topic, String last_accessed, int count) {
        this.topic = topic;
        this.last_accessed = last_accessed;
        Count = count;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLast_accessed() {
        return last_accessed;
    }

    public void setLast_accessed(String last_accessed) {
        this.last_accessed = last_accessed;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
