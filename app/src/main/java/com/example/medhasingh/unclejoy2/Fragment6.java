package com.example.medhasingh.unclejoy2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by medha singh on 6/25/2016.
 */
public class Fragment6 extends Fragment implements AdapterView.OnItemSelectedListener {
    private String UPLOAD_URL="https://ujapi.herokuapp.com/api/v1/u/bookings";
    Spinner day_spin;
    Spinner time_spin;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view6=inflater.inflate(R.layout.selectdate,container,false);
        final EditText ed=(EditText)view6.findViewById(R.id.address);
        final CustomViewPager pager = (CustomViewPager) getActivity().findViewById(R.id.pager2);
        // public static final String[] state={"",""}
        Button bt=(Button) view6.findViewById(R.id.sel_back);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(3);
            }
        });
        day_spin = (Spinner) view6.findViewById(R.id.spinner);
        //ArrayAdapter<CharSequence> adapter_day=ArrayAdapter.createFromResource(getContext(),R.array.dater,android.R.layout.simple_spinner_item );
        //adapter_day
        //        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //day_spin.setAdapter(adapter_day);


        time_spin = (Spinner) view6.findViewById(R.id.spinnerstate);
        day_spin.setAdapter(new CalendarSpinnerAdapter(getActivity(), 7));
        day_spin.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter_time=ArrayAdapter.createFromResource(getContext(), R.array.timer, android.R.layout.simple_spinner_item);
        adapter_time
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spin.setAdapter(adapter_time);
        time_spin.setOnItemSelectedListener(this);
        Button submit=(Button)view6.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addr=ed.getText().toString();
                createbooking(addr);
            }
        });
        return view6;}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinner) {
            System.out.println(parent.getItemAtPosition(position));
            Calendar calender= (Calendar)parent.getItemAtPosition(position);
            String DAY=new SimpleDateFormat("dd-MM-yyyy").format(calender.getTimeInMillis());
            System.out.println(DAY);
            //Adding values to editor
           SharedPreferences sharedPreferences2=this.getContext().getSharedPreferences("dame",0);
            SharedPreferences.Editor editor2=sharedPreferences2.edit();
            editor2.putString("DATE",DAY);
            editor2.apply();
            System.out.println(DAY);
        }

        if(spinner.getId()==R.id.spinnerstate){
        String TIME = parent.getItemAtPosition(position).toString();
            SharedPreferences sharedPreferences2=this.getContext().getSharedPreferences("dame", 0);
            SharedPreferences.Editor editor2=sharedPreferences2.edit();
            editor2.putString("TIME",TIME);
            editor2.apply();
       System.out.println(TIME);
    }}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void createbooking(final String  ad) {
        final ProgressDialog pdialog=new ProgressDialog(getContext());
        pdialog.setTitle("Please wait");
        pdialog.setMessage("Loading..");
        pdialog.show();


                ArrayList<String> mylist = new ArrayList<String>();
                JSONArray array = new JSONArray();
                final Uri[] ur=DataHolderClass.getInstance().getDistributor_uri();
                final Uri[] urd=DataHolderClass.getInstance().getDistributor_uri();
                if (ur != null) {
                    for (Uri uri : ur) {

                            File file=new File(uri.getPath());
                           Bitmap bitmap=decodeFile(file);
                           // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), );
                            array.put("data:image/jpeg;base64," + converttobase64(bitmap));

                    }}
                    final SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME,0);
                    SharedPreferences sharedPreferences2=getContext().getSharedPreferences("dame", 0);
                    JSONObject json=new JSONObject();
                    JSONObject js = new JSONObject();
                    try {
                        js.put("store_category_id", DataHolderClass.getInstance().getDistributor_id());
                        js.put("store_sub_category_id", sharedPreferences.getString(Config.SHARED_STORE_SUB_CATEGORY, ""));
                        js.put("date",sharedPreferences2.getString("DATE",""));
                        js.put("time", sharedPreferences2.getString("TIME",""));
                        js.put("attachment_data",array);
                        js.put("address",ad);
                        json.put("booking",js);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String jsonstring=json.toString();
                    System.out.println(jsonstring);


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UPLOAD_URL, json,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pdialog.dismiss();
                        Toast.makeText(getContext(), "Booking Created", Toast.LENGTH_SHORT).show();
                        System.out.println(response.toString());

                    }
                }
                , new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                VolleyLog.d("Medha", "Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 408) {
                    pdialog.dismiss();
                    Toast.makeText(getContext(),"Request Timed Out!! Please try again",Toast.LENGTH_SHORT).show();
                    // HTTP Status Code: 401 Unauthorized
                }
               else if (networkResponse != null && networkResponse.statusCode == 401) {
                    pdialog.dismiss();
                    Toast.makeText(getContext(),"Please login again first !!",Toast.LENGTH_SHORT).show();

                    // HTTP Status Code: 401 Unauthorized
                }else {
                    pdialog.dismiss();
                    Toast.makeText(getContext(),"Please try again,slow internet connection",Toast.LENGTH_SHORT).show();
                }

            }

        }) {

            /**
             * Passing some request headers
             */
           @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
               final SharedPreferences sharedPreferences=getContext().getSharedPreferences(Config.SHARED_PREF_NAME, 0);
                //headers.put("id",sharedPreferences.getString(Config.SHARED_USER_ID,""));
                headers.put("Authorization", sharedPreferences.getString(Config.TOKEN_SHARED_PREF, ""));
                return headers;
            }
        };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        int socketTimeout = 200000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);
                requestQueue.add(jsonObjReq);

            }




    public String converttobase64(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        System.out.println(encodedImage.getBytes());
        return encodedImage;
    }
    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=1024;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

}


