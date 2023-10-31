package kr.or.ddit.smartware.calendar.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import kr.or.ddit.smartware.calendar.model.Calendar;
import kr.or.ddit.smartware.calendar.model.Category;
import kr.or.ddit.smartware.calendar.service.ICalendarService;
import kr.or.ddit.smartware.calendar.service.ICategoryService;
import kr.or.ddit.smartware.employee.model.Employee;
import kr.or.ddit.smartware.employee.service.IDepartmentService;
import kr.or.ddit.smartware.employee.service.IEmployeeService;
import kr.or.ddit.smartware.employee.service.IPositionService;

@Controller
public class CalendarController {

	@Resource(name = "calendarService")
	private ICalendarService calendarService;

	@Resource(name = "categoryService")
	private ICategoryService categoryService;

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;

	@Resource(name = "departmentService")
	private IDepartmentService departmentService;

	@Resource(name = "positionService")
	private IPositionService positionService;

	@Resource(name = "jsonView")
	private View jsonView;

	private String generateRandomNumber(int length) {
		Random random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
	
	  public static String encodeToUTF8(String input) {
	        if (input == null) {
	            return null;
	        }
	        return new String(input.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
	    }

	/**
	 * Method : calendar 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : 캘린더 페이지 요청
	 */
	@RequestMapping("calendar")
	public String calendar(Model model, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
		String depart_id = employee.getDepart_id();
		String posi_id = employee.getPosi_id();

		// model.addAttribute("depart_nm",
		// departmentService.getDepartNm(depart_id));
		// model.addAttribute("posi_nm", positionService.getPosiNm(posi_id));

		return "tiles/calendar/calendar";
	}

	/**
	 * Method : excel 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : 캘린더 페이지 요청
	 */
	@RequestMapping("excel")
	public String excel(Model model, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");

		return "tiles/calendar/excel";
	}

	/**
	 * Method : getAllCalendarList 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : 현재 로그인한 사원의 모든 일정을 JSON형태로 반환한다.
	 */
	@RequestMapping("getAllCalendarList")
	public View getAllCalendarList(Model model, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
		String emp_id = employee.getEmp_id();

		List<Map<String, Object>> calendarList = new ArrayList<Map<String, Object>>();

		for (Calendar calendar : calendarService.getAllCalendarList(emp_id)) {
			calendarList.add(calendarJson(calendar));
		}

		model.addAttribute("calendarList", calendarList);

		return jsonView;
	}

	/**
	 * Method : getAllCalendarList 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : 현재 로그인한 사원의 모든 일정을 JSON형태로 반환한다.
	 */
	@RequestMapping("getAllExcelList")
	public View getAllExcelList(Model model, HttpSession session, Calendar calendar) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
		String emp_id = employee.getEmp_id();

		if (calendar.getStartDate() == null || calendar.getEndDate() == null) {
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			calendar.setStartDate(now.format(formatter));
			calendar.setEndDate(now.plusWeeks(1).format(formatter));
		}

		List<Calendar> excelList = calendarService.getAllExcelList(calendar);
		model.addAttribute("excelList", excelList);

		return jsonView;
	}

	/**
	 * Method : insertCalendar 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param calendar
	 * @param start
	 * @param end
	 * @return Method 설명 : 일정 추가
	 */
	@PostMapping("insertCalendar")
	public View insertCalendar(Model model, Calendar calendar, long start, long end) {
		// allDay 체크유무에 따라 T, F로 변경
		/*
		 * if(calendar.getAllDay() == null) calendar.setAllDay("F"); else
		 * calendar.setAllDay("T");
		 */

		// 시간 설정(long타입 숫자로 생성된 시간 -> date로)
		calendar.setSt_dt(new Date(start));
		calendar.setEnd_dt(new Date(end));

		calendarService.insertCalendar(calendar);

		return jsonView;
	}

	/**
	 * Method : insertCalendar 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param cal_id
	 * @return Method 설명 : 일정 수정
	 */
	@PostMapping("updateCalendar")
	public View updateCalendar(Model model, Calendar calendar, long start, long end) {
		// allDay 체크유무에 따라 T, F로 변경
		if (calendar.getAllDay() == null)
			calendar.setAllDay("F");
		else
			calendar.setAllDay("T");

		// 시간 설정(long타입 숫자로 생성된 시간 -> date로)
		calendar.setSt_dt(new Date(start));
		calendar.setEnd_dt(new Date(end));

		calendarService.updateCalendar(calendar);

		return jsonView;
	}

	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	@PostMapping("updateCalendarSize")
	public View updateCalendarSize(Model model, String cal_id, long start, long end) {
		Calendar calendar = calendarService.getCalendar(cal_id);
		logger.debug("start: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start));
		logger.debug("end: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
		calendar.setSt_dt(new Date(start));
		calendar.setEnd_dt(new Date(end));

		calendarService.updateCalendar(calendar);

		return jsonView;
	}

	/**
	 * Method : insertCalendar 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param cal_id
	 * @return Method 설명 : 일정 삭제
	 */
	@PostMapping("deleteCalendar")
	public View deleteCalendar(Model model, String cal_id) {
		calendarService.deleteCalendar(cal_id);

		return jsonView;
	}

	/**
	 * Method : calendarJson 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param calendar
	 * @return Method 설명 : CalendarVO를 json형태로 변환
	 */
	private Map<String, Object> calendarJson(Calendar calendar) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("bApcoKey", calendar.getbApcoKey()); // apco
		map.put("bApcoChannel", calendar.getbApcoChannel()); // apco
		map.put("bApcoStatus", calendar.getbApcoStatus()); // apco
		map.put("bApcoNum", calendar.getbApcoNum()); // apco
		map.put("bApcoRoom", calendar.getbApcoRoom()); // apco
		map.put("bApcoCheckInDate", calendar.getbApcoCheckInDate()); // apco
		map.put("bApcoCheckOutDate", calendar.getbApcoCheckOutDate()); // apco
		map.put("bApcoName", calendar.getbApcoName()); // apco
		map.put("bApcoName", calendar.getbApcoName()); // apco
		map.put("bApcoPerson", calendar.getbApcoPerson()); // apco
		map.put("bApcoBarbecue", calendar.getbApcoBarbecue()); // apco
		map.put("bApcoPayment", calendar.getbApcoPayment()); // apco
		map.put("bApcoHp", calendar.getbApcoHp()); // apco
		map.put("bApcoBooDate", calendar.getbApcoBooDate()); // apco
		map.put("bApcoBooTime", calendar.getbApcoBooTime()); // apco
		map.put("bApcoEtc", calendar.getbApcoEtc()); // apco
		map.put("gubun", calendar.getGubun()); // apco

		// 카테고리 name 으로 되게끔
		Category category = categoryService.getCategoryName(calendar.getbApcoChannel());

		map.put("backgroundColor", category.getColor()); // 일정 카테고리 배경색
		map.put("category_nm", category.getCategory_nm()); // 카테고리 이름
		map.put("category_id", category.getCategory_id()); // 카테고리 아이디

		map.put("textColor", "white"); // 기본값 셋팅
		map.put("borderColor", "white"); // 기본값 셋팅

		map.put("start", calendar.getbApcoCheckInDate()); // 일정 시작 시간
		map.put("end", calendar.getbApcoCheckOutDate()); // 일정 종료 시간

		map.put("allDay", false); // 일정 allDay 여부

		/*
		 * map.put("id", calendar.getCal_id()); // 일정 id(고유키) map.put("title",
		 * calendar.getCal_title()); // 일정 제목 map.put("description",
		 * calendar.getCal_cont()); // 일정 설명
		 * 
		 * map.put("start", calendar.getSt_dt()); // 일정 시작 시간 map.put("end",
		 * calendar.getEnd_dt()); // 일정 종료 시간
		 * 
		 * String emp_nm =
		 * employeeService.getEmployee(calendar.getEmp_id()).getEmp_nm();
		 * map.put("emp_nm", emp_nm); // 일정 등록자 이름
		 * 
		 * // 카테고리 name 으로 되게끔 Category category =
		 * categoryService.getCategory(calendar.getCategory_id());
		 * map.put("backgroundColor", category.getColor()); // 일정 카테고리 배경색
		 * map.put("category_nm", category.getCategory_nm()); // 카테고리 이름
		 * map.put("category_id", category.getCategory_id()); // 카테고리 아이디
		 * 
		 * map.put("textColor", "white"); // 기본값 셋팅 map.put("borderColor",
		 * "white"); // 기본값 셋팅
		 * 
		 * map.put("allDay", calendar.getAllDay().equals("T") ? true : false);
		 * // 일정 allDay 여부
		 */
		return map;
	}

	@PostMapping("getTodayCalendar")
	public View getTodayCalendar(HttpSession session, Model model) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");

		model.addAttribute("calList", calendarService.getTodayCalendar(employee.getEmp_id()));

		return jsonView;
	}

	/**
	 * Method : getAllCalendarList 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : 현재 로그인한 사원의 모든 일정을 JSON형태로 반환한다.
	 */
	@RequestMapping("getAllCrawlingCalendarList")
	public View getAllCrawlingCalendarList(Model model, HttpSession session, Calendar pCalendar) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
		String emp_id = employee.getEmp_id();

		if (pCalendar.getbApcoCheckInDate() == null || pCalendar.getbApcoCheckInDate() == null) {

			LocalDate now = LocalDate.now();
			LocalDate firstDayOfMonth = now.withDayOfMonth(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			pCalendar.setbApcoCheckInDate(firstDayOfMonth.format(formatter));
		}
		List<Map<String, Object>> calendarList = new ArrayList<Map<String, Object>>();

		for (Calendar calendar : calendarService.getAllCrawlingCalendarList(pCalendar)) {
			calendarList.add(calendarJson(calendar));
		}

		model.addAttribute("calendarList", calendarList);

		return jsonView;
	}

	/**
	 * Method :getRoomList 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : room JSON형태로 반환한다.
	 */
	@RequestMapping("getRoomList")
	public View getRoomList(Model model, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
		String emp_id = employee.getEmp_id();

		// model.addAttribute(categoryService.getEmpCategoryList(emp_id));
		model.addAttribute("roomList", calendarService.getRoomList());

		return jsonView;
	}

	/**
	 * Method : insertCalendar 변경이력 :
	 * 
	 * @param model
	 * @param calendar
	 * @param start
	 * @param end
	 * @return Method 설명 : 일정 추가
	 */
	@PostMapping("insertNewCalendar")
	public View insertNewCalendar(Model model, Calendar calendar, String start, String end) throws ParseException {
		// Define the date format and timezone
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Parse the date strings to Date objects
		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);

		// Set the start and end dates
		calendar.setSt_dt(startDate);
		calendar.setEnd_dt(endDate);

		// bApcoNum 을 6자리 난수로 만들어서 중첩이 없게 해서 db에 insert
		String bApcoNum;
		do {
			bApcoNum = generateRandomNumber(6);
		} while (calendarService.isBapcoNumExist(bApcoNum)); // DB에 이미 존재하는지 확인
		calendar.setbApcoNum(bApcoNum);

		calendarService.insertNewCalendar(calendar);

		try {
			String pathToExecutable = "C:\\PycharmWS\\AccommodationReservation\\dist\\cashBlock.exe";

			System.out.println("calendar.getRoom_id() = " + calendar.getRoom_id());
			System.out.println("start.substring(0, 10) = " + start.substring(0, 10));
			System.out.println("end.substring(0, 10) = " + end.substring(0, 10));
			System.out.println("calendar.getCategory_id() = " + calendar.getCategory_id());
			System.out.println("calendar.getbApcoName() = " + calendar.getbApcoName());
			System.out.println("calendar.getbApcoPerson() = " + calendar.getbApcoPerson());
			System.out.println("calendar.getbApcoPayment() = " + calendar.getbApcoPayment());

			String[] cmdArray = {
			    pathToExecutable, 
			    encodeToUTF8(calendar.getRoom_id()),
			    start.substring(0, 10),
			    end.substring(0, 10),
			    encodeToUTF8(calendar.getCategory_id()),
			    encodeToUTF8(calendar.getbApcoName()),
			    calendar.getbApcoPerson(),
			    calendar.getbApcoPayment()
			};

			ProcessBuilder processBuilder = new ProcessBuilder(cmdArray);
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {
			    // 프로그램의 표준 출력 읽기
			    System.out.println("Standard output:");
			    String s;
			    while ((s = stdInput.readLine()) != null) {
			        System.out.println(s);
			    }
			}

			int exitCode = process.waitFor();
			if (exitCode == 0) {
			    System.out.println("Program executed successfully");
			} else {
			    System.out.println("Program exited with error code: " + exitCode);
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to execute program");
		}

		return jsonView;
	}

	/**
	 * Method : insertCalendar 작성자 : JO MIN SOO 변경이력 :
	 * 
	 * @param model
	 * @param cal_id
	 * @return Method 설명 : 일정 수정
	 */
	@PostMapping("updateNewCalendar")
	public View updateNewCalendar(Model model, Calendar calendar, String start, String end) throws ParseException {
		// Define the date format and timezone
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Parse the date strings to Date objects
		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);

		// Set the start and end dates
		calendar.setSt_dt(startDate);
		calendar.setEnd_dt(endDate);

		// Perform the update
		calendarService.updateNewCalendar(calendar);

		return jsonView;
	}

	/**
	 * Method : deleteNewCalendar 변경이력 :
	 * 
	 * @param model
	 * @param cal_id
	 * @return Method 설명 : 일정 삭제
	 */
	@PostMapping("deleteNewCalendar")
	public View deleteNewCalendar(Model model, Calendar calendar) {
		calendarService.deleteNewCalendar(calendar);

		return jsonView;
	}

	/**
	 * Method :getRoomList 변경이력 :
	 * 
	 * @param model
	 * @param session
	 * @return Method 설명 : room JSON형태로 반환한다.
	 */
	@RequestMapping("checkAvailability")
	public View checkAvailability(Model model, HttpSession session, @RequestParam Map<String, String> params) {

		String[] startDateParts = params.get("start").split("T");
		String[] endDateParts = params.get("end").split("T");

		// 날짜 부분만 추출합니다.
		String startDate = startDateParts[0];
		String endDate = endDateParts[0];

		// 로깅으로 확인
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(params.get("room_id"));

		// 추출된 날짜 부분을 다시 파라미터 맵에 설정합니다.
		params.put("startDate", startDate);
		params.put("endDate", endDate);

		// 예약 가능 여부 확인
		int availability = calendarService.checkAvailability(params);

		model.addAttribute("isAvailable", availability == 0);

		return jsonView;
	}

}
