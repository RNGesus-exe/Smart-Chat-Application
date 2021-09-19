package com.rngesus.mywallet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rngesus.mywallet.ModelClass.countryitem;
import com.rngesus.mywallet.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<countryitem> {

    int count=0;
    public SpinnerAdapter(Context context, ArrayList<countryitem> countryList) {
        super(context, 0, countryList);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView,  ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.image);
        TextView textViewName = convertView.findViewById(R.id.name);

        countryitem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getFlagImage());
            textViewName.setText(currentItem.getCountryName());
        }

        return convertView;
    }
}
