package camiol.example.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camiol.example.com.dao.StudentDao;
import camiol.example.com.entity.Student;
import camiol.example.com.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentDao dao;
	
	@Override
	public Student findById(long id) {
		return dao.findById(id).orElse(new Student());
	}

	@Override
	public List<Student> findAll() {
		return dao.findAll();
	}

	@Override
	public void saveOrUpdate(Student s) {
		//如果有主鍵就更新該學生資料, 無主鍵的話就新增
		dao.save(s);
	}

	@Override
	public void delete(long id) {
		dao.deleteById(id);
	}

}
