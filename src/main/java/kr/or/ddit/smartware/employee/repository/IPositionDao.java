package kr.or.ddit.smartware.employee.repository;

import java.util.List;
import java.util.Map;

import kr.or.ddit.smartware.employee.model.Position;

public interface IPositionDao {

	/**
	* Method : getPosiNm
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param posi_id
	* @return
	* Method 설명 : 직책 아이디에 해당하는 직책 이름을 반환 
	*/
	String getPosiNm(String posi_id);
	
	List<Position> getAllPosition();


}
