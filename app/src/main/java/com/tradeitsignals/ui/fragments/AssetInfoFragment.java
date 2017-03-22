package com.tradeitsignals.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tradeitsignals.R;

/**
 * Created by ThorneBird on 2/20/2016.
 */
public class AssetInfoFragment  extends DialogFragment{

    private final static String IMG_ID="imgId";
    private Integer imageId;
    public AssetInfoFragment(){}


    public static AssetInfoFragment newInstance(Integer imageId){
        AssetInfoFragment assetInfoFragment=new AssetInfoFragment();
        Bundle args= new Bundle();
        args.putInt(IMG_ID, imageId);
        assetInfoFragment.setArguments(args);
        return assetInfoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageId=getArguments().getInt(IMG_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_info,container,false);

        ImageView im = (ImageView)view.findViewById(R.id.im_asset);
        im.setImageDrawable(getActivity().getResources().getDrawable(imageId));


        return view;
    }
}
