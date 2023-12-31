package kr.or.ddit.smartware.pms.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.smartware.pms.model.ProTask;
import kr.or.ddit.smartware.pms.model.Task;

public interface ITaskService {
	
	/**
	* Method : getWeekTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 주간 업무 리스트 반환
	*/
	List<Task> getWeekTask(Map<String, String> map);
	
	/**
	* Method : getDelayTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 지연중인 업무 리스트 반환
	*/
	List<Task> getDelayTask(Map<String, String> map);

	/**
	* Method : getAllTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param pro_id
	* @return
	* Method 설명 : 프로젝트의 전체 일감 반환(gantt)
	*/
	List<Map<String, Object>> getAllTask(String pro_id);
	
	/**
	* Method : getTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param task_id
	* @return
	* Method 설명 : 일감 반환
	*/
	List<Map<String, Object>> getTask(String task_id);
	
	/**
	* Method : getEmpGantt
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param pro_id
	* @param emp_id
	* @return
	* Method 설명 : 프로젝트의 사원 일감 반환(gantt)
	*/
	List<Map<String, Object>> getEmpGantt(String pro_id, String emp_id);

	/**
	* Method : getAllDelayTask
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 모든 프로젝트의 지연업무 조회 
	*/
	List<Map<String, Object>> getAllDelayTask(String emp_id);

	/**
	* Method : getAllTodayTask
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 모든 프로젝트의 일간업무 조회 
	*/
	List<Map<String, Object>> getAllTodayTask(String emp_id);
	
	/**
	* Method : getAllWeekDlayTask
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 모든 프로젝트의 주간 지연업무 조회
	*/
	List<Map<String, Object>> getAllWeekDlayTask(String emp_id);
	
	/**
	* Method : getAllWeekTask
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param emp_id
	* @return
	* Method 설명 : emp_id에 해당하는 모든 프로젝트의 주간업무 조회
	*/
	List<Map<String, Object>> getAllWeekTask(String emp_id);
	
	/**
	* Method : insertTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param task
	* @param emp_id
	* @return
	* Method 설명 : 일정 추가
	*/
	String insertTask(Task task, String emp_id);
	
	/**
	* Method : updateTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param task
	* @param emp_id
	* @return
	* Method 설명 : 일정 수정, 일정 담당자 수정 
	*/
	int updateTask(Task task, String emp_id);
	
	/**
	* Method : updateParentPer
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param pa_task_id
	* @return
	* Method 설명 : 자식 task가 변경되었을 때 부모 task의 per 갱신 
	*/
	int updateParentPer(String pa_task_id);
	
	/**
	* Method : deleteTask
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param task_id
	* @return
	* Method 설명 : 일감 삭제, 일감담당자 삭제, 일감히스토리 삭제 
	*/
	int deleteTask(String task_id);

}
