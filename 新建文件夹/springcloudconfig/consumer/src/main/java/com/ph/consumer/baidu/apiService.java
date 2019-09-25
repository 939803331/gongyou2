package com.ph.consumer.baidu;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

public class apiService {
	//设置APPID/AK/SK
	public static final String APP_ID = "16934393";
	public static final String API_KEY = "ygMMwkN7nRTSNWCK9VzmydBr";
	public static final String SECRET_KEY = "nMdOGSRFyneCKLSdrgUl9nL29Z3d6lFe";

	public static void method() throws IOException {
		AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

		//设置网络连接
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		Path path = Paths.get("D:\\appStore\\picture\\man.jpg");

		String image = Base64.getEncoder().encodeToString(Files.readAllBytes(path));
		String imageType = "BASE64";
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,landmark72,landmark150,race,quality,eye_status,emotion,face_type");
		options.put("max_face_num", "2");
		options.put("face_type", "LIVE");
		options.put("liveness_control", "LOW");

		JSONObject res = client.detect(image, imageType, options);
		System.out.println(res.toString());
	}

	public static void main(String[] args) throws IOException {
		method();



	}
}















