package com.green.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.users.dto.UserDTO;
import com.green.users.mapper.UserMapper;

@Controller
@RequestMapping("/Users")
public class UserContoller {
	
	@Autowired
	private	UserMapper userMapper;
	
	// /Users/List
	@RequestMapping("/List")
	public ModelAndView list () {
		
		List<UserDTO> userList = userMapper.getUserList();
		
		ModelAndView mv	= new ModelAndView();
		mv.setViewName("users/list");
		mv.addObject("userList", userList);
		return mv;
	} 
	
	// /Users/WriteForm
	@RequestMapping("/WriteForm")
	public ModelAndView writeForm () {
		ModelAndView mv	= new ModelAndView();
		mv.setViewName("users/write");
		mv.addObject("msg", "신상민");
		
		return mv;
	}
	
	@RequestMapping("/Write")
	public ModelAndView write (UserDTO userDTO) {
		//System.out.println("userDTO : " + userDTO);
		//넘어온 data를 db에 저장한다
		userMapper.insertUser(userDTO);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		
		return mv;
	}
	
	// http://localhost:8080/Users/Delete?userid=spring03
	@RequestMapping("/Delete")
	public ModelAndView delete ( UserDTO userDTO ) {
		
		// 넘겨받은 자료를 출력한다
		//System.out.println("넘겨받은 userDTO : " + userDTO);
		
		// db의 자료 삭제
		userMapper.deleteUser(userDTO);
		
		// 목록으로 이동
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return mv;
	}
	
	// http://localhost:8080/Users/UpdateForm?userid=spring04
	@RequestMapping("/UpdateForm")
	public ModelAndView updateForm (UserDTO userDTO) {
		// 넘어온 userDTO 정보
		System.out.println("넘어온 userDTO : " + userDTO);
		
		// 수정을 위해 db에서 조회한 정보
		UserDTO user = userMapper.getUser(userDTO); 
		// 조호된 userDTO 정보
		System.out.println("조회된 user : " + user);
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/update"); // update.jsp로 가는데
		mv.addObject("user", user); // 갈때 user 들고 가라
		return mv;
	}
	
	// http://localhost:8080/Users/Update?userid=spring04&password=1234&username=spring04&email=spring04%40green.com
	@RequestMapping("/Update")
	public ModelAndView update (UserDTO userDTO) {
		
		userMapper.updateUser(userDTO);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		
		return mv;
	}
	
	// 아이디 중복 확인 - jsp를 return하는게 아닌 결과 문자열을 리턴
	// <b class="green">사용 가능한 아이디입니다.</b>
	// <b class="red">사용 불가능한 아이디입니다.</b>
	// /Users/IdDupCheck2?userid=sky
	@GetMapping("/IdDupCheck2")
	@ResponseBody		// 리턴되는 값은 jsp가 아니다
	public UserDTO idDupCheck2 (UserDTO userDTO) {
		// String		userid		=	userDTO.getUserid(); // 넘어온 userid - 필요없음
		UserDTO		user		=	userMapper.getIdDupCheck(userDTO); // 넘어온 userid를 들고가서 조회하는거임
		if (user == null)  // 넘어온 userid가 없으면 null값이 return되는데 jsp로 넘어가는게 아무것도 없어서 새객체를 하나 만들어서 jsp로 넘김
			user = new UserDTO();
		
		return user;
	}
	
	// /Users/DupCheckWindow
	@GetMapping("/DupCheckWindow")
	public ModelAndView dupCheckWindow () {
		
		
		ModelAndView	mv	=	new	ModelAndView();
		mv.setViewName("users/idcheck");
		mv.addObject("userid", "aaa");
		return mv;
	}
	
	// 중복확인 
	// /Users/DupCheck?userid=aaa
	@RequestMapping("/DupCheck")
	public ModelAndView dupCheck (UserDTO userDTO) {
		UserDTO			user	=	userMapper.getUser(userDTO);
		String			msg		=	"<b class='red'>사용 불가능</b>";
		if(user == null)
			msg = "<b class='green'>사용 가능</b>";
		
		ModelAndView	mv	=	new	ModelAndView();
		mv.setViewName("users/idcheck");
		mv.addObject("msg", msg);
		return mv;
	}
	
	/* list 내가한거
	@RequestMapping("/List")
	public String list ( Model model ) {
		List<UserDTO> userList = userMapper.getUserList();
		System.out.println(userList);
		model.addAttribute("userList", userList);
		return "users/list";
	} // list () end
	*/
	
	/* writeform 내가 한거
	// /Users/WriteForm
	@RequestMapping("/WriteForm")
	public String writeForm () {
		
		return "users/write";
	} // writeForm() end
	*/
	
	/* write 내가 한거
	// http://localhost:8080/Users/Write?
	// userid=spring01&password=1234&username=spring01&
	// email=spring01%40green.com
	// /Users/Write
	@RequestMapping("/Write")
	public String	write (UserDTO userDTO) {
		userMapper.insertUser(userDTO);
		return "redirect:/Users/List";
	}
	*/
	
	/* delete 내가 한거
	// http://localhost:8080/Users/Delete?userid=spring03
	// Users/Delete
	@RequestMapping("/Delete")
	public String	delete (UserDTO userDTO) {
		userMapper.deleteUser(userDTO);
		return "redirect:/Users/List";
	}
	*/
	
	/* update 내가 한거
	// /Users/UpdateForm?userid=${user.userid}
	// /Users/UpdateForm
	@RequestMapping("/UpdateForm")
	public String	updateForm (UserDTO userDTO, Model model) {
		UserDTO user = userMapper.getUser(userDTO);
		model.addAttribute("user", user);
		return "users/update";
	}
	
	//http://localhost:8080/Users/Update?
	// userid=&password=12345&username=spring02&
	// email=spring02%40naver.com
	@RequestMapping("/Update")
	public String	update (UserDTO userDTO) {
		userMapper.updateUser(userDTO);
		return "redirect:/Users/List";
	}
	*/
	
} // class UserContoller end