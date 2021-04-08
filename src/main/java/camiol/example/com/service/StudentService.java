package camiol.example.com.service;

import java.util.List;

import camiol.example.com.entity.Student;

public interface StudentService {
	//查詢單一學生資料
	Student findById(long id);

	//查詢所有學生資料
	List<Student> findAll();

	//新增或更新學生資料
	void saveOrUpdate(Student s);
	
	//刪除學生資料
	void delete(long id);

}
