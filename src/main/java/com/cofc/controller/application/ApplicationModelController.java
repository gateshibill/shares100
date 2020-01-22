package com.cofc.controller.application;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/appModel")
public class ApplicationModelController {
	public static Logger log = Logger.getLogger("ApplicationModelController");
}
