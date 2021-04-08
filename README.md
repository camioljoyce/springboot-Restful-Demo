# 建立一個Restful API的專案

[![hackmd-github-sync-badge](https://hackmd.io/iQp4KS1CTXWauL-EOdWXmg/badge)](https://hackmd.io/iQp4KS1CTXWauL-EOdWXmg)


**首先按照之前做的這篇, 建立好基本的spring boot配置**
- [建立一個SpringBoot + Spring + JPA 的Web專案](https://hackmd.io/i3T9xRyQR0OOVczCQmtkZQ)

設定好後,開始加入下列的class和interface

今天預計要做CRUD四個動作,一樣拿之前的Student來當範例
**今天要加入的class和interface如下圖:**
![](https://i.imgur.com/IfvDhXo.jpg)

在Entity層,一樣加入Student
```java=
package camiol.example.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="Name")
	private String name;
	@Column(name="Math_Score")
	private int mathScore;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMathScore() {
		return mathScore;
	}
	public void setMathScore(int mathScore) {
		this.mathScore = mathScore;
	}
	
}
```

另外在vo層, 加上ResponseVo 用來回傳訊息
```java=
package camiol.example.com.vo;

public class ResponseVo {
	private Object result;
	private String message;
	private String rcode;
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRcode() {
		return rcode;
	}
	public void setRcode(String rcode) {
		this.rcode = rcode;
	}
}
```

在dao層,一樣加上StudentDao extends JpaRepository
```java=
package camiol.example.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import camiol.example.com.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long>{

}
```

在Service層, 我們加上CRUD的功能
```java=
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
```

在service.impl層, 我們實作CRUD功能
```java=
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
```

最後在Controller層,要設定好restful api對應的CRUD
並且加上responseVo來回傳結果
```java=
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

@RestController	
@RequestMapping(value = "/student", produces = "application/json")
public class StudentController {
	
	@Autowired
	private StudentService service;
	
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
	@PutMapping("/update")
	public ResponseVo updateStudent(@RequestParam("id") long id,@RequestParam("name") String name,@RequestParam("mathScore") int mathScore) {
		ResponseVo result = new ResponseVo();
		Student bean = service.findById(id);
		if(bean!=null) {
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
	@DeleteMapping("/delete")
	public ResponseVo deleteStudent(@RequestParam("id") long id) {
		ResponseVo result = new ResponseVo();
		Student bean = service.findById(id);
		if(bean!=null) {
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
```

設定好後, 我們將專案啟動,用SoapUI 或 Postman來測試
**以下使用SoapUI來測試:**
先設定好REST來呼叫剛寫的api
![](https://i.imgur.com/ggab2gG.jpg)

**開始一條條測試:**
**1.查詢所有學生資料**
![](https://i.imgur.com/BIzniAr.jpg)

**2.查詢指定學生資料**
![](https://i.imgur.com/feqVjlm.jpg)

**3.新增學生資料**
![](https://i.imgur.com/NmF4U3o.jpg)
**可以看到新增成功,這時再去查詢所有學生,可以看到剛新增的學生資料**
![](https://i.imgur.com/2u7YNlz.jpg)

**4.更新指定學生資料**
![](https://i.imgur.com/qkOZvso.jpg)
**可以看到更新成功,這時再去查詢所有學生,可以看到剛更新的7號學生資料**
![](https://i.imgur.com/Fiv3Vzg.jpg)

**4.刪除指定學生資料**
![](https://i.imgur.com/gyjjnMA.jpg)
**可以看到刪除成功,這時再去查詢所有學生,可以看到剛刪除的14號學生資料已不存在**
![](https://i.imgur.com/DYuKCoQ.jpg)

**以上就是基本的Restful API的專案建立**

###### tags: `Spring boot` `Restful`

