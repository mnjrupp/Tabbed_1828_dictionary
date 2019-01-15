package Utilities;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mnjru.tabbed_1828_dictionary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mnjru on 6/10/2017.
 */

public class HistoryAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public HistoryAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(Topic object) {
        list.add(object);
        super.add(object);
    }

    public void remove(String topicname) {

        for(int i =0;i<list.size();i++){
            Topic topic = (Topic)list.get(i);
            if(topic.getTopic().equals(topicname)){
                list.remove(i);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        TopicHolder topicHolder;
        if(row==null){

            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.raw_history_row,parent,false);
            topicHolder = new TopicHolder();
            topicHolder.tx_topicname = (TextView) row.findViewById(R.id.text_topic);
            topicHolder.tx_lastaccess = (TextView) row.findViewById(R.id.text_last_accessed);
            topicHolder.tx_count = (TextView) row.findViewById(R.id.text_count);

            row.setTag(topicHolder);
        }
        else{
            topicHolder = (TopicHolder) row.getTag();
        }

        Topic topic = (Topic) getItem(position);
        topicHolder.tx_topicname.setText(topic.getTopic().toString());
        topicHolder.tx_lastaccess.setText(topic.getLast_accessed());
        topicHolder.tx_count.setText(Integer.toString(topic.getCount()));
        return row;
    }

    static class TopicHolder
    {
        TextView tx_topicname,tx_lastaccess,tx_count;
    }
}
