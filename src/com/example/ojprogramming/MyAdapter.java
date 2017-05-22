/**
 * 
 */
package com.example.ojprogramming;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Administrator
 *
 
public class MyAdapter extends BaseAdapter {

	
	 @Override
     public int getCount() {
         Log.d("AAA", ""+datas.size());        
         return datas.size();
         
             
     }

     @Override
     public Object getItem(int position) {
 
         return datas.get(position);
     }

     @Override
     public long getItemId(int position) {
         
         return position;
     }
     
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         View view = View.inflate(ShouyeList.this, R.layout.items, null);
         TextView pid = (TextView) view.findViewById(R.id.items_number);
         TextView title = (TextView) view.findViewById(R.id.items_text);
         TextView source = (TextView) view.findViewById(R.id.items_rate);
         Data data = datas.get(position);
         Log.d("aaaaa",datas.get(position).getExp_name() );
         
         id.setText(String.valueOf(datas.get(position).getId()));
         exp_name.setText(datas.get(position).getExp_name());
         //Log.i("exp_name", datas.get(position).getExp_name());
         exp_tech.setText(datas.get(position).getExp_tech());            
         return view;
     }
     
}

}**/
