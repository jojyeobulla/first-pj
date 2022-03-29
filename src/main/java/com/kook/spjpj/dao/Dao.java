package com.kook.spjpj.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kook.spjpj.dto.JoinDto;

public class Dao implements IDao {
//	Mybatis를 이용하여 DAO를 구현하려면 SqlSession 객체가 필요 (참고: https://heewon26.tistory.com/2)	
	@Autowired 
	private SqlSession sqlSession;
	
	@Override
	public String join(JoinDto dto) {
		int res = sqlSession.insert("join", dto); //mapper.xml호출
		System.out.println(res);
		String result = null;
		if(res > 0)
			result = "success";
		else
			result = "failed";
		
		return result;
	}

	//======login======
//	초기세팅에서 아래 거 없으면 에러나서 자동 생성 해놓은 것. (security부분 때문인 듯)
	@Override
	public JoinDto login(String bid) {
		System.out.println(bid);
		JoinDto result = sqlSession.selectOne("login",bid);
		return result;
	}
}
