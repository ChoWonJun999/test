package kr.or.ddit.smartware.employee.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.smartware.employee.model.Position;

@Repository
public class PositionDao implements IPositionDao {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	/**
	* Method : getPosiNm
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param posi_id
	* @return
	* Method 설명 : 직책 아이디에 해당하는 직책 이름을 반환
	*/
	@Override
	public String getPosiNm(String posi_id) {
		return sqlSession.selectOne("position.getPosiNm", posi_id);
	}

	@Override
	public List<Position> getAllPosition() {
		return sqlSession.selectList("position.getAllPosition");
	}

}
