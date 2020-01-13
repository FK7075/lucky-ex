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
		// �����ͻ���
        CloseableHttpClient httpclient = HttpClients.createDefault();
 
        //����һ��get����
        HttpGet httpget = new HttpGet("http://127.0.0.1:8865/book");
        CloseableHttpResponse response = null;
        try {
            //�������󲢻�ȡ���ؽ��
            response = httpclient.execute(httpget);
 
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){//�ж�״̬��
                HttpEntity httpEntity = response.getEntity(); //��ȡ����body
                String result = EntityUtils.toString(httpEntity,"UTF-8");// ת��string
                System.out.println("result:"+result);
            }
        } catch(IOException ioe){
        	ioe.printStackTrace();
        }finally {
            try{
                if(response != null){
                    response.close(); //�ͷ�����
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
            try{
                if(httpclient != null){
                    httpclient.close();//�رտͻ���
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
        }

	}

}
