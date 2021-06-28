package camiol.example.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import camiol.example.com.entity.Student;
import camiol.example.com.service.StudentService;
import camiol.example.com.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "學生相關操作")
@RestController	
@RequestMapping(value = "/student", produces = "application/json")
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	@ApiOperation(value = "取得指定學生資料" , notes = "取得指定學生資料")
	@ApiResponses({
		@ApiResponse(responseCode = "200" ,description = "成功"),
		@ApiResponse(responseCode = "404" ,description = "找不到網頁"),
		@ApiResponse(responseCode = "500" ,description = "其他錯誤")
	})
	@GetMapping("/get")
	public ResponseVo getStudent(@RequestParam("id") long id) {
		ResponseVo result = new ResponseVo();
		Student s = service.findById(id);
		if(s!=null) {
			result.setResult(s);
			result.setMessage("Query Success!");
			result.setRcode("0000");
		}else {
			result.setMessage("No Data!");
			result.setRcode("0301");
		}
		return result;
	}
	
	@ApiOperation(value = "取得所有學生資料" , notes = "取得所有學生資料" )
	@ApiResponses({
		@ApiResponse(responseCode = "200" ,description = "成功"),
		@ApiResponse(responseCode = "404" ,description = "找不到網頁"),
		@ApiResponse(responseCode = "500" ,description = "其他錯誤")
	})
	@GetMapping("/getAll")
	public ResponseVo getAllStudent() {
		ResponseVo result = new ResponseVo();
		List<Student> list = service.findAll();
		if(list!=null && !list.isEmpty()) {
			result.setResult(list);
			result.setMessage("Query Success!");
			result.setRcode("0000");
		}else {
			result.setMessage("No Data!");
			result.setRcode("0301");
		}
		return result;
	}
	
	@ApiOperation(value = "新增學生資料" , notes = "新增學生資料" )
	@ApiResponses({
		@ApiResponse(responseCode = "200" ,description = "成功"),
		@ApiResponse(responseCode = "404" ,description = "找不到網頁"),
		@ApiResponse(responseCode = "500" ,description = "其他錯誤")
	})
	@PostMapping("/add")
	public ResponseVo saveStudent(@RequestParam("name") String name,@RequestParam("mathScore") int mathScore) {
		ResponseVo result = new ResponseVo();
		Student s = new Student();
		s.setName(name);
		s.setMathScore(mathScore);
		service.saveOrUpdate(s);
		
		result.setMessage("Insert Success!");
		result.setRcode("0000");
		return result;
	}
	
	@ApiOperation(value = "更新學生資料" , notes = "更新學生資料" )
	@ApiResponses({
		@ApiResponse(responseCode = "200" ,description = "成功"),
		@ApiResponse(responseCode = "404" ,description = "找不到網頁"),
		@ApiResponse(responseCode = "500" ,description = "其他錯誤")
	})
	@PutMapping("/update")
	public ResponseVo updateStudent(@RequestParam("id") long id,@RequestParam("name") String name,@RequestParam("mathScore") int mathScore) {
		ResponseVo result = new ResponseVo();
		Student bean = service.findById(id);
		if(bean!=null && bean.getId()>0) {
			bean.setName(name);
			bean.setMathScore(mathScore);
			service.saveOrUpdate(bean);
			
			result.setMessage("Update Success!");
			result.setRcode("0000");
		}else {
			result.setMessage("Update Fail! No Data!");
			result.setRcode("0301");
		}
		return result;
	}
	
	@ApiOperation(value = "刪除學生資料" , notes = "刪除學生資料" )
	@ApiResponses({
		@ApiResponse(responseCode = "200" ,description = "成功"),
		@ApiResponse(responseCode = "404" ,description = "找不到網頁"),
		@ApiResponse(responseCode = "500" ,description = "其他錯誤")
	})
	@DeleteMapping("/delete")
	public ResponseVo deleteStudent(@RequestParam("id") long id) {
		ResponseVo result = new ResponseVo();
		Student bean = service.findById(id);
		if(bean!=null && bean.getId()>0) {
			service.delete(id);
			
			result.setMessage("Delete Success!");
			result.setRcode("0000");
		}else {
			result.setMessage("Delete Fail! No Data!");
			result.setRcode("0301");
		}
		return result;
	}
}
