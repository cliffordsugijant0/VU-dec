package node8.valetuncle.helpers;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import node8.valetuncle.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stevsut on 20/4/16.
 */
public class Utils {


    public static int generateRandom(){

        Random rand = new Random();
        int random = rand.nextInt(9000)+1000;

        return random;

    }

    public static void showFieldError(EditText editText,String msg){
        editText.setError(msg);
    }

    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static byte[] readFile(String file) throws IOException {
        return readFile(new File(file));
    }

    public static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * method for get a custom Toast
     *
     * @param mContext
     * @param Message
     */
    public static void Toast(Context mContext, String Message) {
        // create a LinearLayout and Views
        LinearLayout ToastLayout = new LinearLayout(mContext);
        ToastLayout.setBackgroundResource(R.drawable.bg_buttons_main);
        ToastLayout.setPadding(30, 30, 30, 30);
        ToastLayout.setGravity(Gravity.BOTTOM);

        TextView message = new TextView(mContext);
        // set the color and size
        message.setTextColor(Color.BLACK);
        message.setTextSize(14);
        message.setPadding(10, 10, 10, 25);
        message.setGravity(Gravity.CENTER);

        // set the text and the icon you want to show in  Toast
        message.setText(Message);
        ImageView alertIcon = new ImageView(mContext);

        // get the image resource
        alertIcon.setImageResource(R.mipmap.ic_launcher);
        alertIcon.setPadding(5, 5, 5, 5);


        // add the View TextView and icon in layout
        ToastLayout.addView(message);
        ToastLayout.addView(alertIcon);
        Toast toast = new Toast(mContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        // Set The layout as Toast View
        toast.setView(ToastLayout);
        //here i set the position of toast
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        //show the toast
        toast.show();
    }

    public static String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String FormatDate2(String dateStr)
    {
        //2015-03-31 03:46:41
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.ssssss'Z'");

//        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime d1 = format.withZoneUTC().parseDateTime(dateStr).toDateTime(DateTimeZone.getDefault());
        DateTime d2 = new DateTime();

        return d1.toString("EEE, MMM dd yyyy - hh:mm a");
//        Days daysBetween = Days.daysBetween(d1, d2);
//        //Calculate Days
//        int days = daysBetween.getDays();
//        if(days > 0)
//        {
//            if(days > 1)
//            {
//                return d1.toString("EEE, h:mm a");
//            }
//            else
//            {
//                String res = "Yest, ";
//                return res + d1.toString("" +
//                        "h:mm a");
//            }
//        }
//        else
//        {
//            return d1.toString("h:mm a");
//        }
    }

    public static String FormatDate(String dateStr)
    {
        //2015-03-31 03:46:41
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

//        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime d1 = format.withZoneUTC().parseDateTime(dateStr).toDateTime(DateTimeZone.getDefault());
        DateTime d2 = new DateTime();

        return d1.toString("EEE, MMM dd yyyy - hh:mm a");
//        Days daysBetween = Days.daysBetween(d1, d2);
//        //Calculate Days
//        int days = daysBetween.getDays();
//        if(days > 0)
//        {
//            if(days > 1)
//            {
//                return d1.toString("EEE, h:mm a");
//            }
//            else
//            {
//                String res = "Yest, ";
//                return res + d1.toString("" +
//                        "h:mm a");
//            }
//        }
//        else
//        {
//            return d1.toString("h:mm a");
//        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
