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
	
	// http://localhost:8080/Board/View?dix=3
	@RequestMapping("/View")
	public ModelAndView view (BoardDTO boardDTO) {
			
		// 메뉴 목록 조회
		List<MenuDTO> menuList = menuMapper.getMenuList();
		
		// 해당 idx 게시글의 조회수를 1 증가
		boardMapper.incHit(boardDTO);
		
		// idx로 조회한 게시글
		BoardDTO	board = boardMapper.getBoard(boardDTO);
		System.out.println("board : " + board);
		// board : boardDTO [idx=3, menu_id=MENU01, title=JAVA2, content=null, writer=java, regdate=2026-05-06, hit=0]
		
		
		ModelAndView	mv	=	new	ModelAndView();
		mv.setViewName("board/view");
		mv.addObject("menuList", menuList);
		mv.addObject("board", board); // 화면에 뿌려줄 한개의 게시글 정보를 담은거
		return mv;
	}
	
}
