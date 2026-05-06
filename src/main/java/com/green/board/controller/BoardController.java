package com.green.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.board.dto.BoardDTO;
import com.green.board.mapper.BoardMapper;
import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/Board")
public class BoardController {
	
	@Autowired
	private	MenuMapper menuMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	// /Board/List
	@RequestMapping("/List")
	// /Board/List?menu_id=MENU01
	public ModelAndView list (MenuDTO menuDTO) {
		// 메뉴 전체 목록 조회 - menus.jsp
		List<MenuDTO> menuList = menuMapper.getMenuList();
		log.info("menuList" + menuList);
		// 게시물 목록 조회 - list.jsp (menu_id=MENU01)
		List<BoardDTO> boardList = boardMapper.getBoardList(menuDTO);	
		log.info("boardList :" + boardList);
		
		ModelAndView	mv	=	new	ModelAndView();
		
		mv.setViewName("board/list");
		mv.addObject("menuList", menuList);
		mv.addObject("boardList", boardList);
		return mv;
	}
}
