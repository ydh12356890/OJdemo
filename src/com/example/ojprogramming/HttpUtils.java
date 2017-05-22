package com.example.ojprogramming;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

//ͨ��HttpURLCnnection��ȡ����������ݣ��ŵ����Ȼ��������������ת��Ϊ�ַ���������ڣ������ϰ汾1-04��
public class HttpUtils {

    public HttpUtils() {
        // TODO Auto-generated constructor stub
    }

    public static String getJsonContent(String url_path) {
        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if (code == 200) {
                return changeInputStream(connection.getInputStream());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    private static String changeInputStream(InputStream inputStream) {
        // TODO Auto-generated method stub
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[102400];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("abc",jsonString);
        return jsonString;
    }
}
