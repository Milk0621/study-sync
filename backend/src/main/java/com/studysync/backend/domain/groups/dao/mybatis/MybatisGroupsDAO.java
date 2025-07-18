package com.studysync.backend.domain.groups.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.studysync.backend.domain.groups.dao.GroupsDAO;
import com.studysync.backend.domain.groups.model.Groups;

@Repository
public class MybatisGroupsDAO implements GroupsDAO{
	
	private GroupsDAO mapper;
	
	public MybatisGroupsDAO(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(GroupsDAO.class);
	}

	@Override
	public int insertGroup(Groups groups) {
		return mapper.insertGroup(groups);
	}

	@Override
	public List<Groups> selectAllGroups() {
		return mapper.selectAllGroups();
	}

	@Override
	public Groups selectOneGroup(int id) {
		return mapper.selectOneGroup(id);
	}

	@Override
	public int updateGroup(Groups groups) {
		return mapper.updateGroup(groups);
	}

	@Override
	public List<Groups> searchGroups(String search, String category) {
		return mapper.searchGroups(search, category);
	}

	@Override
	public List<Groups> selectUserGroups(String userId) {
		return mapper.selectUserGroups(userId);
	}
	
}
