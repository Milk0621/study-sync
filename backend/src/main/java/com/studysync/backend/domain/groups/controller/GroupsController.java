package com.studysync.backend.domain.groups.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studysync.backend.domain.groups.model.Groups;
import com.studysync.backend.domain.groups.service.GroupsService;
import com.studysync.backend.dto.GroupPageResponse;
import com.studysync.backend.dto.GroupPasswordCheckRequest;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {
	
	private final GroupsService groupsService;
	
	@Autowired
	public GroupsController(GroupsService groupsService) {
		this.groupsService = groupsService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerGroup(@RequestBody Groups groups) {
		int result = groupsService.registerGroup(groups);
		return result > 0
				? ResponseEntity.ok("그룹 생성 완료")
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("그룹 생성 실패");
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> searchGroups(
			@RequestParam(required = false) String search, 
			@RequestParam(required = false) String category, 
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size
			){
		System.out.println("search: " + search + ", category: " + category + ", page: " + page + ", size: " + size);
		GroupPageResponse result = groupsService.getGroups(search, category, page, size);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity<?> getGroupById(@PathVariable int id) {
		//@RequestParam는 쿼리 파라미터 형식에 사용 /post?id=1
		//@PathVariable은 경로 파라미터 형식에 사용 /post/1
		return ResponseEntity.ok(groupsService.getGroupById(id));
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateGroup(@RequestBody Groups groups) {
		int result = groupsService.updateGroup(groups);
		return result > 0
				? ResponseEntity.ok("그룹 수정 완료")
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("그룹 수정 실패");
	}
	
	
	@GetMapping("/my")
	public ResponseEntity<?> getMyGroups(@RequestParam String userId) {
		List<Groups> groups = groupsService.getMyGroups(userId);
		return ResponseEntity.ok(groups);
	}
	
	@PutMapping("/post/{id}/views")
	public ResponseEntity<?> increaseViewCount(@PathVariable Long id){
		try {
			groupsService.increaseViewCount(id);
			return ResponseEntity.ok().body("조회수 증가 완료");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회수 증가 실패");
		}
	}
	
	@PostMapping("/check-password")
	public ResponseEntity<?> checkPassword(@RequestBody GroupPasswordCheckRequest request) {
		boolean result = groupsService.checkGroupPassword(request.getGroupId(), request.getPassword());
		return ResponseEntity.ok(result);
	}
}
