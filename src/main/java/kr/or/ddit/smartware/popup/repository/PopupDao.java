package kr.or.ddit.smartware.popup.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.smartware.popup.model.Popup;

@Repository
public class PopupDao implements IPopupDao{

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	/**
	* Method : getPagePopupList
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @return
	* Method 설명 : 팝업 페이징 리스트 조회
	*/
	@Override
	public List<Popup> getPagePopupList(Map<String, Integer> map) {
		return sqlSession.selectList("popup.getPagePopupList", map);
	}

	/**
	* Method : getAllPopupList
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @return
	* Method 설명 : 전체 팝업 리스트 조회
	*/
	@Override
	public List<Popup> getAllPopupList() {
		return sqlSession.selectList("popup.getAllPopupList");
	}

	/**
	* Method : insertPopup
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param popup
	* @return
	* Method 설명 : 팝업 등록
	*/
	@Override
	public int insertPopup(Popup popup) {
		return sqlSession.insert("popup.insertPopup", popup);
	}

	/**
	* Method : deletePopup
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param pop_id
	* @return
	* Method 설명 : 팝업 정보 삭제
	*/
	@Override
	public int deletePopup(String pop_id) {
		return sqlSession.update("popup.deletePopup", pop_id);
	}

	/**
	* Method : getPopup
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param pop_id
	* @return
	* Method 설명 : 선택 팝업정보 조회
	*/
	@Override
	public Popup getPopup(String pop_id) {
		return sqlSession.selectOne("popup.getPopup", pop_id);
	}

	/**
	* Method : updatePopup
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param popup
	* @return
	* Method 설명 : 팝업 정보 수정
	*/
	@Override
	public int updatePopup(Popup popup) {
		return sqlSession.update("popup.updatePopup", popup);
	}

	/**
	* Method : insertPopupNoLook
	* 작성자 : JEON MIN GYU
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 팝업 오늘만 보지 않기
	*/
	@Override
	public int insertPopupNoLook(Map map) {
		return sqlSession.insert("popup.insertPopupNoLook", map);
	}

	@Override
	public List<Map> getPopupNoLook(String emp_id) {
		return sqlSession.selectList("popup.getPopupNoLook", emp_id);
	}

	@Override
	public int updatePopupNoLook(Map map) {
		return sqlSession.update("popup.updatePopupNoLook", map);
	}

}
