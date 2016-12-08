package node8.valetuncle.core;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by steven on 22/5/15.
 */
public class CustomJSONRequest extends Request<JSONObject> {
    private final Map<String, String> headers;
    private final Map<String,String> params;
    private final Response.Listener<JSONObject> mListener;

    public CustomJSONRequest(int method, String url, Map<String,String> params, Map<String, String> headers, Response.Listener<JSONObject> listener,
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
    protected void deliverResponse(JSONObject response) {
        if(mListener!=null) {
            mListener.onResponse(response);
        }
    }
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
