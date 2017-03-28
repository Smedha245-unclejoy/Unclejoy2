package com.example.medhasingh.unclejoy2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import nl.changer.polypicker.*;
import nl.changer.polypicker.Config;

/**
 * Created by medha singh on 6/17/2016.
 */
public class Fragment5 extends Fragment {
    private DisplayImageOptions options;

    private GridView gridView;
    ArrayList<String> images=new ArrayList<String>();
    Button back, next;
    private int PICKFILE_REQUEST_CODE = 2;
    ImageView show;
    CheckBox list2;
    CheckBox list;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view5 = inflater.inflate(R.layout.upload, container, false);
        final CustomViewPager pager = (CustomViewPager) getActivity().findViewById(R.id.pager2);
        next = (Button) view5.findViewById(R.id.next2);
        gridView=(GridView)view5.findViewById(R.id.grid_select);
        back = (Button) view5.findViewById(R.id.back2);
        show=(ImageView)view5.findViewById(R.id.upload_call2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });

         list = (CheckBox) view5.findViewById(R.id.checkBox2);
         list2 = (CheckBox) view5.findViewById(R.id.checkBox);
        list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                            getContext(), R.style.MyDialogTheme);
                    builderSingle.setTitle("Select One Way to upload your list:-");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.select_dialog_singlechoice);
                    arrayAdapter.add("Browse/Take Pictures");


                    builderSingle.setNegativeButton("cancel",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    list.setChecked(false);
                                }
                            });

                    builderSingle.setAdapter(arrayAdapter,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                                        Config config=new Config.Builder()
                                                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                                                .setTabSelectionIndicatorColor(R.color.blue)
                                                .setCameraButtonColor(R.color.green)
                                                .setSelectionLimit(3)    // set photo selection limit. Default unlimited selection.
                                                .build();
                                        ImagePickerActivity.setConfig(config);
                                        getActivity().startActivityForResult(intent, 102);
                                    }

                                }
                            });
                    builderSingle.create();
                    builderSingle.show();
                }
            }

        });
        list2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pager.setCurrentItem(4);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(4);
            }
        });

        return view5;
    }






    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode==102) {

            if(resultCode==-1) {
                list.setChecked(false);
                Toast.makeText(getContext(),"Yo,I am here",Toast.LENGTH_SHORT);
                Parcelable[] parcelableUris = imageReturnedIntent.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }

                // Java doesn't allow array casting, this is a little hack
                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);


                if (uris != null) {
                    for (Uri uri : uris) {
                        Log.i("Medha", " uri: " + uri);

                    }

                }

                PhotoImageAdapter adp = new PhotoImageAdapter(getActivity(), uris);
                gridView.setAdapter(adp);
                DataHolderClass.getInstance().setDistributor_uri(uris);

            }
                }

            }



    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };


    public class PhotoImageAdapter extends BaseAdapter {
        private Context mContext;
        Uri[] une;

        public PhotoImageAdapter(Context c,Uri[] use) {
            mContext = c;
            une=use;
        }

        public int getCount() {
            return une.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {  // if it's not recycled, initialize some attributes
                show = new ImageView(mContext);
                show.setLayoutParams(new GridView.LayoutParams(150, 200));
                show.setScaleType(ImageView.ScaleType.CENTER_CROP);
                show.setPadding(1, 0, 0, 0);
            } else {
                show = (ImageView) convertView;
            }
            if (une != null) {
                for (Uri uri : une) {
                    show.setImageURI(une[position]);

                }}


            return show;
        }


    }



}
