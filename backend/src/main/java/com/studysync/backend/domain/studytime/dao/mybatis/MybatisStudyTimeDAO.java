package com.studysync.backend.domain.studytime.dao.mybatis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.studysync.backend.domain.studytime.dao.StudyTimeDAO;
import com.studysync.backend.domain.studytime.model.StudyTime;
import com.studysync.backend.dto.StudyRankDto;

@Repository
public class MybatisStudyTimeDAO implements StudyTimeDAO{
	
	private StudyTimeDAO mapper;
	
	public MybatisStudyTimeDAO(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(StudyTimeDAO.class);
	}

	@Override
	public int upsertStudyTime(StudyTime studyTime) {
		return mapper.upsertStudyTime(studyTime);
	}

	@Override
	public StudyTime getLatestStudyTimeByUserAndDate(String userId, LocalDate date) {
		return mapper.getLatestStudyTimeByUserAndDate(userId, date);
	}

	@Override
	public List<StudyRankDto> getStudyRanking(int groupId, LocalDate date) {
		return mapper.getStudyRanking(groupId, date);
	}

	@Override
	public List<Map<String, Object>> getMaxStudyTimeByDateInGroup(int groupId) {
		return mapper.getMaxStudyTimeByDateInGroup(groupId);
	}

	@Override
	public List<Map<String, Object>> getMyStudyTimes(String userId) {
		return mapper.getMyStudyTimes(userId);
	}

}
