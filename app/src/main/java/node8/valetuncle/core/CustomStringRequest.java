package node8.valetuncle.core;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by steven on 9/1/15.
 */
public class CustomStringRequest extends Request<String> {
    private final Map<String, String> headers;
    private final Map<String,String> params;
    private final Response.Listener<String> mListener;

    public CustomStringRequest(int method, String url, Map<String,String> params, Map<String, String> headers, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        this.params = params;
        this.headers = headers;
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}