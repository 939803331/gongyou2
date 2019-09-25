package com.ph.consumer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Controller {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("D:\\appStore\\picture\\man.jpg");

		System.out.println(Base64.getEncoder().encodeToString(Files.readAllBytes(path)));



		//24.1350c351fbfb5c2c203ae5061575baba.2592000.1567241154.282335-16934393
		//24.7a1c7a4555dc9e69be9138f3666b2a71.2592000.1567240855.282335-16934393
	}
}
