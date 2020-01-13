package com.lucky.jacklamb.distributed;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MyTest {

	public static void main(String[] args) {
		// 创建客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
 
        //创建一个get请求
        HttpGet httpget = new HttpGet("http://127.0.0.1:8865/book");
        CloseableHttpResponse response = null;
        try {
            //发送请求并获取返回结果
            response = httpclient.execute(httpget);
 
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){//判断状态码
                HttpEntity httpEntity = response.getEntity(); //获取返回body
                String result = EntityUtils.toString(httpEntity,"UTF-8");// 转成string
                System.out.println("result:"+result);
            }
        } catch(IOException ioe){
        	ioe.printStackTrace();
        }finally {
            try{
                if(response != null){
                    response.close(); //释放连接
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
            try{
                if(httpclient != null){
                    httpclient.close();//关闭客户端
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
        }

	}

}
