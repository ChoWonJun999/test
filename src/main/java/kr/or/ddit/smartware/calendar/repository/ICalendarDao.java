package kr.or.ddit.smartware.calendar.repository;

import java.util.List;
import java.util.Map;

import kr.or.ddit.smartware.calendar.model.Calendar;
import kr.or.ddit.smartware.calendar.model.Category;

/**
 * @author kdh
 *
 */
public interface ICalendarDao {

	/**
	 * Method : getCalendar
	 * 작성자 : JO MIN SOO
	 * 변경이력 :
	 * @param cal_id
	 * @return
	 * Method 설명 : cal_id에 해당하는 일정 불러오기
	 */
	Calendar getCalendar(String cal_id);
	
	/**
	* Method : getAllCalendarList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : 사원의 모든 일정을 가져온다.
	*/
	List<Calendar> getAllCalendarList(String emp_id);

	/**
	* Method : getTodayCalendar
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 사원 일간 일정 내용 조회 
	*/
	List<Calendar> getTodayCalendar(String emp_id);
	
	/**
	* Method : getWeekCalendar
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 사원 주간 일정 내용 조회
	*/
	List<Calendar> getWeekCalendar(String emp_id);
	
	/**
	* Method : insertCalendar
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param calendar
	* @return
	* Method 설명 : 일정 추가
	*/
	int insertCalendar(Calendar calendar);
	
	/**
	* Method : updateCalendar
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param calendar
	* @return
	* Method 설명 : 일정 수정 
	*/
	int updateCalendar(Calendar calendar);
	
	/**
	* Method : deleteCalendar
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param cal_id
	* @return
	* Method 설명 : 일정 삭제
	*/
	int deleteCalendar(String cal_id);
	
	/**
	* Method : deleteCateCalendar
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param category_id
	* @return
	* Method 설명 : 카테고리의 모든 일정 삭제
	*/
	int deleteCateCalendar(String category_id);
	
	/**
	* Method : getAllCalendarList
	* 변경이력 :
	* @return
	* Method 설명 :  모든 예약 일정을 가져온다.
	*/
	List<Calendar> getAllCrawlingCalendarList(Calendar calendar);
	
	
	
	/**
	* Method : getRoomList
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : room 정보를 가져온다
	*/
	List<Calendar> getRoomList();
	
	
	/**
	* Method : insertNewCalendar
	* 변경이력 :
	* @param calendar
	* @return
	* Method 설명 : 일정 추가
	*/
	int insertNewCalendar(Calendar calendar);
	
	/**
	* Method : updateNewCalendar
	* 변경이력 :
	* @param calendar
	* @return
	* Method 설명 : 일정 수정 
	*/
	int updateNewCalendar(Calendar calendar);
	
	/**
	* Method : deleteCalendar
	* 변경이력 :
	* @param cal_id
	* @return
	* Method 설명 : 일정 삭제
	*/
	int deleteNewCalendar(Calendar calendar);
	
	
	/**
	* Method : getAllExcelList
	* 변경이력 :
	* @return
	* Method 설명 :  모든 excel 예약 일정을 가져온다.
	*/
	List<Calendar> getAllExcelList(Calendar calendar);
	
	
	
	/**
	 *  cash 에 해당 예약번호가 있는지 check
	 */
	int isBapcoNumExist(String bApcoNum);
	
	int checkAvailability(Map<String, String> map);
	
	
	/**
	 * log List 
	 */
	List<Map<String, String>> systemLogList(Map<String, Object> map);
	List<Map<String, String>> systemLogAllList(Map<String, Object> map);
	
	
	

}
