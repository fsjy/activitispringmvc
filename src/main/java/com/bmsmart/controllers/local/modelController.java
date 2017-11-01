package com.bmsmart.controllers.local;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class modelController {

	/**
	 *
	 * 进入modelList页面
	 * ignore Post Get...
	 *
	 * @return
	 */
	@RequestMapping(value = "local/modeList")
	public String getSearchResultViaAjax() {

		return "modelList";

	}






}
