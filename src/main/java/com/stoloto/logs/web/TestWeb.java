package com.stoloto.logs.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestWeb {

	@GetMapping("/do")
	public String doIt(@RequestParam(defaultValue = "0") int count) {
		log.info("do do");
		if (count == 0) {
			//            log.info("do={}", News.of(1, "спаси свою любовь", "или нет"));
			//            log.info("do", new Exception("fuk"));

			return "";
		}

		throw new RuntimeException("fuck");
	}
}
