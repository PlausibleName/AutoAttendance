package controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import dao.AnouncementDAO;
import dao.MemberDAO;
import dto.AnouncementDTO;
import utils.BoardPage;

@WebServlet("/Anouncement_List.do")
public class Anouncement_ListCon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnouncementDAO dao = new AnouncementDAO(); // DAO 객체 생성

		Map<String, Object> map = new HashMap<String, Object>(); // 뷰 파일에 전달할 매개변수 저장용 맵 생성

		String searchField = request.getParameter("searchField");
		String searchWord = request.getParameter("searchWord");
		
		if(searchWord != null) { // 전달 받은 매개변수 중 검색 단어가 있다면 map에 해당값 저장
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		int totalCount = dao.selectCount(map); // 게시물의 개수 저장
		
		ServletContext application = getServletContext();
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE")); // 페이지당 표시할 게시물 수
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK")); // 블록당 페이지 수
		// 페이지 설정 상수값 가져와서 페이지당 게시물 수와 블록당 페이지 수를 구함
		// 이후 현재 페이지를 확인한 후 목록에 출력할 게시물 범위 계산해서 컬렉션 map에 추가함
		
		// 현재 페이지 확인
		int pageNum = 1; // 초기값은 1로 설정
		String pageTemp = request.getParameter("pageNum");
		
		if (pageTemp != null && !pageTemp.equals("")) {
			pageNum = Integer.parseInt(pageTemp);
		}
		
		// 목록에 출력할 게시물 범위 확인
		int end = totalCount - (pageSize * (pageNum - 1)); // 첫 게시물 번호
		int start = end - (pageSize - 1); // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		
		// 게시물 목록 받기
		List<AnouncementDTO> am_list = dao.selectListPage(map);
		dao.close();
		
		// 뷰에 전달할 매개변수 추가
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "./Anouncement_List.do");
		
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		request.setAttribute("am_list", am_list);
		request.setAttribute("map", map);
		request.setAttribute("searchWord", map.get("searchWord"));
		
		// 세션생성
		HttpSession session = request.getSession();
		// LoginCon에서 설정한 admin_Key값 가져오기
		String key = (String)session.getAttribute("admin_key");
		System.out.println(key);
		
		// 0이면 관리자 -> 관리페이지로
		// 1이면 학생 -> 메인 페이지로
		if(key.equals("0")) {
			request.getRequestDispatcher("/Anouncement_List.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/Main_Anouncement.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
