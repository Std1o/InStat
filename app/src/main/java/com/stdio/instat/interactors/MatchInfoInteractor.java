package com.stdio.instat.interactors;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MatchInfoInteractor {

    Context context;

    public MatchInfoInteractor(Context context) {
        this.context = context;
    }

    public void getMatchInfo(LoadInfoCallback callback) {
        String url ="https://api.instat.tv/test/data";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        callback.onLoad(jsonObject);
                        System.out.println(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = null;
                try {
                    JSONObject post_dict = new JSONObject();
                    post_dict.put("proc", "get_match_info");
                    JSONObject params = new JSONObject();
                    params.put("_p_sport", 1);
                    params.put("_p_match_id", 1724836);
                    post_dict.put("params", params);
                    requestBody = post_dict.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public interface LoadInfoCallback {
        void onLoad(JSONObject info);
    }
}
