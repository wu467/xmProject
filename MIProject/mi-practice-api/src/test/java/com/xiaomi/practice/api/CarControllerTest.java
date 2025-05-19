package com.xiaomi.practice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.practice.api.base.request.*;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 测试保存车辆信息
     */
    @Test
    public void testSaveCarMessage() throws Exception {
        // 1. 构造请求参数
        SaveCarMsgRequest request = new SaveCarMsgRequest();
        request.setVid(62134624896752660L);
        request.setBatteryType("铁锂电池");
        request.setFrameNumber("666");
        request.setTotalMileage("10000");
        request.setBatteryHealthPercent(86);

        // 2. 模拟 POST 请求
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/saveCarMessage")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andReturn();

        // 3. 获取响应状态码和内容
        int status = result.getResponse().getStatus();
        String responseBody = result.getResponse().getContentAsString();

        // 4. 解析 JSON 响应
        JSONObject jsonResponse = new JSONObject(responseBody);
        int code = jsonResponse.getInt("code");
        boolean success = jsonResponse.getBoolean("success");
        String msg = jsonResponse.getString("message");
        Object data = jsonResponse.get("data");

        log.info("code: {}, msg: {}, success: {} data: {}", code, msg, success, data);
    }


    /**
     * 测试上传电池信息
     */
    @Test
    public void testUploadBatteryMessage() throws Exception {
        // 1. 构造请求参数
        UploadBatteryMsgRequest request = new UploadBatteryMsgRequest();
        request.setCarId(10L);
        request.setBatterySignal("{\"Mx\":12.0,\"Mi\":0.6}");
        request.setWarnId(1);

        // 2. 模拟 POST 请求并获取响应
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/battery/uploadBatteryMessage")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))).andReturn();

        // 3. 解析并打印响应结果
        String responseBody = result.getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(responseBody);
        log.info("响应 code: {}", jsonResponse.optInt("code"));
        log.info("响应 message: {}", jsonResponse.optString("message"));
        log.info("响应 success: {}", jsonResponse.optBoolean("success"));
        log.info("响应 data: {}", jsonResponse.opt("data"));

        System.out.println("================= 下面是确实 warnId参数的请求  ==========================");

        // 1. 构造请求参数
        UploadBatteryMsgRequest request2 = new UploadBatteryMsgRequest();
        request2.setCarId(10L);
        request2.setBatterySignal("{\"Mx\":11.0,\"Mi\":9.6,\"Ix\":12.0,\"Ii\":11.7}");

        // 2. 模拟 POST 请求并获取响应
        MvcResult result2 = mockMvc.perform(
                MockMvcRequestBuilders.post("/battery/uploadBatteryMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2))).andReturn();

        // 3. 解析并打印响应结果
        String responseBody2 = result2.getResponse().getContentAsString();
        JSONObject res = new JSONObject(responseBody2);
        log.info("响应 code: {}", res.optInt("code"));
        log.info("响应 message: {}", res.optString("message"));
        log.info("响应 success: {}", res.optBoolean("success"));
        log.info("响应 data: {}", res.opt("data"));
    }


    /**
     * 查询电池信息, 根据车架 carId 分页查询
     */
    @Test
    public void testQueryBatteryMessageByCarId_success() throws Exception {
        // 构造请求参数
        QueryBatteryMsgRequest request = new QueryBatteryMsgRequest();
        request.setCarId(666L);
        request.setPageNum(1);
        request.setPageSize(10);

        // 执行请求
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/queryBatteryMessageByCarId")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andDo(print()) // 打印请求和响应详情
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // 验证响应内容
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("响应内容: " + responseBody);

    }


    /**
     * 测试删除电池信息
     */
    @Test
    public void testDeleteBatteryMessage() throws Exception {
        // 1.构造请求参数
        DeleteBatteryMsgRequest request = new DeleteBatteryMsgRequest();
        request.setCarId(666L);
        // 待删除的记录id
        request.setId(16L);

        // 2. 模拟 POST 请求
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/deleteBatteryMsgById")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andReturn();

        // 3. 获取响应状态码和内容
        int status = result.getResponse().getStatus();
        String responseBody = result.getResponse().getContentAsString();

        // 4. 解析 JSON 响应
        JSONObject jsonResponse = new JSONObject(responseBody);
        int code = jsonResponse.getInt("code");
        boolean success = jsonResponse.getBoolean("success");
        String msg = jsonResponse.getString("message");
        Object data = jsonResponse.get("data");

        log.info("code: {}, msg: {}, success: {} data: {}", code, msg, success, data);

    }


    /**
     * 根据 carId 查询预警信息
     */
    @Test
    public void testQueryWarnRecord() throws Exception {
        // 1.构造请求参数
        QueryWarnRecordRequest request = new QueryWarnRecordRequest();
        request.setCarId(10L);

        // 2. 模拟 POST 请求
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/getWarnRecords")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andReturn();

        // 3. 获取响应状态码和内容
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        System.out.println("响应：" + responseBody);
    }

}
