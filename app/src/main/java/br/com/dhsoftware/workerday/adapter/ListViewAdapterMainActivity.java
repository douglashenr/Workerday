package br.com.dhsoftware.workerday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;

public class ListViewAdapterMainActivity extends BaseAdapter {

    private List<Registry> registryArrayList;
    private final Activity activity;

    public ListViewAdapterMainActivity(List<Registry> registryArrayList, Activity activity) {
        this.registryArrayList = registryArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return registryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return registryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.main_activity_list_view_adapter, parent, false);
        Registry registry = registryArrayList.get(position);

        TextView textView = view.findViewById(R.id.list_view_main_activity_date);
        textView.setText("Data: " + registry.getDayString());

        textView = view.findViewById(R.id.list_view_main_activity_day);
        textView.setText(DateUtil.getInstanceDateUtil().nameOfDay(registry.getDay()));





        return view;
    }
}
