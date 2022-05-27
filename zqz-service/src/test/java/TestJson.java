import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: TestJson
 * @Date: Created in 16:37 2022/5/13
 */
public class TestJson {


    @Test
    public void test1() {
        try {
            String userJson = "[{\"question\": \"1.What is your marital status?\", \"a\": \"(a)Single\", \"b\": \"(b)Married\"}," +
                    "{\"question\": \"2.Are you planning on getting married next year?\", \"a\": \"(a)Yes\", \"b\": \"(b)No\"}," +
                    "{\"question\": \"3.How long have you been married?\", \"a\": \"(a)Less than a year\", \"b\": \"(b)More than a year\"}," +
                    "{\"question\": \"4.Have you celebrated your one year anniversary?\", \"a\": \"(a)Yes\" , \"b\": \"(b)No\"}]";

            JSONArray jsonArray = JSON.parseArray(userJson);
            System.out.println(jsonArray);
            List<Question> javaList = jsonArray.toJavaList(Question.class);
            writeToFile(javaList);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void writeToFile(List<Question> data) throws Exception {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("D:\\test.txt"));
            for (int i = 0; i < data.size(); i++) {
                bw.write(JSON.toJSONString(data.get(i)));
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (Exception e) {
            throw e;
        }
    }


    @Test
    public void readTxt() {
        String url = "D:\\doc\\一医院异常日志19号.log";
        try {
            File file = new File(url);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//            FileReader fr = new FileReader(url);
//            BufferedReader br = new BufferedReader(fr);
            String temp = "";// 用于临时保存每次读取的内容
            while (temp != null) {
                temp = br.readLine();
                if (temp != null && temp.contains("[2]：新增处方-入参报文:")) {
                    int index = temp.indexOf("文:{");
                    String json = temp.substring(index + 2);

                    JSONObject parseObject = JSON.parseObject(json);
                    String body = parseObject.getString("body");



                    System.out.println(json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
