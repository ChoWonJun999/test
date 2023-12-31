package kr.or.ddit.smartware.login.web;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.smartware.approval.service.IApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.mail.imap.IMAPFolder;

import kr.or.ddit.smartware.board.service.IBoardService;
import kr.or.ddit.smartware.email.model.GmailConnector;
import kr.or.ddit.smartware.employee.model.Employee;
import kr.or.ddit.smartware.employee.service.IEmployeeService;
import kr.or.ddit.smartware.messenger.service.IMessengerService;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name = "messengerService")
	private IMessengerService messengerService;
	
	@Resource(name = "boardService")
	private IBoardService boardService;

	@Resource(name = "approvalService")
	private IApprovalService approvalService;
	
	/**
	 * 
	* Method : view
	* 작성자 : PC-04
	* 변경이력 :
	* @return
	* Method 설명 : 로그인 화면 요청 처리(forward)
	 */
	// get방식으로 왔을 때만 view라는 메서드로 처리
	@RequestMapping(path = "login", method = RequestMethod.GET)
	public String view() {
		// cookie 처리는 생략
		return "login/login";
	}
	
	
	/**
	 * 
	* Method : loginProcess
	* 작성자 : PC-04
	* 변경이력 :
	* @param emp_id
	* @param pass
	* @param rememberMe
	* @param response
	* @param session
	* @return
	* Method 설명 : 로그인 요청 처리 (post일 때만 이 메서드를 실행하도록 설정)
	 * @throws MessagingException 
	 */
	@RequestMapping(path = "login", method = RequestMethod.POST)
	public String loginProcess(String emp_id, String pass, String rememberMe,
								HttpServletResponse response, HttpSession session, HttpServletRequest request
								) throws MessagingException {
		
		manageEmp_IdCookie(response, emp_id, rememberMe);
		
		Employee employee = employeeService.getEmployee(emp_id);
		
		
		if(employee == null) {
			return "login/login";	// view();
			
		}else if(employee.checkLoginValidate(emp_id, pass)) {
			
//			request.getServletContext().setAttribute("A_BOARDLIST", boardService.getBoardList());
			
			employee.setC_use("false");
			
//			IMAPFolder folder = null;
//	        Store store = null;
//	        String subject = null;
//	        Flag flag = null;
//	        
//	        //여기서
//	        //5 , 9, 13, 14, 15
//	        //메일을 못가져온다는 권한오류가 뜨면 Gmail -> 톱니바퀴 -> 설정 -> 전달 및 POP/IMAP -> IMAP 허용만 해주시면됨
//	        //email_pass 넣어줘야함
//	        //email 비밀번호 를 암호화해야함..
//	        if(!(employee.getEmp_id().equals("e0005") || employee.getEmp_id().equals("e0009") || employee.getEmp_id().equals("e0013")
//	        		|| employee.getEmp_id().equals("e0014") || employee.getEmp_id().equals("e0015"))) {
//	        		employee.setEmail("testhoon1217@gmail.com");
//	        		employee.setEmail_pass("ewqdsa556");
//	        }
//	        	Properties props = System.getProperties();
//	        	props.setProperty("mail.store.protocol", "imaps");
//	        	Session mailSession = Session.getDefaultInstance(props, null);
//	        	
//	        	store = mailSession.getStore("imaps");
//	        	store.connect("imap.googlemail.com", employee.getEmail(), employee.getEmail_pass());
//	        	folder = (IMAPFolder) store.getFolder("INBOX");
//	        	if(!folder.isOpen())
//	       		 folder.open(Folder.READ_ONLY);
//	        	
//	        	int cnt = folder.getMessageCount();
//	        	session.setAttribute("cnt", cnt);
//	        	
//	        	session.setAttribute("store", store);
	        	//여기까지
	        	session.setAttribute("S_EMPLOYEE", employee);
	        	return "redirect:/main";
		}
		else {
			return "login/login";
		}
	}
		
	private void manageEmp_IdCookie(HttpServletResponse response, String emp_id, String rememberMe) {
		//remeberMe 파라미터가 존재할 경우 emp_id를 emp_id로 생성
		Cookie cookie = new Cookie("emp_id", emp_id);
		
		if(rememberMe != null) {
			cookie.setMaxAge(60*60*24*30);	//30일
		}
		else {	
			cookie.setMaxAge(0);	//삭제
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 
	* Method : logout
	* 작성자 : PC-04
	* 변경이력 :
	* @param response
	* @param session
	* Method 설명 : 로그아웃
	 * @throws MessagingException 
	 */
	@RequestMapping(path = "logout", method = RequestMethod.GET)
	private String logout(HttpServletResponse response, HttpSession session) throws MessagingException {
		//여기서
//		Store store = (Store) session.getAttribute("store");
//		IMAPFolder imapFolder = null;
//		IMAPFolder folder = null;
//		
//		Folder[] folders1 = store.getDefaultFolder().list("*");
//          for (Folder folder1 : folders1) {
//            imapFolder = (IMAPFolder) folder1;
//            logger.debug("folder1 - {}", folder1);
//            String folderName = (String) imapFolder.getFullName();
//            
//            folder = (IMAPFolder) store.getFolder(folderName);
//            logger.debug("folder - {}", folder);
//          }
//		
//		{
//			if (folder != null && folder.isOpen()) { folder.close(true); }
//			if (store != null) { store.close(); }
//		}
//		
		session.invalidate();	// 세션에 저장된 모든 속성을 제거
//		
//		GmailConnector.getStore();
		//요기
		
		// 로그인 화면으로 이동
		return "redirect:/login";
	}
	
	/**
	 * 
	* Method : Index
	* 작성자 : PC-04
	* 변경이력 :
	* @return
	* Method 설명 : 메인창
	 */
	@RequestMapping(path = "main")
	private String mainView(Model model, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");


		return "tiles/main/main";
	}
	
}
