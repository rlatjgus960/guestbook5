package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestController {
	
	//필드
	@Autowired
	private GuestbookDao guestbookDao;
	
	//리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("[GuestController.list]");
		
		//Dao의 메소드로 데이터 가져오기
		List<GuestbookVo> guestbookList = guestbookDao.getGuestbookList();
		
		//model 담기
		model.addAttribute("guestbookList", guestbookList);
		
		return "addList";
	}
	
	
	//등록
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[GuestController.add]");
		
		
		guestbookDao.guestbookInsert(guestbookVo);
		
		return "redirect:/list";
	}
	
	//등록2
	@RequestMapping(value = "/add2", method = { RequestMethod.GET, RequestMethod.POST })
	public String add2(@RequestParam("name") String name,
					   @RequestParam("password") String password,
					   @RequestParam("content") String content 
					   ) {
		System.out.println("[GuestController.add2]");
		
		guestbookDao.guestbookInsert2(name, password, content);
		
		return "redirect:/list";
	}
	
	
	//삭제폼
	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm() {
		System.out.println("[GuestController.deleteForm]");
		
		return "deleteForm";
	}
	
	
	//삭제
	@RequestMapping(value="/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[GuestController.delete]");
		
		guestbookDao.guestbookDelete(guestbookVo);
		
		return "redirect:/list";
	}
}

